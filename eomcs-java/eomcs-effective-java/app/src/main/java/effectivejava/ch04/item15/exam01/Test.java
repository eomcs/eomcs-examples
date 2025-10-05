// # 아이템 15. 클래스와 멤버의 접근 권한을 최소화하라
// - 어설프게 설계된 컴포넌트와 잘 설계된 컴포넌트의 가장 큰 차이는,
//   클래스 내부 데이터와 내부 구현 정보를 외부 컴포넌트로부터 얼마나 잘 숨겼느냐다.
// - 잘 설계된 컴포넌트는 모든 내부 구현을 완벽히 숨겨,
//   구현과 API를 깔끔히 분리하다.
// - 오직 API를 통해서만 다른 컴포넌트와 소통하며
//   서로의 내부 동작 방식에는 전혀 개의치 않는다.
// - 정보 은닉, 혹은 캡슐화(encapsulation)라고 하는 이 개념은 소프트웨어 설계의 근간이 되는 원리다.

// [정보 은닉의 이점]
// - 시스템 개발 속도를 높인다.
//   여러 컴포넌트를 병렬로 개발할 수 있기 때문이다.
// - 시스템 관리 비용을 낮춘다.
//   컴포넌트를 더 빨리 파악하여 디버깅할 수 있고, 다른 컴포넌트로 교체하는 부담도 적기 때문이다.
// - 성능을 높여주지는 않지만, 성능 최적화에 도움을 준다.
//   다른 컴포넌트에 영향을 주지 않고 최적화 대상 컴포넌트만 고칠 수 있기 때문이다.
// - 소프트웨어 재사용성을 높인다.
//   외부에 거의 의존하지 않고 독자적으로 동작하는 컴포넌트의 경우 다른 환경에서도 유용하게 쓸 수 있다.
// - 큰 시스템을 제작하는 난이도를 낮춰준다.
//   전체 시스템이 완성되지 않은 상태에서도 개별 컴포넌트의 동작을 검증할 수 있다.

// [접근 제한자(access modifier) 사용 원칙]
// - 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다.
//   소프트웨어가 올바로 동작하는 한 항상 가장 낮은 접근 수준을 부여해야 한다.
// - 클래스의 공개 API를 세심히 설계한 후, 그 외의 모든 멤버는 private으로 만들라.
// - 오직 같은 패키지의 다른 클래스가 접근해야 하는 멤버만 package-private 으로 풀어주자.
// - 권한을 풀어주는 일을 자주하게 된다면 컴포넌트를 분해해야 하는 신호일 수 있다.
//
// 1) top-level 클래스와 top-level 인터페이스
//   public:
//      - 그 패키지의 공개 API이다.
//     - 하위 호환을 위해 영원히 관리해줘야만 한다.
//   package-private:
//     - 패키지 안에서만 이용하는 패키지의 내부 구현에 해당한다.
//     - 외부에서 쓸 이유가 없다면 이 수준으로 제한하라.
//     - 즉 public 일 필요가 없는 클래스는 이 수준으로 제한하라.
//   한 클래스에서만 사용되는 package-private top-level 클래스/인터페이스:
//     - private static 중첩 클래스/인터페이스로 바꾸라.
// 2) 클래스 멤버
//   private:
//     - 멤버를 선언한 톱레벨 클래스에서만 접근할 수 있다.
//   package-private:
//     - 멤버가 소속된 패키지 안의 모든 클래스에서 접근할 수 있다.
//     - 접근 제한자를 생략하면 이 수준이 된다.
//     - 인터페이스의 멤버는 기본적으로 public 이다.
//   protected:
//     - package-private 접근 범위를 포함한다.
//     - 이 멤버를 선언한 클래스의 하위 클래스에서도 접근할 수 있다.
//     - 단 필드의 경우 하위 클래스에서 접근 가능한 범위는 this 로 접근할 수 있는 것에만 한정된다.
//     - 이 또한 공개 API이기 때문에 영원히 지원돼야 한다. 따라서 API 문서에 명시해야 한다.
//     - 이 접근 범위는 적을수록 좋다.
//   public:
//     - 모든 곳에서 접근 할 수 있다.

// [주의]
// - 상위 클래스의 메서드를 재정의할 때는 그 접근 수준을 좁게 설정할 수 없다.
// - 리스코프 치환 원칙(Liskov Substitution Principle)을 지켜야 하기 때문이다.
//   즉 상위 클래스의 인스턴스는 하위 클래스의 인스턴스로 대체할 수 있어야 한다.
// - 테스트하려면 목적으로 클래스, 인터페이스, 멤버의 접근 범위를 넓힐 수 있다.
//   단, private 멤버를 package-private까지 풀어주는 것은 괜찮지만, 그 이상은 안된다.
//   왜? 테스트만들 위해 공개 API로 만들어서는 안된다.
// - public 클래스의 인스턴스 필드는 되도록 public 이면 안된다.
//   public 가변 필드를 갖는 클래스는 일반적으로 스레드 안전하지 않다.
//   단 상수일 경우 public static final 필드로 공개해도 된다.
//   관례상 상수의 이름은 대문자 알파벳을 쓰며, 각 단어 사이에 밑줄(_)을 넣는다.
//   상수는 반드시 기본 타입이나 불변 객체를 참조해야 한다.
//   가변 객체를 참조할 경우 다른 객체를 참조하지는 못하지만 참조된 객체가 수정될 수 있는 문제가 발생한다.
// - public static final 배열 필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공하지 말라.
//   클라이언트에서 그 배열의 내용을 수정할 수 있다.

