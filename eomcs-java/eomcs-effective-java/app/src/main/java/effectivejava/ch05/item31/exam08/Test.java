// # 아이템 31. 한정적 와일드카드를 사용해 API 유연성을 높이라
// - parameterized type은 불공변이다.
//   Type1과 Type2가 서로 다른 타입일 때,
//   List<Type1>과 List<Type2>는 서로 상속 관계가 아니다.
//   예) List<Object>와 List<String>는 서로 상속 관계가 아니다.
//   List<Object>에는 어떤 객체든 넣을 수 있지만,
//   List<String>에는 String 객체만 넣을 수 있다.
//   List<Object> 자리에 List<String>을 넣지 못한다.
//   즉 리스코프 치환 원칙에 어긋난다.
//

package effectivejava.ch05.item31.exam08;

// [주제] type parameter와 와일드카드 비교
// - 메서드 선언에 type parameter가 한 번만 나오면 와일드카드로 대체하라.
// - 메서드가 더 간단해지고 직관적이 된다.

import java.util.ArrayList;
import java.util.List;

public class Test {

  // type parameter가 한 번만 나오므로 와일드카드로 대체할 수 있다.
  static <E> void swap(List<E> list, int i, int j) {
    list.set(i, list.set(j, list.get(i)));
  }

  // 와일드카드로 대체한다.
  static void swap2(List<?> list, int i, int j) {
    // 문제는 List<?>에는 null 외에는 어떤 것도 넣을 수 없다는 점이다.
    //    list.set(i, list.set(j, list.get(i))); // 컴파일 오류!

    // 해결책: 와일드카드의 실제 타입을 알려주는 도우미 메서드를 따로 작성하여 활용한다.
    // - 이런 방식으로 사용하면 단순 와일드카드 메서드도 public API로 제공할 수 있다.
    swapHelper(list, i, j);
  }

  // 도우미 메서드: private으로 감추고 public API에는 노출하지 않는다.
  // - 실제 타입을 알라내려면 제네릭 메서드로 정의해야 한다.
  private static <E> void swapHelper(List<E> list, int i, int j) {
    // 이 메서드는 List의 원소 타입이 E 임을 알고 있다.
    // - 이 리스트에서 꺼낸 원소의 타입은 항상 E임을 알고 있다.
    // - E 타입의 값이라면 리스트에 넣어도 안전함을 알고 있다.
    // 그러므로 컴파일러는 다음 문장의 타입 안전성을 명확하게 검사하고 보장할 수 있다.
    list.set(i, list.set(j, list.get(i)));
  }

  public static void main(String[] args) throws Exception {
    List<String> list = new ArrayList<>(List.of("A", "B", "C"));
    swap(list, 0, 2);
    System.out.println(list);

    List<String> list2 = new ArrayList<>(List.of("X", "Y", "Z"));
    swap2(list2, 0, 2);
    System.out.println(list2);
  }
}
