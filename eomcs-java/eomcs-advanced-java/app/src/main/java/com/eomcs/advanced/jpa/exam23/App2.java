package com.eomcs.advanced.jpa.exam23;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam23 - Querydsl 기초: BooleanBuilder - 동적 쿼리
//
// Querydsl의 핵심 장점: 조건을 런타임에 동적으로 조합할 수 있다.
//
// BooleanBuilder: 조건(Predicate)을 and/or로 동적으로 추가하는 빌더
//   - null 파라미터를 그냥 건너뛸 수 있어 if 분기 없이 동적 쿼리 구성 가능
//   - Criteria API보다 훨씬 간결하다
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam23.App2
//
public class App2 {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);

      try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();
        try {
          JPAQueryFactory factory = new JPAQueryFactory(em);

          // ── 1. BooleanBuilder - 조건 동적 조합 ────────────────────────
          System.out.println("=== 1. BooleanBuilder - 조건 1개 (city만) ===");
          List<Customer> r1 = searchCustomers(factory, "서울", null);
          r1.forEach(c -> System.out.println("  " + c));

          System.out.println("\n=== 2. BooleanBuilder - 조건 2개 (city + keyword) ===");
          List<Customer> r2 = searchCustomers(factory, "서울", "홍");
          r2.forEach(c -> System.out.println("  " + c));

          System.out.println("\n=== 3. BooleanBuilder - 조건 없음 (전체) ===");
          List<Customer> r3 = searchCustomers(factory, null, null);
          r3.forEach(c -> System.out.println("  " + c));

          // ── 2. Product 동적 검색 ────────────────────────────────────────
          System.out.println("\n=== 4. Product 동적 검색 (dtype + 가격 범위) ===");
          List<Product> p1 = searchProducts(factory, "DIGITAL", null, null);
          System.out.println("  DIGITAL 전체:");
          p1.forEach(p -> System.out.println("    " + p));

          List<Product> p2 = searchProducts(factory, null,
              new BigDecimal("100000"), new BigDecimal("2000000"));
          System.out.println("  가격 10만~200만:");
          p2.forEach(p -> System.out.println("    " + p));

          em.getTransaction().commit();
        } catch (Exception e) {
          em.getTransaction().rollback();
          throw e;
        }
      }

      System.out.println("\n[정리]");
      System.out.println("  BooleanBuilder       : and/or로 Predicate를 동적 누적");
      System.out.println("  and(조건 != null)    : null이면 추가하지 않음 → if 분기 제거");
      System.out.println("  where(builder)       : 빌더를 WHERE 절에 전달");
      System.out.println("  → Criteria API 대비 훨씬 간결한 동적 쿼리 작성 가능");
    }
  }

  // BooleanBuilder로 city, keyword 조건을 동적 조합
  static List<Customer> searchCustomers(JPAQueryFactory factory, String city, String keyword) {
    QCustomer c = QCustomer.customer;
    BooleanBuilder builder = new BooleanBuilder();

    if (city != null && !city.isBlank()) {
      builder.and(c.city.eq(city));
    }
    if (keyword != null && !keyword.isBlank()) {
      builder.and(c.name.contains(keyword));
    }
    return factory.selectFrom(c).where(builder).orderBy(c.name.asc()).fetch();
  }

  // BooleanBuilder로 dtype, 가격 범위 조건을 동적 조합
  static List<Product> searchProducts(JPAQueryFactory factory,
      String dtype, BigDecimal minPrice, BigDecimal maxPrice) {
    QProduct p = QProduct.product;
    BooleanBuilder builder = new BooleanBuilder();

    if (dtype != null && !dtype.isBlank()) {
      builder.and(p.dtype.eq(dtype));
    }
    if (minPrice != null) {
      builder.and(p.price.goe(minPrice));  // >= minPrice
    }
    if (maxPrice != null) {
      builder.and(p.price.loe(maxPrice));  // <= maxPrice
    }
    return factory.selectFrom(p).where(builder).orderBy(p.price.asc()).fetch();
  }
}
