package com.eomcs.web;

import java.text.SimpleDateFormat;

public class Test {

  public static void main(String[] args) throws Exception {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date d1 = dateFormat.parse("2024-1-1");
    System.out.println(d1);

  }

}
