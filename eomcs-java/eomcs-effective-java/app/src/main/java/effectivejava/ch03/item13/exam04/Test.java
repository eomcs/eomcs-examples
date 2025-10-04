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

package effectivejava.ch03.item13.exam04;

import java.util.Arrays;
import java.util.EmptyStackException;

// 복사 생성자 또는 복사 팩토리를 사용한 객체 복사 방법
// - Clonable 인터페이스를 구현하지 않아도 된다.
// - clone 메서드를 재정의하지 않아도 된다.
// - 정상적인 final 필드 용법과 충동하지 않는다.
// - 언어 모순적이고 위험천만한 객체 생성 메커니즘(생성자를 쓰지 않는 방식)을 사용하지 않는다.
// - 엉성하게 문서화된 규약에 기대지 않는다.
class Stack {

  private Object[] elements; // 가변 객체를 참조하는 필드
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  // 복사 생성자
  public Stack(Stack s) { // 복사 생성자
    this.elements = s.elements.clone(); // 깊은 복제
    this.size = s.size;
  }

  // 복사 팩토리
  public static Stack newInstance(Stack s) {
    return new Stack(s);
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null;
    return result;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  private void ensureCapacity() {
    if (elements.length == size) elements = Arrays.copyOf(elements, 2 * size + 1);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    String[] names = {"Lee", "Kim", "Park"};
    Stack stack = new Stack();
    for (String name : names) stack.push(name);

    // 복사 생성자를 이용한 객제 복제
    Stack copy1 = new Stack(stack);

    // 복사 팩토리를 이용한 객체 복제
    Stack copy2 = Stack.newInstance(stack);

    while (!stack.isEmpty()) System.out.print(stack.pop() + " ");
    System.out.println();

    while (!copy1.isEmpty()) System.out.print(copy1.pop() + " ");
    System.out.println();

    while (!copy2.isEmpty()) System.out.print(copy2.pop() + " ");
    System.out.println();

    // [정리]
    // - 새로운 인터페이스를 만들 때는 절대 Cloneable 인터페이스를 확장하지 말라.
    // - 새로운 클래스도 이를 구현해서는 안된다.
    // - final 클래스라면 Clonable 인터페이스를 구현해도 위험이 크지 않다.
    // - 성능 최적화 관점에서 검토한 후 별다른 문제가 없을 때만 드물게 허용해야 한다.
    // - 기본 원칙은 "복제 기능은 생성자와 팩토리를 이용하는 게 최고"라는 것이다.
    // - 단, 배열만은 clone() 메서드 방식이 가장 낫다.
  }
}
