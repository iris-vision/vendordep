package dev.irisvision;

import dev.irisvision.struct.IrisResultStruct;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.StructSerializable;

public class IrisResult implements StructSerializable {
  private final Pose3d primaryPose;
  private final double primaryReprojError;
  private final Pose3d secondaryPose;
  private final double secondaryReprojError;

  public Transform3d cameraToRobot;

  public static final IrisResultStruct struct = new IrisResultStruct();

  public IrisResult(
      Pose3d primaryPose,
      double primaryReprojError,
      Pose3d secondaryPose,
      double secondaryReprojError) {
    this.primaryPose = primaryPose;
    this.primaryReprojError = primaryReprojError;
    this.secondaryPose = secondaryPose;
    this.secondaryReprojError = secondaryReprojError;
    cameraToRobot = new Transform3d();
  }

  public void setTransform(Transform3d cameraToRobot) {
    this.cameraToRobot = cameraToRobot;
  }

  public Pose3d getPrimaryPose() {
    return primaryPose.transformBy(cameraToRobot);
  }

  public double getPrimaryReprojError() {
    return primaryReprojError;
  }

  public Pose3d getSecondaryPose() {
    return secondaryPose.transformBy(cameraToRobot);
  }

  public double getSecondaryReprojError() {
    return secondaryReprojError;
  }
}
