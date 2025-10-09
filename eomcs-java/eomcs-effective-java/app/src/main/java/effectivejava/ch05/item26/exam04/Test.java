// # 아이템 26. 로 타입(raw types)은 사용하지 말라
// - 클래스와 인터페이스에 타입 파라미터가 쓰이면 이를 제네릭 클래스 혹은 제네릭 인터페이스라 한다.
//   예) interface List<E> {}
//   예) class Class<T> {}
//   제네릭 클래스나 제네릭 인터페이스를 통틀어 "제네릭 타입"이라 부른다.
// - 제네릭 타입 사용: 클래스명<타입,타입,...>
//   예) List<String> list;
//   "매개변수화 타입(parameterized type)"이라 부른다.
//   String이 타입 파라미터 E에 해당하는 실제 타입 파라미터이다.
// - 제네릭 타입을 타입 파라미터 없이 사용하면 "로 타입(raw type)"이라 부른다.
//   예) List list; // raw type
//   제네릭이 도입되기 전 코드와 호환되도록 하기 위한 궁여지책이다.

package effectivejava.ch05.item26.exam04;

// [주제] 제네릭 타입의 값을 파라미터로 받는 메서드를 정의할 때: raw type 사용

import java.util.ArrayList;
import java.util.List;

public class Test {

  // raw type을 사용한 메서드
  static void add(List list, Object value) {
    list.add(value);
  }

  public static void main(String[] args) throws Exception {
    List<String> names = new ArrayList<>();
    add(names, "홍길동"); // raw type을 안전하게 사용할 때
    String s = names.get(0);

    // 그런데 개발자가 다음과 같이 사용해도 컴파일러가 막지 못한다.
    // - 단지 컴파일 시 '안전하지 않을 수 있다'는 경고만 뜰 뿐이다.
    add(names, Integer.valueOf(42));
    String s2 = names.get(1); // 런타임에 ClassCastException 발생!
  }
}
