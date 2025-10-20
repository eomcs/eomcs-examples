// # 아이템 55. 옵셔널 반환은 신중히 하라
// - 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬하는 경우,
//   옵셔널을 반환하는 것이 유용할 수 있다.
// - 옵셔널 반환에는 성능 저하가 뛰따르니,
//   성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다.
// - 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
//

package effectivejava.ch08.item55.exam04;

// [주제] Optional<T> 리턴 값 다루기

import java.util.Optional;

public class Test {

  public static void main(String[] args) {
    ProcessHandle ph = ProcessHandle.current();
    Optional<ProcessHandle> parentProcess = ph.parent();

    // 1) isPresent()
    System.out.printf(
        "부모 PID: %s\n",
        parentProcess.isPresent() ? String.valueOf(parentProcess.get().pid()) : "N/A");

    // 2) map()
    System.out.printf("부모 PID: %s\n", ph.parent().map(h -> String.valueOf(h.pid())).orElse("N/A"));
  }
}
