package com.eomcs.advanced.stream.exam09;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// 검색과 매칭 - 실전 패턴:
//
// 실전에서 자주 쓰이는 패턴:
//   1. 존재 여부 확인:  anyMatch → boolean
//   2. 유효성 전체 검사: allMatch → boolean
//   3. 금지 조건 검사:  noneMatch → boolean
//   4. 첫 번째 결과 조회: filter + findFirst → Optional
//   5. 존재 여부 + 결과 조회: anyMatch와 findFirst의 역할 분리
//
// anyMatch vs filter + findFirst:
//   anyMatch: 존재 여부(boolean)만 필요할 때 사용한다.
//   filter + findFirst: 실제 요소(Optional)가 필요할 때 사용한다.
//   두 패턴을 혼용하면 스트림을 두 번 순회하게 된다. 필요한 것만 선택한다.
//

public class App3 {

  public static void main(String[] args) {

    List<User> users = Arrays.asList(
        new User(1, "alice",   "alice@example.com",  true,  28),
        new User(2, "bob",     "bob@example.com",    false, 35),
        new User(3, "charlie", "charlie@example.com", true, 22),
        new User(4, "dave",    "dave@example.com",   true,  41),
        new User(5, "eve",     "",                   false, 19) // 이메일 없음
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 존재 여부 확인 (anyMatch)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 존재 여부 확인 - anyMatch");

    // 활성 사용자가 한 명이라도 있는가?
    boolean hasActiveUser = users.stream()
        .anyMatch(User::isActive);
    System.out.println("  활성 사용자 존재:      " + hasActiveUser); // true

    // 30세 이상 사용자가 있는가?
    boolean hasAdult30Plus = users.stream()
        .anyMatch(u -> u.getAge() >= 30);
    System.out.println("  30세 이상 존재:        " + hasAdult30Plus); // true

    // 이메일이 없는 사용자가 있는가?
    boolean hasNoEmail = users.stream()
        .anyMatch(u -> u.getEmail().isBlank());
    System.out.println("  이메일 없는 사용자 존재: " + hasNoEmail); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 유효성 전체 검사 (allMatch)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 유효성 전체 검사 - allMatch");

    // 모든 사용자가 이름을 가지고 있는가?
    boolean allHaveNames = users.stream()
        .allMatch(u -> u.getName() != null && !u.getName().isBlank());
    System.out.println("  모두 이름 있음:        " + allHaveNames); // true

    // 모든 사용자가 이메일을 가지고 있는가?
    boolean allHaveEmails = users.stream()
        .allMatch(u -> !u.getEmail().isBlank());
    System.out.println("  모두 이메일 있음:      " + allHaveEmails); // false (eve 이메일 없음)

    // 모든 사용자가 성인(18세 이상)인가?
    boolean allAdults = users.stream()
        .allMatch(u -> u.getAge() >= 18);
    System.out.println("  모두 성인(18세 이상):  " + allAdults); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 금지 조건 검사 (noneMatch)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 금지 조건 검사 - noneMatch");

    // 미성년자(18세 미만)가 없는가? → 없으면 서비스 이용 가능
    boolean noMinor = users.stream()
        .noneMatch(u -> u.getAge() < 18);
    System.out.println("  미성년자 없음:         " + noMinor); // true

    // 이름이 중복된 사용자가 없는가?
    long distinctNames = users.stream().map(User::getName).distinct().count();
    boolean noDuplicateNames = distinctNames == users.size();
    System.out.println("  이름 중복 없음:        " + noDuplicateNames); // true

    // null 이름이 없는가?
    boolean noNullName = users.stream()
        .noneMatch(u -> u.getName() == null);
    System.out.println("  null 이름 없음:        " + noNullName); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 특정 사용자 검색 (filter + findFirst)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 특정 사용자 검색 - filter + findFirst");

    // ID로 사용자 검색
    Optional<User> byId = users.stream()
        .filter(u -> u.getId() == 3)
        .findFirst();
    byId.ifPresent(u ->
        System.out.printf("  ID=3: %s (%d세)%n", u.getName(), u.getAge())); // charlie (22세)

    // 이름으로 검색 (대소문자 무시)
    Optional<User> byName = users.stream()
        .filter(u -> "ALICE".equalsIgnoreCase(u.getName()))
        .findFirst();
    byName.map(User::getEmail)
          .ifPresent(email -> System.out.println("  Alice 이메일: " + email));

    // 활성 상태 + 30세 이상인 첫 번째 사용자
    Optional<User> activeAdult = users.stream()
        .filter(User::isActive)
        .filter(u -> u.getAge() >= 30)
        .findFirst();
    activeAdult.ifPresent(u ->
        System.out.printf("  활성 30세+ 첫 번째: %s (%d세)%n", u.getName(), u.getAge())); // dave

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. anyMatch vs filter + findFirst 역할 분리
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] anyMatch vs findFirst 역할 분리");

    // 나쁜 패턴: anyMatch로 존재 확인 후 다시 findFirst → 스트림 두 번 순회
    boolean exists = users.stream().anyMatch(u -> u.getAge() > 40);
    if (exists) {
      Optional<User> found = users.stream().filter(u -> u.getAge() > 40).findFirst();
      found.ifPresent(u -> System.out.println("  [나쁜 패턴] 40세 초과: " + u.getName())); // dave
    }

    // 좋은 패턴: findFirst 하나로 존재 확인과 결과 조회를 동시에
    users.stream()
        .filter(u -> u.getAge() > 40)
        .findFirst()
        .ifPresent(u -> System.out.println("  [좋은 패턴] 40세 초과: " + u.getName())); // dave

    // 존재 여부만 필요하면 anyMatch
    boolean hasOldUser = users.stream().anyMatch(u -> u.getAge() > 40);
    System.out.println("  [boolean만 필요] anyMatch: " + hasOldUser); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 활성 사용자 이메일 목록 + 특정 도메인 검증
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 활성 사용자 이메일 검증");

    // 활성 사용자의 이메일을 추출
    List<String> activeEmails = users.stream()
        .filter(User::isActive)
        .map(User::getEmail)
        .filter(email -> !email.isBlank())
        .toList();
    System.out.println("  활성 사용자 이메일: " + activeEmails);

    // 모든 이메일이 example.com 도메인인가?
    boolean allExampleDomain = activeEmails.stream()
        .allMatch(email -> email.endsWith("@example.com"));
    System.out.println("  모두 example.com 도메인: " + allExampleDomain); // true

    // 특정 이메일이 이미 등록되어 있는가? (중복 체크)
    String newEmail = "alice@example.com";
    boolean isDuplicate = users.stream()
        .anyMatch(u -> newEmail.equals(u.getEmail()));
    System.out.println("  '" + newEmail + "' 중복: " + isDuplicate); // true

    System.out.println();
    System.out.println("→ anyMatch: 존재 여부 확인. allMatch: 전체 유효성 검사. noneMatch: 금지 조건 검사.");
    System.out.println("→ 결과 요소가 필요하면 filter + findFirst. boolean만 필요하면 anyMatch.");
    System.out.println("→ anyMatch로 확인 후 다시 findFirst를 호출하는 것은 스트림을 두 번 순회한다.");
  }

  static class User {
    private final int     id;
    private final String  name;
    private final String  email;
    private final boolean active;
    private final int     age;

    User(int id, String name, String email, boolean active, int age) {
      this.id     = id;
      this.name   = name;
      this.email  = email;
      this.active = active;
      this.age    = age;
    }

    int     getId()     { return id; }
    String  getName()   { return name; }
    String  getEmail()  { return email; }
    boolean isActive()  { return active; }
    int     getAge()    { return age; }
  }
}
