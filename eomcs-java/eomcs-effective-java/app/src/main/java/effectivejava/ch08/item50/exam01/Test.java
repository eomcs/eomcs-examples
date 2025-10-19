// # 아이템 50. 적시에 방어적 복사본을 만들라
// - 늘 클라이언트가 클래스의 불변식을 깨뜨리지 못하도록 방어적으로 프로그래밍해야 한다.
// - 클래스가 클라이언트로 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면
//   그 요소는 반드시 방어적으로 복사해야 한다.
// - 복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할 일이 없음을 신뢰한다면,
//   방어적 복사를 수행하는 대신에
//   해당 구성요소를 수정했을 때의 책임이 클라이언트에게 있음을 문서에 명시하라.
//
package effectivejava.ch08.item50.exam01;

// [주제] 불변식을 깨드리는 예

import java.util.Date;

final class Period {
  private final Date start;
  private final Date end;

  /**
   * @param start 시작일
   * @param end 종료일; 시작일 보다 뒤여야 한다.
   * @throws IllegalArgumentException 시작일이 종료일보다 늦을 때 예외 발생
   * @throws NullPointerException 시작일이나 종료일이 null일 때 예외 발생
   */
  public Period(Date start, Date end) {
    if (start.compareTo(end) > 0) {
      throw new IllegalArgumentException(start + " after " + end);
    }
    this.start = start;
    this.end = end;
  }

  public Date start() {
    return start;
  }

  public Date end() {
    return end;
  }

  public String toString() {
    return start + " - " + end;
  }
}

public class Test {
  public static void main(String[] args) {
    Date start = new Date();
    Date end = new Date();
    Period p = new Period(start, end);
    System.out.println(p);

    // [문제] 클라이언트가 불변식을 깬다.
    end.setYear(78); // 1978년으로 변경
    System.out.println(p);
  }
}
