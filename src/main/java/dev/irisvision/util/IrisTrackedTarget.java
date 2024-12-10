package dev.irisvision.util;

import dev.irisvision.util.struct.IrisTargetStruct;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.StructSerializable;

public record IrisTrackedTarget(
    int id,
    Transform3d primaryTransform,
    double primaryReprojError,
    Transform3d secondaryTransform,
    double secondaryReprojError,
    TargetAngle center,
    TargetAngle c0,
    TargetAngle c1,
    TargetAngle c2,
    TargetAngle c3)
    implements StructSerializable {
  public static final IrisTargetStruct struct = new IrisTargetStruct();
}
