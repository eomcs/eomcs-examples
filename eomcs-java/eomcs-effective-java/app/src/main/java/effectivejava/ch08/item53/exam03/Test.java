// # 아이템 53. 가변인수는 신중히 사용하라
// [가변인수(varargs) 메서드]
// - 명시한 타입의 아규먼트를 0개 이상 받을 수 있다.
// - 가변인수 메서드를 호출하면,
//   가장 먼저 아규먼트의 개수와 길이가 같은 배열을 만들고, 이 배열에 저장하여 메서드에 전네준다.
// - 아규먼트 개수가 일정하지 않을 경우 유용하다.
// - 필수 파라미터는 가변인수 앞에 두고, 가변인수를 사용할 때 성능 문제까지 고려하자.
//

package effectivejava.ch08.item53.exam03;

// [주제] 아규먼트가 한 개 이상이어야 하는 경우 가변인수 메서드를 작성하는 올바른 예

public class Test {

  static int min(int firstArg, int... args) {
    // 인수가 0개인지 검사할 필요가 없다.
    int min = firstArg;

    // for-each문을 사용할 수 있다.
    for (int arg : args) {
      if (arg < min) {
        min = arg;
      }
    }
    return min;
  }

  public static void main(String[] args) {
    System.out.println(min(3, 7, 4, 2, 5, 9)); // 2
    //    System.out.println(min()); // 컴파일 단계에 오류를 감지할 수 있다.

    // [정리]
    // - 오류의 검증은 컴파일타임에 하는 것이 가장 좋다.
    // - 아규먼트가 한 개 이상이어야 하는 경우,
    //   가변인수 메서드를 작성할 때는 필수 아규먼트를 가변인수 앞에 두어라.
  }
}
