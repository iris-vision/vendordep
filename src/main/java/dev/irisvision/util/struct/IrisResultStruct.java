package dev.irisvision.util.struct;

import dev.irisvision.util.IrisPoseEstimationResult;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;

public class IrisResultStruct implements Struct<IrisPoseEstimationResult> {
  @Override
  public Class<IrisPoseEstimationResult> getTypeClass() {
    return IrisPoseEstimationResult.class;
  }

  @Override
  public String getTypeName() {
    return "IrisResult";
  }

  @Override
  public int getSize() {
    return 2 * Pose3d.struct.getSize() + 2 * kSizeDouble;
  }

  @Override
  public String getSchema() {
    return "Pose3d primaryPose;double primaryReprojError;Pose3d secondaryPose;double secondaryReprojError";
  }

  @Override
  public IrisPoseEstimationResult unpack(ByteBuffer bb) {
    Pose3d primaryPose = Pose3d.struct.unpack(bb);
    double primaryReprojError = bb.getDouble();
    Pose3d secondaryPose = Pose3d.struct.unpack(bb);
    double secondaryReprojError = bb.getDouble();
    return new IrisPoseEstimationResult(
        primaryPose, primaryReprojError, secondaryPose, secondaryReprojError);
  }

  @Override
  public void pack(ByteBuffer bb, IrisPoseEstimationResult value) {
    Pose3d.struct.pack(bb, value.primaryPose());
    bb.putDouble(value.primaryReprojError());
    Pose3d.struct.pack(bb, value.secondaryPose());
    bb.putDouble(value.secondaryReprojError());
  }
}
