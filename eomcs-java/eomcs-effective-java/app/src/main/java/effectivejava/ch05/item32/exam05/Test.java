// # 아이템 32. 제네릭과 가변인수를 함께 쓸 때는 신중하라
// [가변인수]
// - 가변인수 메서드를 호출하면 가변인수를 담기 위한 배열이 자동으로 하나 만들어진다.
// - 제네릭 배열은 직접 만들 수 없지만, 가변인수 파라미터에는 허용한다.
//      List<String>[] stringLists = new List<String>[5]; // 컴파일 오류!
//      void method(List<String>... stringLists) {} // 허용!
// - 다만 제네릭과 가변인수를 혼합하면 타입 안정성이 깨질 수 있다.
//
// [제네릭 varargs 파라미터가 선언된 메서드]
// - 힙 오염(heap pollution) 경고가 뜨는 메서드가 있다면 다음 조건에 만족하도록 수정하라.
//   - varargs 파라미터 배열에 아무것도 저장하지 않는다
//   - varargs 파라미터 배열의 참조가 밖으로 노출되지 않는다
// - 그리고 @SafeVarargs 애노테이션을 붙여서 컴파일러 경고를 억제하라.
//   그래야 사용자를 헷갈리게 하는 컴파일러 경고를 없앨 수 있다.
//   단, 이 애너테이션은 재정의할 수 없는 메서드에만 달아야 한다.
//   Java 8: static 메서드, final 인스턴스 메서드에만 붙일 수 있다.
//   Java 9: private 인스턴스 메서드에도 붙일 수 있다.

package effectivejava.ch05.item32.exam05;

// [주제] 제네릭 varargs 파라미터 대신 List와 List.of() 사용하기

import java.util.List;

public class Test {

  // 배열 대신 List를 사용하면 제네릭을 사용하여 타입 안전성을 보장할 수 있다.
  static <T> List<T> pickTwo(T a, T b, T c) {
    switch ((int) (Math.random() * 3)) {
      case 0:
        return List.of(a, b);
      case 1:
        return List.of(a, c);
      case 2:
        return List.of(b, c);
    }
    throw new AssertionError(); // 도달할 수 없는 곳
  }

  public static void main(String[] args) throws Exception {
    List<String> list = pickTwo("홍길동", "임꺽정", "장길산");
    System.out.println(list); // OK!
  }
}
