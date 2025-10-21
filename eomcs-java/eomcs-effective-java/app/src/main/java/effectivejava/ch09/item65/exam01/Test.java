// # 아이템 65. 리플렉션보다는 인터페이스를 사용하라
// [리플렉션 기능(java.lang.reflect)]
// - 프로그램에서 임의의 클래스에 접근할 수 있다.
// - Class 객체를 통해 생성자, 메서드, 필드를 제어하는 객체를 가져올 수 있고,
//   이들 제어객체를 통해 생성자, 메서드, 필드를 제어하고 인스턴스를 생성할 수 있다.
//
// [리플렉션의 단점]
// - 컴파일타임 타입 검사가 주는 이점을 하나도 누릴 수 없다.
//   존재하지 않는 혹은 접근할 수 없는 메서드를 호출하려 시도하면 런타임 오류가 발생한다.
//   주의해서 대비 코드를 작성해둬야 한다.
// - 코드가 지저분하고 장황해진다.
// - 성능이 떨어진다.
//   리플렉션을 통한 메서드 호출은 일반 메서드 호출보다 훨씬 느리다.
//
// [리플렉션 사용]
// - 아주 제한된 형태로만 사용해야 그 단점을 피하고 이점을 취할 수 있다.
// - 리플렉션은 인스턴스 생성에만 쓰고, 이렇게 만든 인스턴스는 인터페이스나 상위 클래스를 통해 참조하라.
//

package effectivejava.ch09.item65.exam01;

// [주제] 리플렉션 사용 예 - 인스턴스를 생성하고 인터페이스로 참조

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class Test {
  public static void main(String[] args) throws Exception {
    // 클래스 이름으로 Class 객체 얻기
    Class<? extends Set<String>> cl = null;
    try {
      cl = (Class<? extends Set<String>>) Class.forName("java.util.HashSet");
      //      cl = (Class<? extends Set<String>>) Class.forName("java.util.TreeSet");
    } catch (ClassNotFoundException e) {
      fatalError("Class not found.");
    }

    // 생성자 제어 객체 얻기
    Constructor<? extends Set<String>> cons = null;
    try {
      cons = cl.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      fatalError("No parameterless constructor");
    }

    // 인스턴스 생성하기
    Set<String> s = null;
    try {
      s = cons.newInstance();
    } catch (IllegalAccessException e) {
      fatalError("Constructor not accessible");
    } catch (InstantiationException e) {
      fatalError("Class not instantiable.");
    } catch (InvocationTargetException e) {
      fatalError("Constructor threw " + e.getCause());
    } catch (ClassCastException e) {
      fatalError("Class doesn't implement Set");
    }

    // Set 객체에 값 담기
    String[] names = {"홍길동", "임꺽정", "장길산", "유관순", "안중근", "윤봉길"};
    s.addAll(Arrays.asList(names));
    System.out.println(s);

    // [정리]
    // - 리플렉션의 단점을 전형적으로 보여주고 있다.
    //   1) 런타임에 여섯 가지나 되는 예외를 던질 수 있다.
    //      리플렉션 없이 인스턴스를 생성했다면 컴파일타임에 잡아낼 수 있었을 예외들이다.
    //   2) 클래스 이름만으로 인스턴스를 생성하기 위해 무료 20줄 이상의 코드를 작성했다.
    // - 리플렉션으로 객체를 생성한 이후 코드는 컴파일타임 타입 검사를 제대로 수행한다.
    // - 리플렉션은 런타임에 존재하지 않을 수도 있는 다른 클래스, 메서드, 필드와의 의존성을 관리할 때 적합하다.
    //   여러 개 외부 패키지를 다룰 때 유용하다.
  }

  private static void fatalError(String msg) {
    System.err.println(msg);
    System.exit(1);
  }
}
