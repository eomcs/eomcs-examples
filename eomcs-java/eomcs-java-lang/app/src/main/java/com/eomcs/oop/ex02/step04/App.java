package com.eomcs.oop.ex02.step04;

// # 관련된 기능(메서드)을 묶어 분류하기
// 1) 분류 전
// 2) 메서드를 클래스로 묶어 분류하기
// 3) 클래스 변수 도입
// 4) 클래스 변수의 한계 확인
//
public class App {

  public static void main(String[] args) {

    // 다음 두 개의 식을 분리하여 계산해 보자!
    // - 연산자 우선 순위를 고려하지 않고 순서대로 계산하라!
    // 식1) 2 + 3 - 1 * 7 / 3 = ?
    // 식2) 3 * 2 + 7 / 4 - 5 = ?

    // 클래스 변수는 오직 한 개만 존재하기 때문에
    // 동시에 여러 개의 계산을 진행할 수 없다.
    // 한 개의 식을 먼저 계산한 후에 다른 식을 순차적으로 계산해야 한다.

    // 식1 계산:
    Calculator.plus(2); // + 2
    Calculator.plus(3); // + 2 + 3
    Calculator.minus(1); // + 2 + 3 - 1
    Calculator.multiple(7); // + 2 + 3 - 1 * 7
    Calculator.divide(3); // + 2 + 3 - 1 * 7 / 3 = ?

    System.out.printf("result = %d\n", Calculator.result);
    // 이렇게 계산을 완료한 후 다음 식을 계산해야 한다.

    // 식2 계산:
    // 다른 식을 계산하기 전에 기존의 계산 결과를 갖고 있는
    // result 변수를 0으로 초기화시켜야 한다.
    Calculator.result = 0;

    Calculator.plus(3); // + 3
    Calculator.multiple(2); // + 3 * 2
    Calculator.plus(7); // + 3 * 2 + 7
    Calculator.divide(4); // + 3 * 2 + 7 / 4
    Calculator.minus(5); // + 3 * 2 + 7 / 4 - 5 = ?

    System.out.printf("result = %d\n", Calculator.result);
  }
}

