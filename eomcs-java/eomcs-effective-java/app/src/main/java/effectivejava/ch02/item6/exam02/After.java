// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam02;

import java.util.regex.Pattern;

class RomanNumerals2 {
  // 객체 생성에 비용이 많이 드는 Pattern 객체를 미리 생성해 둔다.
  // - static final 필드로 선언해, 클래스 로딩 시점에 단 한번만 생성된다.
  // - 따라서 성능이 매우 빠르다.
  private static final Pattern ROMAN =
      Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

  static boolean isRomanNumeral(String s) {
    // 미리 생성된 Pattern 객체를 재활용한다.
    return ROMAN.matcher(s).matches();
  }
}

public class After {
  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      for (int j = 0; j < 1000; j++) {
        RomanNumerals2.isRomanNumeral("MCMLXXVI");
      }
      long end = System.nanoTime();
      System.out.println(((end - start) / 1000) + " ns.");
    }

    // [결론]
    // - 객체 생성에 시간이 많이 걸리는 경우, 기존 객체를 재활용하여 성능을 높일 수 있다.
    // - Pattern 인스턴스를 static final 필드로 끄집어내니, 코드의 의미가 훨씬 잘 드러난다.
    //
  }
}
