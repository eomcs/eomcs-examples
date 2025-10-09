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

package effectivejava.ch05.item26.exam01;

// [주제] parameterized type 을 사용하는 예

import java.util.ArrayList;
import java.util.Collection;

public class Test {
  public static void main(String[] args) throws Exception {
    Collection<String> names = new ArrayList<>();
    names.add("홍길동");
    names.add("임꺽정");
    //    names.add(100); // 컴파일 오류 발생!

    // 컬렉션에 들어 있는 값을 꺼낼 때도 형변환이 필요 없다.
    // 컴파일러가 값을 꺼내는 부분에 자동으로 형변환을 추가한다.
    for (String name : names) {
      System.out.println(name);
    }

    // [결론]
    // - 오류를 컴파일할 때 발견할 수 있다.

    // [용어 정리]
    // parameterized type: List<String>
    // actual type parameter: String
    // generic type: List<E>
    // formal type parameter: E
    // unbounded wildcard type: List<?>
    // raw type: List
    // bounded type parameter: <T extends Number>
    // recursive type bound: <T extends Comparable<T>>
    // bounded wildcard type: List<? extends Number>
    // generic method: <T> T get(T t);
    // type token: String.class
  }
}
