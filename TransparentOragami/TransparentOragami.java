package TransparentOragami;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Helpers.Helpers;

public class TransparentOragami {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  Map<Integer, Set<Integer>> graph;
  List<Integer> folds;

  public TransparentOragami() {
    URL path = TransparentOragami.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);
    this.folds = new ArrayList<>();

    this.init();

    int part1Result = this.runPart1();
    System.out.println("Transparent Oragami part 1: " + part1Result);

    this.init();
    System.out.println("Transparent Oragami part 2:");
    runPart2();
  }

  private void init() {
    this.graph = new HashMap<>();
    int index = 0;
    String line = this.lines.get(index);

    while (line.length() > 0) {
      String[] coords = line.split(",");
      int col = Integer.parseInt(coords[0]);
      int row = Integer.parseInt(coords[1]);

      if (!this.graph.containsKey(row)) {
        this.graph.put(row, new HashSet<>());
      }
      this.graph.get(row).add(col);
      line = this.lines.get(++index);
    }

    index++;

    for (int i = index; i < this.lines.size(); i++) {
      line = this.lines.get(i);
      int fold = Integer.parseInt(line.substring(13));

      if (line.indexOf("x=") != -1) {
        this.folds.add(fold);
      } else {
        this.folds.add(fold * -1);
      }
    }
  }

  private int runPart1() {
    this.fold(this.folds.get(0));

    int total = 0;

    for (int row : this.graph.keySet()) {
      total += this.graph.get(row).size();
    }

    return total;
  }

  private void runPart2() {
    for (int fold : this.folds) {
      this.fold(fold);
    }
    int maxRow = 0;
    int maxCol = 0;

    for (int row : this.graph.keySet()) {
      maxRow = Math.max(maxRow, row);
      for (int col : this.graph.get(row)) {
        maxCol = Math.max(maxCol, col);
      }
    }

    char[][] grid = new char[maxRow + 1][maxCol + 1];

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j] = ' ';
      }
    }

    for (int row : this.graph.keySet()) {
      for (int col : this.graph.get(row)) {
        grid[row][col] = '#';
      }
    }

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  private void fold(int fold) {
    if (fold < 0) {
      this.foldY(Math.abs(fold));
    } else {
      this.foldX(fold);
    }
  }

  private void foldY(int fold) {
    Set<Integer> originalRows = new HashSet<>();
    originalRows.addAll(this.graph.keySet());
    for (int row : originalRows) {
      if (row > fold) {
        Set<Integer> cols = this.graph.get(row);
        int newRow = row - ((row - fold) * 2);
        if (!this.graph.containsKey(newRow)) {
          this.graph.put(newRow, new HashSet<>());
        }
        this.graph.get(newRow).addAll(cols);
        this.graph.remove(row);
      }
    }
  }

  private void foldX(int fold) {
    for (int row : this.graph.keySet()) {
      Set<Integer> originalCols = new HashSet<>();
      originalCols.addAll(this.graph.get(row));
      for (int col : originalCols) {
        if (col > fold) {
          int newCol = col - ((col - fold) * 2);
          this.graph.get(row).add(newCol);
          this.graph.get(row).remove(col);
        }
      }
    }
  }
}
