// # 아이템 69. 예외는 진짜 예외 상황에만 사용하라
// - 예외는 예외 상황에서 쓸 의도록 설계되었다.
//   정상적인 제어 흐름에서 사용해서는 안되며, 이를 프로그래머에게 강요하는 API를 만들어서는 안된다.
//

package effectivejava.ch10.item69.exam01;

// [주제] 예외를 잘못 사용한 예

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) throws Exception {
    List<String> words = new ArrayList<>();
    File f = new File("words.txt");
    try (Scanner in = new Scanner(f)) {
      while (in.hasNextLine()) {
        words.add(in.nextLine());
      }
    }

    String[] arr = words.toArray(String[]::new);

    // 잘못된 코드: 예외를 써서 루프를 종료한다.
    String lastWord = "";
    try {
      int i = 0;
      while (true) {
        lastWord = arr[i++];
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
    System.out.println(lastWord);
    // [예외를 써서 루프를 종료한 이유]
    // - 잘못된 추론을 근거로 성능을 높여보려 한 것이다.
    //   즉 조건문을 삽입하면 매 반복마다 조건을 검사하는 오버헤드가 발생할 것이라고 생각했기 때문이다.
    // [실제 결과]
    // - 자바에서 예외(exception)는 정상적인 제어 흐름이 아니라 비정상 흐름이다.
    // - 예외를 던지고 잡는 비용은 매우 크며(스택 추적 생성 등) 단순한 인덱스 비교보다 훨씬 비싸다.
    // - 특히 코드를 try-catch 블록 안에 넣으면 JVM이 적용할 수 있는 최적화가 제한된다.
    // - 코드를 헷갈리게 하고 성능을 떨어뜨릴 뿐만 아니라,
    //   반복문 안에 버그가 숨어 있다면, 흐름 제어에 쓰인 예외가 이 버그를 숨겨 디버깅을 훨씬 어렵게 한다.
    System.out.println("--------------------------------");

    // [해결책]
    // - 표준적인 관용구대로 작성하라.
    //   JVM이 알아서 최적화하여 인덱스 범위 검사를 제거한다.
    //   즉 JIT(Just-In-Time) 컴파일러는 정상 루프의 분기 예측을 매우 잘 최적화하기 때문에
    //   i < size 비교는 거의 0에 가까운 비용이 된다.
    String lastWord2 = "";
    for (String word : arr) {
      lastWord2 = word;
    }
    System.out.println(lastWord2);

    // [정리]
    // - 예외는 오직 예외 상황에서만 써야 한다. 절대로 일상적인 제어 흐름용으로 쓰여선 안된다.
    //   표준적이고 쉽게 이해되는 관용구를 사용하고, 성능 개선을 목적으로 과하게 머리를 쓴 기법은 자제하라.
    // - 잘 설계된 API라면 클라이언트가 정상적인 제어 흐름에서 예외를 사용할 일이 없게 해야 한다.
    //   특정 상태에서만 호출할 수 있는 '상태 의존적' 메서드를 제공하는 클래스는
    //   '상태 검사' 메서드도 함께 제공해야 한다.
    //   예) Iterator의 next()와 hasNext()

    // ['상태 검사 메서드', '옵셔널', '특정 값' 중에서 하나를 선택하는 지침]
    // 1) 외부 동기화 없이 여러 스레드가 동시에 접근할 수 있거나 외부 요인으로 상태가 변할 수 있다면,
    //    Optional<T>이나 '특정 값'을 사용하라.
    // 2) 성능이 중요한 상황에서 상태 검사 메서드가 상태 의존적 메서드의 작업 일부를 중복 수행한다면,
    //    Optional<T>이나 '특정 값'을 선택한다.
    // 3) 기타 상황에선 '상태 검사 메서드' 방식이 조금 더 낫다고 할 수 있다.
    //    가독성이 살짝 더 좋고, 잘못 사용했을 때 발견하기가 쉽다.
    //    상태 검사 메서드 호출을 깜박 잊었을 때 상태 의존적 메서드가 예외를 던져 버그를 확실히 드러낼 것이다.

    // ['특정 값'으로 상태를 검사하는 예]
    // - InputStream의 read() 메서드:
    //   읽을 바이트가 더 이상 없으면 -1을 반환한다.

    // ['옵셔널'로 상태를 검사하는 예]
    // - Optional의 isPresent():
    //   값이 있으면 true, 없으면 false를 반환한다.
  }
}
