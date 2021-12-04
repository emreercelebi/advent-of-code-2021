package BinaryDiagnostic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryDiagnostic {
  
  private static final String FILE_NAME = "input.txt";

  File file;
  List<String> binaryStrings;
  int[] freqArr;

  public BinaryDiagnostic() {
    URL path = BinaryDiagnostic.class.getResource(FILE_NAME);

    this.file = new File(path.getFile());
    this.binaryStrings = new ArrayList<>();

    try {
      this.populateBinaryStrings();
    } catch (Exception e) {
      System.out.println("error reading file");
    }

    this.buildFreqArr();

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Binary Diagnostic part 1: " + part1Result);
    System.out.println("Binary Diagnostic part 2: " + part2Result);
  }

  private void populateBinaryStrings() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;
    while ((line = br.readLine()) != null) {
      binaryStrings.add(line);
    }

    br.close();
  }

  private void buildFreqArr() {
    this.freqArr = new int[this.binaryStrings.get(0).length()];

    for (String bin : this.binaryStrings) {
      for (int i = 0; i < bin.length(); i++) {
        char bit = bin.charAt(i);
        if (bit == '1') {
          this.freqArr[i]++;
        } else {
          this.freqArr[i]--;
        }
      }
    }
  }

  private int[] buildNewFreqArr(List<String> binStrings) {
    int[] fArr = new int[binStrings.get(0).length()];

    for (String bin : binStrings) {
      for (int i = 0; i < bin.length(); i++) {
        char bit = bin.charAt(i);
        if (bit == '1') {
          fArr[i]++;
        } else {
          fArr[i]--;
        }
      }
    }

    return fArr;
  }

  private int runPart1() {
    int gr = this.getGammaRate();
    int er = this.getEpsilonRate();
    
    return gr * er;
  }

  private int runPart2() {
    int ogr = this.getOxygenGeneratorRating();
    int csr = this.getCO2ScrubberRating();

    return ogr * csr;
  }

  private int getGammaRate() {
    int gr = 0;
    for (int i = this.freqArr.length - 1; i >= 0; i--) {
      int exp = this.freqArr.length - 1 - i;
      if (this.freqArr[i] > 0) {
        gr += Math.pow(2, exp);
      }
    }

    return gr;
  }

  private int getEpsilonRate() {
    int gr = 0;
    for (int i = this.freqArr.length - 1; i >= 0; i--) {
      int exp = this.freqArr.length - 1 - i;
      if (this.freqArr[i] < 0) {
        gr += Math.pow(2, exp);
      }
    }

    return gr;
  }

  private int getOxygenGeneratorRating() {
    List<String> filteredList = new ArrayList<>();
    for (String bin : this.binaryStrings) {
      filteredList.add(bin);
    }
    int[] newFreqArr = this.buildNewFreqArr(filteredList);

    for (int i = 0; i < newFreqArr.length; i++) {
      int idx = i;
      int[] fArr = newFreqArr;
      filteredList = filteredList
        .stream()
        .filter(str -> (str.charAt(idx) == '1' && fArr[idx] > 0) || 
                  (str.charAt(idx) == '0' && fArr[idx] < 0) ||
                  (str.charAt(idx) == '1' && fArr[idx] == 0))
        .collect(Collectors.toList());

      if (filteredList.size() == 1) {
        break;
      }

      newFreqArr = buildNewFreqArr(filteredList);
    }

    if (filteredList.isEmpty()) {
      return 0;
    }

    return binaryStringToInt(filteredList.get(0));
  }

  private int getCO2ScrubberRating() {
    List<String> filteredList = new ArrayList<>();
    for (String bin : this.binaryStrings) {
      filteredList.add(bin);
    }
    int[] newFreqArr = this.buildNewFreqArr(filteredList);

    for (int i = 0; i < newFreqArr.length; i++) {
      int idx = i;
      int[] fArr = newFreqArr;
      filteredList = filteredList
        .stream()
        .filter(str -> (str.charAt(idx) == '1' && fArr[idx] < 0) || 
                (str.charAt(idx) == '0' && fArr[idx] > 0) || 
                (str.charAt(idx) == '0' && fArr[idx] == 0))
        .collect(Collectors.toList());

      if (filteredList.size() == 1) {
        break;
      }

      newFreqArr = buildNewFreqArr(filteredList);
    }

    if (filteredList.isEmpty()) {
      return 0;
    }
    return binaryStringToInt(filteredList.get(0));
  }

  private int binaryStringToInt(String binaryString) {
    int num = 0;
    for (int i = binaryString.length() - 1; i >= 0; i--) {
      int exp = binaryString.length() - 1 - i;
      char c = binaryString.charAt(i);
      if (c == '1') {
        num += Math.pow(2, exp);
      }
    }

    return num;
  }
}
