// # 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라
//
// ## 장점
// - 이름을 가질 수 있다.
// - 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
// - 반환 타입의 하위 타입 객체를 반환할 수 있다.
// - 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
// - 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
//
// ## 단점
// - 상속을 하려면 public이나 protected 생성자가 필요하다.
// - 정적 팩터리 메서드만 제공하는 클래스는 확장할 수 없다.
// - 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
// - API 문서에서 정적 팩터리 메서드를 잘 찾아봐야 한다.
// - 따라서 메서드 이름은 널리 알려진 규약을 따라 짓는 것이 좋다.
//   예) `from`, `valueOf`, `of`, `instance` | `getInstance`,
//       `create` | `newInstance`, `getType`, `newType`,  `type`
//       등의 이름을 사용한다.

package effectivejava.ch02.item1;

public class Exam03_after {
  public static void main(String[] args) {
    // 장점3: 반환 타입의 하위 타입 객체를 반환할 수 있다.

    InterestCalculator sic = InterestCalculatorFactory.simpleCalculator(6.5);
    System.out.printf("%20.0f\n", sic.calculate(100_000_000, 3600));

    InterestCalculator cic = InterestCalculatorFactory.compoundCalculator(6.5);
    System.out.printf("%20.0f\n", cic.calculate(100_000_000, 3600));

    // - API 설계자가 반환할 객체의 클래스를 자유롭게 선택할 수 있는 유연성을 제공한다.
    // - API 사용자에게 구현 클래스의 노출을 피할 수 있다.
    // - API의 외견을 단순하게 유지할 수 있다.
    // - 프로그래머가 API를 사용하기 위해 익혀야 하는 개념의 수와 난이도를 낮춘다.
    // - 얻은 객체를 구현 클래스가 아닌 인터페이스로 다룰 수 있다.
  }

  interface InterestCalculator {
    double calculate(double principal, int days);
  }

  static class SimpleInterestCalculator implements InterestCalculator {
    private final double rate;

    public SimpleInterestCalculator(double rate) {
      this.rate = rate;
    }

    public double calculate(double principal, int days) {
      return principal + principal * (rate / 100.0) * (days / 365.0);
    }
  }

  static class CompoundInterestCalculator implements InterestCalculator {
    private final double rate;

    public CompoundInterestCalculator(double rate) {
      this.rate = rate;
    }

    public double calculate(double principal, int days) {
      double annualRate = rate / 100.0; // 퍼센트 값을 소수 값으로 변경. 예) 5% --> 0.05
      return principal * Math.pow(1 + annualRate / 365, days);
    }
  }

  // 정적 팩토리
  interface InterestCalculatorFactory {
    // Java 8: 인터페이스에서 static 메서드 제공 가능

    static InterestCalculator simpleCalculator(double rate) {
      return new SimpleInterestCalculator(rate);
    }

    static InterestCalculator compoundCalculator(double rate) {
      return new CompoundInterestCalculator(rate);
    }
  }
}
