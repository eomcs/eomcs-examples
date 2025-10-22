// # 아이템 76. 가능한 한 실패 원자적으로 만들라
// [실패 원자적(failure atomic) 메서드]
// - 호출된 메서드가 실패하더라도 해당 객체는 호출 전 상태를 유지하는 것
//   즉 메서드가 작업 도중 실패하더라도 그 객체가 부분적으로만 변경된 상태로 남지 않는 것을 의미한다.
//
//
package effectivejava.ch10.item76.exam02;

// [주제] 메서드를 실패 원자적으로 만드는 방법
// 1) 불변 객체로 설계하는 것이다.
//    - 불변 객체는 태생적으로 실패 원자적이다.
// 2) 작업 수행에 앞서 파라미터의 유효성을 검사하는 것이다.
//    - 가변 객체가 이 경우에 해당한다.
//    - 객체의 내부 상태를 변경하기 전에 잠재적 예외의 가능성 대부분을 걸러낼 수 있는 방법이다.
// 3) 객체의 임시 복사본으로 작업을 수행하는 것이다.
//    - 작업이 성공적으로 끝나면 원본 객체를 복사본으로 교체하는 방법이다.
//    - 데이터를 임시 자료구조에 저장해 작업하는 게 더 빠를 때 적용하기 좋은 방식이다.
//      예) 정렬할 원소들 배열로 옮겨 담는다.
//         배열을 사용하면 정렬 알고리즘의 반복문에서 원소들을 더 빠르게 접근할 수 있다.
// 4) 작업 도중 발생하는 실패를 가로채는 복구 코드를 작성하여 작업 전 생태로 되돌리는 것이다.
//
//
import java.util.EmptyStackException;

class Stack<E> {
  private Object[] elements = new Object[10];
  private int size = 0;

  public void push(E e) {
    elements[size++] = e; // size 증가
  }

  public E pop() {
    // 작업 수행에 앞서 잠재적 예외의 가능성을 걸러내기
    if (size == 0) {
      throw new EmptyStackException();
    }
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
    } catch (EmptyStackException e) {
      System.out.println("스택이 비어 있습니다!");
    }

    // pop()이 실패하더라도 객체는 변경된 상태가 아니다.
    stack.push("일지매"); // OK

    // [정리]
    // - 계산을 수행해보기 전에는 아규먼트의 유효성을 검사해볼 수 없을 때
    //   위 예제의 방법과 더불어,
    //   실패할 가능성이 있는 모든 코드를 객체의 상태를 바꾸는 코드보다 앞에 배치하라.
    // - 실패 원자성은 항상 달성할 수 있는 것이 아니다.
    //   Error는 복구할 수 없으므로 AssertionError에 대해서는
    //   실패 원자적으로 만들려는 시도조차 할 필요가 없다.
    // - 실패 원자적으로 만들 수 있더라도 항상 그리 해야 하는 것도 아니다.
    //   실패 원자성을 달성하기 위한 비용이나 복잡도가 아주 큰 연산도 있기 때문이다.
    // - 메서드 명세에 기술한 예외라면,
    //   예외가 발생하더라도 객체의 상태를 메서드 호출 전과 똑같이 유지돼야 한다는 것이 기본 규칙이다.
    //   이 규칙을 지키지 못한다면 실패 시의 개체 상태를 API 설명에 명시해야 한다.
    //
  }
}
