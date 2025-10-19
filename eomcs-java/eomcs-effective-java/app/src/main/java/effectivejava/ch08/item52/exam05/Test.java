// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam05;

// [주제] 다중정의로 발생하는 혼란과 대비책 II - 람다와 메서드 레퍼런스로 인한 혼란

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

  public static void main(String[] args) {
    // Thread 생성자 호출
    // - Thread(Runnable target): Runnable을 받아들이는 다중정의 메서드
    new Thread(System.out::println).start(); // OK!

    // ExecutorService의 submit 메서드 호출
    ExecutorService exec = Executors.newCachedThreadPool();
    //    exec.submit(System.out::println); // 컴파일 오류
    // [이유]
    // - submit(Runnable task): Runnable을 받아들이는 다중정의 메서드
    // - submit(Callable<T> task): Callable<T>을 받아들이는 다중정의 메서드
    // - Runnable과 Callable<T>은 모두 매개변수가 없는 추상 메서드를 가진 함수형 인터페이스이다.
    // - 따라서 람다 표현식으로 둘 다 구현할 수 있다.
    // - 이 경우 컴파일러는 어떤 다중정의 메서드를 호출해야 할지 알 수 없어서 컴파일 오류가 발생한다.

    // [정리]
    // - 다중정의된 메서드들이 함수형 인터페이스를 인수로 받을 때
    //   서로 다른 인터페이스라도 아규먼트 위치가 같으면 혼란이 발생할 수 있다.
    // - 메서드를 다중정의할 때,
    //   서로 다른 함수형 인터페이스라도 같은 위치의 아규먼트로 받아서는 안된다.
  }
}
