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

package effectivejava.ch05.item32.exam02;

// [주제] 제네릭 가변인수 파라미터 배열의 참조를 노출할 때 발생하는 문제 확인

public class Test {

  // 가변인수 파라미터 배열의 참조를 그대로 리턴하는 메서드
  static <T> T[] toArray(T... args) {
    return args;
  }

  // T 타입 인수를 3개 받아 그 중 2개를 무작위로 골려 배열에 담아서 리턴하는 메서드
  static <T> T[] pickTwo(T a, T b, T c) {
    switch ((int) (Math.random() * 3)) {
      case 0:
        return toArray(a, b);
      case 1:
        return toArray(a, c);
      case 2:
        return toArray(b, c);
    }
    throw new AssertionError(); // 도달할 수 없는 곳
  }

  public static void main(String[] args) throws Exception {
    String[] arr = pickTwo("홍길동", "임꺽정", "장길산");
    System.out.println(arr); // 런타임 오류 발생!

    // [오류 원인 분석]
    // 1) pickTwo() 메서드에서 toArray() 메서드를 호출한다.
    //    - 컴파일러는 toArray()에 넘겨줄 가변인수 파라미터 배열을 만드는 코드를 생성한다.
    //    - 이 배열의 타입은 Object[] 이다. 어떤 타입이라도 담을 수 있기 때문이다.
    // 2) Object[] 배열에 String 객체 2개를 담아서 toArray()에 넘겨준다.
    // 3) toArray() 메서드는 파라미터로 받은 Object[] 배열을 그대로 리턴한다.
    // 4) pickTwo() 메서드는 toArray()가 리턴한 Object[] 배열을 그래도 리턴한다.
    // 5) main() 메서드의 다음 코드는 컴파일러가 자동 형변환 코드로 바꾼다.
    //        원래코드: String[] arr = pickTwo("홍길동", "임꺽정", "장길산");
    //        바뀐코드: String[] arr = (String[]) pickTwo("홍길동", "임꺽정", "장길산");
    //    - 여기서 런타임 오류가 발생한 것이다.
    //    - pickTwo()가 리턴한 배열의 실제 타입은 Object[]인데 String[]으로 형변환하려 했기 때문이다.
    // 런타임 오류의 내용을 보고 그 내용을 확인해보자!
    // 이렇게 "제네릭 가변인수 파라미터 배열에 다른 메서드가 접근하도록 허용하는 것"은 안전하지 않다.
  }
}
