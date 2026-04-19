package com.eomcs.advanced.jpa.exam23;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam23 - Querydsl 기초: JPAQueryFactory 기본 쿼리
//
// Querydsl: 타입 안전(type-safe) 동적 쿼리 라이브러리
//   - JPQL을 Java 코드로 작성 → 오타를 컴파일 타임에 발견
//   - Q-타입(QCustomer, QProduct)으로 필드를 타입 안전하게 참조
//   - 조건을 메서드 체인으로 표현 → 가독성↑
//
// 이 예제에서 확인할 내용:
//   1. selectFrom() - 기본 조회
//   2. where()      - 조건 (eq, like, gt, between)
//   3. orderBy()    - 정렬
//   4. limit()      - 개수 제한
//   5. fetchOne()   - 단건 조회
//   6. fetchCount() - 개수 조회 (deprecated in 5.x → fetch().size() 권장)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      // Spring 컨텍스트에서 EntityManagerFactory를 꺼내 직접 EntityManager 생성
      // → Spring 트랜잭션 관리 없이 JPAQueryFactory를 사용하는 가장 단순한 패턴
      EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);

      try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();
        try {
          JPAQueryFactory factory = new JPAQueryFactory(em);

          // Q-타입 별칭 (static import 대신 변수로 사용)
          QCustomer c = QCustomer.customer;
          QProduct  p = QProduct.product;

          // ── 1. 기본 전체 조회 ───────────────────────────────────────────
          System.out.println("=== 1. selectFrom - 전체 조회 ===");
          List<Customer> all = factory.selectFrom(c).fetch();
          all.forEach(cu -> System.out.println("  " + cu));

          // ── 2. eq (동등 조건) ──────────────────────────────────────────
          System.out.println("\n=== 2. where(c.city.eq()) - 동등 조건 ===");
          List<Customer> seoul = factory
              .selectFrom(c)
              .where(c.city.eq("서울"))
              .fetch();
          seoul.forEach(cu -> System.out.println("  " + cu));

          // ── 3. like / contains ──────────────────────────────────────────
          System.out.println("\n=== 3. where(c.name.contains()) - LIKE '%?%' ===");
          List<Customer> withHong = factory
              .selectFrom(c)
              .where(c.name.contains("홍"))
              .fetch();
          withHong.forEach(cu -> System.out.println("  " + cu));

          // ── 4. gt / lt - 숫자 범위 ────────────────────────────────────
          System.out.println("\n=== 4. where(p.price.gt()) - 가격 > 조건 ===");
          List<Product> expensive = factory
              .selectFrom(p)
              .where(p.price.gt(new BigDecimal("1000000")))
              .orderBy(p.price.desc())
              .fetch();
          expensive.forEach(pr -> System.out.println("  " + pr));

          // ── 5. 복합 조건 and / or ──────────────────────────────────────
          System.out.println("\n=== 5. 복합 조건 and() ===");
          List<Product> digitalCheap = factory
              .selectFrom(p)
              .where(
                  p.dtype.eq("DIGITAL")
                  .and(p.price.lt(new BigDecimal("100000"))))
              .fetch();
          digitalCheap.forEach(pr -> System.out.println("  " + pr));

          // ── 6. orderBy + limit ─────────────────────────────────────────
          System.out.println("\n=== 6. orderBy + limit ===");
          List<Product> top3 = factory
              .selectFrom(p)
              .orderBy(p.price.desc())
              .limit(3)
              .fetch();
          top3.forEach(pr -> System.out.println("  " + pr));

          // ── 7. fetchOne - 단건 조회 ────────────────────────────────────
          System.out.println("\n=== 7. fetchOne - 단건 조회 ===");
          Customer one = factory
              .selectFrom(c)
              .where(c.email.eq("hong@example.com"))
              .fetchOne();
          System.out.println("  " + one);

          em.getTransaction().commit();
        } catch (Exception e) {
          em.getTransaction().rollback();
          throw e;
        }
      }

      System.out.println("\n[정리]");
      System.out.println("  selectFrom(Q타입)       : FROM 절");
      System.out.println("  where(조건)             : WHERE 절 - eq / contains / gt / lt / between");
      System.out.println("  and(조건) / or(조건)    : 복합 조건");
      System.out.println("  orderBy(필드.asc/desc)  : ORDER BY 절");
      System.out.println("  limit(n)                : LIMIT (top-N)");
      System.out.println("  fetch()                 : List<T> 반환");
      System.out.println("  fetchOne()              : 단건 반환 (없으면 null, 둘 이상이면 예외)");
    }
  }
}
