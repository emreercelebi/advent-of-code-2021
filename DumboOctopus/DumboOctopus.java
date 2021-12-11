package DumboOctopus;

import java.net.URL;
import java.util.List;

import Helpers.Helpers;

public class DumboOctopus {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  int[][] gridOriginal;
  int[][] grid;
  int[][] directions = {
    {-1, -1},
    {-1, 0},
    {-1, 1},
    {0, -1},
    {0, 1},
    {1, -1},
    {1, 0},
    {1, 1}
  };

  public DumboOctopus() {
    URL path = DumboOctopus.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();

    int part1Result = this.runPart1();

    this.resetGrid();
    int part2Result = this.runPart2();

    System.out.println("Dumbo Octopus part 1: " + part1Result);
    System.out.println("Dumbo Octopus part 2: " + part2Result);
  }

  private void init() {
    this.gridOriginal = new int[10][10];
    for (int i = 0; i < this.lines.size(); i++) {
      String line = this.lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        this.gridOriginal[i][j] = Character.getNumericValue(line.charAt(j));
      }
    }

    this.grid = new int[10][10];
    this.resetGrid();
  }

  private void resetGrid() {
    for (int i = 0; i < this.gridOriginal.length; i++) {
      for (int j = 0; j < this.gridOriginal[0].length; j++) {
        this.grid[i][j] = this.gridOriginal[i][j];
      }
    }
  }

  private int runPart1() {
    return simulate(100);
  }

  private int runPart2() {
    int flashes = 0;
    int index = 0;

    while (flashes != this.grid.length * this.grid[0].length) {
      index++;
      flashes = this.simulateOneStep();
    }

    return index;
  }

  private int simulate(int steps) {
    int count = 0;
    for (int i = 0; i < steps; i++) {
      count += simulateOneStep();
    }
    return count;
  }

  private int simulateOneStep() {
    int count = 0;
    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[row].length; col++) {
        this.grid[row][col]++;
      }
    }

    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[row].length; col++) {
        if (this.grid[row][col] > 9) {
          count += flash(row, col);
        }
      }
    }
    return count;
  }

  private int flash(int row, int col) {
    this.grid[row][col] = 0;
    int otherFlashes = 0;
    
    for (int[] dir : this.directions) {
      int nRow = row + dir[0];
      int nCol = col + dir[1];
      if (!isInBounds(nRow, nCol)) {
        continue;
      }
      if (this.grid[nRow][nCol] == 0) {
        continue;
      }

      this.grid[nRow][nCol]++;

      if (this.grid[nRow][nCol] > 9) {
        otherFlashes += flash(nRow, nCol);
      }
    }

    return 1 + otherFlashes;
  }

  private boolean isInBounds(int row, int col) {
    return row >= 0 && row < this.grid.length && col >= 0 && col < this.grid[0].length;
  }

  private void printGrid() {
    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[row].length; col++) {
        System.out.print(this.grid[row][col] + " ");
      }
      System.out.println();
    }
  }
}
