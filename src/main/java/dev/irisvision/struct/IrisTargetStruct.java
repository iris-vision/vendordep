package dev.irisvision.struct;

import dev.irisvision.IrisTarget;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;

public class IrisTargetStruct implements Struct<IrisTarget> {
  @Override
  public Class<IrisTarget> getTypeClass() {
    return IrisTarget.class;
  }

  @Override
  public String getTypeName() {
    return "IrisTarget";
  }

  @Override
  public int getSize() {
    return kSizeInt32
        + 2 * Transform3d.struct.getSize()
        + 2 * kSizeDouble
        + 2 * Rotation2d.struct.getSize()
        + 4 * Translation2d.struct.getSize();
  }

  @Override
  public String getSchema() {
    return "int32 id;Transform3d primaryTransform;double primaryReprojError;Transform3d secondaryTransform;double secondaryReprojError;Rotation2d angleOffsetX;Rotation2d angleOffsetY;Translation2d c1;Translation2d c2;Translation2d c3;Translation2d c4";
  }

  @Override
  public IrisTarget unpack(ByteBuffer bb) {
    int id = bb.getInt();
    Transform3d primaryTransform = Transform3d.struct.unpack(bb);
    double primaryReprojError = bb.getDouble();
    Transform3d secondaryTransform = Transform3d.struct.unpack(bb);
    double secondaryReprojError = bb.getDouble();
    Rotation2d angleOffsetX = Rotation2d.struct.unpack(bb);
    Rotation2d angleOffsetY = Rotation2d.struct.unpack(bb);
    Translation2d[] corners =
        new Translation2d[] {
          Translation2d.struct.unpack(bb),
          Translation2d.struct.unpack(bb),
          Translation2d.struct.unpack(bb),
          Translation2d.struct.unpack(bb),
        };
    return new IrisTarget(
        id,
        primaryTransform,
        primaryReprojError,
        secondaryTransform,
        secondaryReprojError,
        angleOffsetX,
        angleOffsetY,
        corners);
  }

  @Override
  public void pack(ByteBuffer bb, IrisTarget value) {
    bb.putInt(value.getId());
    Transform3d.struct.pack(bb, value.getPrimaryTransform());
    bb.putDouble(value.getPrimaryReprojError());
    Transform3d.struct.pack(bb, value.getSecondaryTransform());
    bb.putDouble(value.getSecondaryReprojError());
    Rotation2d.struct.pack(bb, value.getAngleOffsetX());
    Rotation2d.struct.pack(bb, value.getAngleOffsetY());
    for (Translation2d c : value.getCorners()) {
      Translation2d.struct.pack(bb, c);
    }
  }
}
