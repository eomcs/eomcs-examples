// # 아이템 10. equals() 는 일반 규약을 지켜 재정의하라
// [재정의하지 말아야 할 상황]
// - 각 인스턴스가 본질적으로 고유하다.
//   - 값을 표현하는 게 아니라 동작하는 객체를 표현하는 클래스인 경우
// - 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
// - 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는다.
// - 클래스가 private이거나, package-private이고 equals() 메서드를 호출할 일이 없다.
//
// [equals() 메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.]
// 1. 반사성(reflexive):
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(x)는 true여야 한다.
// 2. 대칭성(symmetric):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)가 true이면, y.equals(x)도 true여야 한다.
// 3. 추이성(transitive):
//    - null 이 아닌 모든 참조 값 x, y, z에 대해,
//      x.equals(y)가 true이고, y.equals(z)가 true이면, x.equals(z)도 true여야 한다.
// 4. 일관성(consistency):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)를 반복해서 호출해도 항상 같은 결과를 반환해야 한다.
// 5. null-아님:
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(null)는 false여야 한다.

package effectivejava.ch03.item10.exam08;

import java.net.URI;
import java.net.URL;

// equals()가 일관성을 위배한 대표적인 예:

public class Test {
  public static void main(String[] args) throws Exception {

    // 일관성: 두 객체가 같다면 앞으로 영원히 같아야 한다.
    // - 다음은 equals()가 일관성을 위배한 대표적인 예이다.
    URL url1 = new URI("https://www.facebook.com").toURL();
    URL url2 = new URI("https://www.facebook.com").toURL();

    for (int i = 0; i < 100; i++) {
      // URL 클래스의 equals()는 호스트 이름을 확인하기 위해 DNS 조회를 수행한다.
      // - DNS 서버가 여러 개 있고, 각 서버가 동일한 도메인에 대해 서로 다른 IP 주소를 반환할 수 있다.
      // - 특히 CDN이나 로드밸런싱이 적용된 서비스의 경우, DNS 조회 시마다 다른 IP가 반환될 수 있다.
      // - 따라서 URL의 equals()는 네트워크 상태나 DNS 응답에 따라 결과가 달라질 수 있다.
      // - 즉, url1과 url2가 같을 수도 있고, 다를 수도 있다.
      System.out.println(url1.equals(url2)); // true or false ?
      // - 100번을 비교해 봐도 true만 나온다.
      // - 현재 컴퓨터에 설정된 DNS 서버가 일관되게 같은 IP를 반환하기 때문이다.
      // - 하지만 이론적으로는 false가 나올 가능성이 있음을 명심하자.
    }

    // [결론] equals()의 일관성 원칙
    // - equals() 메서드를 재정의할 때는 반드시 일관성 규칙을 지켜야 한다.
    // - 클래스가 가변이든 불변이든 equals()의 판단에 신뢰할 수 없는 자원이 끼어들게 해서는 안 된다.
    // - 일관성을 위배한 대표적인 예가 URL 클래스의 equals()이다.
    //   URL 클래스의 equals()는 호스트의 IP를 확인하기 위해 외부 서비스(DNS 조회)를 사용하는데,
    //   이 부분에서 리턴 값이 달라 질 수 있다.
    //   "외부 자원이 끼어들어 발생하는 일관성 위배"의 전형적인 예이다.
    // - 이런 상황을 방지하기 위해서는,
    //   "항시 메모리에 존재하는 객체만을 사용한 결정적(deterministic) 계산만 수행해야 한다.
  }
}
