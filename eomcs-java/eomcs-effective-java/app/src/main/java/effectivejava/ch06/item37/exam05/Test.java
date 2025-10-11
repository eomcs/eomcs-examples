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

package effectivejava.ch06.item37.exam05;

// [주제] 2차원 배열 + ordinal() 대신 중첩 EnumMap을 사용한 예

import static java.util.stream.Collectors.groupingBy;

import java.util.stream.Stream;

enum Phase {
  //  PLASMA, // 플라즈마
  SOLID, // 고체
  LIQUID, // 액체
  GAS; // 기체

  enum Transition {
    //    IONIZE(GAS, Phase.PLASMA), // 기체 --> 플라즈마
    //    DEIONIZE(Phase.PLASMA, GAS), // 플라즈마 --> 기체
    MELT(SOLID, LIQUID), // 고체 --> 액체
    FREEZE(LIQUID, SOLID), // 액체 --> 고체
    BOIL(LIQUID, GAS), // 액체 --> 기체
    CONDENSE(GAS, LIQUID), // 기체 --> 액체
    SUBLIME(SOLID, GAS), // 고체 --> 기체
    DEPOSIT(GAS, SOLID); // 기체 --> 고체

    private final Phase from;
    private final Phase to;

    Transition(Phase from, Phase to) {
      this.from = from;
      this.to = to;
    }

    // 상태전이 맵을 초기화한다.
    // - from 상태를 키로, to 상태를 키로, Transition을 값으로 하는 중첩 EnumMap
    // - 예)
    //   {
    //     SOLID:   {
    //                LIQUID: MELT,
    //                GAS: SUBLIME
    //              }
    //     LIQUID:  {
    //                SOLID: FREEZE,
    //                GAS: BOIL
    //              }
    //     GAS:     {
    //                LIQUID: CONDENSE,
    //                SOLID: DEPOSIT
    //              }
    //   }
    private static final java.util.Map<Phase, java.util.Map<Phase, Transition>> TRANSITIONS =
        Stream.of(values())
            .collect(
                groupingBy(
                    t -> t.from, // 분류 기준 함수
                    () -> new java.util.EnumMap<>(Phase.class), // 분류 맵의 구현체
                    java.util.stream.Collectors.toMap( // 분류 맵의 값 맵을 만드는 함수
                        t -> t.to, // 값 맵의 키를 만드는 함수
                        t -> t, // 값 맵의 값을 만드는 함수
                        (x, y) -> y, // 값 맵에서 키가 중복될 때 병합하는 함수
                        () -> new java.util.EnumMap<>(Phase.class) // 값 맵의 구현체
                        )));

    // 한 상태에서 다른 상태로의 전이를 반환한다.
    public static Transition from(Phase from, Phase to) {
      // 먼저 from 상태에 해당하는 맵을 꺼내고,
      // 그 맵에서 to 상태에 해당하는 전이를 꺼낸다.
      return TRANSITIONS.get(from).get(to);
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Phase from = Phase.SOLID;
    Phase to = Phase.LIQUID;
    Phase.Transition transition = Phase.Transition.from(from, to);
    System.out.printf("%s에서 %s로 가는 전이는 %s입니다.%n", from, to, transition);

    // [확인]
    // - 새로운 상태인 PLASMA를 추가해보자.
    // - PLASMA 상태 전이를 추가해보자.
    //   GAS --> PLASMA: IONIZE
    //   PLASMA --> GAS: DEIONIZE
    // - 상태나 전이를 추가할 때 순서를 지킬 필요가 없다.
    // - 잘 동작한다. 이것이 EnumMap을 사용하는 이유다.
    //
    // [2차원 배열로 처리했다면?]
    // - 상태 순서에 맞춰서 전이 배열을 만들어야 한다.
    // - 전이 배열의 크기도 상태 갯수에 맞춰 늘려야 한다.
  }
}
