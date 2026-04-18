package com.eomcs.advanced.stream.exam16;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

// 병렬 스트림과 수집:
//
// collect(Collectors.groupingBy(...))
//   - 일반 Map으로 그룹화한다.
//   - 병렬 스트림에서도 사용할 수 있지만 병합 비용이 발생한다.
//
// collect(Collectors.groupingByConcurrent(...))
//   - ConcurrentMap으로 그룹화한다.
//   - 병렬 스트림에서 여러 스레드가 동시에 결과 컨테이너를 갱신할 수 있다.
//   - 결과의 그룹 내부 순서는 보장하지 않는다.
//
// unordered()
//   - encounter order를 포기해도 된다고 스트림에 알려준다.
//   - 병렬 처리에서 일부 연산의 부담을 줄일 수 있다.
//
// -----------------------------------------------------------------------
// groupingBy vs groupingByConcurrent 비교
// -----------------------------------------------------------------------
//
// [groupingBy]
//   - 각 스레드가 부분 결과(Map)를 따로 만든 뒤 나중에 병합(merge)
//   - 병합 비용이 발생
//   - 반환 타입: HashMap
//   - 그룹 내 순서 보장
//
// [groupingByConcurrent]
//   - 여러 스레드가 단 하나의 ConcurrentMap을 동시에 직접 갱신
//   - 병합 비용 없음 → 병렬 환경에서 더 효율적
//   - 반환 타입: ConcurrentHashMap
//   - 그룹 내 순서 보장 안 됨
//
// 항목               | groupingBy          | groupingByConcurrent
// -------------------|---------------------|---------------------
// 수집 전략          | 부분 Map 생성 후 병합 | 공유 Map에 직접 삽입
// 병합 비용          | 있음                | 없음
// 반환 타입          | HashMap             | ConcurrentHashMap
// 순서 보장          | O                   | X
// 적합한 상황        | 순서가 중요할 때     | 순서 불필요, 성능 우선
//

public class App4 {

  public static void main(String[] args) {

    List<Order> orders = Arrays.asList(
        new Order("O-001", "도서", 12000),
        new Order("O-002", "문구", 3000),
        new Order("O-003", "도서", 18000),
        new Order("O-004", "식품", 9000),
        new Order("O-005", "문구", 5000),
        new Order("O-006", "도서", 24000),
        new Order("O-007", "식품", 11000),
        new Order("O-008", "생활", 15000),
        new Order("O-009", "생활", 8000),
        new Order("O-010", "식품", 7000)
    );

    // ─────────────────────────────────────────────────────────────
    // 예제 1. groupingBy - 병렬 스트림에서 부분 결과 병합 방식
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] groupingBy - 병렬 스트림에서도 사용 가능");

    Map<String, Long> countByCategory = orders.parallelStream()
        .collect(Collectors.groupingBy(
            Order::category,    // 카테고리를 키로 그룹화
            Collectors.counting())); // 각 그룹의 요소 수를 집계

    System.out.println("  카테고리별 주문 수: " + countByCategory);
    System.out.println("  결과 Map 타입: " + countByCategory.getClass().getName()); // HashMap
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. groupingByConcurrent - 공유 ConcurrentMap에 직접 갱신
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] groupingByConcurrent - 병렬 수집");

    ConcurrentMap<String, Long> concurrentCount = orders.parallelStream()
        .collect(Collectors.groupingByConcurrent(
            Order::category,    // 여러 스레드가 동일한 ConcurrentMap에 동시에 삽입
            Collectors.counting()));

    System.out.println("  카테고리별 주문 수: " + concurrentCount);
    System.out.println("  결과 Map 타입: " + concurrentCount.getClass().getName()); // ConcurrentHashMap
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. unordered() - encounter order 포기로 병렬 처리 부담 완화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] unordered() - 순서가 필요 없는 병렬 처리");

    List<String> expensiveOrders = orders.parallelStream()
        .unordered()                          // 원본 순서를 유지하지 않아도 된다고 스트림에 알림
        .filter(order -> order.amount() >= 10_000)
        .map(Order::id)
        .toList();

    System.out.println("  10,000원 이상 주문: " + expensiveOrders);
    System.out.println();

    System.out.println("→ 병렬 수집에서 결과 순서가 중요하지 않다면 concurrent collector와 unordered()를 고려한다.");
    System.out.println("→ groupingByConcurrent()는 ConcurrentMap을 반환하지만, 그룹 내부 순서는 보장하지 않는다.");
  }

  record Order(String id, String category, int amount) {}
}
