// # 아이템 58. 전통적인 for문 보다는 for-each 문을 사용하라
// - 스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.
// - 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다.
//   성능 저하도 없다.
//   가능한 모든 곳에서 for문이 아닌 for-each 문을 사용하라.
//
package effectivejava.ch09.item58.exam04;

// [주제] for-each 문을 사용할 수 없는 상황 - 변형(tranforming)
// - 리스트나 배열을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 한다면,
//   리스트의 반복자나 배열의 인덱스를 사용해야 한다.

public class Test {
  public static void main(String[] args) {

    // 문제점: for-each 문을 값을 변경하기
    int[] scores = {100, 90, 80, 70, 60};
    for (int score : scores) {
      if (score < 70) {
        // 점수가 60미만인 경우 10점을 더하고 싶다.
        // 문제는 현재 배열의 인덱스를 알 수 없어서 변경할 수 없다.
        score += 10; // 배열의 원소가 변경되지 않는다.
      }
    }
    System.out.println(scores[4]);

    // 해결책: 전통적인 for 문을 사용해야 한다.
    int[] scores2 = {100, 90, 80, 70, 60};
    for (int i = 0; i < scores2.length; i++) {
      if (scores2[i] < 70) {
        // 점수가 60미만인 경우 10점을 더하고 싶다.
        scores2[i] += 10; // 배열의 원소가 변경된다.
      }
    }
    System.out.println(scores2[4]);
  }
}
