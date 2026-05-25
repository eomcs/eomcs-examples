package com.eomcs.cleancode.ch06.exam04;

// 예제 3: Active Record와 비즈니스 규칙 분리
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: Active Record에 비즈니스 규칙이 섞여 있다.
  // - 데이터 보관, DB 저장/조회, 프리미엄 승급 규칙, 상태 변경 정책이 한 클래스에 뭉쳐 있다.
  // - 자료 구조와 객체가 섞인 Hybrid가 된다.
  // - 비즈니스 규칙이 바뀔 때마다 DB 접근 코드와 함께 있는 이 클래스를 수정해야 한다.
  static class BadUserRecord {
    private Long id;
    private String email;
    private String name;
    private int purchaseAmount;
    private boolean premium;

    BadUserRecord(Long id, String email, String name, int purchaseAmount) {
      this.id = id;
      this.email = email;
      this.name = name;
      this.purchaseAmount = purchaseAmount;
    }

    public Long getId()            { return id; }
    public String getEmail()       { return email; }
    public String getName()        { return name; }
    public int getPurchaseAmount() { return purchaseAmount; }
    public boolean isPremium()     { return premium; }

    // DB 저장
    public void save() {
      System.out.println("UPDATE users SET premium=" + premium + " WHERE id=" + id);
    }

    // DB 조회
    public static BadUserRecord findById(Long id) {
      return new BadUserRecord(id, "user@example.com", "홍길동", 1_200_000);
    }

    // 비즈니스 규칙이 Active Record 안에 섞여 있다
    public void upgradeToPremiumIfEligible() {
      if (purchaseAmount >= 1_000_000) {
        premium = true;
        save();
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: Active Record는 자료 구조 역할만 담당한다.
  // - DB 테이블에 가까운 데이터 구조로서 데이터 보관과 DB 접근만 책임진다.
  static class GoodUserRecord {
    private Long id;
    private String email;
    private String name;
    private int purchaseAmount;
    private boolean premium;

    GoodUserRecord(Long id, String email, String name, int purchaseAmount) {
      this.id = id;
      this.email = email;
      this.name = name;
      this.purchaseAmount = purchaseAmount;
    }

    public Long getId()            { return id; }
    public String getEmail()       { return email; }
    public String getName()        { return name; }
    public int getPurchaseAmount() { return purchaseAmount; }
    public boolean isPremium()     { return premium; }

    // 상태 변경은 허용하되, 판단 규칙은 외부에 위임한다.
    public void markAsPremium() { this.premium = true; }

    // DB 저장
    public void save() {
      System.out.println("UPDATE users SET premium=" + premium + " WHERE id=" + id);
    }

    // DB 조회
    public static GoodUserRecord findById(Long id) {
      return new GoodUserRecord(id, "user@example.com", "홍길동", 1_200_000);
    }
  }

  // Good: 비즈니스 규칙은 별도 객체가 담당한다.
  // - 승급 기준이 바뀌어도 UserRecord는 수정하지 않는다.
  static class PremiumPolicy {
    private static final int PREMIUM_PURCHASE_AMOUNT = 1_000_000;

    public boolean isEligible(GoodUserRecord user) {
      return user.getPurchaseAmount() >= PREMIUM_PURCHASE_AMOUNT;
    }
  }

  // Good: 흐름 제어는 서비스 객체가 담당한다.
  // - 조회, 정책 적용, 저장의 흐름을 조율한다.
  static class UserPremiumService {
    private final PremiumPolicy premiumPolicy;

    UserPremiumService(PremiumPolicy premiumPolicy) {
      this.premiumPolicy = premiumPolicy;
    }

    public void upgradeIfEligible(Long userId) {
      GoodUserRecord user = GoodUserRecord.findById(userId);

      if (premiumPolicy.isEligible(user)) {
        user.markAsPremium();
        user.save();
      }
    }
  }

  // 사용 예
  static class Application {
    void run() {
      UserPremiumService service = new UserPremiumService(new PremiumPolicy());
      service.upgradeIfEligible(1L);
    }
  }
}