package effectivejava.ch04.item15.exam01;

// [주제]
// - public static final 배열 필드의 문제점 해결

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Movies {
  // public static final 배열 필드 (좋지 않은 예)
  public static final String[] MOVIE_CATEGORIES = {
    "Action", "Comedy", "Documentary", "Drama", "Horror", "Musical", "Romance", "Sci-Fi", "Thriller"
  };

  // 해결책 1: private 배열 필드 + public 불변 리스트
  public static final String[] MOVIE_CATEGORIES2 = {
    "Action", "Comedy", "Documentary", "Drama", "Horror", "Musical", "Romance", "Sci-Fi", "Thriller"
  };
  public static final List<String> UNMODIFIABLE_MOVIE_CATEGORIES =
      Collections.unmodifiableList(Arrays.asList(MOVIE_CATEGORIES2));

  // 해결책 2: private 배열 필드 + 복사본을 반환하는 public 접근자 메서드
  public static final String[] categories() {
    return MOVIE_CATEGORIES2.clone();
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // public static final 배열 필드 (좋지 않은 예)
    System.out.println("before = " + Arrays.toString(Movies.MOVIE_CATEGORIES));
    Movies.MOVIE_CATEGORIES[0] = "Animation"; // 배열의 내용을 바꿀 수 있다.
    System.out.println("after = " + Arrays.toString(Movies.MOVIE_CATEGORIES));
    System.out.println("--------------------------");

    // 해결책 1: private 배열 필드 + public 불변 리스트
    System.out.println("before = " + Movies.UNMODIFIABLE_MOVIE_CATEGORIES);
    try {
      Movies.UNMODIFIABLE_MOVIE_CATEGORIES.set(0, "Animation"); // UnsupportedOperationException
    } catch (UnsupportedOperationException e) {
      System.out.println("오류 발생: List 항목을 변경할 수 없다!");
    }
    System.out.println("after = " + Movies.UNMODIFIABLE_MOVIE_CATEGORIES);
    System.out.println("--------------------------");

    // 해결책 2: private 배열 필드 + 복사본을 반환하는 public 접근자 메서드
    String[] categories2 = Movies.categories();
    System.out.println("before = " + Arrays.toString(categories2));
    categories2[0] = "Animation"; // 배열의 복사본을 바꾸는 것은 가능하다.

    // 하지만 원본 배열에는 아무런 영향을 끼치지 못한다. 다시 원본의 복사본을 꺼내서 출력해보자!
    System.out.println("after = " + Arrays.toString(Movies.categories()));

    // [정리]
    // - 클라이언트가 무엇을 원하느냐를 판단해 둘 중 하나를 선택하면 된다.
    // - 어느 반환이 더 쓰기 편할지, 성능은 어느 쪽이 나은지를 고민해 결정하라.

    // [참고]
    // - Java 9에서는 모듈 시스템이라느느 개념이 도입되면서 두 가지 암묵적 접근 수준이 추가되었다.
    // - 모듈은 패키지의 묶음이다.
    // - 공개할 패키지는 "module-info.java" 파일에 명시적으로 선언한다.
    //      module your.module.name {
    //        exports effectivejava.ch04.item15.exam01;
    //      }
    //   이렇게 하면, 해당 모듈을 사용하는 다른 모듈에서 exam01 패키지의 public 클래스를 사용할 수 있다.
    // - 모듈 시스템을 활용하면 클래스를 외부에 공개하지 않으면서도
    //   같은 모듈을 이루는 패키지 끼리는 서로 접근할 수 있게 할 수 있다.
    // - 모듈 안에서만 접근할 수 있는, 숨겨진 패키지 안에 있는 public 클래스의 멤버가
    //   public 또는 protected 라면, 그 효과가 모듈 내부로 한정된다는 점이다.
    //   즉 public과 protected에 모듈 한정이라는 제약이 추가된 접근 레벨이 생긴 셈이다.

    // [주의]
    // - 모듈의 JAR 파일을 자신의 모듈 경로가 아닌 CLASSPATH에 두면
    //   그 모듈 안에 모든 패키지는 마치 모듈이 없는 것처럼 행동한다.
    // - 즉 모듈이 해당 패키지를 공개했는지 여부와 상관없이
    //   public 클래스의 모든 public/protected 멤버를 모듈 밖에서 접근할 수 있다.

    // [모듈을 만들 때 해야 할 것]
    // 1) 패키지들을 모듈 단위로 묶고, 모듈 선언에 패키지들의 모든 의존성을 명시한다.
    // 2) 소스 트리를 재배치하고, 모듈 안으로부터 일반 패키지로의 모든 접근에 특별한 조치를 취해야 한다.
    // 3) JDK는 모듈이 적용되어 있지만, 아직 실무에서는 적용하지 않고 있다.
    //    따라서 꼭 필요한 경우가 아니라면 모듈 시스템을 사용하지 말라.
  }
}
