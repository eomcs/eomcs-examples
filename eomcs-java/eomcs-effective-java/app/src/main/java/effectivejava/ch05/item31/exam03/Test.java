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

package effectivejava.ch05.item31.exam03;

// [주제] 한정적 와일드카드 타입을 사용하지 않은 예 II

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

  // 한정적 와일드카드를 사용하지 않은 경우
  public void popAll(Collection<E> dst) {
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

    // Stack에 들어 있는 Number 타입의 값을 다른 Collection에 옮기려고 한다.
    // Collection<Object>는 Object 타입을 담을 수 있기 때문에
    // 일견 Number 원소를 담는데 문제가 없어 보인다.
    // 그런데 컴파일하면 오류가 발생한다!
    //    numberStack.popAll(objects); // 컴파일 오류!

    // [컴파일 오류가 발생하는 이유]
    // - popAll()은 Collection<Number> 타입만 파라미터로 받을 수 있다.
    // - Collection<Object>는 Collection<Number> 타입이 아니다.

    // [해결책]
    // - 한정적 와일드카드를 사용하라!
  }
}
