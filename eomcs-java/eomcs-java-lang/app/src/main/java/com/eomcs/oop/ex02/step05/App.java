package com.eomcs.oop.ex02.step05;

// # 관련된 기능(메서드)을 묶어 분류하기
// 1) 분류 전
// 2) 메서드를 클래스로 묶어 분류하기
// 3) 클래스 변수 도입
// 4) 클래스 변수의 한계 확인
// 5) 인스턴스 변수 도입
//
public class App {

  public static void main(String[] args) {

    // 다음 두 개의 식을 분리하여 계산해 보자!
    // - 연산자 우선 순위를 고려하지 않고 순서대로 계산하라!
    // 식1) 2 + 3 - 1 * 7 / 3 = ?
    // 식2) 3 * 2 + 7 / 4 - 5 = ?

    // 두 개의 식을 동시에 계산하고 싶은가?
    // 그럴려면 계산 결과를 개별적으로 관리할 수 있어야 한다.
    // 다음과 같이 각 식의 계산 결과를 보관할 메모리를 준비한다.
    Calculator c1 = new Calculator();
    Calculator c2 = new Calculator();

    // 계산을 수행할 때 어떤 result 변수를 사용할 것인지 
    // 메서드에 알려줘야 한다. 
    Calculator.plus(c1, 2); 
    Calculator.plus(c2, 3); 

    Calculator.plus(c1, 3); 
    Calculator.multiple(c2, 2);

    Calculator.minus(c1, 1); 
    Calculator.plus(c2, 7); 

    Calculator.multiple(c1, 7); 
    Calculator.divide(c2, 4);

    Calculator.divide(c1, 3); 
    Calculator.minus(c2, 5); 

    // 식1의 계산 결과는 c1 인스턴스의 result 변수에 들어 있고,
    // 식2의 계산 결과는 c2 인스턴스의 result 변수에 들어 있다.
    System.out.printf("result = %d\n", c1.result);
    System.out.printf("result = %d\n", c2.result);
  }
}

