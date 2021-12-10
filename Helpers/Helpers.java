package Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Helpers {
  public static List<String> getFileLines(URL path) {
    File file = new File(path.getFile());
    BufferedReader br = null;
    
    try {
      br = new BufferedReader(new FileReader(file));
    } catch (Exception e) {
      System.out.println("error opening file");
      System.out.println(e.getMessage());
    }

    List<String> lines = new ArrayList<>();
    String line;

    try {
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      System.out.println("error reading file line");
      System.out.println(e.getMessage());
    }

    return lines;
  }
}
