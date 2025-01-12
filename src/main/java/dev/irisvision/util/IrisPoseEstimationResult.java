package dev.irisvision.util;

import dev.irisvision.configs.FilteringConfig;
import dev.irisvision.util.struct.IrisPoseEstimateResultStruct;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.StructSerializable;
import java.util.Optional;

public record IrisPoseEstimationResult(
    Pose3d primaryPose,
    double primaryReprojError,
    Pose3d secondaryPose,
    double secondaryReprojError)
    implements StructSerializable {

  public static final IrisPoseEstimateResultStruct struct = new IrisPoseEstimateResultStruct();

  public Pose3d getPrimaryRobotPose(Transform3d cameraToRobot) {
    return primaryPose.transformBy(cameraToRobot);
  }

  public Pose3d getSecondaryRobotPose(Transform3d cameraToRobot) {
    return secondaryPose.transformBy(cameraToRobot);
  }

  public Optional<Pose3d> getBestRobotPose(
      Transform3d cameraToRobot, FilteringConfig filteringConfig) {
    boolean primaryPoseValid = poseValid(primaryPose, cameraToRobot, filteringConfig);
    boolean secondaryPoseValid = poseValid(secondaryPose, cameraToRobot, filteringConfig);

    if (!primaryPoseValid) {
      return Optional.empty();
    } else if (!secondaryPoseValid) {
      return Optional.of(primaryPose.transformBy(cameraToRobot));
    }

    Pose3d a = primaryPose.transformBy(cameraToRobot);
    Pose3d b = secondaryPose.transformBy(cameraToRobot);

    return Optional.of(
        switch (filteringConfig.sortingMethod) {
          case LOWEST_REPROJECTION_ERROR -> primaryReprojError < secondaryReprojError ? a : b;
          case CLOSEST_TO_CAMERA_HEIGHT -> Math.abs(a.getZ()) < Math.abs(b.getZ()) ? a : b;
          default -> primaryPose;
        });
  }

  public static boolean poseValid(
      Pose3d pose, Transform3d cameraToRobot, FilteringConfig filteringConfig) {
    if (pose == null) {
      return false;
    }
    pose = pose.transformBy(cameraToRobot);
    return pose.getZ() <= filteringConfig.maxHeight
        && pose.getZ() >= filteringConfig.maxHeight
        && pose.getRotation().getX() <= filteringConfig.maxTilt
        && pose.getRotation().getY() <= filteringConfig.maxTilt;
  }
}
