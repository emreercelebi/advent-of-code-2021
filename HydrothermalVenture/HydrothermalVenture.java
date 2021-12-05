package HydrothermalVenture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HydrothermalVenture {
  private static final String FILE_NAME = "input.txt";

  File file;
  List<PointPair> pointPairs;

  public HydrothermalVenture() {
    URL path = HydrothermalVenture.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.pointPairs = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Hydrothermal Venture part 1: " + part1Result);
    System.out.println("Hydrothermal Venture part 2: " + part2Result);
  }

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;
    while ((line = br.readLine()) != null) {
      String[] points = line.split(" -> ");
      String[] p1 = points[0].split(",");
      String[] p2 = points[1].split(",");

      this.pointPairs.add(
        new PointPair(
          new Point(
            Integer.parseInt(p1[0]), Integer.parseInt(p1[1])
          ),
          new Point(
            Integer.parseInt(p2[0]), Integer.parseInt(p2[1])
          )
        )
      );
    }
  }

  private int runPart1() {
    Map<Integer, Map<Integer, Integer>> xToYFreq = new HashMap<>();

    for (PointPair pair : this.pointPairs) {
      //only consider horizontal and vertical lines
      if (!(pair.first().x() == pair.second().x() || pair.first().y() == pair.second().y())) {
        continue;
      }

      int x1 = pair.first().x();
      int y1 = pair.first().y();
      int x2 = pair.second().x();
      int y2 = pair.second().y();

      if (x1 == x2) {
        int x = x1;
        if (!xToYFreq.containsKey(x)) {
          xToYFreq.put(x, new HashMap<>());
        }
        Map<Integer, Integer> yFreq = xToYFreq.get(x1);
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
          yFreq.put(y, yFreq.getOrDefault(y, 0) + 1);
        }
      } else { // y1 == y2
        int y = y1;
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
          if (!xToYFreq.containsKey(x)) {
            Map<Integer, Integer> newYFreq = new HashMap<>();
            newYFreq.put(y, 1);
            xToYFreq.put(x, newYFreq);
          } else {
            Map<Integer, Integer> yFreq = xToYFreq.get(x);
            yFreq.put(y, yFreq.getOrDefault(y, 0) + 1);
          }
        }
      }
    }

    int result = 0;
    for (Map<Integer, Integer> yFreq : xToYFreq.values()) {
      for (int freq : yFreq.values()) {
        if (freq > 1) {
          result++;
        }
      }
    }
    return result;
  }

  private int runPart2() {
    Map<Integer, Map<Integer, Integer>> xToYFreq = new HashMap<>();

    for (PointPair pair : this.pointPairs) {
      int x1 = pair.first().x();
      int y1 = pair.first().y();
      int x2 = pair.second().x();
      int y2 = pair.second().y();

      if (x1 == x2) {
        int x = x1;
        if (!xToYFreq.containsKey(x)) {
          xToYFreq.put(x, new HashMap<>());
        }
        Map<Integer, Integer> yFreq = xToYFreq.get(x);
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
          yFreq.put(y, yFreq.getOrDefault(y, 0) + 1);
        }
      } else if (y1 == y2) {
        int y = y1;
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
          if (!xToYFreq.containsKey(x)) {
            Map<Integer, Integer> newYFreq = new HashMap<>();
            newYFreq.put(y, 1);
            xToYFreq.put(x, newYFreq);
          } else {
            Map<Integer, Integer> yFreq = xToYFreq.get(x);
            yFreq.put(y, yFreq.getOrDefault(y, 0) + 1);
          }
        }
      } else { //diagonal
        int leftX = x1 < x2 ? x1 : x2;
        int leftY = x1 < x2 ? y1 : y2;
        int rightX = leftX == x1 ? x2 : x1;
        int rightY = leftY == y1 ? y2 : y1;
        boolean leftIsLower = leftY < rightY;
        int x = leftX;
        int y = leftY;
        while (x <= rightX && ((leftIsLower && y <= rightY) || (!leftIsLower && y >= rightY))) {
          if (!xToYFreq.containsKey(x)) {
            xToYFreq.put(x, new HashMap<>());
          }
          Map<Integer, Integer> yFreq = xToYFreq.get(x);
          yFreq.put(y, yFreq.getOrDefault(y, 0) + 1);
          x++;
          y += leftIsLower ? 1 : -1;
        }
      }
    }

    int result = 0;
    for (Map<Integer, Integer> yFreq : xToYFreq.values()) {
      for (int freq : yFreq.values()) {
        if (freq > 1) {
          result++;
        }
      }
    }
    return result;
  }
}
