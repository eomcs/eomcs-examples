// # 아이템 30. 이왕이면 제네릭 메서드로 만들라

package effectivejava.ch05.item31.exam07;

// [주제] 한정적 와일드카드 타입을 적용하기: Item30 예제에 적용 전 문제점 확인

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Before {

  // 한정적 와일드카드 타입을 사용하기 전
  static <E extends Comparable<E>> Optional<E> max(List<E> c) {
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
    // - max()의 파라미터로 전달하는 List의 원소 타입은 User 이다.
    // - 따라서 E는 User로 추론할 수 있다.
    // - User는 Comparable<User>를 구현하고 있으므로 <E extends Comparable<E>> 조건을 만족한다.
    System.out.println(maxUser.get());

    List<Admin> admins = List.of(new Admin("유관순"), new Admin("안중근"), new Admin("윤봉길"));
    //    Optional<Admin> maxAdmin = max(admins); // 컴파일 오류!
    // [오류 원인 분석]
    // - max()의 파라미터로 전달하는 List의 원소 타입은 Admin 이다.
    // - 따라서 E는 Admin으로 추론할 수 있다.
    // - 그런데 Admin은 다음 조건을 만족시키지 못한다.
    //       <Admin extends Comparable<Admin>> Optional<Admin> max(List<Admin> c)
    //   Admin은 User의 하위 클래스이므로,
    //   User가 구현한 Comparable<User>을 그대로 구현한 것으로 간주된다.
    //   즉 <Admin extends Comparable<User>> 으로 간주하여,
    //   <Admin extends Comparable<Admin>> 조건을 만족시키지 못한다.

    //    System.out.println(maxAdmin.get());
  }
}
