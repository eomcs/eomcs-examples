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

package effectivejava.ch04.item24.exam02;

// [주제] 비정적 멤버 클래스(instance member class, non-static member class) 만들기
// - static이 붙지 않은 멤버 클래스이다.
// - 바깥 클래스의 인스턴스와 암묵적으로 연결된다.
//   그래서 비정적 멤버 클래스의 인스턴스 메서드에서 정규화된 this를 사용해
//   바깥 인스턴스의 메서드를 호출하거나 바깥 인스턴스의 참조를 가져올 수 있다.
// - 정규화된 this 란?
//   '바깥클래스명.this' 형태로 바깥 클래스의 이름을 명시하는 용법이다.
// - 바깥 인스턴스 없이는 생성할 수 없을 때 비정적 멤버 클래스로 만든다.
// - 멤버 클래스의 인스턴스가 생성될 때 바깥 클래스의 인스턴스와 관계가 맺어진다.
//   (바깥 클래스의 인스턴스에 대한 참조를 멤버 클래스에 저장한다.)
// - 가비지 컬렉션이 바깥 클래스의 인스턴스를 수거하지 못하는 메모리 누수가 발생할 수 있다.
//   멤버 클래스의 인스턴스에 바깥 인스턴스의 참조가 숨겨져 있어서,
//   멤버 클래스의 인스턴스가 살아 있는 한 바깥 인스턴스도 살아 있기 때문이다.
//   눈에 보이지 않으니 문제의 원인을 찾기 어려워 때때로 심각한 상황을 초래하기도 한다.
// - 공개된 클래스의 멤버 클래스가 public이나 protected라면,
//   멤버 클래스 역시 공개 API가 되기 때문에 신중하게 설계하고 문서화해야 한다.

class Outer {
  private String f1 = "Outer.f";

  private void m1() {
    System.out.println("Outer.m()");
  }

  // 비정적 멤버 클래스의 인스턴스 생성
  public Inner getInner() {
    return new Inner();
  }

  // 비정적 멤버 클래스(non-static member class; inner class)
  public class Inner {
    private String f2 = "Inner.f2";

    public void m2() {
      String f3 = "m2()의 f3";

      // 로컬 변수 접근
      System.out.println(f3);

      // 자신의 private 멤버 접근
      System.out.println(this.f2);

      // 바깥 클래스의 private 멤버에 접근 가능
      System.out.println(Outer.this.f1);

      // 바깥 클래스의 메서드 호출 가능
      Outer.this.m1();
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Outer outer = new Outer();
    Outer.Inner inner = outer.getInner();
    inner.m2();
  }
}
