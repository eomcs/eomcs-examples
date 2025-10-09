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

package effectivejava.ch05.item26.exam05;

// [주제] 제네릭 타입의 값을 파라미터로 받는 메서드를 정의할 때: parameterized type 사용

import java.util.ArrayList;
import java.util.List;

public class Test {

  // parameterized type을 사용한 메서드
  static void add(List<Object> list, Object value) {
    list.add(value);
  }

  public static void main(String[] args) throws Exception {
    List<String> names = new ArrayList<>();

    // 이번에는 메서드의 아규먼트 조건에 맞지 않아 컴파일 오류가 발생한다.
    //    add(names, "홍길동");

    String s = names.get(0);

    // List<Object> 는 Object 타입이라면 무엇이든 담을 수 있다.
    // List<String> 은 String 및 그 하위 타입만 담을 수 있다.
    // add() 메서드가 원하는 아규먼트는 String 만 담을 수 있는 List가 아니라
    // String 뿐만아니라 무엇이든 담을 수 있는 List이기 때문이다.
    //
  }
}
