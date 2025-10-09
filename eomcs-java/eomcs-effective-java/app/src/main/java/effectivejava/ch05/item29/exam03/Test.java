// # 아이템 29. 이왕이면 제네릭 타입으로 만들어라
// - 클라이언트에서 직접 형변환해야 하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편하다.
// - 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록 하라.
// - 기존 타입 중 제네릭이었어야 하는 게 있다면 제네릭 타입으로 변경하자.
// - 기존 클라이언트에는 아무 영향을 주지 않으면서, 새로운 사용자를 훨씬 편하게 해주는 길이다.

package effectivejava.ch05.item29.exam03;

// [주제] 제네릭 타입을 적용한 후: Object[] 배열 사용하기
// - Object[] 배열을 사용하면 코드의 이곳 저곳을 뜯어고쳐야 하는 불편함이 있다.
// - 대신 힙 오염(heap pollution)을 막을 수 있다.

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack<E> {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY]; // "unchecked" 경고!
  }

  public void push(E e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public E pop() {
    if (size == 0) throw new EmptyStackException();

    // 배열에서 값을 꺼낼 때 명시적으로 파라미터 타입으로 형변환한다.
    // - push()로 넣은 것은 E 타입이므로, Object[] 배열에 들어 있는 것은 E 타입이 분명하다.
    // - 따라서 컴파일러에게 경로를 띄우지 말라고 지시할 필요가 있다.
    @SuppressWarnings("unchecked")
    E result = (E) elements[--size];

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
    String[] names = {
      "Hong Gil-dong", "Im Kkeok-jeong", "Yu Gwan-sun", "Ahn Jung-geun", "Yoon Bong-gil"
    };

    Stack<String> stack = new Stack<>();
    for (String name : names) stack.push(name);
    while (!stack.isEmpty()) {
      String name = (String) stack.pop(); // 명시적 형변환 필요
      System.out.println(name.toUpperCase());
    }
  }
}
