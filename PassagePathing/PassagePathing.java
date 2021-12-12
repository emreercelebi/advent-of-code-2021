package PassagePathing;

import java.net.URL;
import java.util.List;

import Helpers.Helpers;

public class PassagePathing {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  Graph graph;

  public PassagePathing() {
    URL path = PassagePathing.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);
    this.graph = new Graph();

    this.init();

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Passage Pathing part 1: " + part1Result);
    System.out.println("Passage Pathing part 2: " + part2Result);
  }

  private void init() {
    for (String line : this.lines) {
      this.graph.addConnection(line);
    }
  }

  private int runPart1() {
    return this.graph.countPathsWithOneSmall();
  }

  private int runPart2() {
    return 0;
  }
}
