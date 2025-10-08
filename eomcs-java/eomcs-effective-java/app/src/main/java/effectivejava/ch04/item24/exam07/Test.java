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

package effectivejava.ch04.item24.exam07;

// [주제] Iterator 만들기: 익명 클래스(anonymous class)
// - 클래스 이름이 없다.
// - 바깥 클래스의 멤버도 아니다.
// - 쓰이는 시점에 선언과 동시에 인스턴스가 만들어진다.
// - 오직 비정적 문맥에서 사용될 때만 바깥 클래스의 인스턴스를 참조할 수 있다.
// - 정적 문맥에서도 상수 변수 이외에 정적 멤버는 가질 수 없다.
//   상수 표현을 위해 초기화된 final 기본 타입과 문자열 필드만 가질 수 있다.
// - 여러 인터페이스를 구현할 수 없다.
// - 상속과 인터페이스 구현을 동시에 할 수 없다.
// - 익명 클래스는 표현식 중간에 등작하므로 10줄 이하로 짧지 않으면 가독성이 떨어진다.
// - 람다가 등작하기 전에 작은 함수 객체나 처리 객체를 만들 때 주로 익명 클래스를 사용했다.
// - 정적 팩토리를 구현할 때 주로 사용된다.

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
    // 인스턴스를 한 번만 생성할 것이라면 익명 클래스를 사용하여 Iterator 구현한다.
    // - 컴파일러는 다음 코드를 컴파일 할 때 익명 클래스는 별도의 .class 파일로 생성한다.
    //   예) MyList$1.class
    // - 익명 클래스의 인스턴스를 생성하는 코드로 변경한다.
    //   예) return new MyList$1(this);
    return new Iterator() {
      // 바깥 클래스의 인스턴스 주소를 저장할 필드가 컴파일 시에 자동으로 추가된다.
      // 예) MyList this$0;

      private int cursor = 0;

      // 바깥 클래스의 인스턴스 주소를 받는 생성자가 컴파일 시에 자동으로 추가된다.
      // 예) MyList$1(MyList this$0) { this.this$0 = this$0; }

      public boolean hasNext() {
        return cursor < MyList.this.size();
      }

      public Object next() {
        return MyList.this.get(cursor++);
      }
    };
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

    // 익명 클래스로 만든 Iterator 인스턴스 사용
    Iterator it = list.iterator();

    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
