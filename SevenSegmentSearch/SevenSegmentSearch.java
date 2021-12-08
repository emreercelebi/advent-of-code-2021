package SevenSegmentSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SevenSegmentSearch {
  private static final String FILE_NAME = "input.txt";

  File file;
  List<List<String>> numberLists;
  List<List<String>> outputLists;

  public SevenSegmentSearch() {
    URL path = SevenSegmentSearch.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.numberLists = new ArrayList<>();
    this.outputLists = new ArrayList<>();

    try {
      this.init();
    } catch (Exception e) {
      System.out.println("error reading file");
      System.out.println(e.getMessage());
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Seven Segment Search part 1: " + part1Result);
    System.out.println("Seven Segment Search part 2: " + part2Result);
  }

  

  private void init() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;

    while ((line = br.readLine()) != null) {
      String[] parts = line.split(",");
      List<String> numberList = new ArrayList<>();
      List<String> outputList = new ArrayList<>();
      for (String number : parts[0].split(" ")) {
        numberList.add(number);
      }
      for (String output : parts[1].split(" ")) {
        outputList.add(output);
      }

      this.numberLists.add(numberList);
      this.outputLists.add(outputList);
    }

  }

  private int runPart1() {
    int total = 0;
    for (List<String> outputList : this.outputLists) {
      for (String output : outputList) {
        int l = output.length();
        if (l == 2 || l == 3 || l == 4 || l == 7) {
          total++;
        }
      }
    }

    return total;
  }

  private int runPart2() {
    int total = 0;
    for (int i = 0; i < this.numberLists.size(); i++) {
      List<String> numbers = this.numberLists.get(i);
      Map<Integer, Set<Character>> numberMap = getNumberMap(numbers);

      List<String> outputs = this.outputLists.get(i);
      int decodedNum = decode(outputs, numberMap);
      total += decodedNum;
    }
    return total;
  }

  private int decode(List<String> outputs, Map<Integer, Set<Character>> numberMap) {
    int total = 0;
    for (int i = 0; i < outputs.size(); i++) {
      String output = outputs.get(i);
      Set<Character> charSet = buildCharSet(output);

      for (int num : numberMap.keySet()) {
        if (areEqualSets(charSet, numberMap.get(num))) {
          total = total * 10 + num;
        }
      }
    }

    return total;
  }

  private Map<Integer, Set<Character>> getNumberMap(List<String> numbers) {
    Map<Integer, Set<Character>> numberMap = new HashMap<>();

    for (String number : numbers) {
      int l = number.length();
      int knownNum = -1;
      if (l == 2) {
        knownNum = 1;
      } else if (l == 3) {
        knownNum = 7;
      } else if (l == 4) {
        knownNum = 4;
      } else if (l == 7) {
        knownNum = 8;
      }

      if (knownNum > 0) {
        numberMap.put(knownNum, new HashSet<>());
        for (int i = 0; i < l; i++) {
          numberMap.get(knownNum).add(number.charAt(i));
        }
      }
    }

    char topLeft = '?';
    for (String number: numbers) {
      //get 6
      if (number.length() == 6) {
        Set<Character> charSet = buildCharSet(number);
        boolean isSix = false;
        for (char c : numberMap.get(1)) {
          if (!charSet.contains(c)) {
            isSix = true;
            topLeft = c;
          }
        }
        if (isSix) {
          numberMap.put(6, charSet);
        }
      }
    }

    //get 5
    for (String number : numbers) {
      if (number.length() == 5) {
        Set<Character> charSet = buildCharSet(number);
        if (!charSet.contains(topLeft)) {
          numberMap.put(5, charSet);
        }
      }
    }

    //get 3
    for (String number : numbers) {
      if (number.length() == 5) {
        Set<Character> charSet = buildCharSet(number);
        boolean isThree = true;
        for (char c : numberMap.get(1)) {
          if (!charSet.contains(c)) {
            isThree = false;
          }
        }
        if (isThree) {
          numberMap.put(3, charSet);
        }
      }
    }

    //get 2
    for (String number : numbers) {
      if (number.length() == 5) {
        Set<Character> charSet = buildCharSet(number);
        if (charDiffCount(charSet, numberMap.get(5)) == 2) {
          numberMap.put(2, charSet);
        }
      }
    }

    //get 0 and 9
    for (String number : numbers) {
      if (number.length() == 6) {
        Set<Character> charSet = buildCharSet(number);
        boolean isNine = true;
        for (char c : numberMap.get(4)) {
          if (!charSet.contains(c)) {
            isNine = false;
          }
        }
        if (isNine) {
          numberMap.put(9, charSet);
        } else if (charDiffCount(charSet, numberMap.get(6)) != 0) {
          numberMap.put(0, charSet);
        }
      }
    }


    return numberMap;
  }

  private HashSet<Character> buildCharSet(String s) {
    HashSet<Character> charSet = new HashSet<Character>();
    for (int i = 0; i < s.length(); i++) {
      charSet.add(s.charAt(i));
    }
    return charSet;
  }

  private int charDiffCount(Set<Character> set1, Set<Character> set2) {
    if (set1.size() != set2.size()) {
      return -1;
    }

    int diffCount = 0;
    for (char c : set1) {
      if (!set2.contains(c)) {
        diffCount++;
      }
    }
    return diffCount;
  }

  private boolean areEqualSets(Set<Character> set1, Set<Character> set2) {
    if (set1.size() != set2.size()) {
      return false;
    }

    for (char c : set1) {
      if (!set2.contains(c)) {
        return false;
      }
    }

    for (char c : set2) {
      if (!set1.contains(c)) {
        return false;
      }
    }

    return true;
  }
}
