// Singleton 연습 : 김밥 인스턴스를 한 개만 생성하도록  Singleton 패턴을 적용하라. 
package com.eomcs.design_pattern.singleton;

class Kimbap {
  // 클래스 안의 코드를 완성하시오.
}

public class Test03 {
  public static void main(String[] args) {
    // 다음 코드는 컴파일 오류를 발생시켜야 한다.
    //Kimbap bap1 = new Kimbap();

    Kimbap bap2 = Kimbap.getInstance();
    Kimbap bap3 = Kimbap.getInstance();
    System.out.println(bap2 == bap3); // true
  }
}








