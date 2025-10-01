// # 아이템 6. 불필요한 객체 생성을 피하라
// - 똑같은 기능의 객체를 매번 생성하기 보다는 하나의 객체를 재사용하는 편이 낫다.

package effectivejava.ch02.item6.exam02;

import java.util.regex.Pattern;

// 지연 초기화(lazy initialization) 기법
// - static 필드를 처음 사용할 때 초기화한다.
// - 불필요한 초기화를 없앨 수 있다.
// - 단 코드를 더 복잡하게 만들고, 성능은 크게 개선되지 않는다.
class RomanNumerals3 {

  // 클래스를 로딩할 때 초기화하지 않는다.
  private static Pattern ROMAN;

  static boolean isRomanNumeral(String s) {
    if (ROMAN == null) {
      // 처음 사용할 때 초기화한다.
      // 이것을 "지연 초기화(lazy initialization)"라 부른다.
      // 괜히 조건문만 추가되어 코드만 복잡해지고, 그렇게 성능이 크게 개선되는 것도 아니다.
      ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }
    return ROMAN.matcher(s).matches();
  }
}

public class After2 {
  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      for (int j = 0; j < 1000; j++) {
        RomanNumerals3.isRomanNumeral("MCMLXXVI");
      }
      long end = System.nanoTime();
      System.out.println(((end - start) / 1000) + " ns.");
    }

    // [결론]
    // - 지연 초기화(lazy initialization) 기법은 무엇이고 어떤 문제점이 있는지 알아두자.
    //
  }
}
