// # 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
// - 싱글턴(Singleton): 인스턴스를 오직 하나만 생성할 수 있는 클래스
// - 적용 예:
//   - 함수와 같은 무상태(stateless) 객체
//   - 설계상 유일해야 하는 시스템 컴포넌트

package effectivejava.ch02.item3.exam02;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

// 방법2: 정적 팩터리 방식의 싱글턴
class Elvis {
  private static final Elvis INSTANCE = new Elvis();

  private Elvis() {}

  public static Elvis getInstance() {
    return INSTANCE;
  }

  public void leaveTheBuilding() {
    System.out.println("Whoa baby, I'm outta here!");
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Elvis elvis = Elvis.getInstance();
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

    // "pulic static final" vs "static factory"
    // - public static final 필드 방식:
    //   - 직관적이고 간단하다.
    // - 정적 팩터리 방식
    //   - 마음이 바뀌면 API를 변경하지 않고도 싱글턴이 아닌 클래스로 바꿀 수 있다.
    //   - 제네릭 싱글턴 팩토리로 만들 수 있다.
    //   - 정적 팩토리의 메서드를 공급자(supplier)로 사용할 수 있다.

    // 정적 팩토리 메서드를 공급자(supplier)로 사용한 예:
    Supplier<Elvis> elvisSupplier = Elvis::getInstance;

    // [결론]
    // - 정적 팩토리 방식의 장점이 필요하지 않다면 public static final 필드 방식을 사용하라.
  }
}
