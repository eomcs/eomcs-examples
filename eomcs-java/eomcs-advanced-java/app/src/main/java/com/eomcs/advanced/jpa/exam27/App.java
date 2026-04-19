package com.eomcs.advanced.jpa.exam27;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam27 - 지연 로딩(Lazy) vs 즉시 로딩(Eager)
//
// FetchType.LAZY (기본값 @OneToMany):
//   - 프록시 객체가 orders 자리를 차지하다가 최초 접근 시 SELECT 실행
//   - 트랜잭션 안에서만 정상 동작
//   - 트랜잭션 밖에서 접근 → LazyInitializationException
//
// FetchType.EAGER (기본값 @ManyToOne):
//   - 부모 로드 시 연관 데이터를 항상 JOIN하여 함께 로드
//   - 사용하지 않아도 항상 로드 → 불필요한 데이터 전송 위험
//   - 특히 @OneToMany EAGER는 N+1 문제를 더 심화시킬 수 있어 비권장
//
// OSIV(Open Session In View):
//   - 웹 요청 시작부터 종료까지 Hibernate 세션을 열어 두는 패턴
//   - View 렌더링 중 LAZY 로딩 허용
//   - 단점: 커넥션 점유 시간 증가, 예상 외 쿼리 발생 가능
//   - Spring Boot: spring.jpa.open-in-view=true (기본값)
//   - 비웹 환경(이 예제): 세션 범위 = @Transactional 범위
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam27.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerService svc = ctx.getBean(CustomerService.class);

      // ── 1. 트랜잭션 안: LAZY 로딩 정상 동작 ──────────────────────────
      System.out.println("=== 1. 트랜잭션 안 - LAZY 로딩 정상 동작 ===");
      svc.printOrdersInsideTransaction();

      // ── 2. 트랜잭션 안에서 초기화 후 반환 ────────────────────────────
      System.out.println("\n=== 2. 트랜잭션 안에서 미리 초기화 후 사용 ===");
      List<Customer> initialized = svc.findAllInitialized();
      for (Customer c : initialized) {
        // 이미 초기화되어 있으므로 트랜잭션 없이도 접근 가능
        System.out.printf("   %s → 주문 %d건 (초기화됨)%n", c.getName(), c.getOrders().size());
      }

      // ── 3. 트랜잭션 밖: LazyInitializationException ───────────────────
      System.out.println("\n=== 3. 트랜잭션 밖 - LazyInitializationException 발생 ===");
      Customer detached = svc.findByIdDetached(1L);
      System.out.println("   조회된 고객: " + detached.getName());
      System.out.println("   >> getOrders() 접근 시도...");
      try {
        // 세션이 닫힌 후 LAZY 필드 접근 → 예외 발생
        int count = detached.getOrders().size();
        System.out.println("   주문 수: " + count);
      } catch (Exception e) {
        System.out.println("   [예외] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        System.out.println("   원인: 트랜잭션(세션)이 종료된 후 LAZY 필드에 접근");
      }

      System.out.println("\n[정리]");
      System.out.println("  LAZY (권장)    : 실제 사용 시 로드, 트랜잭션 범위 안에서만 동작");
      System.out.println("  EAGER (비권장) : 항상 함께 로드, @OneToMany EAGER는 N+1 심화 위험");
      System.out.println("  OSIV           : 세션 범위를 요청 전체로 확장 (웹 환경)");
      System.out.println("  해결책         : 트랜잭션 안에서 미리 초기화 또는 JOIN FETCH 사용");
    }
  }
}
