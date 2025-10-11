// # 아이템 37. ordinal 인덱싱 대신 EnumMap을 사용하라
// - 배열이나 리스트에서 원소를 꺼낼 때 ordinal() 메서드로 인덱스를 얻는 코드는 피하라!
//   1) 열거 타입의 순서가 바뀌거나 추가, 삭제되면 코드가 깨진다.
//   2) 배열은 제네릭과 호환되지 않아서 비검사 형변환을 수행해야 하고 깔끔히 컴파일되지 않는다.
//   3) 배열은 각 인덱스의 의미를 모르니 출력 결과에 직접 레이블을 달아야 한다.
// - EnumMap을 사용하라!
//   1) EnumMap은 열거 타입을 키로 사용하는 데 최적화된 Map 구현체이다.
//   2) 짧고 명료하고 안전하고 성능도 원래 버전과 비등하다.
//      EnumMap은 내부적으로 배열을 사용하여 메모리를 효율적으로 사용한다.
//   3) 안전하지 않은 형변환은 쓰지 않고,
//      맵의 키로 사용되는 열거 타입이 그 자체로 출력용 문자열을 제공하니 따로 레이블을 붙일 필요가 없다.
//   4) 배열 인덱스를 계산하는 과정에서 오류가 날 가능성도 원천봉쇄된다.
//

package effectivejava.ch06.item37.exam04;

// [주제] 배열들의 배열의 인덱스에 ordinal()을 사용한 예

enum Phase {
  SOLID, // 고체
  LIQUID, // 액체
  GAS; // 기체

  enum Transition {
    MELT, // 고체 --> 액체
    FREEZE, // 액체 --> 고체
    BOIL, // 액체 --> 기체
    CONDENSE, // 기체 --> 액체
    SUBLIME, // 고체 --> 기체
    DEPOSIT; // 기체 --> 고체

    private static final Transition[][] TRANSITIONS = {
      {null, MELT, SUBLIME}, // from SOLID
      {FREEZE, null, BOIL}, // from LIQUID
      {DEPOSIT, CONDENSE, null} // from GAS
    };

    // 한 상태에서 다른 상태로의 전이를 반환한다.
    public static Transition from(Phase from, Phase to) {
      // ordinal()을 사용한 배열 인덱싱 - 바람직하지 않음!
      return TRANSITIONS[from.ordinal()][to.ordinal()];
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Phase from = Phase.SOLID;
    Phase to = Phase.LIQUID;
    Phase.Transition transition = Phase.Transition.from(from, to);
    System.out.printf("%s에서 %s로 가는 전이는 %s입니다.%n", from, to, transition);

    // [문제점]
    // - ordinal()을 사용했을 때의 문제점을 그대로 안고 있다.
    //   즉 열거 타입의 순서가 바뀌거나 추가, 삭제되면 코드가 깨진다.
    // - 상태의 가짓수가 늘어나면 전의 배열의 크기가 커지고,
    //   null로 채워지는 칸도 많아진다.

    // [해결책]
    // - EnumMap을 사용하라!
    // - 다음 예제를 보라!
  }
}
