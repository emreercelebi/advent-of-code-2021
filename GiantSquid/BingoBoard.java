package GiantSquid;

import java.util.List;

public class BingoBoard {
  private int[][] board;
  private int[] numCheckedRows;
  private int[] numCheckedCols;

  public BingoBoard(List<List<Integer>> board) {
    this.board = new int[board.size()][board.get(0).size()];
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(0).size(); j++) {
        this.board[i][j] = board.get(i).get(j);
      }
    }

    this.numCheckedRows = new int[board.size()];
    this.numCheckedCols = new int[board.get(0).size()];
  }

  public void check(int target) {
    for (int row = 0; row < this.board.length; row++) {
      for (int col = 0; col < this.board[0].length; col++) {
        if (this.board[row][col] == target) {
          //mark as negative to indicate checked
          this.board[row][col] *= -1;
          this.numCheckedRows[row]++;
          this.numCheckedCols[col]++;
        }
      }
    }
  }

  public boolean hasWon() {
    for (int num : this.numCheckedRows) {
      if (num == this.board.length) {
        return true;
      }
    }

    for (int num : this.numCheckedCols) {
      if (num == this.board.length) {
        return true;
      }
    }

    return false;
  }

  public int calculateScore(int lastCalled) {
    int unmarked = 0;

    for (int row = 0; row < this.board.length; row++) {
      for (int col = 0; col < this.board[0].length; col++) {
        if (this.board[row][col] > 0) {
          unmarked += this.board[row][col];
        }
      }
    }

    return unmarked * lastCalled;
  }

  public void reset() {
    for (int row = 0; row < this.board.length; row++) {
      for (int col = 0; col < this.board[0].length; col++) {
        this.board[row][col] = Math.abs(this.board[row][col]);
      }
    }

    for (int i = 0; i < this.numCheckedCols.length; i++) {
      this.numCheckedCols[i] = 0;
    }

    for (int i = 0; i < this.numCheckedRows.length; i++) {
      this.numCheckedRows[i] = 0;
    }
  }

  public void printBoard() {
    System.out.println();
    for (int[] row : this.board) {
      for (int num : row) {
        System.out.print(num + "  ");
      }
      System.out.println();
    }
  }
}
