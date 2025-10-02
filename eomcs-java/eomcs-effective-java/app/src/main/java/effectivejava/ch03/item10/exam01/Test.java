// # 아이템 10. equals() 는 일반 규약을 지켜 재정의하라
// 재정의하지 말아야 할 상황
// - 각 인스턴스가 본질적으로 고유하다.
//   - 값을 표현하는 게 아니라 동작하는 객체를 표현하는 클래스인 경우
// - 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
// - 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는다.
// - 클래스가 private이거나, package-private이고 equals() 메서드를 호출할 일이 없다.

package effectivejava.ch03.item10.exam01;

// equals()를 호출하지 못하도록 막는 방법
class MyObject {
  @Override
  public boolean equals(Object obj) {
    throw new AssertionError();
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    MyObject obj1 = new MyObject();
    MyObject obj2 = new MyObject();

    // equals()를 호출하면 예외가 발생한다.
    obj1.equals(obj2);
  }
}
