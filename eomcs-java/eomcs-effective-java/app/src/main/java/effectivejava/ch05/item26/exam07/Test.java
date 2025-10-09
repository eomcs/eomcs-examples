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

package effectivejava.ch05.item26.exam07;

// [주제] raw type 사용해도 되는 경우
// - class 리터럴에는 raw type을 써야 한다.
// - instanceof 연산자는 parameterized type을 다룰 수 없다.

import java.util.ArrayList;
import java.util.List;

public class Test {

  public static void main(String[] args) throws Exception {
    // 1) class 리터럴
    Class<?> clazz1 = List.class; // raw type
    Class<?> clazz2 = String[].class; // raw type
    //    Class<?> clazz3 = List<String>.class; // 문법 오류!
    //    Class<?> clazz3 = List<?>.class; // 문법 오류!

    // 2) instanceof 연산자
    List<String> list = new ArrayList<>();

    // - 런타임에는 제네릭 타입 정보가 지워지기 때문에,
    //   raw type이든 unbounded wildcard type이든 똑같이 동작한다.
    // - 굳이 List<?>를 써서 코드만 지저분하게 만들 필요가 없다.
    System.out.println(list instanceof List);
    System.out.println(list instanceof List<?>); // 비추!

    // 다만 instanceof 연산자로 검사한 다음에 다시 형변환 할 때는,
    // 타입 검사 경고가 뜨지 않도록 unbounded wildcard type을 써야 한다.
    if (list instanceof List) {
      List<?> list2 = (List<?>) list; // 타입 검사 경고 없음
      System.out.println(list2.size());
    }
  }
}
