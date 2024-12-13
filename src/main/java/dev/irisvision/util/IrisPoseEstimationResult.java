package dev.irisvision.util;

import dev.irisvision.util.struct.IrisPoseEstimateResultStruct;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.StructSerializable;

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
}
