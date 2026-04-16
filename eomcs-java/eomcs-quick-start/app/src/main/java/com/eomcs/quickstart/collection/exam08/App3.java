package com.eomcs.quickstart.collection.exam08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// 방어적 복사(Defensive Copy) 패턴:
// - 외부에서 전달받은 컬렉션이나 내부 컬렉션을 반환할 때 복사본을 사용하여
//   외부의 수정으로부터 내부 상태를 보호하는 설계 기법이다.
// - 불변 컬렉션과 결합하면 외부에서 내부 데이터를 변경할 수 없다.
//
// 방어적 복사 위치:
//   입력(생성자·메서드 매개변수): 외부 컬렉션을 그대로 저장하지 않고 복사본을 저장
//   출력(getter): 내부 컬렉션을 그대로 반환하지 않고 복사본이나 읽기 전용 뷰를 반환
//
// 불변 컬렉션 선택 기준:
// ┌────────────────────────────────┬───────────────────────────────────────────────┐
// │ 상황                           │ 권장 방법                                     │
// ├────────────────────────────────┼───────────────────────────────────────────────┤
// │ 리터럴로 초기화                 │ List.of() / Set.of() / Map.of()               │
// │ 기존 컬렉션을 불변으로 복사     │ List.copyOf() / Set.copyOf() / Map.copyOf()   │
// │ 기존 컬렉션의 읽기 전용 뷰     │ Collections.unmodifiableXxx()                 │
// │ 방어적 복사 후 읽기 전용 반환  │ Collections.unmodifiableList(new ArrayList(src))│
// └────────────────────────────────┴───────────────────────────────────────────────┘
//

public class App3 {

  // 나쁜 예: 방어적 복사 없는 클래스
  static class UnsafeCart {
    private List<String> items;

    UnsafeCart(List<String> items) {
      this.items = items; // 외부 참조 그대로 저장
    }

    List<String> getItems() {
      return items; // 내부 참조 그대로 반환
    }
  }

  // 좋은 예: 방어적 복사를 적용한 클래스
  static class SafeCart {
    private final List<String> items;

    SafeCart(List<String> items) {
      this.items = List.copyOf(items); // 복사 후 불변으로 저장
    }

    List<String> getItems() {
      return items; // 이미 불변이므로 그대로 반환해도 안전
      // 또는: return Collections.unmodifiableList(new ArrayList<>(items));
    }
  }

  public static void main(String[] args) {

    // 1. 방어적 복사 없는 클래스 - 내부 상태 변경 위험
    System.out.println("[방어적 복사 없는 클래스 - 내부 상태 변경 위험]");
    List<String> externalList = new ArrayList<>(List.of("apple", "banana"));
    UnsafeCart unsafeCart = new UnsafeCart(externalList);
    System.out.println("초기 items: " + unsafeCart.getItems());

    externalList.add("cherry"); // 외부에서 원본 수정
    System.out.println("외부 수정 후: " + unsafeCart.getItems()); // cherry가 포함됨 (취약)

    unsafeCart.getItems().add("grape"); // getter로 받은 참조로 내부 수정
    System.out.println("getter 통해 수정 후: " + unsafeCart.getItems()); // grape가 포함됨 (취약)

    // 2. 방어적 복사 적용 클래스 - 내부 상태 보호
    System.out.println("\n[방어적 복사 적용 클래스 - 내부 상태 보호]");
    List<String> safeExternal = new ArrayList<>(List.of("apple", "banana"));
    SafeCart safeCart = new SafeCart(safeExternal);
    System.out.println("초기 items: " + safeCart.getItems());

    safeExternal.add("cherry"); // 외부에서 원본 수정
    System.out.println("외부 수정 후: " + safeCart.getItems()); // cherry 미포함 (보호됨)

    try {
      safeCart.getItems().add("grape"); // getter 반환값 수정 시도
    } catch (UnsupportedOperationException e) {
      System.out.println("getter 수정 시도 → UnsupportedOperationException (보호됨)");
    }

    // 3. Collections.unmodifiableList()로 읽기 전용 뷰 반환
    System.out.println("\n[읽기 전용 뷰 반환 패턴]");
    List<String> internalData = new ArrayList<>(List.of("X", "Y", "Z"));
    // 복사본의 읽기 전용 뷰 반환 - 원본 보호
    List<String> view = Collections.unmodifiableList(new ArrayList<>(internalData));
    System.out.println("view: " + view);
    internalData.add("W"); // 원본 수정
    System.out.println("원본 수정 후 view: " + view); // W 미포함 (복사본이므로 독립적)

    try {
      view.add("V");
    } catch (UnsupportedOperationException e) {
      System.out.println("view.add() → UnsupportedOperationException");
    }

    // 4. 중첩 컬렉션의 얕은 불변성 주의
    System.out.println("\n[중첩 컬렉션의 얕은 불변성 주의]");
    List<String> inner1 = new ArrayList<>(List.of("a", "b"));
    List<String> inner2 = new ArrayList<>(List.of("c", "d"));
    List<List<String>> nested = List.of(inner1, inner2); // 외부 List는 불변
    System.out.println("nested: " + nested);

    try {
      nested.add(new ArrayList<>()); // 외부 List 수정 → 예외
    } catch (UnsupportedOperationException e) {
      System.out.println("nested.add() → UnsupportedOperationException");
    }

    // 내부 요소(inner1)는 여전히 가변
    inner1.add("z"); // 내부 List 수정은 허용
    System.out.println("inner1 수정 후 nested: " + nested); // inner1에 z가 포함됨

    // 5. 상수 컬렉션 정의 패턴
    System.out.println("\n[상수 컬렉션 정의 패턴]");
    // 나쁜 예: 가변 컬렉션을 상수로 선언 - 외부에서 수정 가능
    final List<String> BAD_CONSTANT = new ArrayList<>(List.of("READ", "WRITE", "DELETE"));
    BAD_CONSTANT.add("ADMIN"); // final이지만 참조가 같을 뿐, 내용 수정 가능
    System.out.println("BAD_CONSTANT (수정됨): " + BAD_CONSTANT);

    // 좋은 예: 불변 컬렉션을 상수로 선언
    final List<String> GOOD_CONSTANT = List.of("READ", "WRITE", "DELETE");
    try {
      GOOD_CONSTANT.add("ADMIN"); // 수정 시도 → 예외
    } catch (UnsupportedOperationException e) {
      System.out.println("GOOD_CONSTANT.add() → UnsupportedOperationException (보호됨)");
    }
    System.out.println("GOOD_CONSTANT: " + GOOD_CONSTANT);

    // 6. Map.copyOf() 방어적 복사
    System.out.println("\n[Map.copyOf() 방어적 복사]");
    Map<String, List<String>> config = new java.util.HashMap<>();
    config.put("servers", new ArrayList<>(List.of("host1", "host2")));

    Map<String, List<String>> immutableConfig = Map.copyOf(config); // 얕은 복사
    System.out.println("immutableConfig: " + immutableConfig);

    try {
      immutableConfig.put("db", new ArrayList<>()); // Map 자체 수정 → 예외
    } catch (UnsupportedOperationException e) {
      System.out.println("immutableConfig.put() → UnsupportedOperationException");
    }

    // 값으로 저장된 List는 여전히 가변 (얕은 불변)
    immutableConfig.get("servers").add("host3");
    System.out.println("내부 List 수정 후: " + immutableConfig); // host3 포함됨
  }
}
