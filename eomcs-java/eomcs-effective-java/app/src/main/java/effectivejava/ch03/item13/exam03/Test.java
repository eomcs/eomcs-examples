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

package effectivejava.ch03.item13.exam03;

import java.util.Arrays;
import java.util.EmptyStackException;

// 가변 객체를 참조하는 경우: 깊은 복제(deep copy)로 해결
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
      // 원본과 똑같은 객체를 얕은 복제한 후,
      Stack copy = (Stack) super.clone();

      // 가변 객체를 다시 복제해서 참조하게 한다.
      // 복제본의 elements 필드는 원본의 elements 필드와 다른 배열 객체를 가리키게 된다.
      copy.elements = this.elements.clone(); // 깊은 복제
      // - 배열의 clone() 메서드는 원본 배열과 똑같은 배열을 새로 만들어서 반환한다.
      // - 가변 객체 필드가 final 일 경우 새로운 값을 할당할 수 없기 때문에 깊은 복제를 할 수 없다.
      // - 복제할 수 있는 클래스를 만들 경우, 일부 필드에서 final을 떼어내야 할 수도 있다.

      return copy;
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

    // [해결 방법]
    // - 원본 객체의 가변 필드는 따로 복제해야 한다.
    // - 그래야 원복 객체의 변경에 영향을 받지 않는다.
    // - 가변 객체를 참조하는 필드가 final이면 복제할 수 없다.
    // - 복제할 수 있는 클래스를 만들어야 한다면 final을 떼어내야 한다.

    // [정리]
    // - Cloneable 인터페이스를 구현하는 모든 클래스는 clone() 메서드를 재정의해야 한다.
    // - clone() 메서드는 public으로 선언해야 한다.
    // - clone() 반환 타입은 클래스 자신으로 변경한다.
    // - clone() 메서드는 super.clone()을 제일 먼저 호출해야 한다.
    //   그런 후, 필요한 필드를 전부 적절히 수정한다.
    // - 기본 타입 필드(primitives)나 불변 객체 참조만 있는 경우 필드 값을 수정할 필요가 없다.
    //   다만 "일련번호"나 "고유ID" 같은 필드인 경우 수정해줘야 한다.
    // - 가변 객체 참조 필드가 있는 경우,
    //   그 필드가 가리키는 객체도 따로 복제해서 참조하게 만들어야 한다.
    //   즉 깊은 복제(deep copy)를 해야 한다.

  }
}
