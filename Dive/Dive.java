package Dive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dive {
  private static final String FORWARD = "forward";
  private static final String UP = "up";
  private static final String DOWN = "down";
  private static final String FILE_NAME = "input.txt";

  File file;
  List<Integer> deltaXs;
  List<Integer> deltaYs;
  List<Command> commands;

  public Dive() {
    URL path = Dive.class.getResource(FILE_NAME);
    
    this.file = new File(path.getFile());
    this.deltaXs = new ArrayList<>();
    this.deltaYs = new ArrayList<>();
    this.commands = new ArrayList<>();

    try {
      this.populateDeltasAndCommands();
    } catch (Exception e) {
      System.out.println("error reading file");
    }

    int part1Result = this.runPart1();
    int part2Result = this.runPart2();

    System.out.println("Dive part 1: " + part1Result);
    System.out.println("Dive part 2: " + part2Result);
  }

  private void populateDeltasAndCommands() throws Exception{
    BufferedReader br = new BufferedReader(new FileReader(this.file));

    String line;
    while ((line = br.readLine()) != null) {
      String[] items = line.split(" ");
      String direction = items[0];
      int value = Integer.parseInt(items[1]);

      if (direction.equals(FORWARD)) {
        this.deltaXs.add(value);
        this.commands.add(new Command(0, value));
      } else if (direction.equals(DOWN)) {
        this.deltaYs.add(value);
        this.commands.add(new Command(value, 0));
      } else {
        this.deltaYs.add(value * -1);
        this.commands.add(new Command(value * -1, 0));
      }
    }

    br.close();
  }

  private int runPart1() {
    int finalX = 0;
    int finalY = 0;

    for (int x : this.deltaXs) {
      finalX += x;
    }

    for (int y : this.deltaYs) {
      finalY += y;
    }

    return finalX * finalY;
  }

  private int runPart2() {
    int finalX = 0;
    int finalY = 0;
    int aim = 0;

    for (Command c : this.commands) {
      if (c.dX == 0) {
        aim += c.dA;
      } else {
        finalX += c.dX;
        finalY += aim * c.dX;
      }
    }

    return finalX * finalY;
  }

  private class Command {
    int dA;
    int dX;

    public Command(int dA, int dX) {
      this.dA = dA;
      this.dX = dX;
    }
  }
}
