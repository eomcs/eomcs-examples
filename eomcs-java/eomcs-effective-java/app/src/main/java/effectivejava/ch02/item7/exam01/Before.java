// # 아이템 7. 다 쓴 객체 참조를 해제하라
// - 다 쓴 참조(obsoletre reference)를 해제하지 않으면 가비지 컬렉터가 객체를 회수하지 못한다.
//   "다 쓴 참조"란 더 이상 사용하지 않는 객체를 가리키는 참조를 말한다.
// - 다 쓴 참조를 살려두면, 그 객체가 참조하는 다른 객체들도 살아남게 된다.
// - 이것은 장기간 멈춤없이 실행하는 서버 애플리케이션에서 메모리 누수(memory leak)를 일으키는 원인이 된다.
// - 다 쓴 참조를 해제하는 방법은 참조를 null로 설정하는 것이다.

package effectivejava.ch02.item7.exam01;

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) throw new EmptyStackException();

    // 스택에서 객체를 꺼낼 때 인덱스만 줄이고, 배열에는 여전히 객체의 참조가 남아 있다.
    // 이것이 "다 쓴 참조(obsolete reference)"이다.
    // 참조가 있으면 객체는 가비지가 되지 않는다.
    return elements[--size];
  }

  private void ensureCapacity() {
    if (elements.length == size) elements = Arrays.copyOf(elements, 2 * size);
  }
}

public class Before {

  public static void main(String[] args) throws Exception {
    Stack stack = new Stack();
    stack.push("홍길동");
    stack.push("임꺽정");
    stack.push("유관순");
    stack.push("안중근");
    stack.push("윤봉길");

    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());

    // [결론]
    // - "다 쓴 참조(obsolete reference)"는 객체를 제때 가비지로 회수되지 않게 만든다.
    // - 이것은 메모리 누수의 원인이 된다.
  }
}
