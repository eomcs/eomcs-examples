// # 아이템 78. 공유 중인 가변 데이터는 동기화해 사용하라
// - 여러 스레드가 가변 데이터를 공유한다면 그 데이터를 읽고 쓰는 동작은 반드시 동기화해야 한다.
//   동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수 있다.
// - 공유되는 가변 데이터를 동기화하는데 실패하면,
//   응답 불가 상태에 빠지거나 안전 실패로 이어질 수 있다.
//   이는 디버깅 난이도가 가장 높은 문제에 속한다.
//   간헐적이거나 특정 타이밍에만 발생할 수 있고, VM에 따라 현상이 달라지기도 한다.
// - 베타적 실행은 필요 없고 스레드끼리의 통신만 필요하다면,
//   volatile 한정자만으로 동기화할 수 있다. 다만 올바로 사용하기가 까다롭다.
//
//

package effectivejava.ch11.item78.exam01;

// [주제] synchronized 키워드를 사용한 동기화 - 배타적 실행 (mutual exclusion)
// - 해당 메서드나 블록을 한번에 한 스레드씩 수행하도록 보장한다.
// - 한 객체가 일관된 상태를 가지고 생성되고,
//   이 객체에 접근하는 메서드는 그 객체 lock을 건다.
//   lock을 건 메서드는 객체의 상태를 확인하고 필요하면 수정한다.
//   즉 객체를 하나의 일관된 상태에서 다른 일관된 상태로 변화시킨다.
//   동기화를 사용하면 어떤 메서드도 이 객체의 상태가 일관되지 않은 순간을 볼 수 없을 것이다.

// 일관된 상태란?
// - 객체가 내부적으로 갖는 여러 필드들이 "서로 정합성을 유지하고 있는 상태"를 뜻한다.
//   즉 "객체가 설계된 규칙(불변식, invariant)을 만족하는 상태"를 뜻한다.
// - 예를 들어, Range 클래스는 lower 필드가 upper 필드보다 작거나 같은 상태를 유지해야 한다.
//   이 불변식이 깨지면 Range 객체는 잘못된 상태가 된다.
class Range {
  private int lower;
  private int upper;

  public Range(int lower, int upper) {
    if (lower > upper) throw new IllegalArgumentException("lower > upper");
    this.lower = lower;
    this.upper = upper;
  }

  // [동기화의 역할]
  // - 멀티스레드 환경에서는 여러 스레드가 동시에 필드에 적금하거나 값을 바꿀 수 있다.
  // - 동기화를 하지 않으면, 한 스레드가 객체를 수정 중간 상태에 둘 수 있다.
  //   예) 한 스레드가 lower를 바꾸는 동안, 다른 스레드가 upper를 읽는 경우
  //   스레드 A: lower = 5 -> 10
  //   스레드 B: upper = 8 (이 시점에서 lower는 10으로 바뀌었지만 upper는 아직 8이다.)
  //   결과적으로 lower > upper가 되어 불변식이 깨진다.
  //   즉 객체가 "일관되지 않은 상태"로 노출된 것이다.
  // - "하나의 일관된 상태" --> "다른 일관된 상태"로 안전하게 전환시키려면
  //   동기화가 필요하다.
  public synchronized void setRange(int lower, int upper) {
    // 한 스레드가 이 메서드를 수행하는 동안 다른 스레드는 이 객체의 상태를 볼 수 없다.
    // 메서드가 끝나면 다시 불변식(lower <= upper)이 성립된 "새로운 일관된 상태"로 돌아온다.
    if (lower > this.upper) throw new IllegalArgumentException("lower > upper");
    this.lower = lower;
    this.upper = upper;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {}
}
