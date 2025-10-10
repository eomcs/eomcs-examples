// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam09;

// [주제] 중첩 열거 타입의 활용

import static effectivejava.ch06.item34.exam09.PayrollDay.PayType.*;

enum PayrollDay {
  MONDAY(WEEKDAY),
  TUESDAY(WEEKDAY),
  WEDNESDAY(WEEKDAY),
  THURSDAY(WEEKDAY),
  FRIDAY(WEEKDAY),
  SATURDAY(WEEKEND),
  SUNDAY(WEEKEND);

  // 각 상수에 초과 노동 수당을 계산할 전략을 연결한다.
  private final PayType payType;

  // 생성자에서 각 상수에 해당하는 초과 노동 수당 계산 전략을 선택한다.
  PayrollDay(PayType payType) {
    this.payType = payType;
  }

  // 급여 계산은 각 상수에 연결된 전략에 위임한다.
  int pay(int minutesWorked, int payRate) {
    return payType.pay(minutesWorked, payRate);
  }

  // "초과 노동 수당 계산"을 전략으로 선택할 수 있도록 상수로 정의한다.
  enum PayType {
    // 평일에 초과 노동 시간을 계산하는 것을 상수로 정의한다.
    WEEKDAY {
      int overtimePay(int minsWorked, int payRate) {
        return (minsWorked <= MINS_PER_SHIFT) ? 0 : (minsWorked - MINS_PER_SHIFT) * payRate / 2;
      }
    },
    // 주말에 초과 노동 시간을 계산하는 것을 상수로 정의한다.
    WEEKEND {
      int overtimePay(int minsWorked, int payRate) {
        return minsWorked * payRate / 2;
      }
    };

    abstract int overtimePay(int mins, int payRate);

    // 하루 기본 노동 시간(분)
    private static final int MINS_PER_SHIFT = 8 * 60;

    int pay(int minsWorked, int payRate) {
      int basePay = minsWorked * payRate;
      // 초과 노동 수당을 계산할 때는 각 상수에 정의된 메서드를 호출한다.
      return basePay + overtimePay(minsWorked, payRate);
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 시급이 167원(= 1만원/60분)일 때, 8시간 일한 각 요일별 급여를 출력한다.
    // (2025년 대한민국 시간당 최저 임금 10,030원 기준)
    for (PayrollDay day : PayrollDay.values()) {
      System.out.printf("%-10s%d%n", day, day.pay(8 * 60, 167));
    }

    // [정리]
    // - 새로운 상수를 추가할 때 임금 계산 전략을 반드시 선택해야 하기 때문에 코드 누락을 방지할 수 있다.
    // - 초과 임금을 계산하는 다양한 전략을 열거 타입 상수로 정의할 수 있다.
    // - 각 상수마다 목적에 맞춰 다양한 전략을 연결할 수 있다.

  }
}
