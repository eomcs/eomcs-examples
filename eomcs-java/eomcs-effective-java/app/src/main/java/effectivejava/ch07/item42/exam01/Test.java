// # 아이템 42. 익명 클래스보다는 람다를 사용하라
// [자바에서 함수 타입을 표현하는 방법]
// 1) 추상 메서드를 하나만 담은 인터페이스(드물게 추상 클래스)
//    - 이런 인터페이스의 인스턴스를 함수 객체(function object)라고 부른다.
//    - 특정 함수나 동작을 나타내는 데 썼다.
// 2) 익명 클래스로 함수 객체를 구현
//    - 코드가 너무 길기 때문에 함수형 프로그래밍에 적합하지 않았다.
// 3) 람다 표현식(lambda expression)으로 함수형 인터페이스(functional interface)를 구현
//    - 함수형 인터페이스: 추상 메서드를 하나만 담은 인터페이스
//    - 람다 표현식은 익명 클래스보다 코드가 더 간결하다.
//    - 자질구레한 코드들이 사라지고 어떤 동작을 하는지가 명확하게 드러난다.

package effectivejava.ch07.item42.exam01;

// [주제] 익명 클래스 vs 람다 표현식 vs (비교자 생성 메서드 + 메서드 레퍼런스)

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    List<String> words =
        new ArrayList<>(List.of("strawberry", "apple", "orange", "banana", "kiwi", "mango"));

    // 문자열을 길이 순으로 정렬하기

    // 방법1: 익명 클래스로 Comparator 인터페이스 구현하기
    System.out.println(words); // 정렬 전
    Collections.sort(
        words,
        new Comparator<String>() {
          @Override
          public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
          }
        });
    System.out.println(words); // 정렬 후

    // 방법2: 람다 표현식으로 Comparator 인터페이스 구현하기
    System.out.println("--------------------");
    Collections.shuffle(words); // 리스트를 섞는다.
    System.out.println(words); // 정렬 전
    Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
    System.out.println(words); // 정렬 후
    // [람다 표현식]
    // - 컴파일러가 문맥을 살펴 타입을 추론하기 때문에,
    //   파라미터의 타입이나 리턴 타입을 명시할 필요가 없다.
    //   다만 상황에 따라 컴파일러가 타입을 추론할 수 없을 경우가 있는데,
    //   그럴 때는 프로그래머가 직접 타입을 명시해야 한다.
    // - 타입을 명시해야 코드가 더 명확할 때만 제외하고는, 람다의 모든 파라미터 타입은 생략하라.
    //   컴파일러가 "타입을 알 수 없다"는 오류를 낼 때만 해당 타입을 명시하면 된다.
    // - raw type 대신 제네릭을 써야 하는 이유는,
    //   컴파일러가 타입을 추론하는 데 필요한 타입 정보 대부분을 제네릭에서 얻기 때문이다.
    //   우리가 이 정보를 제공하지 않으면 컴파일러는 람다의 타입을 추론할 수 없게 되어,
    //   결국 우리가 일일이 타입을 명시해야 한다. 코드가 너저분해질 것이다.

    // 방법3: '비교자 생성 메서드(comparator construction method)'를 사용하면 더 간결하다.
    System.out.println("--------------------");
    Collections.shuffle(words); // 리스트를 섞는다.
    System.out.println(words); // 정렬 전
    Collections.sort(words, comparingInt(String::length));
    System.out.println(words); // 정렬 후
    // [비교자 생성 메서드]
    // - Comparator.comparingInt()는 Comparator 인스턴스를 간편하게 만들어주는 정적 팩토리 메서드이다.
    // - Comparator 가 값을 비교할 때 사용할 키 추출 함수를 파라미터로 받는다.
    // - 키 추출 함수는 String의 length() 메서드를 '메서드 레퍼런스'로 전달한다.
    // - 두 개의 int 값을 비교하는 Comparator를 직접 정의하는 것 보다 훨씬 간결하다.

    // 방법4: Java 8부터 List 인터페이스에 추가된 sort() 메서드를 사용하면 더 간결하다.
    System.out.println("--------------------");
    Collections.shuffle(words); // 리스트를 섞는다.
    System.out.println(words); // 정렬 전
    words.sort(comparingInt(String::length));
    System.out.println(words); // 정렬 후
  }
}
