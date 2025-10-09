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

package effectivejava.ch05.item26.exam06;

// [주제] 제네릭 타입의 값을 파라미터로 받는 메서드를 정의할 때: unbounded wildcard type 사용

import java.util.ArrayList;
import java.util.List;

public class Test {

  // unbounded wildcard type을 사용한 메서드
  // - List<?> : 어떤 타입의 값을 담는 List인지 상관하지 않겠다고 컴파일러에게 명시한다.
  static void add(List<?> list, Object value) {

    // 다만 List에 넣는 타입이 뭔지 모르기 때문(?)에
    // 컴파일러는 List에 값을 넣는 것을 허락하지 않는다.
    //    list.add(value); // 컴파일 오류발생!

    // 값을 넣을 수 없는 상태가 되었지만
    // 최소한, 컬렉션의 타입 불변식을 훼손하지 못하게 막았다.
    // 해결 방법은? 다음 add2() 메서드처럼 정확하게 타입을 지정하는 것이다.
  }

  static void add2(List<String> list, String value) {
    list.add(value); // 컴파일 오류발생!
  }

  public static void main(String[] args) throws Exception {
    List<String> names = new ArrayList<>();

    // add() 메서드가 아규먼트로 List를 받을 때
    // 그 List가 어떤 타입의 값을 담는지 상관하지 않겠다고 선언했기 때문에
    // 컴파일러는 경로를 띄우지 않는다.
    add(names, "홍길동");

    String s = names.get(0);
  }
}
