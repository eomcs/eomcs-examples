package com.eomcs.advanced.oop.exam14;

import java.util.function.Function;

public class App3 {

  static void printScore(
      String[] scores, Function<String, Integer> sumFunc, Function<String, String> nameFunc) {
    for (String score : scores) {
      int sum = sumFunc.apply(score);
      System.out.printf("%s: %d %.1f%n", nameFunc.apply(score), sum, sum / 3f);
    }
  }

  public static void main(String[] args) {
    String[] scores = {"홍길동 100 100 100", "임꺽정 90 90 90", "유관순 80 80 80"};
    printScore(
        scores,
        score -> {
          String[] values = score.split(" ");
          int sum = 0;
          for (int i = 1; i <= 3; i++) {
            sum += Integer.parseInt(values[i]);
          }
          return sum;
        },
        score -> score.split(" ")[0]);
  }
}
