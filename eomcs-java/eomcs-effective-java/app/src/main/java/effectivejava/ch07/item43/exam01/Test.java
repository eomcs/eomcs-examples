// # 아이템 43. 람다보다는 메서드 참조를 사용하라
// [메서드 레퍼런스]
// - 함수 객체를 람다보다도 더 간결하게 만들 수 있다.

package effectivejava.ch07.item43.exam01;

// [주제] 람다 표현식 vs 메서드 레퍼런스

import java.util.HashMap;
import java.util.Map;

public class Test {
  public static void main(String[] args) {
    String[] names = {
      "홍길동", "김삿갓", "임꺽정", "장길산", "홍길동", "이몽룡",
      "성춘향", "이순신", "홍길동", "강감찬", "이순신", "임꺽정"
    };

    // 이름의 빈도수를 맵에 저장하기
    Map<String, Integer> nameCount = new HashMap<>();
    for (String name : names) {
      // 방법1: 람다 표현식
      //      nameCount.merge(
      //          name, // key
      //          1, // value
      //          (oldValue, value) -> oldValue + value);
      //

      // 방법2: 메서드 레퍼런스
      nameCount.merge(name, 1, Integer::sum);
    }
    System.out.println(nameCount);

    // [Map.merge(key, value, BiFunction)]
    // - 맵에 key가 없으면 value를 넣는다.
    // - 맵에 key가 있으면,
    //   맵에 들어있는 기존 값(oldValue)과 value를 BiFunction에 정의된 함수를 호출하여 그 리턴 값을 넣는다.

    // [Integer::sum]
    // - 두 int 값을 더하여 그 합을 리턴하는 static 메서드 레퍼런스이다
    // - 람다 표현식 (x, y) -> x + y 을 대체할 수 있다.

    // [정리]
    // - 파라미터 개수가 많을수록 람다 표현식보다 메서드 레퍼런스가 더 낫다.
    // - 람다의 파라미터 이름 자체가 프로그래머에게 좋은 가이드가 되기도 한다.
    //   이런 람다는 길이는 더 길지만 메서드 레퍼런스보다 읽기 쉽고 유지보수도 쉬울 수 있다.
    // - 람다로 할 수 없는 일이라면 메서드 레퍼런스로도 할 수 없다.
    // - 람다로 구현했을 때 너무 길거나 복잡하다면,
    //   람다로 작성할 코드를 새로운 메서드에 담은 다음, 람다 대신 그 메서드 레퍼런스를 사용하자.
    //   메서드 레퍼런스에는 기능을 잘 드러내는 이름을 지어줄 수 있고 친절한 설명을 문서로 남길 수 있다.
  }
}
