// # 아이템 35. ordinal 메서드 대신 인스턴스 필드를 사용하라
// - 대부분의 열거 타입 상수는 하나의 정숫값에 대응된다.
// - 모든 열거 타입은 해당 상수가 그 열거 타입에서 몇 번째 위치하는지를 반환하는 ordinal 메서드를 제공한다.
//
//

package effectivejava.ch06.item35.exam01;

// [주제] ordinal() 메서드 사용 예와 문제점 확인

enum Ensemble {
  SOLO,
  DUET,
  TRIO,
  QUARTET,
  QUINTET,
  SEXTET,
  SEPTET,
  OCTET,
  NONET,
  DECTET;

  public int numberOfMusicians() {
    return ordinal() + 1;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    System.out.println(Ensemble.SOLO.numberOfMusicians());
    System.out.println(Ensemble.QUARTET.numberOfMusicians());
    System.out.println(Ensemble.DECTET.numberOfMusicians());

    // [문제점]
    // - 열거 타입에 새로운 상수를 추가하거나 순서를 바꾸면 ordinal() 값이 바뀐다.
    // - ordinal() 메서드에 의존하는 코드는 깨질 수밖에 없다.
    // - 따라서 ordinal() 메서드를 사용하는 코드는 작성하지 말자!

    // [해결책]
    // - ordinal() 메서드 대신 인스턴스 필드를 사용하자!
  }
}
