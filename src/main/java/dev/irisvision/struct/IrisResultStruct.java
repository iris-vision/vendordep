package dev.irisvision.struct;

import dev.irisvision.IrisResult;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;

public class IrisResultStruct implements Struct<IrisResult> {
    @Override
    public Class<IrisResult> getTypeClass() {
        return IrisResult.class;
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
    public IrisResult unpack(ByteBuffer bb) {
        Pose3d primaryPose = Pose3d.struct.unpack(bb);
        double primaryReprojError = bb.getDouble();
        Pose3d secondaryPose = Pose3d.struct.unpack(bb);
        double secondaryReprojError = bb.getDouble();
        return new IrisResult(primaryPose, primaryReprojError, secondaryPose, secondaryReprojError);
    }

    @Override
    public void pack(ByteBuffer bb, IrisResult value) {
        Pose3d.struct.pack(bb, value.getPrimaryPose());
        bb.putDouble(value.getPrimaryReprojError());
        Pose3d.struct.pack(bb, value.getSecondaryPose());
        bb.putDouble(value.getSecondaryReprojError());
    }
}
