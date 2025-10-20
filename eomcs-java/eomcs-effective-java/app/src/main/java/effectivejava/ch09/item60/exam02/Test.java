// # 아이템 60. 정확한 답이 필요하다면 float와 double은 피하라
// [float, double]
// - float과 double 타입은 과학과 공학 계산용으로 설계되었다.
// - 이진 부동소수점 연산에 쓰이며, 넓은 범위의 수를 빠르게 정밀한 '근사치'로 계산하도록 고안되었다.
// - 따라서 정확한 결과가 필요할 때는 사용하면 안된다.
// - float과 double 타입은 특히 금융 관련 계산과는 맞지 않는다.
//
// [BigDecimal]
// - 소수점 추적은 시스템에 맡기고, 코딩 시에 불편함이나 성능 저하를 신경 쓰지 않겠다면 BigDecimal을 사용하라.
// - BigDecimal이 제공하는 여덟 가지 반올림 모드를 이용하여 반올림을 완벽히 제어할 수 있다.
//   법으로 정해진 반올림을 수행해야 하는 비즈니스 계산에서 아주 편리한 기능이다.
//
// [int, long]
// - 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크기 않다면 int나 long 타입을 사용하라.
// - 숫자를 아홉 자리 십진수로 표현할 수 있다면 int를 사용하라.
// - 열여덟 자리 십진수로 표현할 수 있다면 long을 사용하라.
// - 열여덟 자리를 넘어가면 BigDecimal을 사용하라.
//
package effectivejava.ch09.item60.exam02;

// [주제] 금융 계산에는 BigDecimal, int, long을 사용하라.
//

import java.math.BigDecimal;

public class Test {

  public static void main(String[] args) {

    // [해결책 1] BigDecimal 사용하기
    final BigDecimal TEN_CENTS = new BigDecimal(".10"); // 금액을 문자열로 초기화한다.
    int itemsBought = 0;
    BigDecimal funds = new BigDecimal("1.00"); // 금액을 문자열로 초기화한다.
    for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
      funds = funds.subtract(price);
      itemsBought++;
    }
    System.out.println(itemsBought + " items bought.");
    System.out.println("Money left over: $" + funds);
    System.out.println("--------------------------");

    // [해결책 2] int 사용하기
    int itemsBought2 = 0;
    int funds2 = 100; // 기본 단위를 센트로 바꾼다.
    for (int price = 10; funds2 >= price; price += 10) {
      funds2 -= price;
      itemsBought2++;
    }
    System.out.println(itemsBought2 + " items bought.");
    System.out.println("Cash left over: " + funds2 + " cents");

    // [정리]
    // - BigDecimal은 기본 타입보다 쓰기가 훨씬 불편하고 훨씬 느리다.
    // - int나 long 타입을 사용하면 성능 문제를 피할 수 있지만,
    //   값의 크기가 제한되고 소수점을 직접 관리해야 한다.
    //
  }
}
