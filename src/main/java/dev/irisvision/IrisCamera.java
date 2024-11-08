package dev.irisvision;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.*;

import java.util.*;

public class IrisCamera {
    private static final String kRootTableName = "Iris";

    private final StructSubscriber<IrisResult> result;
    private final StructArraySubscriber<IrisTarget> targets;

    private final NetworkTable cameraTable;
    private final String deviceName;

    private Transform3d cameraToRobot;
    private Map<Integer, TimestampedObject<IrisTarget>> latestTargetsMap = new HashMap<>();

    /**
     * Constructs a new IrisCamera on a specified NetworkTableInstance and device name.
     *
     * @param instance The NetworkTableInstance to use for NetworkTables interactions
     * @param deviceName The device id of the perception client
     */
    public IrisCamera(NetworkTableInstance instance, String deviceName, Transform3d cameraToRobot) {
        this.deviceName = deviceName;
        this.cameraToRobot = cameraToRobot;
        var perceptionRootTable = instance.getTable(kRootTableName);
        cameraTable = perceptionRootTable.getSubTable(this.deviceName);
        result = cameraTable.getStructTopic("cameraPose", IrisResult.struct).subscribe(null);
        targets =
                cameraTable.getStructArrayTopic("targets", IrisTarget.struct).subscribe(new IrisTarget[0]);
    }

    /**
     * Constructs a new IrisCamera using the default NetworkTableInstance and device name.
     *
     * @param deviceName The device id of the perception client
     */
    public IrisCamera(String deviceName) {
        this(NetworkTableInstance.getDefault(), deviceName, new Transform3d());
    }

    public IrisCamera(String deviceName, Transform3d cameraToRobot) {
        this(NetworkTableInstance.getDefault(), deviceName, cameraToRobot);
    }

    /**
     * Sets the transform from the camera to the robot
     *
     * @param cameraToRobot The transform to set.
     */
    public void setTransform(Transform3d cameraToRobot) {
        this.cameraToRobot = cameraToRobot;
    }

    public List<TimestampedObject<IrisResult>> getPoseObservations() {
        return Arrays.stream(result.readQueue())
                .peek(r -> r.value.setTransform(cameraToRobot))
                .toList();
    }

    public Optional<TimestampedObject<IrisResult>> getLatestResult() {
        return Optional.ofNullable(result.getAtomic(null));
    }

    public Map<Integer, TimestampedObject<IrisTarget>> getTargets() {
        for (TimestampedObject<IrisTarget[]> timestampedObjects : targets.readQueue()) {
            for (IrisTarget target : timestampedObjects.value) {
                latestTargetsMap.put(
                        target.id,
                        new TimestampedObject<>(
                                timestampedObjects.timestamp, timestampedObjects.serverTime, target));
            }
        }
        return latestTargetsMap;
    }

    public Optional<TimestampedObject<IrisTarget>> getTarget(int id) {
        return Optional.ofNullable(getTargets().get(id));
    }
}
