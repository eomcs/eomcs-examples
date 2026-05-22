package com.eomcs.cleancode.ch02.exam15;

public class BadAndGood5 {

  // Bad
  // - number, verb, pluralModifier 변수가 통계 추측 메시지에 사용된다는 사실은
  //   코드를 읽어 봐야 유추할 수 있다. 변수 이름만으로는 맥락이 부족하다.
  private void printGuessStatistics(char candidate, int count) {
    String number;
    String verb;
    String pluralModifier;

    if (count == 0) {
      number = "no";
      verb = "are";
      pluralModifier = "s";
    } else if (count == 1) {
      number = "1";
      verb = "is";
      pluralModifier = "";
    } else {
      number = Integer.toString(count);
      verb = "are";
      pluralModifier = "s";
    }
    String guessMessage =
        String.format("There %s %s %s%s", verb, number, candidate, pluralModifier);
    System.out.print(guessMessage);
  }

  // Good (완전한 맥락)
  // - number, verb, pluralModifier 세 변수를 GuessStatistics라는 클래스로 묶으면
  //   이 클래스가 통계 추측 메시지에 사용된다는 사실이 명확해진다.
  // - 변수 이름과 클래스 이름이 함께 맥락을 제공한다.
  static class GuessStatistics {
    private String number;
    private String verb;
    private String pluralModifier;

    public String make(char candidate, int count) {
      createPluralDependentMessage(count);
      return String.format("There %s %s %s%s", verb, number, candidate, pluralModifier);
    }

    private void createPluralDependentMessage(int count) {
      if (count == 0) {
        thereAreNoLetters();
      } else if (count == 1) {
        thereIsOneLetter();
      } else {
        thereAreManyLetters(count);
      }
    }

    private void thereAreManyLetters(int count) {
      number = Integer.toString(count);
      verb = "are";
      pluralModifier = "s";
    }

    private void thereIsOneLetter() {
      number = "1";
      verb = "is";
      pluralModifier = "";
    }

    private void thereAreNoLetters() {
      number = "no";
      verb = "are";
      pluralModifier = "s";
    }
  }
}
