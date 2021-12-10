package SmokeBasin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmokeBasin {
  private static final String FILE_NAME = "input.txt";

  File file;
  List<List<Integer>> grid;
  int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  public SmokeBasin() {
    URL path = SmokeBasin.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.grid = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Smoke Basin part 1: " + part1Result);
    System.out.println("Smoke Basin part 2: " + part2Result);
  }

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;

    while ((line = br.readLine()) != null) {
      List<Integer> newList = new ArrayList<>();

      for (int i = 0; i < line.length(); i++) {
        newList.add(Character.getNumericValue(line.charAt(i)));
      }

      this.grid.add(newList);
    }
  }

  private int runPart1() {
    int score = 0;

    for (int i = 0; i < this.grid.size(); i++) {
      for (int j = 0; j < this.grid.get(i).size(); j++) {
        if (isLowPoint(i, j)) {
          score += this.grid.get(i).get(j) + 1;
        }
      }
    }

    return score;
  }

  private int runPart2() {
    List<Integer> basinSizes = new ArrayList<Integer>();

    for (int i = 0; i < this.grid.size(); i++) {
      for (int j = 0; j < this.grid.get(i).size(); j++) {
        if (isLowPoint(i, j)) {
          basinSizes.add(getBasinSize(i, j));
        }
      }
    }

    basinSizes.sort(new Comparator<Integer>() {
      @Override
      public int compare(Integer a, Integer b) {
        return b - a;
      }
    });

    int first = basinSizes.get(0);
    int second = basinSizes.get(1);
    int third = basinSizes.get(2);

    return first*second*third;
  }

  private boolean isLowPoint(int i, int j) {
    int val = this.grid.get(i).get(j);
    int top = i > 0 ? this.grid.get(i - 1).get(j) : Integer.MAX_VALUE;
    int bottom = i < this.grid.size() - 1 ? this.grid.get(i + 1).get(j) : Integer.MAX_VALUE;
    int left = j > 0 ? this.grid.get(i).get(j - 1) : Integer.MAX_VALUE;
    int right = j < this.grid.get(0).size() - 1 ? this.grid.get(i).get(j + 1) : Integer.MAX_VALUE;
    
    return val < top && val < bottom && val < left && val < right;
  }

  private int getBasinSize(int i, int j) {
    boolean[][] visited = new boolean[this.grid.size()][this.grid.get(0).size()];

    return this.getBasinSize(i, j, Integer.MIN_VALUE, visited);
  }

  private int getBasinSize(int i, int j, int prev, boolean[][] visited) {
    if (i < 0 || i >= this.grid.size() || j < 0 || j >= this.grid.get(0).size()) {
      return 0;
    }

    int val = this.grid.get(i).get(j);
    if (val == 9 || val <= prev || visited[i][j]) {
      return 0;
    }
    visited[i][j] = true;

    int surrounding = 0;
    for (int[] dir : this.directions) {
      surrounding += this.getBasinSize(i + dir[0], j + dir[1], val, visited);
    }

    return 1 + surrounding;
  }
}
