// 1) 패턴 적용 전
// 2) private 생성자 + Factory Method 설계 패턴
//
package com.eomcs.design_pattern.test01;

import com.eomcs.design_pattern.test01.step2.Sedan;

public class Test02 {
  public static void main(String[] args) {
    Sedan sonata = Sedan.create("소나타");
    Sedan k7 = Sedan.create("K7");

    System.out.println(sonata);// sonata.toString() 리턴 값을 출력
    System.out.println(k7); // k7.toString() 리턴 값을 출력
  }
}
