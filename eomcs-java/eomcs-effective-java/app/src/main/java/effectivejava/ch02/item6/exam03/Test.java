// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam03;

public class Test {

  private static long sum1() {
    Long sum = 0L;
    for (long i = 0; i < Integer.MAX_VALUE; i++) {
      sum += i; // long 변수 i에 대해 auto-boxing 이 일어난다.
    }
    return sum;
  }

  private static long sum2() {
    long sum = 0L;
    for (long i = 0; i < Integer.MAX_VALUE; i++) {
      sum += i; // auto-boxing 이 일어나지 않는다.
    }
    return sum;
  }

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 3; i++) {
      long start = System.nanoTime();
      sum1();
      long end = System.nanoTime();
      System.out.println("Wrapper 객체 사용: " + ((end - start) / 1000) + " ns.");
    }

    System.out.println();

    for (int i = 0; i < 3; i++) {
      long start = System.nanoTime();
      sum2();
      long end = System.nanoTime();
      System.out.println("Primitive Type 사용: " + ((end - start) / 1000) + " ns.");
    }

    // [결론]
    // - 기본 타입(primitive type)을 사용하자.
    // - 의도치 않는 오토박싱(autoboxing)이 숨어들지 않게 주의하자.
    // - 왜? 오토박싱(boxing) 과정에서 객체 생성이 일어나기 때문이다.
    // - 기본 타입을 사용하면 불필요한 객체 생성을 피할 수 있어 성능이 향상된다.
  }
}
