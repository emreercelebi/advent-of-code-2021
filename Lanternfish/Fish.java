package Lanternfish;

import java.util.List;
import java.util.ArrayList;

public class Fish {
  int num;
  int daysRemaining;
  
  public Fish(int num, int daysRemaining) {
    this.num = num;
    this.daysRemaining = daysRemaining;
  }

  public List<Fish> generateOffspring() {
    int fish = this.num;
    int days = this.daysRemaining;
    if (fish >= days) {
      return new ArrayList<>();
    } else {
      List<Fish> offspring = new ArrayList<Fish>();
      days -= fish;
      int offspringCount = 1 + (days - 1) / 7;
      for (int i = 0; i < offspringCount; i++) {
        int daysRem = days - 7*i - 1;
        offspring.add(new Fish(8, daysRem));
      }

      return offspring;
    }
  }
}
