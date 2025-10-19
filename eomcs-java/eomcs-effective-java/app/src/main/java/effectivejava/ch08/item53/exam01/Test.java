// # 아이템 53. 가변인수는 신중히 사용하라
// [가변인수(varargs) 메서드]
// - 명시한 타입의 아규먼트를 0개 이상 받을 수 있다.
// - 가변인수 메서드를 호출하면,
//   가장 먼저 아규먼트의 개수와 길이가 같은 배열을 만들고, 이 배열에 저장하여 메서드에 전네준다.
// - 아규먼트 개수가 일정하지 않을 경우 유용하다.
// - 필수 파라미터는 가변인수 앞에 두고, 가변인수를 사용할 때 성능 문제까지 고려하자.
//

package effectivejava.ch08.item53.exam01;

// [주제] 간단한 가변인수 활용 예

public class Test {

  static int sum(int... args) {
    int sum = 0;
    for (int i : args) {
      sum += i;
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(sum(1, 2, 3)); // 6
    System.out.println(sum()); // 0
  }
}
