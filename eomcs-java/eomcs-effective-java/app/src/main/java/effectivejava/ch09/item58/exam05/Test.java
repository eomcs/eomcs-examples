// # 아이템 58. 전통적인 for문 보다는 for-each 문을 사용하라
// - 스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.
// - 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다.
//   성능 저하도 없다.
//   가능한 모든 곳에서 for문이 아닌 for-each 문을 사용하라.
//
package effectivejava.ch09.item58.exam05;

// [주제] for-each 문을 사용할 수 없는 상황 - 병렬 반복(parallel iteration)
// - 여러 컬렉션을 병렬로 순회해야 한다면 각각의 반복자와 인덱스 변수를 사용해
//   엄격하고 명시적으로 제어해야 한다.

public class Test {
  public static void main(String[] args) {

    // 문제점: for-each 문을 값을 변경하기
    String[] names = {"홍길동", "임꺽정", "장길산", "일지매", "김삿갓"};
    int[] scores = {100, 90, 80, 70, 60};
    for (String name : names) {
      // 두 배열의 이름과 점수를 병렬로 순회하면서 출력하지 못한다.
      //      System.out.printf("%s: %d 점\n", name, /*점수*/);
    }

    // 해결책: 전통적인 for 문을 사용해야 한다.
    String[] names2 = {"홍길동", "임꺽정", "장길산", "일지매", "김삿갓"};
    int[] scores2 = {100, 90, 80, 70, 60};
    for (int i = 0; i < names2.length; i++) {
      // 명시적으로 인덱스를 사용하여 두 배열의 값을 꺼낼 수 있다.
      System.out.printf("%s: %d 점\n", names2[i], scores2[i]);
    }

    // [정리]
    // - for-each 문은 컬렉션과 배열은 물론 Iterable 인터페이스를 구현한 객체라면 무엇이든 순회할 수 있다.
    // - 원소들의 묶음을 표현하는 타입(클래스)을 작성해야 한다면 Iterable 인터페이스 구현을 고려하라.

  }
}
