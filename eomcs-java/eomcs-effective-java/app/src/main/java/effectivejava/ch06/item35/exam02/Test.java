// # 아이템 35. ordinal 메서드 대신 인스턴스 필드를 사용하라
// - 대부분의 열거 타입 상수는 하나의 정숫값에 대응된다.
// - 모든 열거 타입은 해당 상수가 그 열거 타입에서 몇 번째 위치하는지를 반환하는 ordinal 메서드를 제공한다.
//
//

package effectivejava.ch06.item35.exam02;

// [주제] ordinal() 메서드 대신 인스턴스 필드를 사용하기

enum Ensemble {
  SOLO(1),
  DUET(2),
  TRIO(3),
  QUARTET(4),
  QUINTET(5),
  SEXTET(6),
  SEPTET(7),
  OCTET(8),
  DOUBLE_QUARTET(8),
  NONET(9),
  DECTET(10),
  TRIPLE_QUARTET(12);

  // 상수와 연결된 값을 저장할 인스턴스 필드
  // 기본이 private이므로 private을 명시하지 않아도 된다.
  private final int numberOfMusicians;

  // 상수를 생성할 때 값을 설정하는 생성자
  Ensemble(int size) {
    this.numberOfMusicians = size;
  }

  public int numberOfMusicians() {
    return this.numberOfMusicians;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    System.out.println(Ensemble.SOLO.numberOfMusicians());
    System.out.println(Ensemble.QUARTET.numberOfMusicians());
    System.out.println(Ensemble.DECTET.numberOfMusicians());

    // [ordinal() 메서드에 대한 API 문서 일부 내용]
    // "대부분의 프로그래머는 이 메서드를 쓸 일이 없다.
    // 이 메서드는 EnumSet과 EnumMap 같이 열거 타입 기반의
    // 범용 자료구조에 쓸 목적으로 설계되었다."
  }
}
