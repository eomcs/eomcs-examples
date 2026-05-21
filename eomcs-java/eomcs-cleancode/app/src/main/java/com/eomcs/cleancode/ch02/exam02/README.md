# 그릇된 정보를 피하라(Avoid Disinformation)

> **이름은 사실을 말해야 하며, 오해를 유발하면 안 된다**

- 잘못된 이름은 버그보다 더 위험할 수 있다
- 개발자는 이름을 통해 “진실”을 전달해야 한다

## 예제 1: 자료 구조를 속이는 이름

```java
// Bad: 실제로 List가 아닌데 List라고 이름 붙이는 경우
Map<String, Account> accountList = new HashMap<>();
// → List는 프로그래머에게 특별한 의미를 가진다. 잘못된 정보를 준다.

// Good: 실제 타입을 반영한 이름
Map<String, Account> accountMap = new HashMap<>();
// 또는 더 간결하게
Map<String, Account> accounts = new HashMap<>();
```

## 예제 2: 서로 비슷한 이름 (혼동 유발)

```java
// Bad: 숫자 0, 1로 혼동되는 변수명
int a = l;
if (O == l)
    a = O1;
else
    l = 01;
// → l(소문자 L), O(대문자 O)는 1과 0으로 착각하기 쉽다.

// Good: 명확한 변수명 사용
int itemCount = 1;
int zeroValue = 0;
```

## 예제 3: 약어/축약어로 인한 오해

```java
// Bad: 약어/축약어로 인한 혼동
String hp;
// → hp가 home phone인지, horsepower인지, 혹은 다른 의미인지 알 수 없다.

// Good: 약어 제거
String homePhoneNumber;
```

## 예제 4: 유사한 이름 (미묘한 차이)

```java
// Bad: 미묘한 차이만 있는 유사한 이름
XYZControllerForEfficientHandlingOfStrings handler;
XYZControllerForEfficientStorageOfStrings storage;
// → 두 이름의 차이를 한눈에 파악하기 어렵다.

// Good: 차이가 명확하게 드러나는 이름
StringHandler stringHandler;
StringStorage stringStorage;
```

## 예제 5: 잘못된 개념 사용

```java
// Bad: 잘못된 개념 사용
Set<User> userList = new HashSet<>();
// → List와 Set은 서로 다른 개념이다. List는 순서가 있고 중복을 허용하지만, Set은 순서가 없고 중복을 허용하지 않는다.

// Good: 자료 구조에 맞는 이름 사용
Set<User> users = new HashSet<>();
```

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 이름            | 문제     | 좋은 이름           |
| ---- | ---------------- | ------ | --------------- |
| 자료구조 | accountList (배열) | 타입 오해  | accounts        |
| 문자   | l, O, I          | 시각적 혼동 | one, zero       |
| 약어   | hp               | 의미 불명확 | homePhoneNumber |
| 컬렉션  | userList (Set)   | 개념 불일치 | users           |

## 핵심 원칙

반드시 피해야 할 이름의 유형:

- 실제 타입과 다른 이름
- 혼동 가능한 문자 (l, O, I)
- 의미가 불명확한 약어
- 유사하지만 다른 이름
- 잘못된 도메인 개념 사용

좋은 이름 기준:

- 정확하다 (Accurate)
- 명확하다 (Clear)
- 일관된다 (Consistent)
- 오해의 여지가 없다 (Unambiguous)