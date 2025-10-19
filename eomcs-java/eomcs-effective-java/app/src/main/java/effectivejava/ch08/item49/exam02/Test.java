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

package effectivejava.ch08.item49.exam02;

// [주제] null 여부를 검사할 때 java.util.Objects.requireNonNull를 사용하라.

import java.util.Objects;

public class Test {

  public static String sort(String str) {
    // null 검사를 수동으로 하는 대신 다음과 같이 작성하라.
    Objects.requireNonNull(str, "str은 null일 수 없습니다.");

    char[] chars = str.toCharArray();
    java.util.Arrays.sort(chars);
    return new String(chars);
  }

  public static void main(String[] args) {
    String s1 = sort("effectivejava");
    System.out.println(s1);

    String s2 = sort(null); // NullPointerException 발생
    System.out.println(s2);

    // [Objects의 범위 검사 메서드들]
    // checkIndex(int index, int size):
    //   - index가 0 이상 size 미만인지 검사
    // checkFromToIndex(int fromIndex, int toIndex, int size):
    //   - fromIndex가 0 이상 size 이하인지,
    //   - toIndex가 fromIndex 이상 size 이하인지 검사
    // checkFromIndexSize(int fromIndex, int size, int arraySize):
    //   - fromIndex가 0 이상 arraySize 이하인지,
    //   - size가 음수가 아닌지,
    //   - fromIndex + size가 arraySize 이하인지 검사
  }
}
