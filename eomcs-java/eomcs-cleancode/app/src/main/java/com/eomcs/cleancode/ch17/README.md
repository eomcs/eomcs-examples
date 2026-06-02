# Chapter 17: 냄새와 휴리스틱 (Smells and Heuristics)

이번 장에서는 코드에서 냄새를 감지하는 방법과 그 냄새가 무엇을 의미하는지 알아본다.

> **휴리스틱(Heuristic)이란 “항상 맞는 규칙은 아니지만, 좋은 결정을 내리도록 도와주는 경험 기반의 판단 기준”이다**

## 주석 (Comments)

### C1: 부적절한 정보 (Inappropriate Information)

> 코드와 관련 없는 정보를 주석에 넣지 마라

```java
// Bad:
// 작성자: 홍길동
// 작성일: 2021-03-01
// 마지막 수정: 2022-07-15
public class UserService {
}
```

- 코드와 무관한 메타 정보
- Git이 이미 다 관리함
- 코드 가독성만 떨어뜨림

```java
//Bad:
// 고객이 요청해서 급하게 만든 기능
public void process() { }
```

- 왜 중요한지 알 수 없음
- 유지보수에 도움 안 됨

**✅ 좋은 방향:**

이런 정보는

- Git commit
- 이슈 트래커

에 있어야 한다

### C2: 쓸모 없는 주석 (Obsolete Comment)

> 코드가 바뀌었는데 주석이 그대로인 경우

```java
// Bad:
// 10% 할인 적용
price = price * 0.8;
```


- 주석: 10%
- 실제 코드: 20%
- 주석이 거짓말함

```java
// Bad:
// 최대 100개까지 처리
for (int i = 0; i < 200; i++) {
```

- 코드와 주석 불일치

**핵심 위험:**

- 개발자는 코드를 믿지 못함
- 디버깅 비용 증가

### C3: 중복된 주석 (Redundant Comment)

> 코드만 읽어도 알 수 있는 내용을 주석으로 반복하지 마라

```java
// Bad:
// 사용자 이름을 반환한다
return user.getName();
```

- 주석이 아무 정보도 추가하지 않음

```java
// i를 1 증가시킨다
i++;
```

- 완전 불필요

```java
// Good:
return user.getName();
```

- 코드 자체가 설명이 되어야 한다

### C4: 성의 없는 주석 (Poorly Written Comment)

> 문법, 표현, 의미가 엉망인 주석

```java
// Bad:
// 이거 뭐하는건지 모르겠는데 일단 돌아감
public void process() { }
```

- 신뢰성 없음
- 유지보수 불가능

```java
// Bad:
// value check
if (value > 0)
```

- 의미 없음
- 너무 모호함

**핵심 문제:**

- 부정확
- 모호
- 불완전

코드 이해 방해

### C5: 주석 처리된 코드 (Commented-Out Code)

> 죽은 코드를 주석으로 남겨두지 마라

```java
// Bad:
// int result = oldCalculation();
// System.out.println(result);
```

- 왜 남겨놨는지 모름
- 읽는 사람 혼란
- 코드 clutter 증가

**✅ 좋은 방향:**

- "혹시 나중에 필요할까봐..." 라는 생각으로 남겨두지 말라. 
- 삭제하라. 
- 필요하면 Git에서 복구해라.

## 환경 (Environment)

### E1: 여러 단계로 빌드해야 한다 (Build Requires More Than One Step)

> 프로젝트를 빌드하려면 여러 수동 단계를 거쳐야 한다면 나쁜 냄새다

**나쁜 예:**

1. 라이브러리 A를 수동으로 복사
2. 환경 변수 설정
3. 특정 순서로 스크립트 실행
4. 그 다음 컴파일

**실제 상황:**

```bash
cp lib/special.jar /usr/local/lib
export CONFIG_PATH=/etc/myapp
./prepare.sh
./build.sh
```

- 누구는 되고, 누구는 안 됨
- “내 컴퓨터에서는 되는데요?” 발생
- 빌드 실패 원인 파악 어려움

