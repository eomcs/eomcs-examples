// # 아이템 57. 지역변수의 범위를 최소화하라
// - 지역변수의 유효 범위를 최소로 줄이면 코드 가독성과 유지보수성이 높아지고 오류 가능성은 낮아진다.
// - 가장 처음 쓰일 때 선언하라.
//   사용하려면 멀었는데, 미리 선언부터 해두면 코드가 어수선해져 가독성이 떨어진다.
//   C 언어의 경우 코드 블록의 첫 머리에 선언하는 경우가 많은데,
//   C99 표준부터는 필요한 곳에서 바로 선언할 수 있다.
// - 가능한 선언과 동시에 초기화하라.
//   초기화에 필요한 정보가 충분하지 않다면 충분해질 때까지 선언을 미뤄야 한다.
//   변수를 초기화하는 표현식에서 검사 예외를 던질 가능성이 있다면,
//   try 블록 안에서 초기화해야 한다.
// - 반복문에서는 종료된 뒤에도 써야 하는 상황이 아니라면,
//   while 문보다 for 문을 사용하라.
//
package effectivejava.ch09.item57.exam01;

// [주제] 컬렉션을 순회할 때 권장하는 방식

import java.util.Iterator;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    // 컬렉션이나 배열을 순회하는 경우
    List<Integer> list = List.of(1, 2, 3, 4, 5);
    List<Integer> list2 = List.of(1, 2, 3, 4, 5);

    for (int i : list) {}

    // 반복자를 사용해야 하는 상황이면, for-each 문 대신 전통적인 for 문을 쓰는 것이 낫다.
    for (Iterator<Integer> i = list.iterator(); i.hasNext(); ) {
      Integer e = i.next();
      // ...
    }

    // for 문을 사용하면 이런 실수를 방지할 수 있다.
    // 이전 반복문에서 사용한 i는 반복문이 끝나면 사라지기 때문에 컴파일 오류가 난다.
    //    for (Iterator<Integer> i2 = list2.iterator(); i.hasNext(); ) {
    //      Integer e = i2.next();
    //      // ...
    //    }

    // while 문을 사용했을 때 문제점:
    Iterator<Integer> i = list.iterator();
    while (i.hasNext()) {
      Integer e = i.next();
      // ...
    }

    Iterator<Integer> i2 = list2.iterator();
    // 실수로 i2가 아닌 이전 반복문에서 사용한 i를 사용해도 컴파일 오류가 나지 않는다.
    while (i.hasNext()) {
      Integer e = i2.next();
      // ...
    }

    // for 문의 초기화 블록에서 변수를 초기화하는 적절한 사례:
    // - for 문에서 어떤 메서드가 리턴한 값을 사용할 때,
    //   그 메서드가 매번 같은 값을 리턴할 때,
    //   이런 경우 for 문의 초기화 블록에서 변수를 선언하고 초기화하는 것이 성능에 낫다.
    for (int index = 0, n = list.size(); index < n; index++) {
      Integer e = list.get(index);
      // ...
    }

    // [정리]
    // 1) while 문보다 for 문을 사용하라.
    //    - for 문에 선언한 변수는 반복문이 끝나면 사라지므로
    //      복사/붙여넣기 실수를 방지할 수 있다.
    //    - 변수의 유효범위가 for 문 범위와 일치하므로
    //      똑같은 이름의 변수를 여러 반복문에서 써도 서로 아무런 영향을 주지 않는다.
    //    - while 문보다 짧아서 가독성이 좋다.
    // 2) 메서드를 작게 유지하고 한 가지 기능에 집중하라.
    //    - 한 메서드에서 여러 가지 기능을 처리하다보면,
    //      다른 기능과 관련된 변수를 접근하게 되고,
    //      결국 잘못된 사용으로 오류가 발생할 수 있다.
    //    - 단순히 메서드를 기능별로 쪼개면 지역변수의 유효범위를 확실하게 줄일 수 있다.
  }
}
