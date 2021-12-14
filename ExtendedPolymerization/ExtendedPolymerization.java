package ExtendedPolymerization;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.Helpers;

public class ExtendedPolymerization {
  private static final String FILE_NAME = "input.txt";

  String start;
  List<String> lines;
  Map<String, String> mapping;
  Map<String, Long> pairFreq;

  public ExtendedPolymerization() {
    URL path = ExtendedPolymerization.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();
    long part1Result = this.runPart1();

    this.init();
    long part2Result = this.runPart2();

    System.out.println("Extended Polymerization part 1: " + part1Result);
    System.out.println("Extended Polymerization part 2: " + part2Result);
  }

  private void init() {
    this.mapping = new HashMap<>();
    this.pairFreq = new HashMap<>();

    this.start = this.lines.get(0);
    for (int i = 0; i < this.start.length() - 1; i++) {
      String pair = this.start.charAt(i) + "" + this.start.charAt(i + 1);
      this.pairFreq.put(pair, this.pairFreq.getOrDefault(pair, 0L) + 1);
    }

    for (int i = 2; i < this.lines.size(); i++) {
      String[] mapItem = this.lines.get(i).split(",");
      this.mapping.put(mapItem[0], mapItem[1]);
    }
  }

  private long runPart1() {
    return simulate(10);
  }

  private long runPart2() {
    return simulate(40);
  }

  private long simulate(int steps) {
    Map<Character, Long> charFreq = new HashMap<>();
    for (char c : this.start.toCharArray()) {
      charFreq.put(c, charFreq.getOrDefault(c, 0L) + 1);
    }

    for (int i = 0; i < steps; i++) {
      this.performInsertion(charFreq);
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

  private void performInsertion(Map<Character, Long> charFreq) {
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
