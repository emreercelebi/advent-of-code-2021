package SyntaxScoring;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import Helpers.Helpers;

public class SyntaxScoring {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;

  public SyntaxScoring() {
    URL path = SyntaxScoring.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    int part1Result = this.runPart1();
    long part2Result = this.runPart2();

    System.out.println("Syntax Scoring part 1: " + part1Result);
    System.out.println("Syntax Scoring part 2: " + part2Result);
  }

  private int runPart1() {
    List<Character> invalidChars = new ArrayList<>();

    for (String line : this.lines) {
      Stack<Character> stack = new Stack<>();
      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (isOpening(c)) {
          stack.push(c);
        } else if (isClosing(c)) {
          if (stack.peek() == getOpenFromClose(c)) {
            stack.pop();
          } else {
            invalidChars.add(c);
            break;
          }
        }
      }
    }

    int totalScore = 0;

    for (char c : invalidChars) {
      totalScore += getCharScore(c);
    }

    return totalScore;
  }

  private long runPart2() {
    List<String> completionStrings = new ArrayList<>();

    for (String line : this.lines) {
      Stack<Character> stack = new Stack<>();
      boolean isCorrupted = false;

      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (isOpening(c)) {
          stack.push(c);
        } else if (isClosing(c)) {
          if (stack.peek() == getOpenFromClose(c)) {
            stack.pop();
          } else {
            isCorrupted = true;
            break;
          }
        }
      }

      if (!isCorrupted) {
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
          sb.append(getCloseFromOpen(stack.pop()));
        }

        completionStrings.add(sb.toString());
      }
    }

    List<Long> scores = new ArrayList<>();

    for (String s : completionStrings) {
      long score = 0;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        score = score * 5 + getCharScore2(c);
      }
      scores.add(score);
    }

    scores.sort(new Comparator<Long>() {
      @Override
      public int compare(Long a, Long b) {
        return a < b ? -1 : 1;
      }
    });

    return scores.get(scores.size() / 2);
  }

  private boolean isOpening(char c) {
    return c == '(' || c == '[' || c == '{' || c == '<';
  }

  private boolean isClosing(char c) {
    return c == ')' || c == ']' || c == '}' || c == '>';
  }

  private char getOpenFromClose(char c) {
    if (c == '}') {
      return '{';
    } else if (c == ')') {
      return '(';
    } else if (c == ']') {
      return '[';
    } else {
      return '<';
    }
  }

  private char getCloseFromOpen(char c) {
    if (c == '{') {
      return '}';
    } else if (c == '(') {
      return ')';
    } else if (c == '[') {
      return ']';
    } else {
      return '>';
    }
  }

  private int getCharScore(char c) {
    if (c == '}') {
      return 1197;
    } else if (c == ')') {
      return 3;
    } else if (c == ']') {
      return 57;
    } else {
      return 25137;
    }
  }

  private int getCharScore2(char c) {
    if (c == '}') {
      return 3;
    } else if (c == ')') {
      return 1;
    } else if (c == ']') {
      return 2;
    } else {
      return 4;
    }
  }
}
