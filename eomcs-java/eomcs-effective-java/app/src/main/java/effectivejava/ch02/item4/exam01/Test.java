// # 아이템 4. 인스턴스화를 막으려거든 private 생성자를 사용하라
// - 정적 메서드나 정적 필드만을 담을 클래스를 만들 때 주로 사용
//   예) java.lang.Math, java.util.Arrays, java.util.Collections 등
// - 추상클래스로 인스턴스화를 막을 수 없다.
// - private 생성자를 추가하면 클래스의 인스턴화를 막을 수 있다.
// - 상속을 막는 효과도 있다.
//   왜? 서브 클래스 생성자에서 수퍼 클래스의 생성자를 호출할 수 없기 때문.

package effectivejava.ch02.item4.exam01;

class UtilityClass {

  // 컴파일러가 자동으로 default 생성자를 추가하지 못하도록 한다.(인스턴스 생성 방지)
  private UtilityClass() {
    // 내부에서 실수로 생성자를 호출하지 않도록 하기 위함
    throw new AssertionError();
  }

  // 유틸리티 메서드들...
  public static void hello(String name) {
    System.out.println("Hello " + name);
  }
}

// 상속 불가
// class SubUtilityClass extends UtilityClass {}

public class Test {
  public static void main(String[] args) throws Exception {
    UtilityClass.hello("홍길동");
  }
}
