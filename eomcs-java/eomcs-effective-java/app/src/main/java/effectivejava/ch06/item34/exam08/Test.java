// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam08;

// [주제] 값에 따라 분기하여 코드를 공유하는 열거 타입

enum PayrollDay {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY;

  // 하루 기본 노동 시간(분)
  private static final int MINS_PER_SHIFT = 8 * 60;

  // payRate = 분당 급여 = 시간당 급여 / 60
  // (하~~ 외국은 노동시간을 분당으로 따져 계산하는구나. 빡세다.)
  int pay(int minutesWorked, int payRate) {

    // 노동 시간에 따른 기본 급여를 계산한다.
    int basePay = minutesWorked * payRate;

    // switch expression(Java 14+)을 사용하여 초과 노동 수당(50%)을 계산한다.
    int overtimePay =
        switch (this) {
          // 주말은 전체 노동 시간을 초과 노동으로 간주한다.
          case SATURDAY, SUNDAY -> minutesWorked * payRate / 2;

          // 평일은 8시간(480분)을 초과한 노동 시간에 대해서만 초과 노동 수당을 지급한다.
          default ->
              (minutesWorked <= MINS_PER_SHIFT)
                  ? 0
                  : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
        };
    return basePay + overtimePay;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 시급이 167원(= 1만원/60분)일 때, 8시간 일한 각 요일별 급여를 출력한다.
    // (2025년 대한민국 시간당 최저 임금 10,030원 기준)
    for (PayrollDay day : PayrollDay.values()) {
      System.out.printf("%-10s%d%n", day, day.pay(8 * 60, 167));
    }

    // [문제점]
    // - 휴가와 같은 새로운 값을 열거 타입에 추가하려면,
    //   그 값을 처리하는 case 문을 잊지 말고 쌍(상수+case)으로 넣어줘야 한다.
    //   코드를 누락하면 휴가는 평일로 처리되어 임금이 계산된다.

    // [해결책]
    // - 열거 타입 상수별로 초과 노동 수당을 계산할 떄 사용할 전략을 선택하도록 한다.
    // - 다음 예제를 참조하라.
  }
}
