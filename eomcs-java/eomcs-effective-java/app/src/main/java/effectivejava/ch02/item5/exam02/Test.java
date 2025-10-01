// # 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
// - 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면,
//   싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
// - 이 자원들을 클래스가 직접 만들게 해서도 안된다.
// - 대신 필요한 자원을 혹은 그 자원을 만들어주는 팩토리를,
//   생성자에 혹은 정적 팩토리나 빌더에 넘겨주자.
// - 의존 객체 주입이라는 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.

package effectivejava.ch02.item5.exam02;

import java.util.List;

// 싱글턴을 잘못 사용한 예 - 유연하지 않고 테스트하기 어렵다.
// => 영어 사전 클래스
class EnglishLexicon {
  public boolean contains(String word) {
    // 사전에 단어가 있는지 검사하는 코드
    return true; // 예시를 위한 임시 구현
  }
}

// => 사전을 이용해 맞춤법을 검사하는 클래스
class SpellChecker {
  private final EnglishLexicon dictionary = new EnglishLexicon();

  private SpellChecker() {}
  public static final SpellChecker INSTANCE = new SpellChecker();

  public boolean isValid(String word) {
    return dictionary.contains(word);
  }
  public List<String> suggestions(String typo) {
    // 철자가 틀린 단어에 대한 교정 제안을 생성하는 코드
    return List.of("suggestion1", "suggestion2"); // 예시를 위한 임시 구현
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    SpellChecker spellChecker = SpellChecker.INSTANCE;

    String word = "example";
    if (spellChecker.isValid(word)) {
      System.out.println(word + " is valid.");
    } else {
      System.out.println(word + " is not valid. Suggestions: " + spellChecker.suggestions(word));
    }

    // [문제점] 언어를 바꾸거나 특수 분야의 어휘를 사용할 경우?
    // - 다른 사전을 사용하기 위해서는 SpellChecker 클래스의 코드를 수정해야 한다.
  }
}