```java
// Bad:
// 코드가 아니라 환경에 의존
String path = System.getenv("CONFIG_PATH");
```

- 환경이 다르면 빌드 실패
- 재현 어려움

**좋은 예:**

```bash
./gradlew build

# 또는

mvn clean install
```

- 한 번에 실행
- 자동화됨
- 환경 독립적
- **빌드는 "one-step"이어야 한다**

### E2: 여러 단계로 테스트해야 한다 (Tests Require More Than One Step)

> 테스트를 실행하려면 여러 준비 작업이 필요하면 나쁜 냄새다

**나쁜 예:**

1. DB 서버 수동 실행
2. 테스트 데이터 수동 삽입
3. 환경 변수 설정
4. 테스트 실행

```bash
docker run postgres
psql -f init.sql
export TEST_ENV=local
./run-tests.sh
```

- 테스트 재현 어려움
- 자동화 불가능
- CI/CD에서 실패

```java
// Bad:
@Test
void testUser() {
    // DB에 데이터가 이미 있어야 성공
    User user = userRepository.findById(1L);
    assertNotNull(user);
}
```

- 테스트가 외부 상태에 의존
- 독립적이지 않음

```java
// Good:
@Test
void testUser() {
    User user = new User(1L, "kim");
    userRepository.save(user);

    User result = userRepository.findById(1L);

    assertNotNull(result);
}
```

또는

```java
// Good:
@Test
void testUser() {
    TestDatabase db = new InMemoryDatabase();

    UserRepository repo = new UserRepository(db);

    repo.save(new User(1L, "kim"));

    assertNotNull(repo.findById(1L));
}
```

- 테스트 자체가 모든 준비를 한다
- 외부 환경 필요 없음
- 언제 어디서든 실행 가능
- **테스트는 "one-step"이어야 한다**

## 함수 (Functions)

### F1: 너무 많은 인수 (Too Many Arguments)

> 함수 인자가 많다는 것은 책임이 많다는 신호다

```java
// Bad:
public void createUser(
    String name,
    int age,
    String email,
    String address,
    String phone
) {
}
```

- 기억하기 어려움
- 호출하기 어려움
- 순서 실수 발생 가능
- SRP 위반 가능성 높음

```java
// Bad:
createUser("kim", 20, "a@a.com", "seoul", "010");
```

- 무엇이 무엇인지 명확하지 않다

```java
// Good:
public void createUser(User user) {
}
```

```java
// 사용 예:
User user = new User("kim", 20, "a@a.com", "seoul", "010");
createUser(user);
```

- **인자가 많으면 → 객체로 묶어라**

### F2: 출력 인수 (Output Arguments)

> 함수가 값을 반환하지 않고, 인자를 수정하는 방식

```java
// Bad:
public void appendFooter(StringBuilder report) {
    report.append("footer");
}
```

- 함수가 외부 상태를 변경
- side effect 발생
- 함수 사용이 직관적이지 않음

```java
// Bad:
public void calculate(int a, int b, Result result) {
    result.value = a + b;
}
```

- 함수가 결과를 “밀어 넣음”
- 객체 상태 변경

```java
// Good:
public String appendFooter(String report) {
    return report + "footer";
}
```

```java
// Good:
public int calculate(int a, int b) {
    return a + b;
}
```

- **출력은 return으로 하라**

### F3: 플래그 인수 (Flag Arguments)

> boolean 인자로 함수 동작을 바꾸는 것

```java
// Bad:
public void render(boolean isAdmin) {
    if (isAdmin) {
        renderAdmin();
    } else {
        renderUser();
    }
}
```

- 함수가 두 가지 일을 한다
- 조건 분기 발생
- SRP 위반

```java
// 호출도 문제
render(true);
```

- 의미 불명확

```java
// Good:
public void renderAdmin() { }

public void renderUser() { }
```

- flag = 숨겨진 if문
- 함수가 여러 책임을 갖고 있다는 신호
- **플래그 인수는 함수 분리의 신호다**

### F4: 죽은 함수 (Dead Function)

