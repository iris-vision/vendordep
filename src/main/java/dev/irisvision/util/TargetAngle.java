package dev.irisvision.util;

import dev.irisvision.util.struct.TargetAngleStruct;
import edu.wpi.first.util.struct.StructSerializable;

public record TargetAngle(double x, double y) implements StructSerializable {
  public static final TargetAngleStruct struct = new TargetAngleStruct();
}
