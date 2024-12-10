package dev.irisvision.util.struct;

import dev.irisvision.util.IrisTrackedTarget;
import dev.irisvision.util.TargetAngle;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;

public class IrisTargetStruct implements Struct<IrisTrackedTarget> {
  @Override
  public Class<IrisTrackedTarget> getTypeClass() {
    return IrisTrackedTarget.class;
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
        + 4 * TargetAngle.struct.getSize();
  }

  @Override
  public String getSchema() {
    return "int32 id;Transform3d primaryTransform;double primaryReprojError;Transform3d secondaryTransform;double secondaryReprojError;TargetAngle center;TargetAngle c0;TargetAngle c1;TargetAngle c2;TargetAngle c3";
  }

  @Override
  public IrisTrackedTarget unpack(ByteBuffer bb) {
    int id = bb.getInt();
    Transform3d primaryTransform = Transform3d.struct.unpack(bb);
    double primaryReprojError = bb.getDouble();
    Transform3d secondaryTransform = Transform3d.struct.unpack(bb);
    double secondaryReprojError = bb.getDouble();
    TargetAngle center = TargetAngle.struct.unpack(bb);
    TargetAngle c0 = TargetAngle.struct.unpack(bb);
    TargetAngle c1 = TargetAngle.struct.unpack(bb);
    TargetAngle c2 = TargetAngle.struct.unpack(bb);
    TargetAngle c3 = TargetAngle.struct.unpack(bb);
    return new IrisTrackedTarget(
        id,
        primaryTransform,
        primaryReprojError,
        secondaryTransform,
        secondaryReprojError,
        center,
        c0,
        c1,
        c2,
        c3);
  }

  @Override
  public void pack(ByteBuffer bb, IrisTrackedTarget value) {
    bb.putInt(value.id());
    Transform3d.struct.pack(bb, value.primaryTransform());
    bb.putDouble(value.primaryReprojError());
    Transform3d.struct.pack(bb, value.secondaryTransform());
    bb.putDouble(value.secondaryReprojError());
    TargetAngle.struct.pack(bb, value.center());
    TargetAngle.struct.pack(bb, value.c0());
    TargetAngle.struct.pack(bb, value.c1());
    TargetAngle.struct.pack(bb, value.c2());
    TargetAngle.struct.pack(bb, value.c3());
  }
}
