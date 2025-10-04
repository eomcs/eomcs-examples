// # 아이템 13. clone 재정의는 주의해서 진행하라
// [Cloneable 인터페이스]
// - Cloneable 인터페이스는 아무 메서드도 선언하지 않는다.
// - 단지 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스(mixin interface)다.
// - 믹스인 인터페이스(mixin interface)란?
//   그 타입의 본질적 분류(상속 계층)과 무관하게, 추가 능력/역할을 섞어 넣는 용도의 인터페이스를 말한다.
// - Object의 protected 메서드 clone()의 동작 방식을 결정한다.
// - Cloneable 인터페이스를 구현하지 않은 클래스에서 clone()을 호출하면,
//   CloneNotSupportedException 예외가 발생한다.
// - Cloneable 인터페이스를 구현한 클래스는 clone() 메서드를 public으로 재정의해야 한다.

// [clone() 메서드의 일반 규약]
// - x.clone() != x
// - x.clone().getClass() == x.getClass()
// - x.clone().equals(x) == true
// - 관례상 이 메서드가 반환하는 객체는 super.clone()을 호출해서 얻어야 한다.
// - 관례상 반환된 객체와 원본 객체는 독립적이어야 한다.

package effectivejava.ch03.item13.exam05;

import java.util.HashSet;
import java.util.TreeSet;

// 변환 생성자(conversion constructor) 또는 변환 팩토리(conversion factory)를 사용한 객체 복사 방법
// - 복사 생성자 또는 복사 팩토리에서 "인터페이스 타입"의 인스턴스를 파라미터로 받는다.
// - 이 경우 복제본의 구현 클래스를 자유롭게 선택할 수 있다.
// - 다음은 인터페이스 Set의 인스턴스를 받아 TreeSet 객체로 복사하는 예이다.
public class Test {
  public static void main(String[] args) throws Exception {
    HashSet<String> set = new HashSet<>();
    set.add("Kim");
    set.add("Lee");
    set.add("Park");

    // 인터페이스 기반 복사 생성자: "변환 생성자(conversion constructor)"라 부른다.
    TreeSet<String> treeSet = new TreeSet<>(set); // 복사 생성자
    // - 원본은 HashSet이지만, 복제본은 TreeSet이다.

    System.out.println(set);
    System.out.println("----------------");

    System.out.println(treeSet);

    // [결론]
    // - 변환 생성자나 변환 팩토리를 통해 객체를 복사하는 방법은,
    //   원본의 구현 타입에 얽매이지 않고 복제본의 타입을 직접 선택할 수 있는 장점이 있다.
  }
}
