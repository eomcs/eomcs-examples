// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam04;

// [주제] 다중정의로 발생하는 혼란과 대비책 - 제레릭과 오토박싱으로 인한 혼란

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test {

  public static void main(String[] args) {
    Set<Integer> set = new TreeSet<>();
    List<Integer> list = new ArrayList<>();

    for (int i = -3; i < 3; i++) {
      set.add(i); // autoboxing
      list.add(i); // autoboxing
    }
    System.out.println(set); // [-3, -2, -1, 0, 1, 2]
    System.out.println(list); // [-3, -2, -1, 0, 1, 2]

    for (int i = 0; i < 3; i++) {
      set.remove(i); // remove(Object o) 호출. autoboxing
      list.remove(i); // remove(int index) 호출.
      //      list.remove(Integer.valueOf(i)); // remove(Object o) 호출
    }
    System.out.println(set); // [-3, -2, -1]
    System.out.println(list); // [-2, 0, 2]

    // [이유]
    // - set.remove(i)는 remove(Object o)를 호출하여 값 i를 제거한다.
    //   Set 클래스에는 remove(Object) 외에 다중정의된 메서드가 없다.
    // - list.remove(i)는 remove(int index)를 호출하여 인덱스 i에 해당하는 값을 제거한다.
    //   List 클래스에는 remove(Object) 외에 remove(int)도 다중정의되어 있다.
    //   컴파일타임에서 정적으로 결정된 매개변수 타입이 int이므로 remove(int index)가 호출된다.
    //   만약 remove(Object)가 호출되길 원한다면,
    //   list.remove(Integer.valueOf(i))처럼 명시적으로 형변환을 해주어야 한다.

    // [정리]
    // - 매개변수 수가 같은 다중정의 메서드가 많더라도,
    //   매개변수 중 하나 이상이 "근본적으로 다르다"면 헷갈릴 일이 없다.
    //   근본적으로 다르다는 건, "두 타입의 값을 어느 쪽으로든 형변환할 수 없다"는 뜻이다.
    // - 오토박싱(autoboxing) 이전에는 Object와 int가 근본적으로 달라서 헷갈릴 문제가 없었다.
    //   그러나 제네릭과 오토박싱이 도입되면서 상황이 달라졌다.
    //   오토박싱은 int를 Integer로, Integer를 int로 자동 변환해준다.
    //   따라서 remove(Object)와 remove(int) 둘 다 호출될 가능성이 생겼다.
    // - 자바 언어에 제네릭과 오토박싱을 더한 결과 List 인터페이스가 취약해졌다.
    //   메서드 다중정의시 더더욱 주의를 기울여야 한다.
  }
}
