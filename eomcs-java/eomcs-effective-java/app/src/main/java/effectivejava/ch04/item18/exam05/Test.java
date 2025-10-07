// # 아이템 18. 상속보다는 컴포지션을 사용하라
// - 상속은 코드를 재사용하는 강력한 수단이지만, 남용하면 오히려 해가 된다.
// - 상위 클래스와 하위 클래스를 모두 같은 프로그래머가 통제하는 패키지 안에서라면 상속도 안전한 방법이다.
// - 확장할 목적으로 설계되었고 문서화도 잘 된 클래스도 안전하다.
// - 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 사용하라.
//   하위 클래스의 패키지가 상위 클래스와 다르고, 상위 클래스가 확장을 고려해 설계되지 않았다면 여전히 문제가 될 수 있다.
// - 상속의 취약점을 피하는 방법은 컴포지션과 전달을 사용하는 것이다.
//
// [상속이 위험한 경우] 다른 패키지의 구체(concrete) 클래스를 상속할 때
// 1) 내부 구현을 알 수 없음
//    부모 클래스의 private 필드, protected 메서드, 호출 순서를 파악할 수 없다.
// 2) 업데이트 시 깨짐
//    부모 클래스 제작자가 내부 구현을 변경하면 하위 클래스가 예기치 않게 오작동할 수 있다.
// 3) 테스트 불가능
//    부모의 동작을 모르면 자식이 어떤 상황에서 깨질지 테스트로 포착하기 어렵다.
// 4) 의도되지 않은 호출 순서
//    부모가 자기 메서드 안에서 다른 메서드를 호출할 수 있는데,
//    그게 하위 클래스가 오버라이드한 메서드일 수 있다.
//    "self-use problem"이라 부른다.
// - 상속은 "계약"이 아니라 "구현"을 재사용하는 방식이다.
//   즉 상속은 부모 클래스의 내부 구현 세부사항까지 함께 끌어안는 것이기에 문제가 된다.
//
//

package effectivejava.ch04.item18.exam05;

// [주제] 상속을 잘못 사용해서 발생한 문제를 확인하기

import java.util.Properties;

public class Test {

  public static void main(String[] args) throws Exception {
    Properties props = new Properties();

    // Properties는 key와 value 모두 String만 허용한다.
    props.setProperty("url", "jdbc:mysql://localhost/test");
    props.setProperty("user", "root");
    props.setProperty("password", "password");
    String value = props.getProperty("url");
    props.store(System.out, "Properties Example");
    System.out.println("-------------------------");

    // [문제 확인]
    // Properties는 Hashtable의 하위 클래스이기 때문에,
    // Hashtable의 메서드를 모두 사용할 수 있다.
    // 즉 String이 아닌 key나 value도 넣을 수 있다.
    props.put(1, 2); // 컴파일 오류 없음!
    System.out.println(props.size()); // 4 반환
    System.out.println(props.get(1)); // 2 반환
    System.out.println(props.getProperty("1")); // null 반환
    props.store(System.out, "Properties Example2");
    // 예외 발생
    // - 왜? 마지막에 추가한 (1,2) 는 String이 아니기 때문이다.

    // [정리1]
    // - Properties의 경우처럼 잘못된 상속은 치명적이다.
    // - 상위 클래스인 Hashtable의 메서드를 직접 호출할 경우
    //   Properties의 불변식(key와 value가 String이다)을 깨버릴 수 있다.
    // - 불변식이 한 번 깨지면 load()나 store() 같은 Properties API는 더 이상 사용할 수 없다.
    // - 이 문제가 밝혀졌을 때는 이미 수많은 사용자가 String 이외의 타입을
    //   key나 value로 사용하고 있었다. 문제를 바로잡기에는 너무 늦어 버린 것이다.
    // - 이렇게 상속이 무서운 것이다!

    // [상속을 사용하기로 했을 때 마지막으로 자문해 볼 것]
    // - 확장하려는 클래스의 API에 아무런 결함이 없는가?
    // - 결함이 있다면 그 결함을 하위 클래스 API까지 전파해도 괜찮은가?

    // [정리2]
    // - 이렇게 상속은 결함까지 그대로 승계된다는 것을 명심하라!
    // - 컴포지션을 사용하면 결함을 숨기는 새로운 API를 설계할 수 있다.
  }
}
