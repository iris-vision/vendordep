package dev.irisvision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.*;
import java.util.*;

public class IrisCamera {
  private static final String kRootTableName = "iris";
  private static final NetworkTable kRootTable =
      NetworkTableInstance.getDefault().getTable(kRootTableName);

  private final NetworkTableInstance cameraInstance;
  private final NetworkTable cameraOptionsTable;
  private final NetworkTable publishedResultsTable;
  private final String deviceName;

  private final StructSubscriber<IrisResult> result;
  private final StructArraySubscriber<IrisTarget> targets;

  private Transform3d cameraToRobot;
  private Map<Integer, TimestampedObject<IrisTarget>> latestTargetsMap = new HashMap<>();

  /**
   * Constructs a new {@link IrisCamera}
   *
   * @param deviceName The device id of the perception client
   * @param robotToCamera The 3d transform of the camera using the robot as a base frame
   */
  public IrisCamera(String deviceName, Transform3d robotToCamera) {
    this.deviceName = deviceName;
    this.cameraToRobot = robotToCamera.inverse();

    cameraInstance = NetworkTableInstance.create();
    cameraInstance.setServer(deviceName + ".local");
    cameraInstance.startClient4("rio");
    cameraOptionsTable = cameraInstance.getTable("/");

    publishedResultsTable = kRootTable.getSubTable(this.deviceName);
    result = publishedResultsTable.getStructTopic("cameraPose", IrisResult.struct).subscribe(null);
    targets =
        publishedResultsTable
            .getStructArrayTopic("targets", IrisTarget.struct)
            .subscribe(new IrisTarget[0]);
  }

  /**
   * Constructs a new {@link IrisCamera} using the default {@link NetworkTableInstance} and device
   * name.
   *
   * @param deviceName The device id of the perception client
   */
  // TODO: Remove?
  public IrisCamera(String deviceName) {
    this(deviceName, new Transform3d());
  }

  /**
   * Sets the transform from the camera base frame to the robot
   *
   * @param cameraToRobot The transform to set.
   */
  public void setCameraToRobotTransform(Transform3d cameraToRobot) {
    this.cameraToRobot = cameraToRobot;
  }

  /**
   * Sets the transform from the robot base frame to the camera
   *
   * @param robotToCamera The transform to set.
   */
  public void setRobotToCameraTransform(Transform3d robotToCamera) {
    this.cameraToRobot = robotToCamera.inverse();
  }

  /**
   * Retrieves the frames per second (FPS) that the device is currently processing.
   *
   * @return The current processing FPS
   */
  public double getProcessingFPS() {
    return publishedResultsTable.getEntry("fps").getDouble(0d);
  }

  /**
   * Retrieves the total amount of time the device has been operational since it was last started or
   * restarted.
   *
   * @return The uptime of the camera, in seconds
   */
  public double getUptime() {
    return cameraOptionsTable.getEntry("uptime").getInteger(0);
  }

  /**
   * Retrieves the version of the device software. Defaults to {@code "Unknown"} If the version
   * information is not available.
   *
   * @return The device version as a String.
   */
  public String getDeviceVersion() {
    return publishedResultsTable.getEntry("version").getString("Unknown");
  }

  /**
   * Retrieves unread {@link IrisResult} packets sent over NetworkTables. Use this method instead of
   * {@link #getLatestPose()} or {@link #getUnreadPoses()} when custom filtering of results is
   * required.
   *
   * @return A list of timestamped {@link IrisResult} objects received since the last time this
   *     method was called
   */
  public List<TimestampedObject<IrisResult>> getRawUnfilteredResults() {
    return Arrays.stream(result.readQueue())
        .peek(r -> r.value.setTransform(cameraToRobot))
        .toList();
  }

  // TODO: add filtering logic
  /**
   * Retrieves the last estimated 3D pose from the camera
   *
   * @return A timestamped {@link Pose3d} representing the robot's position on the field. {@link
   *     Optional#empty()} if no AprilTags are seen.
   */
  public Optional<TimestampedObject<Pose3d>> getLatestPose() {
    return Optional.ofNullable(result.getAtomic(null))
        .map(r -> new TimestampedObject<>(r.timestamp, r.serverTime, r.value.getPrimaryPose()));
  }

  /**
   * Retrieves all unread estimated 3D Poses from the camera
   *
   * @return A list of timestamped {@link Pose3d} representing the robot's position on the field.
   */
  public List<TimestampedObject<Pose3d>> getUnreadPoses() {
    return Arrays.stream(result.readQueue())
        .peek(r -> r.value.setTransform(cameraToRobot))
        .map(r -> new TimestampedObject<>(r.timestamp, r.serverTime, r.value.getPrimaryPose()))
        .toList();
  }

  public TimestampedObject<IrisTarget[]> getTargets() {
    return targets.getAtomic();
  }
}
