package PassagePathing;

import java.util.HashSet;
import java.util.Set;

public class Node {
  String name;
  Set<Node> neighbors;
  boolean isSmall;
  boolean isStart;
  boolean isEnd;
  int visitCount;

  public Node(String name) {
    this.name = name;
    this.neighbors = new HashSet<>();
    this.isStart = name.equals("start");
    this.isEnd = name.equals("end");
    this.isSmall = !this.isStart && !this.isEnd && Character.isLowerCase(name.charAt(0));
    this.visitCount = 0;
  }

  public boolean shouldOnlyVisitOnce() {
    return this.isStart || this.isEnd || this.isSmall;
  }

  public boolean isStart() {
    return this.isStart;
  }

  public boolean isEnd() {
    return this.isEnd;
  }

  public boolean isSmall() {
    return this.isSmall;
  }

  public Set<Node> getNeighbors() {
    return this.neighbors;
  }

  public void addNeighbor(Node n) {
    this.neighbors.add(n);
  }

  public void visit() {
    this.visitCount++;
  }

  public void unVisit() {
    this.visitCount--;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
