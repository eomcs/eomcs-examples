// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam02;

class RomanNumerals {
  static boolean isRomanNumeral(String s) {
    // matches() 메서드는 호출될 때 마다 매번 Pattern 객체를 생성한다.
    // 따라서 성능이 매우 느리다.
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  }
}

public class Before {
  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      for (int j = 0; j < 1000; j++) {
        RomanNumerals.isRomanNumeral("MCMLXXVI");
      }
      long end = System.nanoTime();
      System.out.println(((end - start) / 1000) + " ns.");
    }
  }
}
