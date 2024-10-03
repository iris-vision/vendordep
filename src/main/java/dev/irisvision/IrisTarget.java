package dev.irisvision;

import dev.irisvision.struct.IrisTargetStruct;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.struct.StructSerializable;

public class IrisTarget implements StructSerializable {
    int id;
    Transform3d primaryTransform;
    double primaryReprojError;
    Transform3d secondaryTransform;
    double secondaryReprojError;
    Rotation2d angleOffsetX;
    Rotation2d angleOffsetY;

    public static final IrisTargetStruct struct = new IrisTargetStruct();

    public IrisTarget(
            int id,
            Transform3d primaryTransform,
            double primaryReprojError,
            Transform3d secondaryTransform,
            double secondaryReprojError,
            Rotation2d angleOffsetX,
            Rotation2d angleOffsetY) {
        this.id = id;
        this.primaryTransform = primaryTransform;
        this.primaryReprojError = primaryReprojError;
        this.secondaryTransform = secondaryTransform;
        this.secondaryReprojError = secondaryReprojError;
        this.angleOffsetX = angleOffsetX;
        this.angleOffsetY = angleOffsetY;
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
}
