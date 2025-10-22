// # 아이템 76. 가능한 한 실패 원자적으로 만들라
// [실패 원자적(failure atomic) 메서드]
// - 호출된 메서드가 실패하더라도 해당 객체는 호출 전 상태를 유지하는 것
//   즉 메서드가 작업 도중 실패하더라도 그 객체가 부분적으로만 변경된 상태로 남지 않는 것을 의미한다.
//
//
package effectivejava.ch10.item76.exam01;

// [주제] 실패 원자적이지 않은 예

class Stack<E> {
  private Object[] elements = new Object[10];
  private int size = 0;

  public void push(E e) {
    elements[size++] = e; // size 증가
  }

  public E pop() {
    // 만약 스택이 비어 있는 상태에서 pop()을 호출하면
    // ArrayIndexOutOfBoundsException 예외가 발생한다.
    // 문제는 size가 감소한 후에 예외가 발생하므로
    // 스택이 부분적으로 변경된 상태로 남는다는 것이다.
    // 이 상태에서 push()를 호출하면 예외가 발생할 것이다.
    E result = (E) elements[--size]; // size 감소
    elements[size] = null;
    return result;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    Stack<String> stack = new Stack<>();
    stack.push("홍길동");
    stack.push("임꺽정");
    stack.push("장길산");

    System.out.println(stack.pop()); // 장길산
    System.out.println(stack.pop()); // 임꺽정
    System.out.println(stack.pop()); // 홍길동

    try {
      System.out.println(stack.pop()); // 예외 발생
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("스택이 비어 있습니다!");
    }

    // pop()이 실패한 후에 push()를 호출하면?
    stack.push("일지매"); // 예외 발생!

    // [이유]
    // - pop()이 실패했을 때 스택 객체의 상태(size)가 부분적으로 변경된 상태로 남아 있기 때문이다.
    // - "실패 원자적"이라는 것을 따르지 않았을 때 발생하는 문제의 예이다.
  }
}
