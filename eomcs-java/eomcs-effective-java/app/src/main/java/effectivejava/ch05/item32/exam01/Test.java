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

package effectivejava.ch05.item32.exam01;

// [주제] 제네릭과 varargs(가변인수)를 혼합하면 타입 안정성이 깨진다.
// - 제네릭 varargs 배열 파라미터에 값을 저장하는 것은 안전하지 않다.

import java.util.List;

public class Test {

  // 다음은 제네릭 타입을 가변인수 파라미터로 선언했을 때 발생하는 문제를 보여준다.
  static void dangerous(List<String>... stringLists) {
    // 임의의 List<Integer> 객체를 만든다.
    List<Integer> intList = List.of(42);

    // List<String>... -> List<String>[] -> Object[]
    // - 가변인수 파라미터 stringLists는 List<String>[] 배열이다.
    // - Object[] 배열 레퍼런스는 모든 타입의 배열 객체의 주소를 저장할 수 있다.
    // - 따라서 List<String>[] 배열의 주소도 Object[] 배열 레퍼런스에 저장할 수 있다.
    Object[] array = stringLists;

    // Object[] 배열의 0번째 요소에 List<Integer> 객체의 주소를 저장한다.
    // - Object[] 배열에는 어떤 타입의 객체라도 저장할 수 있기 때문에 컴파일러는 오류를 내지 않는다.
    //   바로 여기서 제네릭이 무력화되는 것이다.
    // - 참담하게도 이제 stringLists[0]에는 List<Integer> 객체의 주소가 들어 있다.
    //   그런데도 컴파일러는 아무런 경고를 내지 않는다. 문법상 아무런 문제가 없기 때문이다.
    //   컴파일 시 타입 안정성을 검사하기 위해 사용했던 제네릭이 쓸모가 없어지는 것이다.
    array[0] = intList; // 타입 안정성 깨짐

    // stringLists[0]의 원래 타입은 List<String>이다.
    // 그래서 당연히 get()의 리턴 값은 String일 것이라고 생각한다.
    // 하지만 stringLists[0]에는 List<Integer> 객체의 주소가 들어 있다.
    // 런타임 시에 오류가 발생한다.
    String s = stringLists[0].get(0); // ClassCastException 발생 가능
  }

  public static void main(String[] args) throws Exception {
    List<String> stringList1 = List.of("홍길동", "임꺽정", "장길산");
    List<String> stringList2 = List.of("짜장면", "짬뽕", "볶음밥");
    dangerous(stringList1, stringList2); // 런타임 오류 발생!
  }
}
