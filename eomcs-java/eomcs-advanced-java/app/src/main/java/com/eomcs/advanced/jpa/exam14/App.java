package com.eomcs.advanced.jpa.exam14;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam14 - 자기 참조 연관관계 (Self-Join)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam14.App
//
public class App {

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
        Persistence.createEntityManagerFactory("exam14", props)) {

      // ── 1. 기존 계층 구조 조회 ────────────────────────────────────────────
      System.out.println("=== 1. 루트 카테고리 조회 (parent IS NULL) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Category> roots = em.createQuery(
            "SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.id",
            Category.class)
            .getResultList();

        for (Category root : roots) {
          printTree(root, 0);
        }
      }

      // ── 2. 특정 카테고리의 부모 체인 탐색 ────────────────────────────────
      System.out.println("\n=== 2. '게이밍노트북'의 부모 체인 탐색 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Category gaming = em.find(Category.class, 3L); // 게이밍노트북
        System.out.print("  경로: ");
        printAncestors(gaming);
        System.out.println();
      }

      // ── 3. CascadeType.PERSIST 실습: 부모 저장 시 자식도 함께 저장 ────────
      System.out.println("\n=== 3. 새 계층 구조 저장 (CascadeType.PERSIST) ===");
      Long sportsCategoryId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Category sports = new Category("스포츠");        // 루트
        Category outdoor = new Category("아웃도어");      // 자식
        Category hiking  = new Category("등산");          // 손자

        sports.addChild(outdoor);   // outdoor.parent = sports
        outdoor.addChild(hiking);   // hiking.parent = outdoor

        // sports만 persist해도 cascade로 outdoor, hiking도 함께 저장됨
        em.persist(sports);

        tx.commit();
        sportsCategoryId = sports.getId();

        System.out.println("  저장 완료:");
        System.out.println("    " + sports);
        System.out.println("    " + outdoor);
        System.out.println("    " + hiking);
      }

      // ── 4. 저장된 계층 구조 확인 ─────────────────────────────────────────
      System.out.println("\n=== 4. 저장된 '스포츠' 계층 구조 확인 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Category sports = em.find(Category.class, sportsCategoryId);
        printTree(sports, 0);
      }

      // ── 5. 특정 뎁스의 자식 JPQL 조회 ────────────────────────────────────
      System.out.println("\n=== 5. JPQL: 특정 부모의 직계 자식 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // 전자제품(id=1)의 직계 자식
        List<Category> directChildren = em.createQuery(
            "SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.id",
            Category.class)
            .setParameter("parentId", 1L)
            .getResultList();

        System.out.println("  전자제품의 직계 자식:");
        directChildren.forEach(c -> System.out.println("    " + c));
      }

      System.out.println("\n[정리: 자기 참조 연관관계]");
      System.out.println("  @ManyToOne parent : 부모 참조 (FK = parent_id, nullable)");
      System.out.println("  @OneToMany children: 자식 목록 (mappedBy = \"parent\")");
      System.out.println("  루트 카테고리     : parent = null");
      System.out.println("  CascadeType.PERSIST: 부모 저장 시 자식도 자동 저장");
    }
  }

  // 재귀적으로 트리 출력
  private static void printTree(Category category, int depth) {
    System.out.println("  " + "  ".repeat(depth) + "├─ " + category.getName()
        + " (id=" + category.getId() + ")");
    for (Category child : category.getChildren()) {
      printTree(child, depth + 1);
    }
  }

  // 루트까지 조상 체인 출력
  private static void printAncestors(Category category) {
    if (category.getParent() != null) {
      printAncestors(category.getParent());
      System.out.print(" > ");
    }
    System.out.print(category.getName());
  }
}
