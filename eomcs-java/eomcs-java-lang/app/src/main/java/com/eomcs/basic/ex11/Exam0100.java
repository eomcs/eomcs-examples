package com.eomcs.basic.ex11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exam0100 {

  public static void main(String[] args) {
    Pattern pattern = Pattern.compile("bit", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher("theBitCamp");
    if (matcher.find()) {
      System.out.println("찾았다!");
    } else {
      System.out.println("못찾았다!");
    }
  }

}
