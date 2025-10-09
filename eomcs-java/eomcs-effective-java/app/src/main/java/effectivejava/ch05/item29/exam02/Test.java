// # 아이템 29. 이왕이면 제네릭 타입으로 만들어라
// - 클라이언트에서 직접 형변환해야 하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편하다.
// - 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록 하라.
// - 기존 타입 중 제네릭이었어야 하는 게 있다면 제네릭 타입으로 변경하자.
// - 기존 클라이언트에는 아무 영향을 주지 않으면서, 새로운 사용자를 훨씬 편하게 해주는 길이다.

package effectivejava.ch05.item29.exam02;

// [주제] 제네릭 타입을 적용한 후: E[] 배열 사용하기
// - Object[] 배열을 만들어 E[] 배열로 캐스팅한다.
// - 형변환을 배열 생성 시 한 번만 해주면 된다.
// - 다만 배열의 런타임 타입(Object[])이 컴파일타임(E[])과 달라 힙 오염(heap pollution)을 일으킨다.
//   즉 컴파일에서는 같은 타입으로 간주되어 문제가 없었지만,
//   런타임에서는 실제 타입에 맞춰 처리하기 때문에 문제가 발생할 수 있다.

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack<E> {
  private E[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  @SuppressWarnings("unchecked")
  public Stack() {
    // type parameter로 배열을 생성할 수 없다.
    //    elements = new E[DEFAULT_INITIAL_CAPACITY]; // 컴파일 오류!

    // [해결책]
    // Object 배열을 생성한 후 타입 매개변수 배열로 캐스팅 한다.
    // - 컴파일러는 E 타입이 무엇인지 알 수 없으므로 "unchecked" 경고를 발생시킨다.
    // - 우리는 E 타입이 Object의 하위 타입이라는 것을 안다.
    // - 그래도 컴파일러에게 해동 경고를 무시하라고 지시해야 한다.
    //   @SuppressWarnings("unchecked") 애너테이션을 붙인다.

    // 선언부가 아니기에 여기에 붙일 수 없다. 대신 생성자 선언에 붙인다.
    // @SuppressWarnings("unchecked")
    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // "unchecked" 경고!
  }

  public void push(E e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public E pop() {
    if (size == 0) throw new EmptyStackException();
    E result = elements[--size];
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
