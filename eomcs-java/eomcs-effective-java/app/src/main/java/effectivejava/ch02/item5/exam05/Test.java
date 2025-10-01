// # 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
// - 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면,
//   싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
// - 이 자원들을 클래스가 직접 만들게 해서도 안된다.
// - 대신 필요한 자원을 혹은 그 자원을 만들어주는 팩토리를,
//   생성자에 혹은 정적 팩토리나 빌더에 넘겨주자.
// - 의존 객체 주입이라는 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.

package effectivejava.ch02.item5.exam05;

import java.util.List;
import java.util.function.Supplier;

// 팩토리 메서드 패턴 + Supplier<T> 인터페이스
// => 사전 클래스의 동작 규약을 정의
interface Lexicon {
  boolean contains(String word);
}

// => 영어 사전 클래스
class EnglishLexicon implements Lexicon {
  public boolean contains(String word) {
    // 사전에 단어가 있는지 검사하는 코드
    return true; // 예시를 위한 임시 구현
  }
}

// => 한글 사전 클래스
class KoreanLexicon implements Lexicon {
  public boolean contains(String word) {
    // 사전에 단어가 있는지 검사하는 코드
    return true; // 예시를 위한 임시 구현
  }
}

// => 컴퓨팅 사전 클래스
class ComputingLexicon implements Lexicon {
  public boolean contains(String word) {
    // 사전에 단어가 있는지 검사하는 코드
    return true; // 예시를 위한 임시 구현
  }
}

class LexiconFactory {
  private String type;

  public LexiconFactory(String type) {
    this.type = type;
  }

  public Lexicon create() {
    switch (type) {
      case "EN":
        return new EnglishLexicon();
      case "KR":
        return new KoreanLexicon();
      case "COM":
        return new ComputingLexicon();
      default:
        throw new IllegalArgumentException("Unknown lexicon type: " + type);
    }
  }
}

// => 사전을 이용해 맞춤법을 검사하는 클래스
class SpellChecker {
  // 이 클래스에서 자원 객체를 직접 만들지 않는다.
  // => 대신 생성자를 통해 넘겨 받는다.
  private final Lexicon dictionary;

  public SpellChecker(Supplier<? extends Lexicon> dictionaryFactory) {
    this.dictionary = dictionaryFactory.get();
  }

  public boolean isValid(String word) {
    return dictionary.contains(word);
  }

  public List<String> suggestions(String typo) {
    return List.of(); // 예시를 위한 임시 구현
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    // 필요한 자원을 생성자를 통해 SpellChecker 객체에 주입한다.
    // => 주입할 자원은 Supplier<T> 인터페이스를 구현한 람다 표현식이나 메서드 참조를 사용해 넘긴다.
    SpellChecker englishSpellChecker = new SpellChecker(new LexiconFactory("EN")::create);
    SpellChecker koreanSpellChecker = new SpellChecker(new LexiconFactory("KR")::create);
    SpellChecker computingSpellChecker = new SpellChecker(new LexiconFactory("COM")::create);

    // 영어 사전을 사용하여 맞춤법 검사하기
    String word = "example";
    if (englishSpellChecker.isValid(word)) {
      System.out.println(word + " is valid.");
    } else {
      System.out.println(
          word + " is not valid. Suggestions: " + englishSpellChecker.suggestions(word));
    }

    // 한글 사전을 사용하여 맞춤법 검사하기
    String word2 = "예제";
    if (koreanSpellChecker.isValid(word2)) {
      System.out.println(word2 + " is valid.");
    } else {
      System.out.println(
          word2 + " is not valid. Suggestions: " + koreanSpellChecker.suggestions(word2));
    }

    // 컴퓨팅 사전을 사용하여 맞춤법 검사하기
    String word3 = "컴파일러";
    if (computingSpellChecker.isValid(word3)) {
      System.out.println(word3 + " is valid.");
    } else {
      System.out.println(
          word3 + " is not valid. Suggestions: " + computingSpellChecker.suggestions(word3));
    }
  }
}
