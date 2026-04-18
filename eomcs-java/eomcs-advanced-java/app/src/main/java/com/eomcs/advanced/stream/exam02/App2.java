package com.eomcs.advanced.stream.exam02;

import java.util.Optional;

// Optional 변환 - map과 flatMap:
//
// map(Function<T, U>)
//   - Optional 안의 값에 함수를 적용해 새로운 Optional<U>로 변환한다.
//   - 값이 없으면(empty) 함수를 실행하지 않고 빈 Optional을 그대로 반환한다.
//   - null을 다루는 if 체인을 제거하는 핵심 수단이다.
//
// flatMap(Function<T, Optional<U>>)
//   - map과 달리 함수가 Optional<U>를 반환할 때 사용한다.
//   - map을 쓰면 Optional<Optional<U>>가 되지만, flatMap은 자동으로 평탄화해 Optional<U>를 반환한다.
//   - 중첩 Optional이 생기는 것을 방지한다.
//
// filter(Predicate<T>)
//   - 값이 있고 조건을 만족하면 그 Optional을 그대로 반환한다.
//   - 값이 없거나 조건을 만족하지 않으면 빈 Optional을 반환한다.
//   - 조건부 처리를 null 체크 없이 표현할 수 있다.
//
// ifPresent(Consumer<T>)
//   - 값이 있을 때만 Consumer를 실행한다.
//   - 값이 없으면 아무 일도 일어나지 않는다.
//   - if (x != null) { ... } 패턴을 대체한다.
//
// ifPresentOrElse(Consumer<T>, Runnable)  (Java 9+)
//   - 값이 있으면 Consumer 실행, 없으면 Runnable 실행한다.
//

public class App2 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. map - Optional 안의 값 변환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] map - Optional 안의 값 변환");

    Optional<String> name = Optional.of("alice");

    // null 체크 방식: if로 null을 직접 확인해야 한다.
    String raw = "alice";
    String upperImperative = (raw != null) ? raw.toUpperCase() : null;
    System.out.println("  null 체크: " + upperImperative);

    // Optional.map 방식: null 체크 없이 변환 로직만 작성한다.
    Optional<String> upper = name.map(String::toUpperCase); // alice → ALICE
    System.out.println("  map 변환: " + upper.orElse("없음")); // ALICE

    // 값이 없을 때: map은 함수를 실행하지 않고 빈 Optional을 반환한다.
    Optional<String> empty = Optional.<String>empty().map(String::toUpperCase);
    System.out.println("  빈 Optional에 map: " + empty.orElse("없음")); // 없음

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. map 체이닝 - 연속 변환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] map 체이닝 - 여러 변환을 이어 붙이기");

    Optional<String> city = Optional.of("  Seoul  ");

    // null 체크 방식: 각 단계마다 null을 확인해야 한다.
    String rawCity = "  Seoul  ";
    String result = null;
    if (rawCity != null) {
      String trimmed = rawCity.trim();
      if (!trimmed.isEmpty()) {
        result = trimmed.toUpperCase();
      }
    }
    System.out.println("  null 체크: " + result);

    // Optional.map 체이닝: 각 변환을 독립적인 단계로 표현한다.
    Optional<String> processed = city
        .map(String::trim)        // "  Seoul  " → "Seoul"
        .filter(s -> !s.isEmpty()) // 비어 있지 않으면 통과
        .map(String::toUpperCase); // "Seoul" → "SEOUL"
    System.out.println("  map 체이닝: " + processed.orElse("없음")); // SEOUL

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. flatMap - 중첩 Optional 해소
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] flatMap - Optional을 반환하는 메서드와 연결");

    Optional<User> user = Optional.of(new User("Bob", "bob@example.com"));

    // map을 쓰면 Optional<Optional<String>>이 되어 get()이 두 번 필요하다.
    Optional<Optional<String>> doubleOptional = user.map(u -> u.getEmail()); // Optional<Optional<String>>
    System.out.println("  map 결과 타입: Optional<Optional<String>>");
    System.out.println("  map 결과:      " + doubleOptional.orElse(Optional.empty()).orElse("없음"));

    // flatMap은 Optional<Optional<String>>을 Optional<String>으로 평탄화한다.
    Optional<String> email = user.flatMap(u -> u.getEmail()); // Optional<String>
    System.out.println("  flatMap 결과:  " + email.orElse("없음")); // bob@example.com

    // 이메일이 없는 사용자
    Optional<User> noEmailUser = Optional.of(new User("Charlie", null));
    Optional<String> noEmail = noEmailUser.flatMap(u -> u.getEmail());
    System.out.println("  이메일 없음:   " + noEmail.orElse("이메일 없음")); // 이메일 없음

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. filter - 조건을 만족하지 않으면 빈 Optional
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] filter - 조건 불만족 시 빈 Optional");

    Optional<Integer> score = Optional.of(85);

    // null 체크 방식
    Integer rawScore = 85;
    String grade = null;
    if (rawScore != null && rawScore >= 60) {
      grade = "합격";
    }
    System.out.println("  null 체크: " + grade);

    // Optional.filter 방식
    Optional<String> streamGrade = score
        .filter(s -> s >= 60)   // 60점 이상이면 통과, 미만이면 빈 Optional
        .map(s -> "합격");       // 통과한 경우에만 변환
    System.out.println("  filter 결과: " + streamGrade.orElse("불합격"));

    // 조건 불만족 사례
    Optional<Integer> lowScore = Optional.of(40);
    Optional<String> fail = lowScore.filter(s -> s >= 60).map(s -> "합격");
    System.out.println("  40점 결과:   " + fail.orElse("불합격")); // 불합격

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. ifPresent / ifPresentOrElse - 조건부 실행
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] ifPresent / ifPresentOrElse");

    Optional<String> message = Optional.of("안녕하세요");
    Optional<String> noMessage = Optional.empty();

    // null 체크 방식
    String raw2 = "안녕하세요";
    if (raw2 != null) {
      System.out.println("  null 체크: " + raw2);
    }

    // ifPresent: 값이 있을 때만 실행. null 체크 없이 동일한 의도를 표현한다.
    message.ifPresent(m -> System.out.println("  ifPresent: " + m)); // 실행됨
    noMessage.ifPresent(m -> System.out.println("  ifPresent: " + m)); // 실행 안 됨

    // ifPresentOrElse: 있을 때와 없을 때 각각 다른 동작을 지정한다. (Java 9+)
    message.ifPresentOrElse(
        m -> System.out.println("  값 있음: " + m),
        ()  -> System.out.println("  값 없음")
    );
    noMessage.ifPresentOrElse(
        m -> System.out.println("  값 있음: " + m),
        ()  -> System.out.println("  값 없음")
    );

    System.out.println();
    System.out.println("→ map()은 변환 함수가 일반 값을 반환할 때, flatMap()은 Optional을 반환할 때 사용한다.");
    System.out.println("→ filter()로 조건 검사를 Optional 체인 안에서 처리해 null 체크 if를 없앨 수 있다.");
    System.out.println("→ ifPresent()는 값이 있을 때만 동작하므로 null 체크 if를 대체한다.");
  }

  // 중첩 Optional 예제를 위한 도메인 클래스
  static class User {
    private final String name;
    private final String email; // null일 수 있다.

    User(String name, String email) {
      this.name  = name;
      this.email = email;
    }

    String getName() {
      return name;
    }

    // 이메일이 없을 수 있으므로 Optional로 반환한다.
    Optional<String> getEmail() {
      return Optional.ofNullable(email);
    }
  }
}
