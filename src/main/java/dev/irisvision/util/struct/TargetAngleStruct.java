package dev.irisvision.util.struct;

import dev.irisvision.util.TargetAngle;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;

public class TargetAngleStruct implements Struct<TargetAngle> {
  @Override
  public Class<TargetAngle> getTypeClass() {
    return TargetAngle.class;
  }

  @Override
  public String getTypeName() {
    return "TargetAngle";
  }

  @Override
  public int getSize() {
    return kSizeDouble * 2;
  }

  @Override
  public String getSchema() {
    return "double x;double y";
  }

  @Override
  public TargetAngle unpack(ByteBuffer byteBuffer) {
    double x = byteBuffer.getDouble();
    double y = byteBuffer.getDouble();
    return new TargetAngle(x, y);
  }

  @Override
  public void pack(ByteBuffer byteBuffer, TargetAngle targetAngle) {
    byteBuffer.putDouble(targetAngle.x());
    byteBuffer.putDouble(targetAngle.y());
  }
}