> 사용되지 않는 함수

```java
// Bad
public void oldProcess() {
    // 예전에 쓰던 코드
}
```

- 아무도 호출하지 않음
- 유지보수 혼란
- **흔한 핑계: "혹시 나중에 필요할까봐..."**

## 일반 (General)

### G1: 한 소스 파일에 여러 언어를 사용한다 (Multiple Languages in One Source File)

```java
// Bad:
// Java + SQL + HTML 혼합
String query = "SELECT * FROM users";
String html = "<div>" + user.name + "</div>";
```

- 관심사 섞임
- 유지보수 어려움

**좋은 방향:**

- SQL → Repository
- HTML → Template
- Java → Service

### G2: 당연한 동작을 구현하지 않는다 (Obvious Behavior Is Unimplemented)

```java
// Bad:
Stack stack = new Stack();
stack.pop(); // 비어있을 때 처리 없음
```

- 기본 동작 누락

### G3: 경계를 올바로 처리하지 않는다 (Incorrect Behavior at the Boundaries)

```java
// Bad:
int[] arr = new int[10];
arr[10] = 1; // 오류
```

- boundary 체크 없음

### G4: 안전 절차 무시 (Overridden Safeties)

```java
// Bad:
FileInputStream in = new FileInputStream(file);
// close 안 함
```

- 자원 누수

### G5: 중복 (Duplication)

```java
// Bad:
if (user.isAdmin()) log();
...
if (user.isAdmin()) log();
```

- DRY 위반

### G6: 추상화 수준이 올바르지 못하다 (Code at Wrong Level of Abstraction)

```java
// Bad:
public void process() {
    connectDB();   // low-level
    calculate();   // high-level
}
```

- 추상화 혼합

### G7: 기초 클래스가 파생 클래스에 의존한다 (Base Classes Depending on Their Derivatives)

```java
// Bad:
class Base {
    Derived d;
}
```

- 상속 방향 깨짐

### G8: 과도한 정보 (Too Much Information)

```java
// Bad:
class User {
    String name;
    String address;
    String phone;
    String creditCard;
}
```

- 필요 이상 노출

### G9: 죽은 코드 (Dead Code)

```java
// Bad:
public void oldMethod() { }
```

- 사용 안 됨 → 제거

### G10: 수직 분리 (Vertical Separation)

```java
// Bad:
methodA()

// 200줄 뒤

helperForMethodA()getUserName()
```

- 관련 코드 분리됨

### G11: 일관성 부족 (Inconsistency)

```java
// Bad:
getUserName()
fetch_user_name()
```

- 스타일 혼란

### G12: 잡동사니 (Clutter)

- 사용 안 하는 변수
- 주석 코드
- 빈 catch

### G13: 인위적 결합 (Artificial Coupling)

```java
// Bad:
class A {
    B b; // 이유 없음
}
```

- 억지 의존

### G14: 기능 욕심 (Feature Envy)

```java
// Bad:
a.getB().getC().doSomething();
```

- 다른 객체에 과도 의존

### G15: 선택자 인수 (Selector Arguments)

```java
// Bad:
render(true);
```

- 숨겨진 분기

### G16: 모호한 의도 (Obscured Intent)

```java
// Bad:
if (x == 4)
```

- 의미 없음

### G17: 잘못 지운 책임 (Misplaced Responsibility)

- 클래스가 엉뚱한 일 수행

### G18: 부적절한 static (Inappropriate Static)

```java
// Bad:
static UserService service;
```

- 테스트 어려움
- 결합 증가

### G19: 서술적 변수 (Use Explanatory Variables)

```java
// Good:
int elapsedTimeInDays;
```

- 의도를 드러내라

### G20: 이름과 기능이 일치하는 함수 (Function Names Should Say What They Do)

```java
// Good:
saveUser()
```

### G21: 알고리즘을 이해하라 (Understand the Algorithm)

- 복붙 코드
- 이해 없이 수정

### G22: 논리적 의존성은 물리적으로 드러내라 (Make Logical Dependencies Physical)

```java
// Good:
if (user.isValid())
```

