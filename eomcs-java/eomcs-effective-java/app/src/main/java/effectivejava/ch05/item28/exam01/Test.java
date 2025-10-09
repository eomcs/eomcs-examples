// # 아이템 28. 배열보다는 리스트를 사용하라
// - 배열은 공변(함께 변한다는 의미)이다.
//   Sub가 Super의 하위 타입이라면, Sub[]는 Super[]의 하위 타입이다.
// - 제네릭은 불공변이다.
//   서로 다른 Type1, Type2에 대해, Type1<T>와 Type2<T>는 아무런 관계가 없다.
//

package effectivejava.ch05.item28.exam01;

// [주제] 배열과 제네릭의 차이점 확인하기

public class Test {
  public static void main(String[] args) throws Exception {
    // Long 배열에 String 저장 시도
    Object[] objects = new Long[1]; // OK
    objects[0] = "문자열"; // 런타임 오류!

    // Long 리스트에 String 저장 시도
    //    List<Object> list = new ArrayList<Long>(); // 컴파일 오류!

    // 배열의 실수는 런타임에 발견된다.
    // 제네릭의 실수는 컴파일 타임에 발견된다.
    // 따라서 제네릭이 배열보다 타입 안전성이 더 뛰어나다.

    // [정리]
    // - 제네릭의 타입 정보는 컴파일 후, 런타임에 지워진다.(타입 소거)
    //   그래서 원소의 타입을 컴파일 시에만 검사하며, 런타임에는 알수조차 없다.
    // - 배열은 런타임에서도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
  }
}
