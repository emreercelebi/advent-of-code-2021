package PassagePathing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
  Map<String, Node> graph;

  public Graph() {
    graph = new HashMap<>();
  }

  public boolean hasNode(String name) {
    return graph.containsKey(name);
  }

  public void addConnection(String entry) {
    String[] parts = entry.split("-");

    String name1 = parts[0];
    String name2 = parts[1];

    Node node1;
    Node node2;

    if (graph.containsKey(name1)) {
      node1 = graph.get(name1);
    } else {
      node1 = new Node(name1);
      graph.put(name1, node1);
    }

    if (graph.containsKey(name2)) {
      node2 = graph.get(name2);
    } else {
      node2 = new Node(name2);
      graph.put(name2, node2);
    }

    node1.addNeighbor(node2);
    node2.addNeighbor(node1);
  }

  public int countPaths() {
    Node start = graph.get("start");
    Node end = graph.get("end");
    Set<Node> visited = new HashSet<>();

    visited.add(start);
    return countPathsHelper(start, end, visited);
  }

  private int countPathsHelper(Node curr, Node end, Set<Node> visited) {
    if (curr == end) {
      return 1;
    }

    int totalPaths = 0;

    for (Node n : curr.getNeighbors()) {
      if (n.shouldOnlyVisitOnce()) {
        if (!visited.contains(n)) {
          visited.add(n);
          totalPaths += countPathsHelper(n, end, visited);
          visited.remove(n);
        }
      } else {
        totalPaths += countPathsHelper(n, end, visited);
      }
    }

    return totalPaths;
  }

  public int countPathsWithOneSmall() {
    Node start = graph.get("start");
    Node end = graph.get("end");
    Set<Node> visited = new HashSet<>();

    visited.add(start);
    return countPathsWithOneSmall(start, end, visited, false);
  }

  private int countPathsWithOneSmall(Node curr, Node end, Set<Node> visited, boolean hasSmall) {
    if (curr == end) {
      return hasSmall ? 1 : 0;
    }

    int totalPaths = 0;

    for (Node n : curr.getNeighbors()) {
      if (n.shouldOnlyVisitOnce()) {
        if (!visited.contains(n)) {
          visited.add(n);
          totalPaths += countPathsWithOneSmall(n, end, visited, hasSmall || n.isSmall());
          visited.remove(n);
        }
      } else {
        totalPaths += countPathsWithOneSmall(n, end, visited, hasSmall || n.isSmall());
      }
    }

    return totalPaths;
  }

  public int countPathsOneSmallCanBeVisitedTwice() {
    Node start = graph.get("start");
    Node end = graph.get("end");
    Set<Node> visited = new HashSet<>();

    visited.add(start);
    return countPathsOneSmallCanBeVisitedTwice(start, end, visited, false);
  }

  private int countPathsOneSmallCanBeVisitedTwice(Node curr, Node end, Set<Node> visited, boolean hasVisitedASmallTwice) {
    if (curr == end) {
      return 1;
    }

    int totalPaths = 0;

    for (Node n : curr.getNeighbors()) {
      if (n.shouldOnlyVisitOnce()) {
        if (!visited.contains(n)) {
          visited.add(n);
          totalPaths += countPathsOneSmallCanBeVisitedTwice(n, end, visited, hasVisitedASmallTwice);
          visited.remove(n);
        } else {
          if (!hasVisitedASmallTwice && !n.isStart() && !n.isEnd()) {
            totalPaths += countPathsOneSmallCanBeVisitedTwice(n, end, visited, true);
          }
        }
      } else {
        totalPaths += countPathsOneSmallCanBeVisitedTwice(n, end, visited, hasVisitedASmallTwice);
      }
    }

    return totalPaths;
  }
}
