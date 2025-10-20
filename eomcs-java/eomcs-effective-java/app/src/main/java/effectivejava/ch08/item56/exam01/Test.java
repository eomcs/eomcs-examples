// # 아이템 56. 공개된 API 요소에는 항상 문서화 주석을 작성하라
// - 문서화 주석을 작성하는 규칙은 "문서화 주석 작성법(How to Write Doc Comments)" 웹페이지를 참고하라.
// - API를 올바로 문서화하려면 공개된 모든 클래스, 인터페이스, 메서드, 필드 선언에 문서화 주석을 달아야 한다.
//   직렬화할 수 있는 클래스라면 직렬화 형태에 관해서도 적어야 한다.
// - 메서드용 문서화 주석에는 해당 메서드와 클라이언트 사이의 규약을 명료하게 기술해야 한다.
//   상속용으로 설계된 클래스의 메서드가 아니라면 무엇을 하는지를 기술해야 한다.
//   how가 아닌 what을 기술하라.
//   해당 메서드를 호출하기 위한 전제조건(precondition)을 모두 나열해야 한다.
//   메서드가 성공적으로 수행된 후에 만족해야 하는 사후조건(postcondition)도 모두 나열해야 한다.
// - 전제조건:
//   @throws - 비검사 예외를 선언하여 암시적으로 기술한다.
//   @param - 그 조건에 영향받는 매개변수에 기술할 수도 있다.
// - 부작용:
//   사후 조건으로 명확히 나타나지는 않지만, 시스템의 상태에 어떤한 변화를 가져오는 것.
//   예를 들어, 백그라운드 스레드를 시작시키는 메서드라면 그 사실을 문서에 밝혀야 한다.

package effectivejava.ch08.item56.exam01;

