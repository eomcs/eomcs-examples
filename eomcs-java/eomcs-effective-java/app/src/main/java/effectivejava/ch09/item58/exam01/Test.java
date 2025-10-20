// # 아이템 58. 전통적인 for문 보다는 for-each 문을 사용하라
// - 스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.
// - 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다.
//   성능 저하도 없다.
//   가능한 모든 곳에서 for문이 아닌 for-each 문을 사용하라.
//
package effectivejava.ch09.item58.exam01;

// [주제] for 문 vs for-each 문

import java.util.Iterator;
import java.util.List;

public class Test {
  public static void main(String[] args) {

    // 컬렉션 순회하기 - Iterator 사용
    List<String> names = List.of("홍길동", "김삿갓", "이몽룡");
    for (Iterator<String> i = names.iterator(); i.hasNext(); ) {
      String name = i.next();
      // ...
    }

    // 배열 순회하기
    String[] arr = {"홍길동", "김삿갓", "이몽룡"};
    for (int i = 0; i < arr.length; i++) {
      String name = arr[i];
      // ...
    }

    // [정리]
    // - while 문보다는 낫지만, Iterator와 인덱스 변수는 모두 코드를 지저분하게 만든다.
    //   1회 반복에서 Iterator는 3번 등장하고, 인덱스 변수는 4번 등장한다.
    //   변수를 잘못 사용할 틈새가 넓어진다.
    //

    // for-each 문을 사용한 컬렉션 및 배열 순회
    // - for-each 문에서 콜론(:)은 '안의(in)'으로 읽으로면 된다.
    for (String name : names) { // "names 안의 각 원소 name에 대해"
      // ...
    }

    for (String name : arr) { // "arr 안의 각 원소 name에 대해"
      // ...
    }

    // [정리]
    // - 반복 대상이 컬렉션이든 배열이든, for-each 문을 사용해도 속도는 그대로다.
    //   for-each 문이 만들어내는 코드는 사람이 손으로 최적화한 것과 사실상 같다.
  }
}
