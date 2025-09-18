// java.lang.Runtime - 외부 프로그램 실행하기
package com.eomcs.basic.ex09;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Exam0110 {
  public static void main(String[] args) throws Exception {
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec("ls -al");
    try (
        BufferedReader in = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {

      String line = null;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }
    }
  }

}







