// 상속 - Generalization 수행 전
package com.eomcs.oop.ex05.k;

import com.eomcs.oop.ex05.j.Sedan;
import com.eomcs.oop.ex05.j.Truck;

// 상속의 종류
// 1) specialization
//    => 가장 많이 사용하는 방법으로 수퍼 클래스를 상속 받아 서브 클래스를 만드는 것이다.
//    => 수퍼클래스에 새 특징을 추가하거나 새 기능을 추가하여 더 특별한 일을 수행하는 서브클래스를 만든다.
//       그래서 이런 상속을 "특수화/전문화(specialization)"이라 부른다.
// 2) generalization
//    => 리팩토링 과정에 수행하는 방법이다.
//    => 서브클래스들의 공통 분모를 추출하여 수퍼클래스를 정의하는 방법을 말한다.
//    => 그래서 이런 상속을 "일반화/표준화(generalization)"이라 부른다.
//
// Sedan 클래스와 Truck 클래스의 공통 분모를 추출하여 
// Car라는 클래스를 정의하고,
// 두 클래스는 이렇게 새로 만든 Car 클래스를 상속 받도록 한다.
public class Exam01 {

  public static void main(String[] args) {
    // 이렇게 Sedan과 Truck 클래스를 만들어 쓰다가 보니
    // 두 클래스 사이에 공통 코드가 발견되었다.
    // => 유지보수를 쉽게하기 위해 공통 코드를 추출하여 중복 코드를 없앨 필요가 있었다.
    // => 다음 예제를 보라!
    Sedan s = new Sedan();
    Truck t = new Truck();

    s.doSunroof(true);
    t.dump();
  }

}
