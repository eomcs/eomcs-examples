package com.eomcs.advanced.stream.exam02;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Optional 실전 패턴 - null 방어 코드 제거:
//
// Optional의 핵심 사용 목적:
//   - 메서드가 "값이 없을 수 있다"는 사실을 반환 타입으로 명시한다.
//   - 호출자가 null 체크를 강제받지 않고도 빈 값을 안전하게 처리할 수 있다.
//   - null 체크 if 체인을 Optional 체인으로 교체해 코드를 간결하게 만든다.
//
// 나쁜 사용 예 (안티패턴):
//   - 필드 타입으로 Optional을 사용하지 않는다. (직렬화 불가, 성능 저하)
//   - 메서드 파라미터 타입으로 Optional을 사용하지 않는다. (오버로딩이나 기본값으로 대체)
//   - Optional.get()을 isPresent() 없이 단독으로 사용하지 않는다.
//   - Optional<Optional<T>>처럼 중첩해서 사용하지 않는다.
//
// 올바른 사용 예:
//   - 메서드의 반환 타입으로 사용한다.
//   - 값이 없는 경우를 API 계약(contract)으로 명확히 표현한다.
//   - map / flatMap / filter / orElse 체인으로 처리한다.
//

public class App3 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 중첩 null 체크 → Optional 체인으로 교체
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 중첩 null 체크 → Optional 체인");

    Order order1 = new Order(new Customer("Alice", new Address("서울")));
    Order order2 = new Order(new Customer("Bob", null));   // 주소 없음
    Order order3 = new Order(null);                        // 고객 없음

    // null 체크 방식: 각 단계마다 null을 확인해야 한다. (null 체크 피라미드)
    System.out.println("  [null 체크 방식]");
    printCity(order1);
    printCity(order2);
    printCity(order3);

    // Optional 방식: 체인으로 표현한다. null 체크가 사라진다.
    System.out.println("  [Optional 방식]");
    System.out.println("  주문1 도시: " + getCityOptional(order1));
    System.out.println("  주문2 도시: " + getCityOptional(order2));
    System.out.println("  주문3 도시: " + getCityOptional(order3));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 컬렉션에서 첫 번째 조건 일치 요소 찾기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 리스트에서 조건에 맞는 첫 번째 요소 찾기");

    List<String> items = Arrays.asList("사과", "바나나", "블루베리", "딸기", "블랙베리");

    // null 반환 방식: 호출자가 null을 확인해야 한다.
    String foundNull = findFirstStartingWithNull(items, "블");
    if (foundNull != null) {
      System.out.println("  null 방식: " + foundNull);
    } else {
      System.out.println("  null 방식: 없음");
    }

    // Optional 반환 방식: 호출자가 orElse로 기본값을 자연스럽게 처리한다.
    String foundOpt = findFirstStartingWith(items, "블").orElse("없음");
    System.out.println("  Optional 방식: " + foundOpt);

    // 조건에 맞는 항목이 없을 때
    String notFound = findFirstStartingWith(items, "포").orElse("없음");
    System.out.println("  '포'로 시작: " + notFound);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 설정값 조회 패턴 - 기본값 폴백(fallback)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 설정값 조회 - 우선순위 폴백 체인");

    // 우선순위: 사용자 설정 → 환경 변수 → 시스템 기본값
    // 각 단계의 값이 없으면 다음 단계로 넘어간다.

    String userSetting = null;              // 사용자가 설정하지 않음
    String envVariable = "UTF-8";           // 환경 변수에 설정되어 있음
    String systemDefault = "ISO-8859-1";    // 시스템 기본값

    // null 체크 방식
    String charsetNull = userSetting != null ? userSetting
        : (envVariable != null ? envVariable : systemDefault);
    System.out.println("  null 체크: " + charsetNull);

    // Optional 폴백 체인 방식
    String charsetOpt = Optional.ofNullable(userSetting)    // 사용자 설정 확인
        .or(() -> Optional.ofNullable(envVariable))          // 없으면 환경 변수 (Java 9+)
        .orElse(systemDefault);                              // 없으면 시스템 기본값
    System.out.println("  Optional:  " + charsetOpt);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 안티패턴 - get()을 isPresent() 없이 사용
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 안티패턴 vs 올바른 패턴");

    Optional<String> maybe = Optional.of("값");

    // 안티패턴: isPresent() + get() 조합은 null 체크와 다를 바 없다.
    if (maybe.isPresent()) {
      System.out.println("  안티패턴 (isPresent+get): " + maybe.get()); // null 체크와 동일
    }

    // 올바른 패턴: orElse / map / ifPresent 중 하나를 선택한다.
    maybe.ifPresent(v -> System.out.println("  ifPresent: " + v));
    System.out.println("  orElse:    " + maybe.orElse("없음"));
    System.out.println("  map:       " + maybe.map(String::toUpperCase).orElse("없음"));

    System.out.println();
    System.out.println("→ Optional은 메서드 반환 타입으로 사용해 '값이 없을 수 있다'는 계약을 명시한다.");
    System.out.println("→ null 체크 피라미드를 Optional 체인으로 교체하면 코드가 선형으로 읽힌다.");
    System.out.println("→ isPresent()+get() 조합은 안티패턴이다. ifPresent/map/orElse를 사용한다.");
  }

  // null 체크 방식
  static void printCity(Order order) {
    if (order != null) {
      Customer customer = order.getCustomer();
      if (customer != null) {
        Address address = customer.getAddress();
        if (address != null) {
          System.out.println("  도시: " + address.getCity());
          return;
        }
      }
    }
    System.out.println("  도시: 알 수 없음");
  }

  // Optional 방식: 체인으로 null 체크 피라미드를 대체한다.
  static String getCityOptional(Order order) {
    return Optional.ofNullable(order)
        .map(Order::getCustomer)          // Order → Customer (null이면 빈 Optional)
        .map(Customer::getAddress)        // Customer → Address (null이면 빈 Optional)
        .map(Address::getCity)            // Address → String (null이면 빈 Optional)
        .orElse("알 수 없음");
  }

  // null 반환 방식
  static String findFirstStartingWithNull(List<String> list, String prefix) {
    for (String item : list) {
      if (item.startsWith(prefix)) return item;
    }
    return null; // 없으면 null 반환 → 호출자가 null 체크 강제
  }

  // Optional 반환 방식: Stream.findFirst()가 Optional을 반환한다.
  static Optional<String> findFirstStartingWith(List<String> list, String prefix) {
    return list.stream()
        .filter(item -> item.startsWith(prefix))
        .findFirst(); // 없으면 Optional.empty() 반환 → 호출자가 자연스럽게 처리
  }

  // 도메인 클래스
  static class Address {
    private final String city;
    Address(String city) { this.city = city; }
    String getCity() { return city; }
  }

  static class Customer {
    private final String name;
    private final Address address;
    Customer(String name, Address address) { this.name = name; this.address = address; }
    String getName() { return name; }
    Address getAddress() { return address; }
  }

  static class Order {
    private final Customer customer;
    Order(Customer customer) { this.customer = customer; }
    Customer getCustomer() { return customer; }
  }
}
