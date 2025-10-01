// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam01;

public class Test2 {
  public static void main(String[] args) throws Exception {
    // 불필요한 객체 생성 예:
    // - new 연산자를 사용해 매번 새로운 객체를 생성한다.
    // - 이런 이유로 Java 9에서 사용자제(deprecated) API로 지정되었다.
    Boolean b1 = new Boolean(true);
    Boolean b2 = new Boolean(true);
    Boolean b3 = new Boolean(true);

    System.out.println(b1 == b2); // false
    System.out.println(b1 == b3); // false
    System.out.println(b2 == b3); // false

    // 객체 재사용 예:
    // - 팩토리 메서드를 사용해 Boolean 객체를 생성한다.
    Boolean b4 = Boolean.valueOf(true);
    Boolean b5 = Boolean.valueOf(true);
    Boolean b6 = Boolean.valueOf(true);

    System.out.println(b4 == b5); // true
    System.out.println(b4 == b6); // true
    System.out.println(b5 == b6); // true
  }
}
