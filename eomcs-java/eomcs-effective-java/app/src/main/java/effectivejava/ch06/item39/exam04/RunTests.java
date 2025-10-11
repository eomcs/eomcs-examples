// # 아이템 39. 명명 패턴보다 애너테이션을 사용하라
// [명명 패턴]
// - 메서드나 필드의 이름에 패턴을 부여하여 특정한 의미를 부여하는 것.
//   예) JUnit 3에서 테스트 메서드의 이름은 test로 시작한다.
// - 단점:
//   - 명명 패턴을 따르지 않고 작성한 이름에 대해 오류로 인식하지 않는다.
//   - 명명 패턴을 상황에 맞게 적용해야 하는데, 잘못 적용하는 경우가 있다.
//   - 프로그램 요소를 파라미터로 전달할 방법이 없다.
//     이름에 포함시키는 방법은 보기도 나쁘고 깨지기도 쉽다.
//     또한 컴파일러가 검사해주지 않는다.
// [애너테이션]
// - 명명 패턴의 이런 모든 단점을 해결해 주는 방법이다.
//
package effectivejava.ch06.item39.exam04;

// [주제] @Repeatable 메타 애너테이션 사용법
// - 하나의 프로그램 요소(클래스, 메서드, 필드 등)에 동일한 애너테이션을 여러 번 달 수 있다.

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

// 반복 가능한 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class) // 컨테이너 애너테이션을 지정한다.
@interface ExceptionTest {
  Class<? extends Throwable> value(); // 단일 애너테이션 파라미터
}

// 컨테이너 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTestContainer {
  ExceptionTest[] value(); // 내부 애너테이션의 배열을 반환할 메서드 정의
}

// 마커 애너테이션을 사용한 예
class Sample {
  // 배열 파라미터를 전달하는 대신에 애너테이션을 여러 번 달 수 있다.
  @ExceptionTest(IndexOutOfBoundsException.class)
  @ExceptionTest(NullPointerException.class)
  public static void doublyBad() {
    // 다음 문장은 ArithmeticException 발생
    // - 테스트가 성공한 후 다음 문장의 주석을 풀고 다시 테스트해 보자.
    //    int a = 100 / 0;

    List<String> list = new ArrayList<>();
    // 다음 메서드는 IndexOutOfBoundsException이나 NullPointerException을 던진다.
    list.addAll(5, null);
  }
}

// 마커 애너테이션을 처리하는 프로그램
public class RunTests {
  public static void main(String[] args) {
    int tests = 0;
    int passed = 0;

    Class<?> testClass = Sample.class;

    for (var m : Sample.class.getDeclaredMethods()) {
      System.out.println(m.isAnnotationPresent(ExceptionTest.class));
      System.out.println(m.isAnnotationPresent(ExceptionTestContainer.class));
      // 반복 가능한 애너테이션이 달린 메서드에서 애너테이션을 다룰 때 주의할 점!
      // - 반복 가능한 애너테이션이 한 개만 달려있으면,
      //     m.isAnnotationPresent(ExceptionTest.class)는 true를 반환한다.
      //     m.getAnnotation(ExceptionTestContainer.class)는 false를 반환한다.
      // - 반복 가능한 애너테이션이 여러 개 달려있으면,
      //     m.isAnnotationPresent(ExceptionTest.class)는 false를 반환한다.
      //     m.getAnnotation(ExceptionTestContainer.class)는 true를 반환한다.
      // - 따라서 둘 다 검사해야 한다.
      if (m.isAnnotationPresent(ExceptionTest.class)
          || m.isAnnotationPresent(ExceptionTestContainer.class)) {
        tests++;
        try {
          m.invoke(null);
          System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);

        } catch (InvocationTargetException ex) {
          Throwable cause = ex.getCause();
          int oldPassed = passed;
          ExceptionTest[] excTypes = m.getAnnotationsByType(ExceptionTest.class);
          for (ExceptionTest excTest : excTypes) {
            if (excTest.value().isInstance(cause)) { // 기대한 예외가 발생한 경우
              passed++;
              break; // 성공한 테스트 수 증가
            }
          }
          if (passed == oldPassed) { // 성공한 테스트 수가 증가하지 않았다면
            System.out.printf("테스트 %s 실패: %s%n", m, cause);
          }
        } catch (Throwable ex) {
          System.out.println("잘못 사용한 @Test: " + m);
        }
      }
    }
    System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

    // [정리]
    // - 반복 가능 애너테이션을 사용하면 코드 가독성이 높아진다.
    // - 다만 이를 처리하는 부분에서 코드의 양이 늘어나며,
    //   처리 코드가 복잡해져 오류가 날 가능성이 커진다.
    // - 애너테이션으로 할 수 있는 일을 명명 패턴으로 처리할 이유는 없다.

    // [결론]
    // - 도구 제작자를 제외하고 일반 프로그래머가 애노테이션 타입을 직접 정의할 일은 거의 없다.
    // - 자바 프로그래머라면 예외 없이 자바가 제공하는 애너테이션 타입들을 사용해야 한다.
    // - IDE나 정적 분석 도구가 제공하는 애너테이션을 사용하면,
    //   도구가 제공하는 진단 정보의 품질을 높여줄 것이다.
    //   단 표준이 아니기 때문에 도구를 바꾸거나 표준이 만들어지면 수정 작업을 수행하는 번거로움이 있다.

  }
}
