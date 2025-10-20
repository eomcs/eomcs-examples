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
package effectivejava.ch09.item60.exam01;

// [주제] float와 double의 설계 목적 - 빠르게 정확한 '근사치'로 계산하기
//

public class Test {

  public static void main(String[] args) {
    // [오류 예1] 1.03 달러가 있었는데, 그중 42센트를 썼다고 하자. 남은 돈은 얼마일까?
    System.out.println(1.03 - 0.43); // 0.6000000000000001
    System.out.println("--------------------------");

    // [오류 예2] 주머니에 1달러가 있는데 10센트짜리 사탕 9개를 샀다고 하자. 남은 돈은 얼마일까?
    System.out.println(1.00 - 9 * 0.10); // 0.09999999999999998
    System.out.println("--------------------------");

    // [오류 예3] 주머니에 1달러가 있고, 선반에 10센, 20센터, 30센트, ... 1달러짜리 사탕이 있다고 하자.
    // 사탕을 몇 개나 살 수 있고, 잔돈은 얼마가 남을까?
    double funds = 1.00;
    int itemsBought = 0;
    for (double price = 0.10; funds >= price; price += 0.10) {
      funds -= price;
      itemsBought++;
    }
    System.out.println(itemsBought + " items bought."); // 3 items bought.
    System.out.println("Change: $" + funds); // Change: $0.3999999999999999
    System.out.println("--------------------------");
  }
}
