// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam02;

// [주제] 재정의(overriding) 메서드의 호출 메커니즘

import java.util.List;

class Wine {
  String name() {
    return "포도주";
  }
}

class SparklingWine extends Wine {
  @Override
  String name() {
    return "발포성 포도주";
  }
}

class Champagne extends SparklingWine {
  @Override
  String name() {
    return "샴페인";
  }
}

public class Test {
  public static void main(String[] args) {
    List<Wine> wineList = List.of(new Wine(), new SparklingWine(), new Champagne());

    for (Wine wine : wineList) {
      System.out.println(wine.name());
    }

    // [출력 결과]
    // 포도주
    // 발포성 포도주
    // 샴페인

    // [이유]
    // - 다중정의한 메서드는 "컴파일타임에서 정적으로 선택"되지만,
    //   재정의한 메서드는 "럼타임에서 동적으로 선택"된다.
    //
  }
}
