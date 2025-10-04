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

package effectivejava.ch03.item13.exam02;

import java.util.Arrays;
import java.util.EmptyStackException;

// 가변 객체를 참조하는 경우: 얕은 복제(shallow copy)의 문제
class Stack implements Cloneable {

  private Object[] elements; // 가변 객체를 참조하는 필드
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
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

  @Override
  public Stack clone() {
    try {
      // 단순히 super.clone()을 호출하면, 얕은 복사가 일어난다.
      // - size 필드는 기본형이므로 문제가 없다. 각 인스턴스가 독립적인 값을 갖는다.
      // - elements 필드는 가변 객체를 참조하므로 문제가 된다.
      //   복제된 스택과 원본 스택이 같은 배열을 참조하게 된다.
      //   따라서 복제된 스택에서 push/pop을 수행하면, 원본 스택의 상태가 변하게 된다.
      //   즉, 두 스택이 독립적이지 않게 된다.
      //   이는 clone() 메서드의 일반 규약을 위반한다.
      return (Stack) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
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
    Stack copy = stack.clone();

    while (!stack.isEmpty()) System.out.print(stack.pop() + " ");
    System.out.println();

    while (!copy.isEmpty()) System.out.print(copy.pop() + " ");

    // [문제점]
    // 원본 스택의 요소를 모두 꺼낸 후에 복제본 스택의 요소를 꺼내면,
    // 복제본 스택이 비어 있게 된다.
    // 왜? 복사본이 사용하는 배열은 원본 스택의 배열과 같은 배열 객체이기 때문이다.

    // [해결책]
    // clone() 메서드에서 super.clone()으로 얻은 복제본의 elements 필드가
    // 원본의 elements 필드와 다른 배열 객체를 가리키도록 만들어야 한다.
    // 다음 예제를 참조하라.

  }
}