- 검증 로직 내부로 이동

### G23: if/else 혹은 switch/case 문보다 다형성을 사용하라 (Prefer Polymorphism to If/Else or Switch/Case)

```java
// Bad:
if (type == A) ...
else if (type == B) ...
```

- 클래스 분리

### G24: 표준 표기법을 따르라 (Follow Standard Conventions)

- 이름 관례 따르기 (naming convention)
- 형식 맞추기 (formatting)

### G25: 매직 숫자는 명명된 상수로 교체하라 (Replace Magic Numbers with Named Constants)

```java
// Bad:
if (age > 18)
```

```java
// Good:
if (age > ADULT_AGE)
```

### G26: 정확하라 (Be Precise)

- float 비교 문제
- null 처리

### G27: 관례보다 구조를 사용하라 (Structure over Convention)

- 관례에 의존 ❌
- 코드 구조로 표현 ✔️

### G28: 조건을 캡슐화하라 (Encapsulate Conditionals)

```java
// Bad:
if (user.age > 18 && user.active)
```

```java
// Good:
if (user.isAdultAndActive())
```

### G29: 부정 조건은 피하라 (Avoid Negative Conditionals)

```java
// Bad:
if (!isNotReady)
```

- 긍정형으로 변경하라

### G30: 함수는 한 가지만 해야 한다 (Functions Should Do One Thing)

```java
// Good:
process() {
  validate();
  save();
  sendEmail();
}
```

- 분리하라

### G31: 숨겨진 시간적인 결합 (Hidden Temporal Couplings)

```java
// Bad:
init();
run();
```

- 순서 강제 필요

### G32: 일관선을 유지하라 (Don’t Be Arbitrary)

- 규칙 없이 작성

### G33: 경계 조건을 캡슐화하라 (Encapsulate Boundary Conditions)

```java
// Bad:
if (i == 0 || i == max)
```

- 함수로 분리

### G34: 함수는 추상화 수준을 한 단계만 내려가야 한다 (Functions Should Descend Only One Level of Abstraction)

```java
// Bad:
process() {
  validateUser();
  jdbc.execute(); // 낮은 수준 섞임
}
```

- 메서드 안에 여러 수준이 섞이지 않도록 하라

### G35: 설정 정보는 최상위 단계에 둬라 (Keep Configurable Data at High Levels)

```java
// Bad:
int timeout = 3000;
```

- config로 이동하라

### G36: 추이적 탐색을 피하라 (Avoid Transitive Navigation)

```java
// Bad:
a.getB().getC().getD()
```

- Law of Demeter 위반


## 자바 (Java)

### J1: 긴 import 목록을 피하고 와일드카드를 사용하라 (Avoid Long Import Lists by Using Wildcards)

> import가 너무 많으면 *를 사용해라 (단, 합리적인 범위에서)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
```

- import 라인 과도하게 증가
- 가독성 저하
- 파일 상단 clutter

```java
import java.util.*;
```

- 코드 간결
- 유지보수 쉬움

**주의:**

- 무조건 * 쓰라는 뜻 ❌
- 적절한 범위에서 사용 ✔️
- import는 가독성을 위한 것
- 불필요하게 길어지면 줄여라

```java
// 피해야 할 예:
import java.awt.*;
import java.util.*;
```

- 이름 충돌 (List 등)

### J2: 상수는 상속하지 않는다 (Don’t Inherit Constants)

> 상수를 쓰려고 상속을 사용하는 것은 잘못된 설계다

```java
// Bad:
public class Constants {
    public static final int MAX = 100;
}

public class OrderService extends Constants {
    public void process() {
        if (count > MAX) {
        }
    }
}
```

- “상수를 쓰려고” 상속함
- is-a 관계 깨짐
- **OrderService is-a Constants** ❌
- 불필요한 결합 발생
- 상속 구조 오염
- 설계 왜곡

```java
// Good:
public class Constants {
    public static final int MAX = 100;
}
```

```java
// 사용 예:
if (count > Constants.MAX) {
}
```

또는

```java
public class OrderService {
    private static final int MAX = 100;
}
```

- 상속은 “행위 공유”를 위한 것
- 상수 공유를 위한 것이 아니다

### J3: 상수 대 열거형 (Constants versus Enums)

> int 상수 대신 enum을 사용하라

```java
// Bad:
class Status {
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;
    public static final int BLOCKED = 3;
}

