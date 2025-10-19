// # 아이템 53. 가변인수는 신중히 사용하라
// [가변인수(varargs) 메서드]
// - 명시한 타입의 아규먼트를 0개 이상 받을 수 있다.
// - 가변인수 메서드를 호출하면,
//   가장 먼저 아규먼트의 개수와 길이가 같은 배열을 만들고, 이 배열에 저장하여 메서드에 전네준다.
// - 아규먼트 개수가 일정하지 않을 경우 유용하다.
// - 필수 파라미터는 가변인수 앞에 두고, 가변인수를 사용할 때 성능 문제까지 고려하자.
//

package effectivejava.ch08.item53.exam02;

// [주제] 아규먼트가 한 개 이상이어야 하는 경우, 가변인수 메서드를 잘못 구현한 예

public class Test {

  static int min(int... args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("최소한 하나의 아규먼트는 필요합니다.");
    }
    int min = args[0];
    for (int i = 1; i < args.length; i++) {
      if (args[i] < min) {
        min = args[i];
      }
    }
    return min;
  }

  public static void main(String[] args) {
    System.out.println(min(3, 7, 4, 2, 5, 9)); // 2
    //    System.out.println(min()); // IllegalArgumentException

    // [정리]
    // - 최소 1개 이상의 아규먼트를 받아야 하는 경우,
    //   가변인수 메서드를 작성하면 처음 부분에서 아규먼트의 개수를 검사해야 한다.
    //   왜? 아규먼트없이 호출할 수 있기 때문이다.
    // - min()의 경우 for-each문을 사용할 수도 없다.
    // - 컴파일타임에서 오류를 검증할 수 없다.
    //   런타임에서 실패여부를 확인할 수 있다.
    // - 코드도 지저분하다.
  }
}
