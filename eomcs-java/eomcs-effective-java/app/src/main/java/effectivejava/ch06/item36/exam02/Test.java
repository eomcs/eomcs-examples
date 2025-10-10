// # 아이템 36. 비트 필드 대신 EnumSet을 사용하라
// - 열거한 값들이 주로 단독이 아닌 집합으로 사용될 경우,
//   서로 다른 2의 거듭제곱 값을 할당한 정수 열거 패턴을 사용해 왔다.
//   하지만 이 기법은 구식이고, 사용하기도 불편하다.
// - EnumSet 클래스는 이 문제를 해결해 준다.
//   비트 필드 수준의 명료함과 성능을 제공하고, 열거 타입의 장점까지 선사한다.
//   Set 인터페이스를 완벽하게 구현하고 있기 때문에
//   타입 안전하고 다른 어떤 Set 구현체와도 함께 사용할 수 있다.

package effectivejava.ch06.item36.exam02;

// [주제] 비트 필드를 대체하는 현대적 기법: EnumSet 사용하기
// - Set 인터페이스를 완벽하게 구현한다.
// - 타입 안전하고 다른 어떤 Set 구현체와도 함께 사용할 수 있다.
// - EnumSet의 내부는 비트 벡터(bit vector)로 구현되어 있어 매우 효율적이다.
//   즉 원소가 64개 이하라면, EnumSet 전체를 long 변수 하나로 표현하여
//   비트 필드에 비견되는 성능을 보여준다.
// - removeAll()과 retainAll() 같은 대량 작업은,
//   비트를 효율적으로 처리할 수 있는 산술 연산을 써서 구현했다.
//   그러면서도 비트를 직접 다룰 때 흔히 겪는 오류들에서 해방된다.

import java.util.EnumSet;

public class Test {

  public enum Style {
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH
  }

  public static void main(String[] args) throws Exception {
    // EnumSet을 사용하여 여러 상수를 모은다.
    EnumSet<Style> textStyles = EnumSet.of(Style.BOLD, Style.ITALIC);
    System.out.println(textStyles);

    // textStyles의 상수를 확인하기
    // EnumSet은 Set 인터페이스를 구현하므로 일반적인 Set을 다루듯이 사용하면 된다.
    System.out.println(textStyles.contains(Style.BOLD));
    System.out.println(textStyles.contains(Style.UNDERLINE));

    // 손쉽게 textStyles에 적용된 모든 스타일을 순회할 수 있다.
    for (Style s : textStyles) {
      System.out.println(s);
    }
  }
}
