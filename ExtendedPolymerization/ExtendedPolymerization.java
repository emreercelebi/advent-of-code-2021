package ExtendedPolymerization;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Helpers.Helpers;

public class ExtendedPolymerization {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  Map<String, String> mapping;
  String start;
  List<Character> charList;

  public ExtendedPolymerization() {
    URL path = ExtendedPolymerization.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Extended Polymerization part 1: " + part1Result);
    System.out.println("Extended Polymerization part 2: " + part2Result);
  }

  private void init() {
    this.mapping = new HashMap<>();

    this.start = this.lines.get(0);
    this.charList = new ArrayList<>();
    for (int i = 0; i < this.start.length(); i++) {
      this.charList.add(this.start.charAt(i));
    }

    for (int i = 2; i < this.lines.size(); i++) {
      String[] mapItem = this.lines.get(i).split(",");
      this.mapping.put(mapItem[0], mapItem[1]);
    }
  }

  private int runPart1() {
    for (int i = 0; i < 40; i++) {
      performInsertion();
    }

    Map<Character, Integer> charFreq = new HashMap<>();
    for (char c : this.charList) {
      charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
    }

    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    for (char c : charFreq.keySet()) {
      int freq = charFreq.get(c);
      max = Math.max(max, freq);
      min = Math.min(min, freq);
    }

    return max - min;
  }

  private int runPart2() {
    return 0;
  }

  private void performInsertion() {
    int originalSize = this.charList.size();

    for (int i = originalSize - 2; i >= 0; i--) {
      String pair = this.charList.get(i) + "" + this.charList.get(i + 1);
      if (this.mapping.containsKey(pair)) {
        this.charList.add(i + 1, this.mapping.get(pair).charAt(0));
      }
    }
  }
}
