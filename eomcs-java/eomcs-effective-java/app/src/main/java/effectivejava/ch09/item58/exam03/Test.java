// # 아이템 58. 전통적인 for문 보다는 for-each 문을 사용하라
// - 스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.
// - 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다.
//   성능 저하도 없다.
//   가능한 모든 곳에서 for문이 아닌 for-each 문을 사용하라.
//
package effectivejava.ch09.item58.exam03;

// [주제] for-each 문을 사용할 수 없는 상황 - 파괴적인 필터링(destructive filtering)
// - 컬렉션을 순회하면서 특정 조건에 맞는 원소들을 제거해야 할 때

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
  public static void main(String[] args) {

    // 문제점: for-each 문을 사용하여 컬렉션을 순회하면서 원소를 제거하면
    List<String> names = new ArrayList<>(List.of("홍길동", "임꺽정", "임꺽순", "장길산"));
    try {
      for (String name : names) {
        if (name.startsWith("임")) {
          names.remove(name); // ConcurrentModificationException 발생
        }
      }
      System.out.println(names);
    } catch (Exception e) {
      System.out.println("예외 발생: " + e);
    }
    // [이유]
    // - for-each 문은 내부적으로 Iterator를 사용한다.
    // - Iterator를 사용하여 컬렉션을 순회하는 도중에 컬렉션을 직접 수정(추가/제거)하면
    //   ConcurrentModificationException이 발생한다.
    // - 동작 원리
    //   Iterator를 생성할 때 컬렉션의 수정 횟수 필드 값을 복사해 둔다.
    //   Iterator가 next() 메서드를 호출할 때마다 이 변수의 값과 컬렉션 변수의 값을 비교하여
    //   값이 같지 않으면 Iterator가 인식하지 못한 변경으로 간주하여 예외를 던진다.

    // 해결책 1: Iterator를 사용하여 명시적으로 제거
    List<String> names2 = new ArrayList<>(List.of("홍길동", "임꺽정", "임꺽순", "장길산"));
    for (Iterator<String> i = names2.iterator(); i.hasNext(); ) {
      String name = i.next();
      if (name.startsWith("임")) {
        i.remove(); // 안전하게 제거
      }
    }
    System.out.println(names2);

    // 해결책 2: removeIf() 메서드 사용
    // - 명시적으로 컬렉션을 순회할 필요가 없다.
    List<String> names3 = new ArrayList<>(List.of("홍길동", "임꺽정", "임꺽순", "장길산"));
    names3.removeIf(e -> e.startsWith("임"));
    System.out.println(names3);
  }
}
