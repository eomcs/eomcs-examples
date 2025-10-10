// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
//

package effectivejava.ch06.item34.exam02;

// [주제] 열거 타입 등장 후: 정수 열거 패턴 대신 열거 타입으로 바꾸기
// - 완전한 형태의 클래스이다. 단순한 정수 값이 아니다.
// - 열거 타입 자체는 클래스이다.
// - 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개한다.
// - 밖에서 접근할 수 있는 생성자를 제공하지 않으므로 사실상 final 이다.
// - 따라서 클라이언트가 인스턴스를 직접 생성하거나 확장할 수 없다.
//   열거 타입으로 만들어진 인스턴스들은 딱 하나씩만 존재한다.
// - 열거 타입은 컴파일타임 타입 안전성을 제공한다.

enum Apple {
  FUJI,
  PIPPIN,
  GRANNY_SMITH
}

enum Orange {
  NAVEL,
  TEMPLE,
  BLOOD
}

public class Test {

  static void printApple(Apple item) {}

  public static void main(String[] args) throws Exception {
    // 열거 타입 상수를 사용하는 것은 일반 정수 상수를 사용하는 것과 똑같다.
    printApple(Apple.FUJI);
    printApple(Apple.PIPPIN);

    // 그러나 컴파일 시 타입 안전성을 검사한다.
    // - 단순 정수 상수를 사용했을 때는 잡아내지 못했다.
    //    printApple(Orange.BLOOD); // 컴파일 오류!

    // 타입이 다르기 때문에 동등 연산자로 비교할 수 없다.
    //    System.out.println(Apple.FUJI == Orange.NAVEL); // 컴파일 오류!

    // 열거 타입에는 각자의 이름 공간이 있어서 이름이 같은 상수가 존재해도 아무 문제가 없다.
    enum Fruit {
      APPLE, // 여기에 APPLE이 있다.
      ORANGE,
      PEAR
    }
    enum SmartphoneMaker {
      APPLE, // 여기에도 APPLE이 있다.
      SAMSUNG,
      Xiaomi
    }

    // 열거 타입에 상수를 추가하거나 순서를 바꿔도 클라이언트 코드를 다시 컴파일할 필요가 없다.
    // 공개되는 것은 오직 필드의 이름뿐이라, 상수 값이 클라이언트로 각인되지 않기 때문이다.

    // 열거 타입의 toString() 메서드는 상수 이름을 반환하도록 재정의되어 있다.
    System.out.println(Apple.FUJI); // FUJI
    System.out.println(Apple.GRANNY_SMITH);

    // 열거 타입은 디버깅할 때도 유용하다.
    Apple value = Apple.PIPPIN; // 디버깅하면 value가 PIPPIN으로 보인다.
    System.out.println(value);
  }
}