class User {
    int status;
}
```

```java
// 사용 예:
user.status = Status.ACTIVE; // 가능
user.status = 1;             // 가능
```

- 타입 안전성 없음
- 잘못된 값 가능

```java
// Good:
enum Status {
    ACTIVE,
    INACTIVE,
    BLOCKED
}

class User {
    Status status;
}
```

```java
// 타입 안정성:
user.status = Status.ACTIVE; // 가능
user.status = 1;             // 컴파일 오류
```

```java
// 기능 추가 가능:
public enum Status {
    ACTIVE {
        @Override
        public boolean isActive() {
            return true;
        }
    },
    INACTIVE {
        @Override
        public boolean isActive() {
            return false;
        }
    };

    public abstract boolean isActive();
}
```

## 이름 (Names)

### N1: 서술적인 이름을 사용하라 (Choose Descriptive Names)

> 짧은 이름보다 의미 있는 이름이 더 중요하다

```java
// Bad:
int d;
```

- d = date? day? distance?

```java
// Good:
int elapsedTimeInDays;
```

- 의미가 명확
- 주석 불필요
- **이름은 “무엇인지”가 아니라 “무엇을 의미하는지”를 말해야 한다**

### N2: 적절한 추상화 수준에서 이름을 선택하라 (Choose Names at the Appropriate Level of Abstraction)

> 함수/클래스의 추상화 수준과 이름이 일치해야 한다

```java
// Bad:
public void processUserDataAndWriteToFile() {
}
```

- 너무 구체적 (low-level)
- 역할이 섞임

```java
// Bad:
public void process() {
}
```

- 너무 추상적
- 의미 없음

```java
// Good:
public void saveUser() {
}
```

- 이름 = 해당 레벨의 개념

### N3: 가능하다면 표준 명명법을 사용하라 (Use Standard Nomenclature Where Possible)

> 관례를 따르면 이해 속도가 빨라진다

| 역할      | 표준 이름  |
| ------- | ------ |
| 가져오기    | get    |
| 설정      | set    |
| boolean | is     |
| 생성      | create |

```java
// Bad:
fetchUserName()
```

```java
// Good:
getUserName()
```

- 표준 이름 = 팀 전체의 언어

### N4: 명확한 이름 (Unambiguous Names)

> 이름이 하나의 의미만 가져야 한다

```java
// Bad:
List users;
```

- 어떤 users?
- active? deleted?

```java
// Bad:
filter();
```

- 무엇을 필터링?

```java
// Good:
List<ActiveUser> activeUsers;
```

- 이름은 질문을 남기면 안 된다

### N5: 긴 범위는 긴 이름을 사용하라 (Use Long Names for Long Scopes)

> 변수 범위가 넓을수록 이름은 더 길어야 한다

```java
// Bad:
int i;
for (i = 0; i < users.size(); i++) {
```

- 긴 범위에서 의미 없음

```java
// Good:
int userIndex;
```

```java
// Good:
for (int i = 0; i < 10; i++) { }
```

- 짧은 범위 → 짧은 이름 OK
- **범위가 길수록 → 이름도 길어야 한다**

### N6: 인코딩을 피하라 (Avoid Encodings)

> 타입이나 의미를 이름에 암호처럼 넣지 마라

```java
// Bad:
String strName;
int iCount;
boolean bFlag;
```

- 헝가리안 표기법
- IDE가 이미 타입 제공

```java
// Bad:
m_userList;
```

- 의미 없는 접두사

```java
// Good:
String name;
int count;
boolean active;
```

- 이름은 “사람을 위한 것”

### N7: 이름으로 부수 효과를 설명하라 (Names Should Describe Side-Effects)

> 함수가 상태를 바꾸면 이름에 반드시 드러내라

```java
// Bad:
public void process() {
    user.setActive(true);
}
```

- 상태 변경 숨김

```java
// Bad:
public User getUser() {
    logAccess(); // side effect
    return user;
}
```

- get인데 side-effect 있음 ❌

```java
// Good:
public void activateUser() {
}

또는

public User getUserAndLogAccess() {
}
```

- 이름 = 실제 동작

## 테스트 (Tests)
### T1: 불충분한 테스트 (Insufficient Tests)

> 테스트가 너무 적다

```java
@Test
void testAdd() {
    assertEquals(3, add(1, 2));
}
```

- 하나만 테스트
- 다양한 경우 없음

```java
// Good:
@Test
void testAddVariousCases() {
    assertEquals(3, add(1, 2));
    assertEquals(0, add(-1, 1));
    assertEquals(-3, add(-1, -2));
}
```

- 테스트는 “충분히 많이” 작성해야 한다

### T2: 커버리지 도구를 사용하라! (Use a Coverage Tool!)

> 테스트가 실제로 얼마나 코드를 실행하는지 확인하라

**나쁜 방법:**

- 테스트는 많지만 실제 코드 일부만 실행됨

**좋은 방법:**

- 테스트 커버리지 도구 사용
  - JaCoCo
  - IntelliJ Coverage
- 어떤 코드가 테스트되고 있는지 시각적으로 보여줌
  - 어떤 코드가 실행됐는가
  - 어떤 코드가 안 됐는가

### T3: 사소한 테스트를 건너뛰지 마라 (Don’t Skip Trivial Tests)

> “이건 당연하니까 테스트 안 해도 되겠지?” → 위험

```java
// Bad:
// getter라서 테스트 안 함
public int getAge() { return age; }
```

- 나중에 로직 추가되면 깨짐

```java
// Good:
@Test
void getAgeReturnsCorrectValue() {
    assertEquals(20, user.getAge());
}
```

- 사소한 테스트가 나중에 중요한 버그를 잡는다

### T4: 무시된 테스트는 모호성을 뜻한다 (An Ignored Test Is a Question about an Ambiguity)

```java
// Bad:
@Disabled
@Test
void testDiscount() {
}
```

- 왜 disabled?
- 요구사항 불명확
- ignored test = “이거 어떻게 해야 하지?”
- **무시된 테스트는 반드시 해결해야 한다**

### T5: 경계 조건을 테스트하라 (Test Boundary Conditions)

```java
// Bad:
assertTrue(isAdult(20));
```

```java
// Good:
assertTrue(isAdult(18));
assertFalse(isAdult(17));
```

- 버그는 경계에서 발생한다

### T6: 버그 주변은 철저히 테스트하라 (Exhaustively Test Near Bugs)

```java
// 다음 코드에서 버그 발생
if (value > 100)
```

```java
// 좋은 테스트:
assertFalse(check(99));
assertFalse(check(100));
assertTrue(check(101));
```

- 버그 발생 지점 주변을 집중 공격하라

### T7: 실패 패턴을 살펴라 (Patterns of Failure Are Revealing)

**나쁜 접근:**

- 테스트 하나 실패 → 그냥 고침

**좋은 접근:**

- 비슷한 테스트들이 같이 실패함 → 공통 원인 분석
- **실패는 패턴을 가진다**

### T8: 테스트 커버리지 패턴을 살펴라 (Test Coverage Patterns Can Be Revealing)

**나쁜 예:**

- Service는 100% 테스트됨
- 하지만 Domain은 0%
- **중요한 로직이 테스트 안 됨**
  - **“어디를 테스트 안 했는가”가 더 중요하다**

### T9: 테스트는 빨라야 한다 (Tests Should Be Fast)

```java
// Bad:
@Test
void testDB() {
    Thread.sleep(5000);
}
```

- 테스트 느림
- 실행 안 하게 됨

```java
// Good:
@Test
void testWithInMemoryDB() {
}
```

- 느린 테스트 = 실행되지 않는 테스트