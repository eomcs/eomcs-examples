# 말장난을 하지 마라(Don’t Pun)

> **같은 단어를 다른 의미로 사용하지 마라**

- 하나의 단어는 하나의 개념만 가져야 한다
- 동일한 단어를 다른 동작에 사용하면 혼란이 발생한다

👉 "Pick One Word per Concept"과 반대 방향에서의 규칙  
👉 여기서는 **같은 단어를 다른 개념에 쓰는 것**을 금지

개발자가 흔히 하는 실수:

- 같은 이름을 재사용 (하지만 의미는 다름)
- 기존 개념과 다른 동작을 같은 단어로 표현

👉 결과:
- 코드가 “거짓말”을 하기 시작함
- API 사용자가 오해함

## 예제 1

```java
// Bad
class UserList {
    void add(User user) {
        users.add(user);
    }
}

class MathUtils {
    int add(int a, int b) {
        return a + b;
    }
}
```

- add라는 동일한 단어 사용
- 하지만 의미가 다름 → 인지 부하 증가
    - UserList.add(): 컬렉션에 요소 추가
    - MathUtils.add(): 숫자 덧셈

```java
// Bad
class SetUtils {
    Set<Integer> add(Set<Integer> set1, Set<Integer> set2) {
        // 두 집합을 합침
    }
}
```

- add → 보통 "하나 추가" 의미
- 실제 동작 → "두 집합 합집합 (union)"
- 단어와 실제 동작이 불일치

```java
// Good
class SetUtils {
    Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        // 합집합
    }
}
```

- union → 정확한 수학적 의미
- 오해 없음
- 도메인에 맞는 표현

## 예제 2

```java
// Bad
class AccountService {
    void update(Account account) { ... }
}

class CacheService {
    void update(String key, Object value) { ... }
}
```

- update라는 동일한 단어 사용
- 하지만 의미가 다름
    - AccountService: 데이터 수정
    - CacheService: 캐시 값 덮어쓰기
    - 사용자가 의미 차이를 추측해야 함

```java
// Good
class AccountService {
    void updateAccount(Account account) { ... }
}

class CacheService {
    void put(String key, Object value) { ... }
}
```

- updateAccount → 명확한 수정 의미
- put → 캐시에 값 저장
- 단어의 의미가 명확하게 구분됨

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드           | 문제    | 좋은 코드               |
| ------ | --------------- | ----- | ------------------- |
| add    | 컬렉션 추가 vs 수학 연산 | 의미 충돌 | add / sum 구분        |
| add    | 단일 추가 vs 합집합    | 오해 발생 | union               |
| update | 서로 다른 동작        | 의미 혼동 | updateAccount / put |

## 핵심 원칙

피해야 할 것:

- 같은 단어를 다른 의미로 사용
- 기존 의미를 왜곡하는 이름
- API에서 혼란을 유발하는 표현

지켜야 할 것:

- 단어 하나 = 의미 하나
- 도메인에 맞는 정확한 용어 사용
- 이미 널리 쓰이는 의미를 존중