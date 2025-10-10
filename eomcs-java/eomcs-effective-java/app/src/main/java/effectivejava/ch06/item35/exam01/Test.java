// # 아이템 35. ordinal 메서드 대신 인스턴스 필드를 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
//

package effectivejava.ch06.item35.exam01;

// [주제] 열거 타입 등장 전: 정수 열거 패턴을 사용할 때의 문제점

public class Test {
  // 정수 열거 패턴 예:
  // - 정수 열거 패턴을 사용하였다.
  // - 타입 안전을 보장할 방법이 없으며, 표현력도 좋지 않다.
  // - 별도의 이름공간(namespace)을 제공하지 않아서 접두어를 사용해 이름 충돌을 방지한다.
  public static final int APPLE_FUJI = 0;
  public static final int APPLE_PIPPIN = 1;
  public static final int APPLE_GRANNY_SMITH = 2;

  public static final int ORANGE_NAVEL = 0;
  public static final int ORANGE_TEMPLE = 1;
  public static final int ORANGE_BLOOD = 2;

  static void printApple(int apple) {}

  public static void main(String[] args) throws Exception {
    // 사과를 보내는 대신 오렌지를 보내도 컴파일 오류가 발생하지 않는다.
    printApple(ORANGE_BLOOD);

    // 동등 연산자로 비교하더라도 컴파일러는 오류를 잡아내지 못한다.
    System.out.println(APPLE_FUJI == ORANGE_NAVEL);

    // 평범한 상수를 나열한 것뿐이라 컴파일 하면 그 값이 클라이언트 파일에 그대로 새겨진다.
    System.out.println(APPLE_PIPPIN);
    // - 문장은 컴파일하면 다음과 같이 심볼 정보는 사라지고 값으로 대체된다.
    //       System.out.println(1);
    // - 나중에 라이브러리에서 해당 상수의 값을 바꾸더라도 클라이언트의 .class 파일에는 변화가 없다.
    //   다시 컴파일 하지 않은 클라이언트는 엉뚱하게 동작할 수 있다.
    //   그래서 클라이언트도 반드시 다시 컴파일해야 하는 문제가 있다.

    // 정수 상수는 문자열로 출력하기가 까다롭고, 디버거로 살펴보면 단지 숫자로만 보여서 도움이 되지 않는다.
    // - 정수 대신 문자열을 사용하는 변형 패턴도 있지만, 문자열은 오타 검출이 불가능하다.
    //   문자열 비교에 따른 성증 저하가 일어난다.
    int value = APPLE_GRANNY_SMITH; // 디버깅하면 value가 2로 보인다.
    System.out.println(value);
  }
}
