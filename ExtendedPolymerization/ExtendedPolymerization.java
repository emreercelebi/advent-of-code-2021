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
  Map<String, Long> pairFreq;

  public ExtendedPolymerization() {
    URL path = ExtendedPolymerization.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();
    int part1Result = this.runPart1();

    this.init();
    long part2Result = this.runPart2();

    System.out.println("Extended Polymerization part 1: " + part1Result);
    System.out.println("Extended Polymerization part 2: " + part2Result);
  }

  private void init() {
    this.mapping = new HashMap<>();
    this.pairFreq = new HashMap<>();

    this.start = this.lines.get(0);
    this.charList = new ArrayList<>();
    for (int i = 0; i < this.start.length(); i++) {
      if (i < this.start.length() - 1) {
        String pair = this.start.charAt(i) + "" + this.start.charAt(i + 1);
        this.pairFreq.put(pair, this.pairFreq.getOrDefault(pair, 0L) + 1);
      }
      this.charList.add(this.start.charAt(i));
    }

    for (int i = 2; i < this.lines.size(); i++) {
      String[] mapItem = this.lines.get(i).split(",");
      this.mapping.put(mapItem[0], mapItem[1]);
    }
  }

  private int runPart1() {
    for (int i = 0; i < 10; i++) {
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

  private long runPart2() {
    Map<Character, Long> charFreq = new HashMap<>();
    for (char c : this.start.toCharArray()) {
      charFreq.put(c, charFreq.getOrDefault(c, 0L) + 1);
    }

    for (int i = 0; i < 40; i++) {
      this.performInsertion2(charFreq);
    }
    
    long max = Long.MIN_VALUE;
    long min = Long.MAX_VALUE;

    for (char c : charFreq.keySet()) {
      long freq = charFreq.get(c);
      max = Math.max(max, freq);
      min = Math.min(min, freq);
    }

    return max - min;
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

  private void performInsertion2(Map<Character, Long> charFreq) {
    Map<String, Long> temp = new HashMap<>(this.pairFreq);

    for (String pair : temp.keySet()) {
      long count = temp.get(pair);
      if (this.mapping.containsKey(pair)) {
        char c = this.mapping.get(pair).charAt(0);
        String firstNewPair = pair.charAt(0) + this.mapping.get(pair);
        String secondNewPair = this.mapping.get(pair) + pair.charAt(1);
        charFreq.put(c, charFreq.getOrDefault(c, 0L) + count);

        this.pairFreq.put(pair, this.pairFreq.get(pair) - count);
        this.pairFreq.put(firstNewPair, pairFreq.getOrDefault(firstNewPair, 0L) + count);
        this.pairFreq.put(secondNewPair, pairFreq.getOrDefault(secondNewPair, 0L) + count);
      }
    }
  }
}
