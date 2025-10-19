// # 아이템 49. 매개변수가 유효한지 검사하라
// - "오류는 가능한 한 빨리 발견하는 것이 좋다"는 원칙에 따라,
//   메서드나 생성자의 몸체가 시작되기 전에 검사하는 것이 좋다.
//   오류가 발생한 즉시 잡지 못하면, 해당 오류를 감지하기 어려워지고
//   감지하더라도 오류의 발생 지점을 찾기 어려워진다.
// - 메서드 몸체가 실행되기 전에 매개변수를 확인한다면 잘못된 값이 넘어왔을 때
//   즉각적이고 깔끔한 방식으로 예외를 던질 수 있다.
//   매개변수 검사에 실패하면 "실패 원자성(failure atomicity)"을 어기는 결과를 낳을 수 있다.
// - public과 protected 메서드는 매개변수 값이 잘못됐을 때 던지는 예외를 문서화해야 한다.
//   (예: NullPointerException, IllegalArgumentException 등)
//

package effectivejava.ch08.item49.exam03;

// [주제] 공개되지 않은 메서드라면 assert 문을 사용하라.

public class Test {

  private static void sort(long a[], int offset, int length) {
    // assert 문
    // - 조건이 무조건 참이라고 선언한다.
    // - 실패하면 AssertionError를 던진다.
    // - 런타임에 아무런 효과도 아무런 성능 저하도 없다.
    // - JVM 옵션 -ea 또는 -enableassertions로 활성화해야 동작한다.
    assert a != null;
    assert offset >= 0 && offset <= a.length;
    assert length >= 0 && offset + length <= a.length;

    long[] subArray = new long[length];
    System.arraycopy(a, offset, subArray, 0, length);
    java.util.Arrays.sort(subArray);
    System.arraycopy(subArray, 0, a, offset, length);
  }

  public static void main(String[] args) {
    long[] array = {5, 3, 8, 1, 2};
    sort(array, 1, 3); // 부분 배열 정렬
    System.out.println(java.util.Arrays.toString(array)); // [5, 1, 3, 8, 2]

    // assert 문이 활성화되었는지 확인하기
    boolean assertsEnabled = false;
    assert assertsEnabled = true;
    System.out.println("asserts enabled: " + assertsEnabled);

    //    sort(null, 1, 3); // assert 문에 걸림
    //    sort(array, -1, 3); // assert 문에 걸림
    //    sort(array, 1, 5); // assert 문에 걸림

    // [매개변수 유효성 검사 예외 상황]
    // - 유효성 검사 비용이 지나치게 높거나 실용적이지 않을 때
    // - 계산 과정에서 암묵적으로 검사가 수행될 때
    //   예:
    //     Collections.sort(list)
    //     - 정렬 과정에서 비교될 수 없는 객체가 들어 있다면, ClassCastException이 발생한다.
    //     - 따라서 비교하기 앞서 리스트 안의 모든 객체가 상호 비교될 수 있는지 검사해봐야 실익이 없다.
    //     - 다만 암묵적 유효성 검사에 의존하는 것은 "실패 원자성"을 해칠 수 있으니 주의해야 한다.
    //
    // [정리]
    // - 메서드나 생성자를 작성할 때 그 매개변수가 어떤 제약이 있을지 생각해야 한다.
    // - 그 제약들을 문서화하고 메서드 코드 시작 부분에서 검사해야 한다.
    //
  }
}
