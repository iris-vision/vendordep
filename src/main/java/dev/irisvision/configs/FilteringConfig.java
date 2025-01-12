package dev.irisvision.configs;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public class FilteringConfig {
  public double maxHeight = Double.POSITIVE_INFINITY;
  public double minHeight = Double.NEGATIVE_INFINITY;
  public double maxTilt = Math.PI * 2;
  public double resultValidTime = Double.POSITIVE_INFINITY;
  public SortingMethod sortingMethod = SortingMethod.LOWEST_REPROJECTION_ERROR;

  public FilteringConfig() {}

  public enum SortingMethod {
    LOWEST_REPROJECTION_ERROR,
    CLOSEST_TO_CAMERA_HEIGHT,
    // TODO: implement
    // LOWEST_TILT,
  }

  public FilteringConfig withMaxHeight(double maxHeight) {
    this.maxHeight = maxHeight;
    return this;
  }

  public FilteringConfig withMaxHeight(Distance maxHeight) {
    this.maxHeight = maxHeight.in(Meters);
    return this;
  }

  public FilteringConfig withMinHeight(double minHeight) {
    this.minHeight = minHeight;
    return this;
  }

  public FilteringConfig withMinHeight(Distance minHeight) {
    this.minHeight = minHeight.in(Meters);
    return this;
  }

  public FilteringConfig withMaxTilt(double maxTilt) {
    this.maxTilt = maxTilt;
    return this;
  }

  public FilteringConfig withMaxTilt(Angle maxTilt) {
    this.maxTilt = maxTilt.in(Radians);
    return this;
  }

  public FilteringConfig withResultValidTime(double resultValidTime) {
    this.resultValidTime = resultValidTime;
    return this;
  }

  public FilteringConfig withResultValidTime(Time resultValidTime) {
    this.resultValidTime = resultValidTime.in(Seconds);
    return this;
  }

  public FilteringConfig withSortingMethod(SortingMethod sortingMethod) {
    this.sortingMethod = sortingMethod;
    return this;
  }
}
