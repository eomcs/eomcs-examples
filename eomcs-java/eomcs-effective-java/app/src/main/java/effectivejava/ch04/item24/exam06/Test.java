// # 아이템 24. 멤버 클래스는 되도록 static으로 만들라
// [중첩 클래스(Nested Class)]
// - 다른 클래스 안에 정의된 클래스이다.
// - 자신을 감싼 클래스에서만 쓰여야 하며, 그 외의 쓰임새가 있다면 top-level 클래스로 만들어야 한다.
// - 중첩 클래스의 종류
//   - 정적 멤버 클래스(static member class)
//   - 내부 클래스(inner class)
//     - 인스턴스 멤버 클래스(instance member class, non-static member class)
//     - 지역 클래스(local class)
//     - 익명 클래스(anonymous class)

package effectivejava.ch04.item24.exam06;

// [주제] Iterator 만들기: 지역 클래스(local class)
// - 네 가지 중첩 클래스 중에서 가장 드물게 사용된다.
// - 지역 변수를 선언할 수 있는 곳이면 어디서든 선언할 수 있다.
// - 유효 범위도 지역 변수와 같다.
// - 멤버 클래스 처럼 이름이 있고, 반복해서 사용할 수 있다.
// - 비정적 문맥에서 선언되면 바깥 클래스의 인스턴스를 참조할 수 있다.
// - 정적 멤버는 가질 수 없으며, 가독성을 위해 짧게 작성해야 한다.

interface List {
  void add(Object obj);

  Object get(int index);

  int size();
}

class MyList implements List {
  private Object[] items;
  private int size = 0;

  public MyList() {
    this.items = new Object[10];
  }

  public void add(Object obj) {
    if (size == items.length) throw new IndexOutOfBoundsException("MyList 용량 초과");
    items[size++] = obj;
  }

  public Object get(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("잘못된 인덱스: " + index);
    return items[index];
  }

  public int size() {
    return size;
  }

  public Iterator iterator() {
    // 메서드 안에서만 사용할 클래스인 경우 지역 클래스로 정의한다.
    class MyIterator implements Iterator {
      // 바깥 클래스의 인스턴스 주소를 저장할 필드가 컴파일 시에 자동으로 추가된다.
      // 예) MyList this$0;

      private int cursor = 0;

      // 바깥 클래스의 인스턴스 주소를 받는 생성자가 컴파일 시에 자동으로 추가된다.
      // 예) MyIterator(MyList this$0) { this.this$0 = this$0; }

      public boolean hasNext() {
        return cursor < MyList.this.size();
      }

      public Object next() {
        return MyList.this.get(cursor++);
      }
    }

    // 지역 클래스의 인스턴스를 생성할 때 바깥 클래스의 인스턴스를 직접 넘겨줄 필요가 없다.
    // 컴파일 시에 바깥 클래스의 인스턴스 주소를 받는 생성자를 호출하도록 코드가 변경된다.
    return new MyIterator(); // 예) new MyIterator(this);
  }
}

interface Iterator {
  boolean hasNext();

  Object next();
}

public class Test {
  public static void main(String[] args) throws Exception {
    MyList list = new MyList();
    list.add("홍길동");
    list.add("임꺾정");
    list.add("유관순");
    list.add("안중근");
    list.add("윤봉길");

    // 지역 클래스로 만든 Iterator 인스턴스 사용
    Iterator it = list.iterator();

    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
