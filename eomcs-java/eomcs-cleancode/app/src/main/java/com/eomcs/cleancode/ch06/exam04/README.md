# 자료 전달 객체 (Data Transfer Objects)

> **DTO(Data Transfer Object)는 데이터를 전달하기 위한 단순한 자료 구조다.**

DTO는 보통 다음 특징을 가진다.

- 필드 중심
- getter/setter 중심
- 비즈니스 로직 없음
- 계층 간 데이터 전달 목적

## 예제 1: DTO

```java
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zip;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    // setter 생략
}
```

- 이런 구조는 객체라기보다 자료 구조에 가깝다.

## 예제 2: Active Record

> **Active Record는 DTO의 특수한 형태다.**

- public 필드 또는 bean-style getter/setter를 가진 자료 구조
- save, find 같은 탐색/저장 메서드를 가진다
- 보통 데이터베이스 테이블을 직접 반영한다

```java
public class UserRecord {
    private Long id;
    private String email;
    private String name;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void save() {
        // INSERT or UPDATE users
    }

    public static UserRecord findById(Long id) {
        // SELECT * FROM users WHERE id = ?
        return null;
    }
}
```

```java
// 사용 예
userRecord.save();
UserRecord userRecord = UserRecord.findById(1L);
```

Active Record의 역할:

- 데이터 보관
- DB 저장
- DB 조회

정도만 담당한다.

## 예제 3: Active Record와 비즈니스 규칙

```java
// Bad: Active Record에 비즈니스 규칙을 넣은 경우
public class UserRecord {
    private Long id;
    private String email;
    private String name;
    private int purchaseAmount;
    private boolean premium;

    public void save() {
        // DB 저장
    }

    public static UserRecord findById(Long id) {
        // DB 조회
        return null;
    }

    public void upgradeToPremiumIfEligible() {
        if (purchaseAmount >= 1_000_000) {
            premium = true;
            save();
        }
    }
}
```

위 코드는 Active Record에 다음 책임이 섞여 있다.

- 데이터 보관
- DB 저장/조회
- 프리미엄 회원 승급 규칙
- 상태 변경 정책

즉, 자료 구조와 객체가 섞인 Hybrid가 된다.

> Active Record에 비즈니스 규칙 메서드를 넣으면 자료 구조와 객체 사이의 어색한 혼합 구조가 된다.
    
```java
// Good: Active Record와 비즈니스 규칙을 분리한 경우
public class UserRecord {
    private Long id;
    private String email;
    private String name;
    private int purchaseAmount;
    private boolean premium;

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void markAsPremium() {
        this.premium = true;
    }

    public void save() {
        // DB 저장
    }

    public static UserRecord findById(Long id) {
        // DB 조회
        return null;
    }
}
```

비즈니스 규칙은 별도 객체로 분리:

```java
// 비즈니스 규칙 객체
public class PremiumPolicy {
    private static final int PREMIUM_PURCHASE_AMOUNT = 1_000_000;

    public boolean isEligible(UserRecord user) {
        return user.getPurchaseAmount() >= PREMIUM_PURCHASE_AMOUNT;
    }
}
```

```java
// 서비스 객체
public class UserPremiumService {
    private final PremiumPolicy premiumPolicy;

    public UserPremiumService(PremiumPolicy premiumPolicy) {
        this.premiumPolicy = premiumPolicy;
    }

    public void upgradeIfEligible(Long userId) {
        UserRecord user = UserRecord.findById(userId);

        if (premiumPolicy.isEligible(user)) {
            user.markAsPremium();
            user.save();
        }
    }
}
```

- 역할이 분리되었다.

| 클래스                  | 책임                 |
| -------------------- | ------------------ |
| `UserRecord`         | DB 테이블에 가까운 데이터 구조 |
| `PremiumPolicy`      | 프리미엄 승급 규칙         |
| `UserPremiumService` | 조회, 정책 적용, 저장 흐름   |

## 나쁜 코드 vs 좋은 코드

| 구분      | 나쁜 Active Record | 좋은 Active Record  |
| ------- | ---------------- | ----------------- |
| 데이터 보관  | 있음               | 있음                |
| DB 접근   | 있음               | 있음                |
| 비즈니스 규칙 | 내부에 섞임           | 별도 객체로 분리         |
| 구조      | Hybrid           | 자료 구조 + 도메인 객체 분리 |
| 변경 영향   | 큼                | 작음                |


## 핵심 원칙

> Active Record는 객체가 아니라 자료 구조로 다루고, 비즈니스 규칙은 별도의 객체에 둬야 한다.

피해야 할 것:

- Active Record에 복잡한 비즈니스 규칙 넣기
- DB 구조와 도메인 규칙을 한 클래스에 섞기
- getter/setter가 많은 클래스를 객체처럼 착각하기

지켜야 할 것:

- Active Record는 자료 구조로 취급
- 비즈니스 규칙은 별도 객체에 둠
- 도메인 로직은 데이터를 숨기는 객체가 담당
- Active Record는 필요한 경우 그 객체 내부에서 사용