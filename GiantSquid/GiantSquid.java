package GiantSquid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GiantSquid {
  private static final String FILE_NAME = "input.txt";

  File file;
  int[] numberQueue;
  List<BingoBoard> bingoBoards;

  public GiantSquid() {
    URL path = GiantSquid.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.bingoBoards = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    int part1Result = this.runPart1();

    for (BingoBoard bingoBoard : this.bingoBoards) {
      bingoBoard.reset();
    }

    int part2Result = this.runPart2();

    System.out.println("Giant Squid part 1: " + part1Result);
    System.out.println("Giant Squid part 2: " + part2Result);
  }

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;

    //first line is number queue
    line = br.readLine();
    String[] commands = line.split(",");
    this.numberQueue = new int[commands.length];
    for (int i = 0; i < commands.length; i++) {
      this.numberQueue[i] = Integer.parseInt(commands[i]);
    }

    List<List<Integer>> board = new ArrayList<>();
    while ((line = br.readLine()) != null) {
      if (line.length() == 0) {
        if (!board.isEmpty()) {
          this.bingoBoards.add(new BingoBoard(board));
        }
        board = new ArrayList<>();
        continue;
      }

      line = line.trim();
      String[] values = line.split("\\s+");

      List<Integer> row = new ArrayList<>();
      for (String value : values) {
        row.add(Integer.parseInt(value));
      }

      board.add(row);
    }

    //get last board
    this.bingoBoards.add(new BingoBoard(board));

    br.close();
  }

  private int runPart1() {
    for (int num : this.numberQueue) {
      for (BingoBoard board : this.bingoBoards) {
        board.check(num);
        if (board.hasWon()) {
          return board.calculateScore(num);
        }
      }
    }

    return 0;
  }

  private int runPart2() {
    int totalBoardCount = this.bingoBoards.size();
    Set<BingoBoard> boardsWon = new HashSet<>();

    for (int num : this.numberQueue) {
      for (BingoBoard board : this.bingoBoards) {
        board.check(num);
        if (board.hasWon()) {
          boardsWon.add(board);
          if (boardsWon.size() == totalBoardCount) {
            return board.calculateScore(num);
          }
        }
      }
    }

    return 0;
  }
}
