package Chiton;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import Helpers.Helpers;

public class Chiton {
  private static final String FILE_NAME = "input.txt";

  String start;
  List<String> lines;
  List<List<Integer>> grid;
  List<List<Integer>> largeGrid;

  public Chiton() {
    URL path = Chiton.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();
    int part1Result = this.runPart1();

    this.init();
    int part2Result = this.runPart2();

    System.out.println("Chiton part 1: " + part1Result);
    System.out.println("Chiton part 2: " + part2Result);
  }

  private void init() {
    this.grid = new ArrayList<>();
    this.largeGrid = new ArrayList<>();

    for (String line : this.lines) {
      List<Integer> newList = new ArrayList<>();
      for (char c : line.toCharArray()) {
        newList.add(Character.getNumericValue(c));
      }
      this.grid.add(newList);
    }

    for (List<Integer> row : this.grid) {
      List<Integer> newList = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        for (int num : row) {
          int val = num + i;
          if (val > 9) {
            val -= 9;
          }
          newList.add(val);
        }
      }
      this.largeGrid.add(newList);
    }

    for (int i = 1; i <= 4; i++) {
      for (int j = 0; j < this.grid.size(); j++) {
        List<Integer> largeGridRow = this.largeGrid.get(j);
        List<Integer> newList = new ArrayList<>();
        for (int num : largeGridRow) {
          int val = num + i;
          if (val > 9) {
            val -= 9;
          }
          newList.add(val);
        }
        this.largeGrid.add(newList);
      }
    }
  }

  private int runPart1() {
    return dijkstra(this.grid);
  }

  private int runPart2() {
    return dijkstra(this.largeGrid);
  }

  // only works if you're limited to moving down or left. happened to produce optimal solution for part 1 but not part 2
  private int traverse(List<List<Integer>> maze) {
    int[][] tempGrid = new int[maze.size()][maze.get(0).size()];

    tempGrid[tempGrid.length - 1][tempGrid[0].length - 1] = maze.get(tempGrid.length - 1).get(tempGrid[0].length - 1);

    for (int i = tempGrid.length - 1; i >= 0; i--) {
      for (int j = tempGrid[0].length - 1; j >= 0; j--) {
        if (i == tempGrid.length - 1 && j == tempGrid[0].length - 1) {
          continue;
        } else if (i == tempGrid.length - 1) {
          tempGrid[i][j] = maze.get(i).get(j) + tempGrid[i][j + 1];
        } else if (j == tempGrid[0].length - 1) {
          tempGrid[i][j] = maze.get(i).get(j) + tempGrid[i + 1][j];
        } else {
          tempGrid[i][j] = maze.get(i).get(j) + Math.min(tempGrid[i][j + 1], tempGrid[i + 1][j]);
        }
      }
    }

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        System.out.print(tempGrid[i][j] + " ");
      }
      System.out.println();
    }
    return tempGrid[0][0] - maze.get(0).get(0);
  }

  // works when able to move in any direction
  private int dijkstra(List<List<Integer>> maze) {
    int[][] distances = new int[maze.size()][maze.get(0).size()];

    for (int i = 0; i < distances.length; i++) {
      for (int j = 0; j < distances.length; j++) {
        distances[i][j] = Integer.MAX_VALUE;
      }
    }

    distances[0][0] = 0;

    PriorityQueue<Integer[]> pq = new PriorityQueue<>(new Comparator<Integer[]>() {
      public int compare(Integer[] a, Integer[] b) {
        return distances[a[0]][a[1]] - distances[b[0]][b[1]];
      }
    });

    pq.add(new Integer[]{0, 0});
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    while (!pq.isEmpty()) {
      Integer[] point = pq.poll();
      int row = point[0];
      int col = point[1];

      for (int[] dir : directions) {
        int r = row + dir[0];
        int c = col + dir[1];
        if (r < 0 || r >= distances.length || c < 0 || c >= distances[0].length) {
          continue;
        }

        int newDist = distances[row][col] + maze.get(r).get(c);
        if (newDist < distances[r][c]) {
          distances[r][c] = newDist;
          pq.offer(new Integer[]{r, c});
        }
      }
    }

    return distances[distances.length - 1][distances[0].length - 1];
  }
}
