// # 아이템 27. 비검사 경고를 제거하라
// - 할 수 있는 한 모든 비검사 경고를 제거하라
//   모두 제거한다면 그 코드는 타입 안정성이 보장된다.
// - 경고를 제거할 수 없지만 타입 안전하다고 확신할 수 있다면,
//   @SuppressWarnings("unchecked") 애너테이션을 달아 경고를 숨기자.

// [@SuppressWarnings("unchecked") 애너테이션 사용법]
// - 항상 가능한 좁은 범위에 적용하자.
//   예) 변수 선언, 아주 짧은 메서드 혹은 생성자
// - 절대로 클래스 전체에 적용하지 말라.
// - 한 줄이 넘는 메서드나 생성자에 달린 @SuppressWarnings("unchecked") 애너테이션은
//   지역변수 선언쪽으로 옮기자.
// - @SuppressWarnings("unchecked") 애너테이션을 달 때는
//   왜 이 경고를 무시해도 되는지 항상 주석으로 설명하자.

package effectivejava.ch05.item27.exam02;

// [주제] @SuppressWarnings("unchecked") 애너테이션 사용해야 할 상황 확인하기

class MyList<E> {
  private Object[] items = new Object[10];
  private int size = 0;

  public void add(E obj) {
    if (size == items.length) throw new IndexOutOfBoundsException("MyList 용량 초과");
    items[size++] = obj;
  }

  public E get(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("잘못된 인덱스: " + index);

    return (E) items[index]; // 비검사 경고 발생!
    // - items 배열은 Object[] 타입이다.
    // - 배열에 들어있는 값이 E 타입인지 확신할 수 없기 때문에
    //   E 타입으로 형변환하는 부분에서 컴파일러는 비검사 경고를 발생시킨다.
    // - 그런데 add(E obj) 메서드를 통해서만 배열에 값을 담기 때문에
    //   E 타입이 아닌 값이 들어갈 수 없다.
    // - 따라서 이 경고는 무시해도 된다.
  }

  public int size() {
    return size;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    MyList<String> list = new MyList<>();
    list.add("홍길동");
    list.add("임꺾정");

    System.out.println(list.get(0));
    System.out.println(list.get(1));
  }
}
