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
package effectivejava.ch06.item39.exam01;

// [주제] 애너테이션의 활용법

// 마커(marker) 역할을 할 애너테이션 정의

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

/** 테스트 메서드임을 선언하는 애너테이션이다. 파라미터 없는 정적 메서드 전용이다. (단, 컴파일러에게 강제하지는 못한다.) */
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 애너테이션 정보를 유지
@Target(ElementType.METHOD) // 메서드에만 적용
@interface Test {}

// [메타 애너테이션(meta-annotation)]
// - 애너테이션을 정의할 때 사용하는 애너테이션
// - 예) @Retention, @Target 등

// 마커 애너테이션을 사용한 예
class Sample {
  @Test
  public static void m1() {} // 테스트 메서드

  public static void m2() {} // 테스트 메서드 아님

  @Test
  public static void m3() { // 실패해야 한다.
    throw new RuntimeException("실패");
  }

  public static void m4() {} // 테스트 메서드 아님

  @Test
  public void m5() {} // 잘못 사용한 예: 정적 메서드가 아니다. 그러나 컴파일러가 잡아주지 않는다.

  public static void m6() {}

  @Test
  public static void m7() { // 실패해야 한다.
    throw new RuntimeException("실패");
  }

  public static void m8(int x) {}
}

// 마커 애너테이션을 처리하는 프로그램
public class RunTests {
  public static void main(String[] args) {
    int tests = 0;
    int passed = 0;

    Class<?> testClass = Sample.class;

    for (var m : Sample.class.getDeclaredMethods()) {
      if (m.isAnnotationPresent(Test.class)) { // m에 @Test가 붙어 있는가?
        tests++; // 테스트 수 증가
        try {
          m.invoke(null); // 정적 메서드이므로 객체 없이 호출
          passed++; // 성공한 테스트 수 증가
        } catch (InvocationTargetException ex) { // 메서드가 예외를 던진 경우
          Throwable cause = ex.getCause();
          System.out.println(m + " 실패: " + cause);
        } catch (Throwable ex) { // 그 외 예외가 발생한 경우
          System.out.println("잘못 사용한 @Test: " + m);
        }
      }
    }
    System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

    // [정리]
    // - @Test 애너테이션은 Sample 클래스의 의미에 직접적인 영향을 주지 않는다.
    // - 이 애너테이션에 관심 있는 프로그램에게 추가 정보를 제공할 뿐이다.
    //   즉 대상 코드의 의미는 그대로 둔 채 그 애노테이션에 관심 있는 도구에서 특별한 처리를 할 기회를 준다.
  }
}
