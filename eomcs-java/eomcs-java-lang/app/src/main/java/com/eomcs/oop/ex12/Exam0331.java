// 아규먼트에 람다(lambda) 활용 III - 여러 개의 문장이 있는 경우
package com.eomcs.oop.ex12;

public class Exam0331 {

  interface Calculator {
    int compute(int a, int b);
  }

  static void test(Calculator c) {
    System.out.println(c.compute(100, 200));
  }

  public static void main(String[] args) {
    // 익명 클래스로 정의한다면?
    test(new Calculator() {
      @Override
      public int compute(int a, int b) {
        int sum = 0;
        for (int i = a; i <= b; i++) {
          sum += i;
        }
        return sum;
      }
    });
  }

}


