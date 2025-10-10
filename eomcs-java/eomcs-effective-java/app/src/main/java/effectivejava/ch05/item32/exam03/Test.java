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

package effectivejava.ch05.item32.exam03;

// [주제] 제네릭 varargs 파라미터를 안전하게 사용하는 방법
// - varargs 파라미터 배열에 아무것도 저장하지 않는다면 타입 안전하다.
// - varargs 파라미터 배열의 참조가 밖으로 노출되지 않는다면 타입 안전하다.
// - 즉, varargs 파라미터 배열이 순수하게 아규먼트들을 전달하는 일만 한다면 그 메서드는 안전하다.
// - 안전함이 보장되는 메서드에는 @SafeVarargs 애노테이션을 붙여서 컴파일러 경고를 억제하라.
//   그래야 사용자를 헷갈리게 하는 컴파일러 경고를 없앨 수 있다.

import java.util.ArrayList;
import java.util.List;

public class Test {

  // 다음은 제네릭 varargs 파라미터 배열을 안전하게 사용한 메서드이다.
  @SafeVarargs // 컴파일러 경고 억제
  static <T> List<T> flatten(List<? extends T>... lists) {
    List<T> result = new ArrayList<>();
    for (List<? extends T> list : lists) {
      result.addAll(list);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    List<Integer> intList = List.of(1, 2, 3);
    List<Double> doubleList = List.of(1.1, 2.2, 3.3);
    List<Long> longList = List.of(10L, 20L, 30L);
    List<Number> numberList = flatten(intList, doubleList, longList);
    System.out.println(numberList);
  }
}