// [주제] 주석 작성 예

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test<E> {
  // 메서드에 대한 문서화 주석 작성 예:
  // - @param: 모든 매개변수
  //   - 매개변수가 뜻하는 값을 설명하는 명사구 또는 산술 표현식
  //   - 마침표를 찍지 않는다.
  // - @return: 반환 타입이 void가 아닌 모든 메서드. 메서드 설명과 같을 때 생략 가능.
  //   - 리턴 값을 설명하는 명사구 또는 산술 표현식
  //   - 마침표를 찍지 않는다.
  // - @throws: 검사든 비검사든 발생할 가능성이 있는 모든 예외
  //   - if로 시작해 해당 예외를 던지는 조건을 설명한다.
  //   - 마침표를 찍지 않는다.
  // - 자바독 유틸리티는 문서화 주석을 HTML로 변환하므로 HTML 태그를 사용할 수 있다.
  // - {@code ...}: 코드용 폰트로 렌더링한다.
  //   - 코드 조각을 표시할 때 사용한다.
  // - "this"의 의미:
  //   - 문서화 주석에서 "this"가 가리키는 것은 호출된 메서드가 자리하는 객체를 가리킨다.
  //
  /**
   * Returns the element at the specified position in this list.
   *
   * <p>This method is <i>not</i> guaranteed to run in constant time. In some implementations it may
   * run in time proportional to the element position.
   *
   * @param index index of element to return; must be non-negative and less than the size of this
   *     list
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >=
   *     this.size()})
   */
  E get(int index) {
    return null;
  }

  // 클래스를 상속용으로 설계할 때,
  // - 자기 사용 패턴(self-use pattern)에 대해서도 문서로 남겨,
  //   다른 프로그래머가 그 메서드를 올바로 재정의하는 방법을 알려줘야 한다.
  // - @implSpec 태그로 문서화 한다.
  //   해당 메서드와 하위 클래스 사이의 계약을 설명하여,
  //   하위 클래스들이 그 메서드를 상속하거나 super 키워드를 이용해 호출할 때
  //   그 메서드가 어떻게 동작하는지를 명확히 인지하고 사용하도록 해줘야 한다.
  // - javadoc 명령줄에서 -tag "implSpec:a:Implementation Requirements:" 스위치를 켜주지 않으면
  //   @implSpec 태그를 무시해버린다.
  //
  /**
   * Returns true if this collection is empty.
   *
   * @implSpec This implementation returns {@code this.size() == 0}.
   * @return true if this collection is empty
   */
  public boolean isEmpty() {
    return false;
  }

  // API 설명에 <, >, & 등의 HTML 메타문자를 포함시키려면,
  // - {@literal ...} 태그를 사용하라.
  //   이 태그는 HTML 마크업이나 자바독 태그를 문시하게 해준다.
  //   코드 폰트로 렌더링하지는 않는다.
  //
  /** A geometric series converges if {@literal |r| < 1}. */
  public void fragment() {}

  // 문서화 주석의 첫 번째 문장
  // - 해당 요소의 요악 설명(summary description)으로 간주된다.
  // - 요약 설명은 반드시 대상의 기능을 고유하게 기술해야 한다.
  // - 헷갈리지 않으려면 한 클래스 안에 요약 설명이 똑같은 멤버가 있어서는 안 된다.
  //   다중정의 메서드도 마찬가지다.
  // - 요약 설명이 끝나는 판단 기준
  //   {<마침표> <공백> <다음 문장 시작>} 에서 <마침표>까지다.
  //   <공백>은 스페이스, 탭, 줄바꿈이다.
  //   <다음 문장 시작>은 '소문자가 아닌' 문자다.
  // - {@literal ...}: 태그 안에 있는 마침표는 요약 설명 종료 판단 기준에서 제외된다.
  //
  /** A suspect, such as Colonel Mustard or {@literal Mrs. Peacock}. */
  public enum FixedSuspect {
    MISS_SCARLETT,
    PROFESSOR_PLUM,
    MRS_PEACOCK,
    MR_GREEN,
    COLONEL_MUSTARD,
    MRS_WHITE
  }

  // - {@summary ...}: 요약 설명을 명시적으로 지정할 수도 있다.
  /** {@summary A suspect in the Clue game.} */
  public enum Suspect {}

  // 요약 설명은 완전한 문장이 되는 경우가 드물다.
  // - 메서드와 생성자의 요약 설명은 동작을 설명하는 "주어가 없는 동사구"여야 한다.
  // - 한글은 원래부터 문맥상 주어가 명확하면 일반적으로 생략하기 때문에 이 규칙을 따르기 쉽다.
  // - 2인칭 문장(return the number)이 아닌 3인칭 문장(returns the number)으로 써야 한다.
  //
  /** 영어) Returns the number of elements in this collection=. 한글) 이 컬렉션의 원소 개수를 반환한다. */
  public void size() {}

  // 클래스, 인터페이스, 필드의 요약 설명은 대상을 설명하는 명사절이어야 한다.
  /** 영어) An instantaneous point on the time-line. 한글) 시간 축 상의 한 순간. */
  class Instant {

    /**
     * 영어) The float value that is closer than any other to pi, the ratio of a circle's
     * circumference to its diameter. 한글) 원주와 지름의 비율인 파이에 가장 근접한 부동소수점 값.
     */
    final float PI = 3.14F;
  }

  // 문서화 주석에 추가로 색인화 할 수 있다.
  // - API 문서 페이지 오른쪽 위에 있는 검색창에 키워드를 입력하면
  //   관련 페이지들이 드롭다운 메뉴로 나타난다.
  //   이를 통해 API 문서를 쉽게 검색할 수 있다.
  // - 클래스, 메서드, 필드 같은 API 요소의 색인은 자동으로 만들어 진다.
  // - {@index ...}: 추가 색인어를 지정할 때 사용한다.
  //
  /** This method complies with the {@index IEEE 754} standard. */
  public void fragment2() {}

  // 제네릭 타입이나 제네릭 메서드를 문서화할 때는 모든 타입 매개변수에 주석을 달아야 한다.
  //
  /**
   * A generic map interface.
   *
   * @param <K> the type of keys maintained by this map
   * @param <V> the type of mapped values
   */
  public interface Map<K, V> {}

  // 열거 타입을 문서화할 때는 상수들에도 주석을 달아야 한다.
  // - 열거 타입 자체와 열거 타입의 public 메서드도 문서화해야 한다.
  // - 설명이 짧다면 주석 전체를 한 문장으로 써도 된다.
  //
  /** An instrument section of a symphony orchestra. */
  public enum OrchestraSection {
    /** Woodwinds, such as flute, clarinet, and oboe. */
    WOODWIND,

    /** Brass instruments, such as french horn and trumpet. */
    BRASS,

    /** Percussion instruments, such as timpani and cymbals. */
    PERCUSSION,

    /** Stringed instruments, such as violin and cello. */
    STRING;
  }

  // 애너테이션 타입을 문서화할 때는 애너테이션 타입 자체는 물론 멤버들에도 모두 주석을 달아야 한다.
  // - 필드 설명은 명사구로 한다.
  // - 요약설명은,
  //   프로그램 요소에 이 애너테이션을 단다는 것이 어떤 의미인지를 설명하는 동사로로 작성한다.
  //
  /**
   * Indicates that the annotated method is a test method that must throw the designated exception
   * to pass.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface ExceptionTest {
    /**
     * The exception that the annotated test method must throw in order to pass. (The test is
     * permitted to throw any subtype of the type described by this class object.)
     */
    Class<? extends Throwable> value();
  }

  public static void main(String[] args) {
    // 패키지를 설명하는 문서화 주석
    // - "package-info.java" 파일에 작성한다.
    //   이 파일은 패키지 선언을 반드시 포함해야 하며,
    //   패키지 선언 관련 애너테이션을 추가로 포함할 수도 있다.

    // 모듈을 설명하는 문서화 주석
    // - "module-info.java" 파일에 작성한다.

    // [주의 사항]
    // - API 문서화에서 자주 누락되는 설명이 있다.
    //   "스레드 안전성"과 "질력화 가능성"이다.
    //   클래스 혹은 정적 메서드가 스레드 안전하든 그렇지 않든,
    //   스레드 안전 수준을 반드시 API 설명에 포함해야 한다.
    //   직렬화할 수 있는 클래스라면, 직렬화 형태도 API 설명에 기술해야 한다.
    // - 여러 클래스가 상호작용하는 복잡한 API라면,
    //   주석 이외도 전체 아키텍처에 대한 설명을 추가해야 할 필요가 있다.
    //   관련 문서가 있다면 그 문서의 링크를 제공해주면 좋다.

    // [문서화 주석의 상속]
    // - 문서화 주석이 없는 API 요소를 발견하면,
    //   javadoc은 인터페이스에서 먼저 찾는다. 그리고 상위 클래스에서 찾는다.
    // - {@inheritDoc}: 문서화 주석을 상속받고자 할 때 사용한다.
    //   클래스는 자신이 구현한 인터페이스의 주석을 복사해 붙여넣지 않고 재사용할 수 있다.
    //   똑같은 문서화 주석 여러 개를 유지보수하는 부담을 줄일 수 있다.
    //   단, 사용하기 까다롭고 제약도 조금 있다.
    //   자세한 것은 오라클 공식 문서를 참고하라.

    // [javadoc]
    // - 기본적으로 HTML 4.01 문서를 생성한다.
    // - HTML 5 버전으로 문서를 생성하고 싶다면, "-html5" 스위치를 사용하라.
    //
  }
}
