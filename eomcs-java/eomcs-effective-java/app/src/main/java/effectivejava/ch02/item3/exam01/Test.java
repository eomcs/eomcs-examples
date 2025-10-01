// # 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
// - 싱글턴(Singleton): 인스턴스를 오직 하나만 생성할 수 있는 클래스
// - 적용 예:
//   - 함수와 같은 무상태(stateless) 객체
//   - 설계상 유일해야 하는 시스템 컴포넌트

package effectivejava.ch02.item3.exam01;

import java.lang.reflect.Constructor;

// 방법1: public static final 필드 방식
class Elvis {
  public static final Elvis INSTANCE = new Elvis();

  private Elvis() {}

  public void leaveTheBuilding() {
    System.out.println("Whoa baby, I'm outta here!");
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Elvis elvis = Elvis.INSTANCE;
    elvis.leaveTheBuilding();

    // [주의]
    // - 리플렉션 API를 사용하면 private 생성자에 접근할 수 있다.
    Constructor<Elvis> constructor = Elvis.class.getDeclaredConstructor();
    constructor.setAccessible(true); // 생성자의 접근 권한 변경
    Elvis elvis2 = constructor.newInstance();
    elvis2.leaveTheBuilding();

    System.out.println(elvis == elvis2); // false

    // [해결책]
    // - 생성자를 수정하여 두 번째 객체를 생성하려 할 때 예외를 던진다.
  }
}
