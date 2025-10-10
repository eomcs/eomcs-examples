// # 아이템 36. 비트 필드 대신 EnumSet을 사용하라
// - 열거한 값들이 주로 단독이 아닌 집합으로 사용될 경우,
//   서로 다른 2의 거듭제곱 값을 할당한 정수 열거 패턴을 사용해 왔다.
//   하지만 이 기법은 구식이고, 사용하기도 불편하다.
// - EnumSet 클래스는 이 문제를 해결해 준다.
//   비트 필드 수준의 명료함과 성능을 제공하고, 열거 타입의 장점까지 선사한다.
//   Set 인터페이스를 완벽하게 구현하고 있기 때문에
//   타입 안전하고 다른 어떤 Set 구현체와도 함께 사용할 수 있다

package effectivejava.ch06.item36.exam01;

// [주제] 비트 필드 열거 상수 - 구식 기법

public class Test {

  public static final int STYLE_BOLD = 1 << 0;
  public static final int STYLE_ITALIC = 1 << 1;
  public static final int STYLE_UNDERLINE = 1 << 2;
  public static final int STYLE_STRIKETHROUGH = 1 << 3;

  public static void main(String[] args) throws Exception {
    // OR 연산자를 사용해 여러 상수를 하나의 집합으로 모을 수 있다.
    int style = STYLE_BOLD | STYLE_ITALIC;
    System.out.println(style);

    // [비트 필드 기법의 장단점]
    // - 비트별 연산을 사용해 합집합, 교집합 같은 집합 연산을 효율적으로 수행할 수 있다.
    // - 하지만 비트 필드는 정수 열거 패턴의 단점을 그대로 지닌다.
    //   더불어 다음과 같은 문제까지 안고 있다.
    //   1) 비트 필드 값이 그대로 출력되면 단순한 정수 값을 출력할 때 보다 해석하기가 훨씬 어렵다.
    //   2) 비트 필드 하나에 녹아 있는 모든 원소를 순회하기도 까다롭다.
    //   3) 최대 몇 비트가 필요한지를 API 작성 시 미리 예측하여
    //      적절한 타입(int나 long)을 선택해야 한다.
    //      API를 수정하지 않고 비트 수를 더 늘릴 수 없기 때문이다.

    // [비트 필드 기법의 대안]
    // - EnumSet을 사용하라!

  }
}
