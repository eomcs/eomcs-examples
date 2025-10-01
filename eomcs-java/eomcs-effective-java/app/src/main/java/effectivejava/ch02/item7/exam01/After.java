// # 아이템 7. 다 쓴 객체 참조를 해제하라
// - 다 쓴 참조(obsoletre reference)를 해제하지 않으면 가비지 컬렉터가 객체를 회수하지 못한다.
//   "다 쓴 참조"란 더 이상 사용하지 않는 객체를 가리키는 참조를 말한다.
// - 다 쓴 참조를 살려두면, 그 객체가 참조하는 다른 객체들도 살아남게 된다.
// - 이것은 장기간 멈춤없이 실행하는 서버 애플리케이션에서 메모리 누수(memory leak)를 일으키는 원인이 된다.
// - 다 쓴 참조를 해제하는 방법은 참조를 null로 설정하는 것이다.

package effectivejava.ch02.item7.exam01;

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack2 {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack2() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) throw new EmptyStackException();
    Object result = elements[--size];

    // 다 쓴 참조를 null로 설정하여 가비지 컬렉터가 객체를 회수할 수 있게 한다.
    elements[size] = null;
    return result;
  }

  private void ensureCapacity() {
    if (elements.length == size) elements = Arrays.copyOf(elements, 2 * size);
  }
}

public class After {

  public static void main(String[] args) throws Exception {
    Stack2 stack = new Stack2();
    stack.push("홍길동");
    stack.push("임꺽정");
    stack.push("유관순");
    stack.push("안중근");
    stack.push("윤봉길");

    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());

    // [결론]
    // - 다 쓴 참조는 null로 처리하여 참조를 해제 해야 한다.
    // - 그래야 그 참조가 가리키는 객체는 가비지가 될 수 있다.
    // - 그렇다고 참조가 필요없을 때 마다 null로 설정한다면 코드가 지저분해질 수 있다.
    // - 가장 좋은 방법은 변수의 범위를 최소화하여 변수의 범위를 벗어 났을 때 자연스럽게 제거되게 만드는 것이다.
    // - 스택처럼 자기 메모리를 직접 관리하는 클래스라면 항시 메모리 누수에 주의해야 한다.

    // [메모리 누수의 또 다른 원인]
    // - 캐시 사용: 사용 후 제거하지 않는 경우가 많다.
    // - 리스너 또는 콜백 등록: 사용후 해제하지 않고, 계속 추가적으로 등록하는 경우가 많다.

    // [해결책]
    // - 약한 참조(Weak Reference)를 사용한다.
    //   예) HashMap 대신 WeakHashMap을 사용한다.
    // - WeakHashMap은 key를 WeakReference에 감싸서 보관한다.
    // - key가 더 이상 참조되지 않으면, GC가 수행될 때 엔트리를 자동 삭제한다.
  }
}
