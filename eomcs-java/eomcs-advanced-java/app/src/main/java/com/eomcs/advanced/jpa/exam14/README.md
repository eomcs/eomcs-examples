# exam14 - 자기 참조 연관관계

## 개념

### 자기 참조(Self-Join)란

같은 테이블의 다른 행을 FK로 참조하는 구조다. 카테고리·조직도·댓글 계층처럼 **재귀적 트리 구조**를 관계형 DB에 표현할 때 사용한다.

```
[DB - shop_category]           [Java - Category]
id | name       | parent_id
 1 | 전자제품   | NULL       →  parent = null          (루트)
 2 | 노트북     | 1          →  parent = 전자제품
 3 | 게이밍노트북| 2          →  parent = 노트북

[트리 구조]
전자제품 (루트, parent = null)
  └─ 노트북
       └─ 게이밍노트북
```

### JPA 자기 참조 매핑

엔티티가 자기 자신의 타입을 연관관계 필드로 갖는다.

```java
@ManyToOne  private Category parent;    // 부모 참조 (FK = parent_id)
@OneToMany  private List<Category> children;  // 자식 목록 (mappedBy = "parent")
```

- `parent` 필드가 연관관계 **주인** (FK `parent_id` 관리)
- `children` 필드는 **비주인** (읽기 전용, mappedBy)
- 루트 카테고리는 `parent = null`

### CascadeType.PERSIST

연관된 엔티티를 함께 저장하는 전파(Cascade) 옵션이다.

```
em.persist(루트);  →  루트, 자식, 손자 모두 INSERT
```

- `CascadeType.REMOVE`를 추가하면 부모 삭제 시 자식도 함께 삭제 — **의도치 않은 대량 삭제에 주의**
- 실무에서는 `PERSIST`만 단독으로 사용하는 경우가 많다

---

## 사용 테이블

```
shop_category
  id        NUMBER PK
  name      VARCHAR2
  parent_id NUMBER FK → shop_category.id  (자기 참조)
```

---

## 계층 구조 예시

```
전자제품 (루트, parent_id = NULL)
  └─ 노트북
       └─ 게이밍노트북

의류 (루트, parent_id = NULL)
  └─ 남성의류
```

---

## 핵심 포인트

| 구분 | 설명 |
|---|---|
| `@ManyToOne` parent | 부모 카테고리 참조. 루트는 `null` |
| `@OneToMany` children | 자식 카테고리 목록 (지연 로딩) |
| `CascadeType.PERSIST` | 부모 persist 시 자식도 자동 persist |
| 루트 조회 JPQL | `WHERE c.parent IS NULL` |
| 자식 조회 JPQL | `WHERE c.parent.id = :id` |

---

## 엔티티 클래스 구조

### Category (자기 참조 엔티티)

```java
@Entity
@Table(name = "shop_category")
public class Category {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Category> children = new ArrayList<>();
}
```

| 어노테이션 | 의미 |
|---|---|
| `@ManyToOne` parent | 부모 카테고리 참조. 같은 `shop_category` 테이블을 자기 참조(Self-Join). 루트는 `null` |
| `@JoinColumn(name = "parent_id")` | FK 컬럼명 지정. 연관관계 주인 |
| `@OneToMany(mappedBy = "parent")` | 자식 카테고리 목록. 비주인(읽기 전용) |
| `CascadeType.PERSIST` | 부모를 `persist`할 때 `children` 목록도 함께 자동 저장 |

- `isRoot()` 메서드: `parent == null`이면 루트 카테고리
- `addChild()` 편의 메서드: 자식 추가 시 `child.parent`도 함께 설정하여 양방향 동기화

```
테이블 구조:
shop_category
  id        PK
  name
  parent_id FK → shop_category.id  (자기 참조)
```

---

## App - 자기 참조 저장/탐색/JPQL 전체 흐름

계층 구조 저장, 트리 탐색, 부모 체인 조회, JPQL 직계 자식 조회 순서로 자기 참조 동작을 확인한다.

```java
// 1. 루트 카테고리 조회 (parent IS NULL)
List<Category> roots = em.createQuery(
    "SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.id",
    Category.class).getResultList();

// 2. 부모 체인 탐색 (지연 로딩으로 조상까지 순회)
Category gaming = em.find(Category.class, 3L);  // 게이밍노트북
gaming.getParent().getName();                    // "노트북"
gaming.getParent().getParent().getName();         // "전자제품"

// 3. CascadeType.PERSIST: 루트만 persist해도 자식/손자까지 함께 저장
Category sports  = new Category("스포츠");
Category outdoor = new Category("아웃도어");
Category hiking  = new Category("등산");

sports.addChild(outdoor);   // outdoor.parent = sports
outdoor.addChild(hiking);   // hiking.parent = outdoor

em.persist(sports);  // sports, outdoor, hiking 모두 INSERT
tx.commit();

// 4. JPQL: 특정 부모의 직계 자식만 조회
List<Category> children = em.createQuery(
    "SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.id",
    Category.class)
    .setParameter("parentId", 1L)
    .getResultList();
```

---

## 실행 방법

```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam14.App
```
