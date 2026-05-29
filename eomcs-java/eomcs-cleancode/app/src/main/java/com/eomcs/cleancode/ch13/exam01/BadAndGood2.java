package com.eomcs.cleancode.ch13.exam01;

// 예제 2: 관심사 분리 - 각 단계를 독립적인 스레드로 분리하라
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: 모든 작업이 순차적으로 묶여 있다
  //   - 단계별 독립성이 없다
  //   - 각 단계가 이전 단계 완료를 기다려야 한다
  static class BadDataProcessor {

    public void process() {
      readData();
      transformData();
      saveData();
    }

    private void readData() {
      System.out.println("Reading data...");
    }

    private void transformData() {
      System.out.println("Transforming data...");
    }

    private void saveData() {
      System.out.println("Saving data...");
    }
  }

  // Good: 각 단계를 독립적인 스레드로 분리한다
  //   - 각 단계가 독립적으로 실행된다
  //   - 구조가 분리된다
  //   - 시스템이 더 확장 가능해진다
  static class DataProcessor {

    public void process() {
      Thread reader = new Thread(this::readData);
      Thread transformer = new Thread(this::transformData);
      Thread saver = new Thread(this::saveData);

      reader.start();
      transformer.start();
      saver.start();
    }

    private void readData() {
      System.out.println("Reading data...");
    }

    private void transformData() {
      System.out.println("Transforming data...");
    }

    private void saveData() {
      System.out.println("Saving data...");
    }
  }
}
