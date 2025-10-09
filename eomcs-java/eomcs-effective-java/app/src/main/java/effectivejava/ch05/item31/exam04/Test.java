// # 아이템 31. 한정적 와일드카드를 사용해 API 유연성을 높이라
// - parameterized type은 불공변이다.
//   Type1과 Type2가 서로 다른 타입일 때,
//   List<Type1>과 List<Type2>는 서로 상속 관계가 아니다.
//   예) List<Object>와 List<String>는 서로 상속 관계가 아니다.
//   List<Object>에는 어떤 객체든 넣을 수 있지만,
//   List<String>에는 String 객체만 넣을 수 있다.
//   List<Object> 자리에 List<String>을 넣지 못한다.
//   즉 리스코프 치환 원칙에 어긋난다.
//

package effectivejava.ch05.item31.exam04;

// [주제] 한정적 와일드카드 타입을 사용한 예 II

import java.util.Collection;

class Stack<E> {
  private E[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  @SuppressWarnings("unchecked")
  public Stack() {
    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(E e) {
    if (size == elements.length) throw new StackOverflowError();
    elements[size++] = e;
  }

  public E pop() {
    if (size == 0) throw new IllegalStateException("스택이 비어있음");
    E result = elements[--size];
    elements[size] = null; // 다 쓴 참조 해제
    return result;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  // 한정적 와일드카드를 사용하면 좀 더 유연하게 파라미터 값을 받을 수 있다.
  public void pushAll(Iterable<? extends E> src) {
    for (E e : src) {
      push(e);
    }
  }

  // 한정적 와일드카드를 사용하면 좀 더 유연하게 파라미터 값을 받을 수 있다.
  // 기존 스택에 들어 있는 원소는 E 타입이다.
  // 따라서 E 타입을 담을 수 있는 Collection이라면 어떤 타입이든 상관없다.
  // E 타입의 상위 타입이라면 E 타입을 담을 수 있다.
  public void popAll(Collection<? super E> dst) {
    while (!isEmpty()) {
      dst.add(pop());
    }
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    Stack<Number> numberStack = new Stack<>();
    numberStack.push(Integer.valueOf(100));
    numberStack.push(Float.valueOf(3.14f));
    numberStack.push(Double.valueOf(1.414));

    Collection<Object> objects = new java.util.ArrayList<>();
    numberStack.popAll(objects); // OK!

    for (Object obj : objects) {
      System.out.println(obj);
    }

    // [설명]
    // - popAll()이 요구한 것은 E 타입의 원소를 담을 수 있는 Collection이다.
    // - Collection<Object>의 원소 타입은 Object 타입이므로,
    //   E 타입의 원소를 담을 수 있다.

    // [결론]
    // - 유연성을 극대화하려면 원소의 생산자나 소비자용 입력 파라미터에 한정적 와일드카드를 사용하라!
    // - PECS(Producer-Extends, Consumer-Super) 원칙 = Get and Put Principle(나프탈린과 와들러가 호칭)
    //   생산자 예:
    //     - pushAll(Iterable<? extends E> src)
    //     - src에서 스택이 사용할 E 인스턴스를 생산한다.
    //   소비자 예:
    //     - popAll(Collection<? super E> dst)
    //     - dst는 스택으로부터 E 인스턴스를 소비한다.
    //

    // [주의]
    // - 입력 파라미터가 생산자와 소비자 역할을 동시에 한다면 와일드카드 타입을 써도 좋을 게 없다.
    // - 타입을 정확히 지정해야 하는 상황으로, 이때는 와일드카드 타입을 쓰지 말아야 한다.
  }
}
