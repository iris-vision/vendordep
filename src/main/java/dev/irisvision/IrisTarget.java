package dev.irisvision;

import dev.irisvision.struct.IrisTargetStruct;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.struct.StructSerializable;

public class IrisTarget implements StructSerializable {
  public static final IrisTargetStruct struct = new IrisTargetStruct();
  private final int id;
  private final Transform3d primaryTransform;
  private final double primaryReprojError;
  private final Transform3d secondaryTransform;
  private final double secondaryReprojError;
  private final Rotation2d angleOffsetX;
  private final Rotation2d angleOffsetY;
  private final Translation2d[] corners;

  public IrisTarget(
      int id,
      Transform3d primaryTransform,
      double primaryReprojError,
      Transform3d secondaryTransform,
      double secondaryReprojError,
      Rotation2d angleOffsetX,
      Rotation2d angleOffsetY,
      Translation2d[] corners) {
    this.id = id;
    this.primaryTransform = primaryTransform;
    this.primaryReprojError = primaryReprojError;
    this.secondaryTransform = secondaryTransform;
    this.secondaryReprojError = secondaryReprojError;
    this.angleOffsetX = angleOffsetX;
    this.angleOffsetY = angleOffsetY;

    if (corners.length != 4) {
      throw new IllegalArgumentException("Corners array must contain at 4 elements");
    }
    this.corners = corners;
  }

  public int getId() {
    return id;
  }

  public Transform3d getPrimaryTransform() {
    return primaryTransform;
  }

  public double getPrimaryReprojError() {
    return primaryReprojError;
  }

  public Transform3d getSecondaryTransform() {
    return secondaryTransform;
  }

  public double getSecondaryReprojError() {
    return secondaryReprojError;
  }

  public Rotation2d getAngleOffsetX() {
    return angleOffsetX;
  }

  public Rotation2d getAngleOffsetY() {
    return angleOffsetY;
  }

  public Translation2d[] getCorners() {
    return corners;
  }
}
