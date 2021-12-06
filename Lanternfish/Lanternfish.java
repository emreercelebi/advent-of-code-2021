package Lanternfish;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Lanternfish {
  private static final String FILE_NAME = "input-test.txt";

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

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

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

  private int runPart1() {
    int result = 0;
    for (int fish : this.fishList) {
      result += 1 + calculateOffspringCount(fish, 80);
    }
    return result;
  }

  private int runPart2() {
    int result = 0;
    for (int fish : this.fishList) {
      result += 1 + calculateOffspringCount(fish, 256);
    }
    return result;
  }

  private int calculateOffspringCount(int fish, int days) {
    // System.out.print("(" + fish + ", " + days + ") ");
    if (fish >= days) {
      return 0;
    } else {
      days -= fish;
      int offspringCount = 1 + (days - 1) / 7;
      // System.out.println("offspring count: " + offspringCount);
      for (int i = 0; i < offspringCount; i++) {
        int daysRemaining = days - 7*i - 1;
        offspringCount += calculateOffspringCount(8, daysRemaining);
      }

      return offspringCount;
    }
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