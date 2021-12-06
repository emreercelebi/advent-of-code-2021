package Lanternfish;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Lanternfish {
  private static final String FILE_NAME = "input.txt";

  File file;
  List<Integer> fishList;

  public Lanternfish() {
    URL path = Lanternfish.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.fishList = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    long part1Result = this.runPart1();
    long part2Result = this.runPart2();

    System.out.println("Lanternfish part 1: " + part1Result);
    System.out.println("Lanternfish part 2: " + part2Result);
  }

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line = br.readLine();

    for (String num : line.split(",")) {
      this.fishList.add(Integer.parseInt(num));
    }
  }

  private long runPart1() {
    return simulate(80);
  }

  private long runPart2() {
    return simulate(256);
  }

  private long simulate(int days) {
    Map<Integer, Long> dayMap = new HashMap<>();
    for (int i = 0; i <= 8; i++) {
      dayMap.put(i, 0L);
    }

    for (int fish : this.fishList) {
      dayMap.put(fish, dayMap.get(fish) + 1);
    }

    for (int i = 0; i < days; i++) {
      long newFish = dayMap.get(0);

      for (int j = 0; j <= 5; j++) {
        dayMap.put(j, dayMap.get(j + 1));
      }

      dayMap.put(6, newFish + dayMap.get(7));
      dayMap.put(7, dayMap.get(8));
      dayMap.put(8, newFish);
    }

    long total = 0;

    for (long count : dayMap.values()) {
      total += count;
    }

    return total;
  }
}

/**   1
 * 1  0
 * 2  6*
 * 3  5
 * 4  4
 * 5  3
 * 6  2
 * 7  1
 * 8  0
 * 9  6*
 */