// # 아이템 30. 이왕이면 제네릭 메서드로 만들라

package effectivejava.ch05.item31.exam07;

// [주제] 한정적 와일드카드 타입을 적용하기: Item30 예제에 적용 후 문제점 해결

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class After {

  // 재귀적 타입 한정에 한정적 와일드카드 타입을 사용한 후
  static <E extends Comparable<? super E>> Optional<E> max(List<E> c) {
    E result = null;
    for (E e : c) {
      if (result == null || e.compareTo(result) > 0) {
        result = Objects.requireNonNull(e);
      }
    }
    return Optional.ofNullable(result); // result가 null이면 Optional.empty()를 반환한다.
  }

  public static void main(String[] args) throws Exception {
    // 일반적인 사용
    List<User> users = List.of(new User("임꺽정"), new User("홍길동"), new User("전우치"));
    Optional<User> maxUser = max(users);
    System.out.println(maxUser.get());

    List<Admin> admins = List.of(new Admin("유관순"), new Admin("윤봉길"), new Admin("안중근"));
    Optional<Admin> maxAdmin = max(admins); // OK!
    System.out.println(maxAdmin.get());
    // [문제 해결 분석]
    // - max()의 파라미터로 전달하는 List의 원소 타입은 Admin 이다.
    // - 따라서 E는 Admin으로 추론할 수 있다.
    //   E를 Admin으로 치환하여 max()의 제네릭 타입 선언을 살펴보면 다음과 같다.
    //     <Admin extends Comparable<User>> Optional<Admin> max(List<Admin> c)
    //   이것은 max()의 시그너처 조건과 일치한다.
    //      <E extends Comparable<? super E>> Optional<E> max(List<E> c)
    //   User는 Admin의 수퍼 클래스이므로,
    //      <Admin extends Comparable<User>> 은
    //      <Admin extends  Comparable<? super Admin>> 조건을 만족한다.

    // 상위 타입으로 리턴 값을 받을 수 없는 오류!
    List<Master> masters = List.of(new Master("세종대왕"), new Master("이순신"), new Master("강감찬"));
    Optional<Master> maxMaster = max(masters); // OK!
    System.out.println(maxMaster.get());
    //    Optional<Admin> maxMaster2 = max(masters); // 컴파일 오류!
    // - max()의 파라미터로 전달하는 List의 원소 타입은 Master 이다.
    // - 따라서 E는 Master로 추론할 수 있다.
    //   E를 Master로 치환하여 max()의 제네릭 타입 선언을 살펴보면 다음과 같다.
    //     <Master extends Comparable<User>> Optional<Master> max(List<Master> c)
  }
}
