// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam01;

public class Test1 {
  public static void main(String[] args) throws Exception {
    // 불필요한 객체 생성 예:
    // - new 연산자를 사용해 매번 새로운 객체를 생성한다.
    String s1 = new String("hello");
    String s2 = new String("hello");
    String s3 = new String("hello");

    System.out.println(s1 == s2); // false
    System.out.println(s1 == s3); // false
    System.out.println(s2 == s3); // false

    // 객체 재사용 예:
    // - 리터럴(literal)을 사용해 String 객체를 생성한다.
    String s4 = "hello";
    String s5 = "hello";
    String s6 = "hello";

    System.out.println(s4 == s5); // true
    System.out.println(s4 == s6); // true
    System.out.println(s5 == s6); // true
  }
}
