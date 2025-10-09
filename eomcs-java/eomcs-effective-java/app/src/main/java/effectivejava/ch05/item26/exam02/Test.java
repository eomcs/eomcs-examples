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

package effectivejava.ch05.item26.exam02;

// [주제] raw type 을 사용하는 예

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Test {
  public static void main(String[] args) throws Exception {
    Collection names = new ArrayList();
    names.add("홍길동");
    names.add("임꺽정");
    names.add(100); // 실수로 이름 대신 정수를 넣어도 컴파일 오류가 발생하지 않는다.

    // 컬렉션에 들어 있는 값을 꺼낼 때 오류가 발생한다.
    for (Iterator i = names.iterator(); i.hasNext(); ) {
      String name = (String) i.next(); // ClassCastException 발생
      System.out.println(name);
    }

    // [결론]
    // - 오류는 컴파일 할 때 발견하는 것이 가장 좋다.
    // - raw type을 사용하면 컴파일러가 타입 검사를 하지 않으므로,
    //   컬렉션에 잘못된 타입의 객체가 들어가는 것을 막을 수 없다.
    // - raw type을 사용하면 제네릭이 안겨주는 안전성과 표현력을 모두 잃게 된다.
    // - raw type이 존재하는 이유는 이전 코드와의 호환성 때문이다.
  }
}
