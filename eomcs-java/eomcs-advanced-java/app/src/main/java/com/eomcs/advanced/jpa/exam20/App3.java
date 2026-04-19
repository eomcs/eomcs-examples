package com.eomcs.advanced.jpa.exam20;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam20 - Native Query: 재귀 CTE(Common Table Expression)로 계층 구조 조회
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App3
//
public class App3 {

  public static void main(String[] args) {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url",      url);
    props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    try (EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("exam20", props)) {

      // 샘플 카테고리 계층:
      //   전자제품 (id=1, parent=NULL)
      //     └─ 노트북 (id=2, parent=1)
      //          └─ 게이밍노트북 (id=3, parent=2)
      //   의류 (id=4, parent=NULL)
      //     └─ 남성의류 (id=5, parent=4)

      // ── 1. 재귀 CTE: 전체 카테고리 계층 트리 조회 ──────────────────────────
      System.out.println("=== 1. 재귀 CTE: 카테고리 계층 트리 (루트부터 전체) ===");
      try (EntityManager em = emf.createEntityManager()) {
        // Oracle 12c R2+ 재귀 CTE 문법 (WITH ... UNION ALL 자기 참조)
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery("""
            WITH category_tree (id, name, parent_id, lvl, path) AS (
              SELECT id, name, parent_id,
                     1 AS lvl,
                     CAST(name AS VARCHAR2(4000)) AS path
                FROM shop_category
               WHERE parent_id IS NULL
              UNION ALL
              SELECT c.id, c.name, c.parent_id,
                     ct.lvl + 1,
                     ct.path || ' > ' || c.name
                FROM shop_category c
                JOIN category_tree ct ON c.parent_id = ct.id
            )
            SELECT id, name, lvl, path
              FROM category_tree
             ORDER BY path
            """)
            .getResultList();

        rows.forEach(row -> {
          int lvl = ((Number) row[2]).intValue();
          System.out.printf("  %s[%s] %s%n",
              "  ".repeat(lvl - 1), row[0], row[1]);
          System.out.printf("    경로: %s%n", row[3]);
        });
      }

      // ── 2. 재귀 CTE: 특정 루트의 하위 계층만 조회 ──────────────────────────
      System.out.println("\n=== 2. 재귀 CTE: '전자제품' 하위 계층만 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery("""
            WITH category_tree (id, name, parent_id, lvl) AS (
              SELECT id, name, parent_id, 1 AS lvl
                FROM shop_category
               WHERE id = :rootId
              UNION ALL
              SELECT c.id, c.name, c.parent_id, ct.lvl + 1
                FROM shop_category c
                JOIN category_tree ct ON c.parent_id = ct.id
            )
            SELECT id, name, lvl
              FROM category_tree
             ORDER BY lvl, id
            """)
            .setParameter("rootId", 1L)
            .getResultList();

        rows.forEach(row -> {
          int lvl = ((Number) row[2]).intValue();
          System.out.printf("  %s[lv%d] %s (id=%s)%n",
              "  ".repeat(lvl - 1), lvl, row[1], row[0]);
        });
      }

      // ── 3. 재귀 CTE: 특정 노드의 조상 체인 조회 (leaf → root) ───────────────
      System.out.println("\n=== 3. 재귀 CTE: '게이밍노트북'(id=3)의 조상 체인 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery("""
            WITH ancestor_chain (id, name, parent_id, depth) AS (
              SELECT id, name, parent_id, 0 AS depth
                FROM shop_category
               WHERE id = :leafId
              UNION ALL
              SELECT c.id, c.name, c.parent_id, ac.depth + 1
                FROM shop_category c
                JOIN ancestor_chain ac ON c.id = ac.parent_id
            )
            SELECT id, name, depth
              FROM ancestor_chain
             ORDER BY depth DESC
            """)
            .setParameter("leafId", 3L)
            .getResultList();

        System.out.print("  경로: ");
        for (int i = 0; i < rows.size(); i++) {
          if (i > 0) System.out.print(" > ");
          System.out.print(rows.get(i)[1]);
        }
        System.out.println();
      }

      // ── 4. Oracle CONNECT BY (Oracle 전통 계층 쿼리) ──────────────────────
      System.out.println("\n=== 4. Oracle CONNECT BY: 계층 쿼리 (전통 방식) ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
            "SELECT id, name, LEVEL AS lvl,"
            + "       SYS_CONNECT_BY_PATH(name, ' > ') AS path"
            + " FROM shop_category"
            + " START WITH parent_id IS NULL"
            + " CONNECT BY PRIOR id = parent_id"
            + " ORDER SIBLINGS BY id")
            .getResultList();

        rows.forEach(row -> {
          int lvl = ((Number) row[2]).intValue();
          System.out.printf("  %s[%s] %s%n",
              "  ".repeat(lvl - 1), row[0], row[1]);
        });
        // CONNECT BY: Oracle 고유 계층 쿼리. CTE보다 간결하지만 Oracle 전용
        // SYS_CONNECT_BY_PATH: 루트부터 현재 노드까지의 경로 문자열 생성
      }

      System.out.println("\n[정리: 재귀 CTE vs CONNECT BY]");
      System.out.println("  재귀 CTE (WITH ... UNION ALL)");
      System.out.println("    - 표준 SQL (ISO/IEC 9075), Oracle 12c R2+ 지원");
      System.out.println("    - 다른 DB(MySQL 8, PostgreSQL 등)로 이식 가능");
      System.out.println("    - 앵커(루트) + 재귀(자식 반복) 두 부분으로 구성");
      System.out.println("  CONNECT BY (Oracle 전통 방식)");
      System.out.println("    - Oracle 전용. 문법이 간결");
      System.out.println("    - LEVEL, SYS_CONNECT_BY_PATH 등 Oracle 함수 활용 가능");
      System.out.println("  JPA JPQL로는 재귀 CTE 표현 불가 → Native Query 필수");
    }
  }
}
