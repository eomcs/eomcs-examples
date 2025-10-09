// # 아이템 27. 비검사 경고를 제거하라
// - 할 수 있는 한 모든 비검사 경고를 제거하라
//   모두 제거한다면 그 코드는 타입 안정성이 보장된다.
// - 경고를 제거할 수 없지만 타입 안전하다고 확신할 수 있다면,
//   @SuppressWarnings("unchecked") 애너테이션을 달아 경고를 숨기자.

// [@SuppressWarnings("unchecked") 애너테이션 사용법]
// - 항상 가능한 좁은 범위에 적용하자.
//   예) 변수 선언, 아주 짧은 메서드 혹은 생성자
// - 절대로 클래스 전체에 적용하지 말라.
// - 한 줄이 넘는 메서드나 생성자에 달린 @SuppressWarnings("unchecked") 애너테이션은
//   지역변수 선언쪽으로 옮기자.
// - @SuppressWarnings("unchecked") 애너테이션을 달 때는
//   왜 이 경고를 무시해도 되는지 항상 주석으로 설명하자.

package effectivejava.ch05.item27.exam01;

// [주제] 비검사 경고를 확인하기
// gradle.build 에 다음 설정을 추가하라.
//    tasks.named('compileJava') {
//        options.compilerArgs << '-Xlint:unchecked'
//    }

import java.util.HashSet;
import java.util.Set;

public class Test {
  public static void main(String[] args) throws Exception {
    Set<String> set = new HashSet(); // 경고 발생

    // 해결방법
    Set<String> set2 = new HashSet<String>();
    Set<String> set3 = new HashSet<>(); // 컴파일러가 실제 타입 매개변수를 추론해준다.
  }
}
