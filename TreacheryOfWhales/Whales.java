package TreacheryOfWhales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Whales {
  private static final String FILE_NAME = "input.txt";

  File file;
  List<Integer> crabs;

  public Whales() {
    URL path = Whales.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.crabs = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Whales part 1: " + part1Result);
    System.out.println("Lanternfish part 2: " + part2Result);
  }

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line = br.readLine();

    for (String num : line.split(",")) {
      this.crabs.add(Integer.parseInt(num));
    }
  }

  private int runPart1() {
    int min = this.getMin();
    int max = this.getMax();

    int minTotal = Integer.MAX_VALUE;

    for (int i = min; i <= max; i++) {
      int total = 0;
      for (int crab : this.crabs) {
        total += Math.abs(i - crab);
      }
      minTotal = Math.min(total, minTotal);
    }

    return minTotal;
  }

  private int runPart2() {
    int min = this.getMin();
    int max = this.getMax();

    int minTotal = Integer.MAX_VALUE;

    for (int i = min; i <= max; i++) {
      int total = 0;
      for (int crab : this.crabs) {
        int diff = Math.abs(i - crab);
        total += (diff * (diff + 1)) / 2;
      }
      minTotal = Math.min(total, minTotal);
    }

    return minTotal;
  }

  private int getMin() {
    int min = Integer.MAX_VALUE;

    for (int crab : this.crabs) {
      min = Math.min(min, crab);
    }

    return min;
  }

  private int getMax() {
    int max = Integer.MIN_VALUE;

    for (int crab : this.crabs) {
      max = Math.max(max, crab);
    }

    return max;
  }
}
