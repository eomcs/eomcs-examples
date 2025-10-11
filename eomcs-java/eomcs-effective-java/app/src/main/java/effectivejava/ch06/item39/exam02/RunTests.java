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
package effectivejava.ch06.item39.exam02;

// [주제] 파라미터를 하나 받는 애너테이션 활용법

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

// 마커(marker) 역할을 할 애너테이션 정의
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTest {
  Class<? extends Throwable> value(); // 애너테이션 파라미터
}

// 마커 애너테이션을 사용한 예
class Sample {
  // 애너테이션에 파라미터를 하나 전달한다.
  // 이 메서드는 ArithmeticException을 던져야 성공한다.
  @ExceptionTest(ArithmeticException.class)
  public static void m1() {
    int i = 0;
    i = i / i;
  }

  @ExceptionTest(ArithmeticException.class)
  public static void m2() {
    int[] a = new int[0];
    int i = a[1];
  }

  @ExceptionTest(ArithmeticException.class)
  public static void m3() {}
}

// 마커 애너테이션을 처리하는 프로그램
public class RunTests {
  public static void main(String[] args) {
    int tests = 0;
    int passed = 0;

    Class<?> testClass = Sample.class;

    for (var m : Sample.class.getDeclaredMethods()) {
      if (m.isAnnotationPresent(ExceptionTest.class)) {
        tests++;
        try {
          m.invoke(null);
          System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);

        } catch (InvocationTargetException ex) {
          Throwable cause = ex.getCause();
          // 애너테이션의 파라미터 값을 추출하여 테스트 메서드가 올바른 예외를 던지는지 확인하는 데 사용한다.
          Class<? extends Throwable> excType = m.getAnnotation(ExceptionTest.class).value();
          if (excType.isInstance(cause)) { // 기대한 예외가 발생한 경우
            passed++; // 성공한 테스트 수 증가
          } else { // 기대한 예외가 아닌 경우
            System.out.printf("테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s%n", m, excType.getName(), cause);
          }
        } catch (Throwable ex) {
          System.out.println("잘못 사용한 @Test: " + m);
        }
      }
    }
    System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
  }
}
