// # 아이템 40. @Override 애너테이션을 일관되게 사용하라
// - @Override는 메서드 선언에만 달 수 있다.
//   상위 타입의 메서드를 재정의했음을 뜻한다.
// - 이 애너테이션을 일관되게 사용하면 여러 가지 악명 높은 버그들을 예방해준다.
// - @Override 애너테이션을 붙이면 컴파일러가 재정의 여부를 검사해준다.
// - 추상 메서드를 재정의할 때는 굳이 @Override를 붙이지 않아도 된다.
// - IDE에서 @Override를 일관되게 붙이도록 설정했다면,
//   실수로 메서드를 재정의한 경우에도 경고를 받을 수 있어 좋다.
// - @Override는 인터페이스의 메서드를 재정의할 때도 사용할 수 있다.
// - 추상 클래스나 인터페이스에서는 상위 클래스나 상위 인터페이스의 메서드를 재정의하는 모든 메서드에
//   @Override를 다는 것이 좋다.

package effectivejava.ch06.item40.exam01;

// [주제] 영어 알파벳 2개로 구성된 문자열을 표현하는 클래스 - 버그 찾기

import java.util.HashSet;
import java.util.Set;

class Bigram {
  private final char first;
  private final char second;

  public Bigram(char first, char second) {
    this.first = first;
    this.second = second;
  }

  //  @Override // 제대로 재정의하지 않으면 컴파일 오류가 발생한다.
  public boolean equals(Bigram b) {
    return b.first == first && b.second == second;
  }

  public int hashCode() {
    return 31 * first + second;
  }
}

public class Test {
  public static void main(String[] args) {
    Set<Bigram> s = new HashSet<>();
    for (int i = 0; i < 10; i++) for (char ch = 'a'; ch <= 'z'; ch++) s.add(new Bigram(ch, ch));
    System.out.println(s.size());

    // [실행 결과]
    // Set은 중복을 허용하지 않으므로 26이 나와야 한다.
    // 그러나 260이 출력된다.

    // [원인]
    // equals 메서드가 재정의되지 않았기 때문이다.
    //   Object.equals(Object o)
    //   Bigram.equals(Bigram b)
    // Object의 equals 메서드를 시그너처에 맞게 올바로 재정의 하지 못했다.
    // 즉 '오버라이딩'이 아니라 '오버로딩'이 된 것이다.

    // [해결책]
    // 재정의하는 메서드에 @Override 애너테이션을 붙여 컴파일러가 검사하게 한다.

  }
}
