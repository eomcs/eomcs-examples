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

package effectivejava.ch05.item26.exam03;

// [주제] raw type처럼 임의 객체를 허용하는 parameterized type 사용 예

import java.util.ArrayList;
import java.util.Collection;

public class Test {
  public static void main(String[] args) throws Exception {
    // 모든 타입을 허용한다고 컴파일러에게 명확히 전달한다.
    Collection<Object> values = new ArrayList<>();
    values.add("홍길동");
    values.add("임꺽정");
    values.add(100); // OK

    // 대신 컬렉션에서 값을 꺼낼 때 Object 타입으로 꺼내야 한다.
    for (Object value : values) {
      System.out.println(value);
    }
  }
}
