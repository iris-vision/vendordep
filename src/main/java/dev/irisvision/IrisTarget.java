package dev.irisvision;

import dev.irisvision.struct.IrisTargetStruct;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N4;
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
  private final Matrix<N4, N2> corners;

  public IrisTarget(
          int id,
          Transform3d primaryTransform,
          double primaryReprojError,
          Transform3d secondaryTransform,
          double secondaryReprojError,
          Rotation2d angleOffsetX,
          Rotation2d angleOffsetY,
          Matrix<N4, N2> corners) {
    this.id = id;
    this.primaryTransform = primaryTransform;
    this.primaryReprojError = primaryReprojError;
    this.secondaryTransform = secondaryTransform;
    this.secondaryReprojError = secondaryReprojError;
    this.angleOffsetX = angleOffsetX;
    this.angleOffsetY = angleOffsetY;
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

  public Matrix<N4, N2> getCorners() {
    return corners;
  }
}
