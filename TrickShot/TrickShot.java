package TrickShot;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Helpers.Helpers;

public class TrickShot {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  int xMin;
  int xMax;
  int yMin;
  int yMax;
  int minXVel;
  int maxXVel;

  public TrickShot() {
    URL path = TrickShot.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();
    int part1Result = this.runPart1();
    long part2Result = this.runPart2();

    System.out.println("Trick Shot part 1: " + part1Result);
    System.out.println("Trick Shot part 2: " + part2Result);
  }

  private void init() {
    String s = this.lines.get(0);
    String[] xCoords = s.substring(s.indexOf("x=") + 2, s.indexOf(",")).split("\\.\\.");
    String[] yCoords = s.substring(s.indexOf("y=") + 2).split("\\.\\.");

    xMin = Integer.parseInt(xCoords[0]);
    xMax = Integer.parseInt(xCoords[1]);
    yMin = Integer.parseInt(yCoords[0]);
    yMax = Integer.parseInt(yCoords[1]);

    System.out.println(xMin + ", " + xMax + ", " + yMin + ", " + yMax);

    for (int i = 1; i < xMin; i++) {
      int sum = (i * (i + 1)) / 2;
      if (sum > xMin) {
        this.minXVel = i;
        break;
      }
    }
    this.maxXVel = xMax;
  }

  private int runPart1() {
    int highestY = 0;

    for (int xVel = this.minXVel; xVel <= this.maxXVel; xVel++) {
      for (int yVel = 1; yVel <= 10000; yVel++) {
        highestY = Math.max(highestY, simulateHighestY(xVel, yVel));
      } 
    }

    return highestY;
  }

  private int runPart2() {
    Map<Integer, Set<Integer>> points = new HashMap<>();

    for (int xVel = this.minXVel; xVel <= this.maxXVel; xVel++) {
      for (int yVel = this.yMin; yVel <= 100000; yVel++) {
        simulateCollectAllPoints(xVel, yVel, points);
      } 
    }

    int total = 0;
    for (int xVel : points.keySet()) {
      total += points.get(xVel).size();
    }

    return total;
  }

  private int simulateHighestY(int xVel, int yVel) {
    int highestY = 0;
    int x = 0;
    int y = 0;
    boolean passesThroughTarget = false;

    while (x <= xMax && y >= yMin) {
      highestY = Math.max(y, highestY);
      if (isInTarget(x, y)) {
        passesThroughTarget = true;
      }
      x += xVel;
      y += yVel;
      xVel = Math.max(0, xVel - 1);
      yVel--;
    }

    if (passesThroughTarget) {
      return highestY;
    } else {
      return -1;
    }
  }

  private void simulateCollectAllPoints(int xVel, int yVel, Map<Integer, Set<Integer>> points) {
    int x = 0;
    int y = 0;
    boolean passesThroughTarget = false;
    int xVelOG = xVel;
    int yVelOG = yVel;

    while (x <= xMax && y >= yMin) {
      if (isInTarget(x, y)) {
        passesThroughTarget = true;
        break;
      }
      x += xVel;
      y += yVel;
      xVel = Math.max(0, xVel - 1);
      yVel--;
    }

    if (passesThroughTarget) {
      if (!points.containsKey(xVelOG)) {
        points.put(xVelOG, new HashSet<>());
      }
      points.get(xVelOG).add(yVelOG);
    }
  }

  private boolean isInTarget(int x, int y) {
    return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
  }
}
