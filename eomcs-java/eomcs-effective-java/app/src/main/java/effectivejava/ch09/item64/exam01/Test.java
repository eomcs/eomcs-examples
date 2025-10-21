// # 아이템 64. 객체는 인터페이스를 사용해 참조하라
// - 적합한 인터페이스만 있다면
//   매개변수뿐 아니라 반환값, 변수, 필드를 전부 인터페이스 타입으로 선언하라.
// - 적합한 인터페이스가 없다면
//   클래스의 계층구조 중 필요한 기능을 만족하는 가장 덜 구체적인 상위 클래스를 타입으로 사용하라.
//
package effectivejava.ch09.item64.exam01;

// [주제] 인터페이스 vs 클래스

import java.util.LinkedHashSet;
import java.util.Set;

public class Test {
  public static void main(String[] args) {
    // 좋은 예
    Set<String> names = new LinkedHashSet<>();

    // 나쁜 예
    LinkedHashSet<String> names2 = new LinkedHashSet<>();

    // [정리]
    // - 인터페이스 타입으로 사용하는 습관을 길러두면 프로그램이 훨씬 유연해질 것이다.
    //   나중에 구현 클래스를 교체하고자 한다면 그저 새 클래스의 생성자(혹은 다른 정적 팩토리)를
    //   호출해주기만 하면 된다.

    // [주의할 점]
    // - 대체하기 전 원래의 클래스가 인터페이스 일반 규약 이외의 특별한 기능을 제공하고 이 기능에 기대어 동작한다면,
    //   새로운 클래스도 반드시 같은 기능을 제공해야 한다.
    //   예) LinkedHashSet: 입력 순서를 기억한다. 이에 기대에 코드를 작성했다면 HashSet으로 바꾸면 문제가 될 수 있다.

    // [구현 타입을 바꾸려 하는 동기]
    // - 원래 것보다 성능이 좋거나 신기능을 제공하기 때문이다.
    //   예) HashMap --> EnumMap
    //      - 속도가 빨라지고, 순회 순서도 키의 순서와 같아진다.
    //      - 단 key가 열거 타입일 때만 사용할 수 있다.
    //   예) HashMap --> LinkedHashMap
    //      - 성능은 비슷하게 유지하면서 순회 순서를 예측할 수 있다.

  }
}
