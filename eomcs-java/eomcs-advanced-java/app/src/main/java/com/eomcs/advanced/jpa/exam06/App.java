package com.eomcs.advanced.jpa.exam06;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam06 - JPA 소개: EntityManagerFactory & EntityManager
//
// JPA(Jakarta Persistence API):
// - 자바 ORM 표준 인터페이스다. 구현체는 Hibernate, EclipseLink 등이 있다.
// - SQL 대신 객체(엔티티) 중심으로 데이터를 다룬다.
// - Hibernate가 내부적으로 SQL을 생성하고 실행한다.
//
// 핵심 클래스 역할:
//   Persistence              → persistence.xml을 읽어 EMF를 생성하는 팩토리
//   EntityManagerFactory     → 앱 당 1개. DB 연결풀·메타데이터를 초기화한다. Thread-safe.
//   EntityManager            → 요청·트랜잭션 당 1개. 영속성 컨텍스트(1차 캐시)를 유지한다. Thread-unsafe.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam06.App
//
public class App {

  public static void main(String[] args) {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    // persistence.xml에 없는 접속 정보를 런타임에 전달한다.
    // → 소스·XML에 계정 정보를 하드코딩하지 않아도 된다.
    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url",      url);
    props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    System.out.println("=== [JPA] EntityManagerFactory & EntityManager ===");

    // 1. EntityManagerFactory 생성
    //    - "exam06": persistence.xml에서 해당 persistence-unit을 찾아 초기화한다.
    //    - 생성 비용이 크므로 앱 전체에서 한 번만 만들고 공유한다.
    //    - AutoCloseable 구현 → try-with-resources 사용 가능 (emf.close() 자동 호출)
    try (EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("exam06", props)) {

      System.out.printf("EMF 구현체 : %s%n", emf.getClass().getSimpleName());
      System.out.printf("EMF 열림?  : %s%n", emf.isOpen());

      // 2. EntityManager 생성
      //    - 요청(트랜잭션) 단위로 생성하고 사용 후 반드시 닫는다.
      //    - 내부에 영속성 컨텍스트(1차 캐시)를 유지한다.
      //    - AutoCloseable 구현 → try-with-resources 사용 가능
      try (EntityManager em = emf.createEntityManager()) {

        System.out.printf("%nEM 구현체  : %s%n", em.getClass().getSimpleName());
        System.out.printf("EM 열림?   : %s%n", em.isOpen());

        // 3. JPQL(Jakarta Persistence Query Language)로 전체 고객 조회
        //    - SQL은 테이블·컬럼명을 사용하지만,
        //      JPQL은 엔티티 클래스명(Customer)과 필드명(id)을 사용한다.
        //    - Hibernate가 JPQL → SQL로 변환하여 실행한다.
        List<Customer> customers = em
            .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
            .getResultList();

        System.out.println("\n--- 고객 목록 (JPQL 조회) ---");
        customers.forEach(c -> System.out.println("  " + c));

        // 4. 기본 키로 단건 조회
        //    - find(엔티티 클래스, 기본 키) : 있으면 엔티티, 없으면 null
        //    - 영속성 컨텍스트 1차 캐시에 이미 있으면 SQL을 실행하지 않는다.
        Customer found = em.find(Customer.class, 1L);
        System.out.println("\n--- id=1 고객 (find) ---");
        System.out.println("  " + found);
      }
      // em.close() → 영속성 컨텍스트 소멸, 관리 중인 엔티티는 모두 준영속 상태가 된다.

      System.out.println("\n--- EMF 닫히기 전 마지막 상태 ---");
      System.out.printf("EMF 열림? : %s%n", emf.isOpen());
    }
    // emf.close() → DB 연결풀 해제, 캐시 정리

    System.out.println("\n[정리]");
    System.out.println("  EMF : persistence.xml 로딩 → 앱 당 1회 생성, 종료 시 닫기");
    System.out.println("  EM  : 요청·트랜잭션마다 생성·닫기 (try-with-resources 권장)");
    System.out.println("  JPQL: 엔티티 클래스명/필드명 기준 쿼리 (테이블·컬럼명 아님)");
    System.out.println("  find(): 기본 키 조회. 1차 캐시 우선, 없으면 DB 조회");
  }
}
