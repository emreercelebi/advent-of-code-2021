package HydrothermalVenture;

public class PointPair {
  private Point first;
  private Point second;

  public PointPair(Point first, Point second) {
    this.first = first;
    this.second = second;
  }

  public Point first() {
    return this.first;
  }

  public Point second() {
    return this.second;
  }

  @Override
  public String toString() {
    return first.toString() + " -> " + second.toString() + "\n";
  }
}
