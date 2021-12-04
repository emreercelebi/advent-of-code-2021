package SonarSweep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

public class SonarSweep {

  private static final String FILE_NAME = "input.txt";
  ArrayList<Integer> depths;
  File file;

  public SonarSweep() {
    URL path = SonarSweep.class.getResource(FILE_NAME);
    
    this.file = new File(path.getFile());
    this.depths = new ArrayList<Integer>();

    try {
      this.populateDepths();
    } catch (Exception e) {
      System.out.println("error reading file");
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Sonar Sweep part 1: " + part1Result);
    System.out.println("Sonar Sweep part 2: " + part2Result);
  }

  private void populateDepths() throws Exception{
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;
    while ((line = br.readLine()) != null) {
      this.depths.add(Integer.parseInt(line));
    }

    br.close();
  }

  private int runPart1() {
    int prev = this.depths.get(0);
    int count = 0;

    for (int i = 0; i < this.depths.size(); i++) {
      int curr = this.depths.get(i);
      if (curr > prev) {
        count++;
      }
      prev = curr;
    }

    return count;
  }

  private int runPart2() {
    int prevWindow = this.depths.get(0) + this.depths.get(1) + this.depths.get(2);
    int count = 0;

    for (int i = 3; i < this.depths.size(); i++) {
      int currWindow = this.depths.get(i - 2) + this.depths.get(i - 1) + this.depths.get(i);
      if (currWindow > prevWindow) {
        count++;
      }
      prevWindow = currWindow;
    }

    return count;
  }
}
