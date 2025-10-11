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

package effectivejava.ch06.item37.exam02;

// [주제] EnumMap을 사용한 예

import effectivejava.ch06.item37.exam02.Plant.LifeCycle;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Plant {
  enum LifeCycle {
    ANNUAL, // 한해살이
    PERENNIAL, // 여러해살이
    BIENNIAL // 두해살이
  }

  final String name;
  final LifeCycle lifeCycle;

  Plant(String name, LifeCycle lifeCycle) {
    this.name = name;
    this.lifeCycle = lifeCycle;
  }

  @Override
  public String toString() {
    return this.name;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    List<Plant> garden =
        List.of(
            new Plant("바질", Plant.LifeCycle.ANNUAL),
            new Plant("백리향", Plant.LifeCycle.PERENNIAL),
            new Plant("당근", Plant.LifeCycle.BIENNIAL),
            new Plant("딜", Plant.LifeCycle.ANNUAL),
            new Plant("라벤더", Plant.LifeCycle.PERENNIAL),
            new Plant("파슬리", Plant.LifeCycle.BIENNIAL),
            new Plant("로즈마리", Plant.LifeCycle.PERENNIAL),
            new Plant("세이지", Plant.LifeCycle.PERENNIAL),
            new Plant("타임", Plant.LifeCycle.PERENNIAL),
            new Plant("토마토", Plant.LifeCycle.ANNUAL),
            new Plant("호박", Plant.LifeCycle.ANNUAL));

    // 집합의 참조를 담을 배열 생성
    // - key: Plant.LifeCycle 열거 타입
    // - value: Set<Plant>
    // - 컴파일 시에 타입 안전성을 명확히 검사한다.
    // - 생성자에 넘겨주는 것은 key에 대해 런타임 타입 정보를 제공하기 위한 '한정적 토큰 타입'이다.
    Map<LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);

    // Plant.LifeCycle 열거 타입의 상수 값을 key로 사용하여 집합 인스턴스를 맵에 추가한다.
    for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
      plantsByLifeCycle.put(lc, new java.util.HashSet<>());
    }

    // 식물을 생명주기에 따라 분류한다.
    for (Plant p : garden) {
      // 식물의 생명주기를 key로 사용하여 집합 인스턴스를 꺼낸 다음에, 그 집합에 식물을 추가한다.
      plantsByLifeCycle.get(p.lifeCycle).add(p);
    }

    // 결과 출력
    System.out.println(plantsByLifeCycle);
  }
}
