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

package effectivejava.ch06.item37.exam03;

// [주제] EnumMap + Stream을 사용한 예

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import effectivejava.ch06.item37.exam03.Plant.LifeCycle;
import java.util.Arrays;
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

    // Stream으로 분류 방법1: groupingBy(classifier)
    // - 파라미터:
    //   classsifier: 분류 기준을 제공하는 메서드(식물의 lifeCycle)
    //   mapFactory: 최종 분류 결과를 담는 객체를 만들어줄 팩토리(기본: HashMap)
    //   downstream: 분류된 원소를 수집하는 방법(기본: List)
    // - 리턴 값:
    //   HashMap<LifeCycle, List<Plant>>
    // - 문제점:
    //   EnumMap의 공간과 성능 이점을 누리지 못한다.
    Map<Plant.LifeCycle, List<Plant>> plantsByLifeCycle1 =
        Arrays.stream(garden.toArray(Plant[]::new)) // 배열을 스트림으로 변환
            .collect(
                groupingBy(
                    p -> p.lifeCycle // 분류 기준
                    ));
    // 결과 출력
    System.out.println(plantsByLifeCycle1);
    System.out.println(plantsByLifeCycle1.size());
    System.out.println(plantsByLifeCycle1.getClass().getName());

    // Stream으로 분류 방법2: groupingBy(classifier, mapFactory, downstream)
    //   classsifier: 분류 기준을 제공하는 메서드(식물의 lifeCycle)
    //   mapFactory: 최종 분류 결과를 담는 객체를 만들어줄 팩토리(EnumMap)
    //   downstream: 분류된 원소를 수집하는 방법(Set)
    // - 리턴 값:
    //   EnumMap<LifeCycle, Set<Plant>>
    // - 장점:
    //   EnumMap의 공간과 성능 이점을 누릴 수 있다.
    EnumMap<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle2 =
        Arrays.stream(garden.toArray(Plant[]::new)) // 배열을 스트림으로 변환
            .collect(
                groupingBy(
                    p -> p.lifeCycle, // 분류 기준
                    () -> new EnumMap<>(LifeCycle.class), // EnumMap 생성
                    toSet() // 분류된 원소를 Set으로 수집
                    ));

    // 결과 출력
    System.out.println(plantsByLifeCycle2);
    System.out.println(plantsByLifeCycle2.size());
    System.out.println(plantsByLifeCycle2.getClass().getName());

    // [EnumMap을 직접 사용할 때와 Stream을 사용할 때의 차이점]
    // - EnumMap을 직접 사용할 때는 열거 타입의 상수 개수 만큼 미리 공간을 할당한다.
    // - Stream을 사용할 때는 실제로 사용된 열거 타입의 상수 개수 만큼만 공간을 할당한다.
    // - garden에 BIENNIAL 타입의 식물을 제거한 후 실행해 보라.
  }
}
