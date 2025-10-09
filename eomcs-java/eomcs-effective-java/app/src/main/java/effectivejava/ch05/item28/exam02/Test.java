// # 아이템 28. 배열보다는 리스트를 사용하라
// - 배열은 공변(함께 변한다는 의미)이다.
//   Sub가 Super의 하위 타입이라면, Sub[]는 Super[]의 하위 타입이다.
// - 제네릭은 불공변이다.
//   서로 다른 Type1, Type2에 대해, Type1<T>와 Type2<T>는 아무런 관계가 없다.
//

package effectivejava.ch05.item28.exam02;

// [주제] 제네릭 배열 생성 불가 확인하기
// - 타입이 안전하지 않게 된다.
// - 배열의 공변성이 제네릭의 불공변성과 충돌하기 때문이다.

public class Test {

  static <T> void m() {
    // 1) generic type으로 배열 생성 불가
    //    List<T>[] arr = new List<T>[10];

    // 2) parameterized type으로 배열 생성 불가
    //    List<String>[] arr2 = new List<String>[10];

    // 3) type parameter로 배열 생성 불가
    //    T[] arr3 = new T[10];
  }

  public static void main(String[] args) throws Exception {
    //    // 만약 제네릭 타입의 배열이 가능하다면?
    //    List<String>[] arr = new List<String>[10];
    //
    //    // 1) Integer 리스트를 생성한다.
    //    List<Integer> intList = List.of(100, 200, 300);
    //
    //    // 2) Object[] 배열은 공변이기 때문에 모든 타입의 배열을 담을 수 있다.
    //    Object[] objects = arr;
    //
    //    // 3) objects[0]의 타입은 Object 이기 때문에 어떤 인스턴스의 주소라도 담을 수 있다.
    //    // objects[0]에 List<Integer>가 들어갔다.
    //    objects[0] = intList; // 실제로는 arr[0]에 List<Integer>가 들어갔다.
    //

    //    // 4) arr[0]에 들어있는 값을 꺼내보자.
    //    // - arr[0]의 타입은 List<String>이다.
    //    // - 그래서 String 값을 꺼낼 것이라 예상하고 String으로 자동 형변환을 시도할 것이다.
    //    // - 여기서 문제가 발생하는 것이다.
    //    //   실제 arr[0]에는 List<Integer>가 들어있기 때문이다.
    //    String s = arr[0].get(0); // ClassCastException 발생!

    // [정리]
    // 제네릭을 사용하는 이유는 타입 안전성을 확보하기 위해서이다.
    // 컴파일 시 타입 검사를 수행하지 못한다면, 제네릭의 존재 이유가 없다.
    // 이런 일을 방지하기 위해 제네릭 배열을 금지한 것이다.
  }
}
