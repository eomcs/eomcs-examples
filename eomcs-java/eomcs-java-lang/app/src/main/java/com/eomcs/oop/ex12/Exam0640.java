// 메서드 레퍼런스 - 활용예
package com.eomcs.oop.ex12;

import java.util.function.Predicate;

public class Exam0640 {

  static class My {
    public boolean m() {
      return true;
    }
  }

  public static void main(String[] args) {

    //    interface Predicate<T> {
    //      boolean test(T value);
    //      ...
    //    }

    // 기존의 My의 m()을 Predicate<T>의 test() 메서드를 구현하는데 사용하기

    // 1) 람다 표현식으로 구현하기
    Predicate<My> obj =
        (My value) -> {
          return value.m();
        };

    // 2) 람다 표현식에서 파라미터 타입과 return 문 생략하기
    Predicate<My> obj2 = value -> value.m();

    // 3) 메서드 레퍼런스 사용하기 - 바로 위의 코드를 작성하는 경우가 많다 보니 다음과 같은 단축 문법이 등장!
    // - T 타입과 클래스가 같다면, 인스턴스 메서드를 메서드 레퍼런스로 사용할 수 있다.
    Predicate<My> obj3 = My::m; // OK!
    // 위 코드는 다음의 람다 표현식과 같다.
    //   Predicate<My> obj3 = (My value) -> { return value.m(); };

    // 4) T 타입과 클래스가 다르면, 인스턴스 메서드를 메서드 레퍼런스로 사용할 수 없다.
    //    Predicate<String> obj4 = My::m; // 컴파일 오류!
    // 위 코드는 다음의 람다 표현식과 같다.
    //    Predicate<String> obj4 = (String value) -> { return My.m(value); };
    // - m()은 static 메서드가 아니기 때문에, My.m()처럼 호출할 수 없다.

    // 5) T 타입과 클래스가 다르면, 인스턴스 메서드 레퍼런스의 시그너처가 유효해야 한다.
    //    My obj5 = new My();
    //    Predicate<String> p2 = obj::m; // 컴파일 오류!
    // 위 코드는 다음의 람다 표현식과 같다.
    //    Predicate<String> p2 = (String value) -> { return obj.m(value); };
    // - My 클래스의 인스턴스로 m()을 호출하는 것은 가능하다.
    // - 하지만, m()은 String 파라미터를 못받는다.
  }
}
