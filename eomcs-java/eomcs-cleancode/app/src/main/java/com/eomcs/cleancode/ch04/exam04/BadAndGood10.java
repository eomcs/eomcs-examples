package com.eomcs.cleancode.ch04.exam04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BadAndGood10 {

  // Bad: 닫는 괄호를 다는 주석 (Closing Brace Comments)
  // - //while, //try, //catch, //main 주석은 중첩이 심한 코드를 설명하려는 시도다.
  // - 하지만 이는 함수가 너무 크고 복잡하다는 신호다.
  // - 해결책은 주석이 아니라 함수를 작게 나누는 것이다.
  static class BadWordCount {
    public static void main(String[] args) {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String line;
      int lineCount = 0;
      int charCount = 0;
      int wordCount = 0;
      try {
        while ((line = in.readLine()) != null) {
          lineCount++;
          charCount += line.length();
          String[] words = line.split("\\W");
          wordCount += words.length;
        } //while
        System.out.println("wordCount = " + wordCount);
        System.out.println("lineCount = " + lineCount);
        System.out.println("charCount = " + charCount);
      } // try
      catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
      } //catch
    } //main
  }

  // Good: 함수를 작게 나누면 닫는 괄호 주석이 필요 없어진다.
  // - main()은 전체 흐름만 보여주고 세부 구현은 각 메서드로 위임한다.
  // - 함수가 작아서 구조가 명확해지므로 //while, //try 같은 주석이 불필요하다.
  static class GoodWordCount {

    static class CountResult {
      private int lineCount;
      private int charCount;
      private int wordCount;

      void incrementLineCount() { lineCount++; }
      void addCharCount(int n) { charCount += n; }
      void addWordCount(int n) { wordCount += n; }
      int getLineCount() { return lineCount; }
      int getCharCount() { return charCount; }
      int getWordCount() { return wordCount; }
    }

    public static void main(String[] args) {
      try {
        CountResult result = countFromStdIn();
        printResult(result);
      } catch (IOException e) {
        printError(e);
      }
    }

    private static CountResult countFromStdIn() throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      CountResult result = new CountResult();
      String line;
      while ((line = reader.readLine()) != null) {
        processLine(line, result);
      }
      return result;
    }

    private static void processLine(String line, CountResult result) {
      result.incrementLineCount();
      result.addCharCount(line.length());
      result.addWordCount(countWords(line));
    }

    private static int countWords(String line) {
      return line.split("\\W").length;
    }

    private static void printResult(CountResult result) {
      System.out.println("wordCount = " + result.getWordCount());
      System.out.println("lineCount = " + result.getLineCount());
      System.out.println("charCount = " + result.getCharCount());
    }

    private static void printError(IOException e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
