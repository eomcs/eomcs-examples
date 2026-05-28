# 경계 살피고 익히기 (Exploring and Learning Boundaries)

> **외부 라이브러리를 바로 운영 코드에 붙이지 말고, 먼저 작은 테스트로 사용법을 탐색하라.**

- 외부 라이브러리를 배우는 일과 운영 코드에 통합하는 일을 동시에 하면 어렵다. 
- 그래서 운영 코드에서 실험하지 말고, 외부 API를 우리가 사용할 방식대로 호출하는 학습 테스트(Learning Test)를 작성하라.

## 학습 테스트를 위한 준비

### 의존 라이브러리 추가

libs.versions.toml:

```toml
[versions]
junit-jupiter = "6.1.0"
jackson = "2.21.3"

[libraries]
junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit-jupiter" }
jackson-databind = { module = "com.fasterxml.jackson.core:jackson-databind", version.ref = "jackson" }
```

build.gradle:

```groovy
dependencies {
    testImplementation libs.junit.jupiter
    testImplementation libs.jackson.databind

    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named('test') {
    useJUnitPlatform()
}
```

### 학습 테스트 실행

```zsh
./gradlew test --rerun-tasks --tests "com.eomcs.cleancode.ch08.exam02.*" 2>&1
```

### 학습 테스트 소스 코드 위치

`app/src/test/java/com/eomcs/cleancode/ch08/exam02`

## 예제 1

```java
// Bad - 함수 정의
public class OrderService {
    private final Logger logger = Logger.getLogger("OrderService");

    public void createOrder(Order order) {
        logger.info("주문 생성 시작");

        orderRepository.save(order);

        logger.info("주문 생성 완료");
    }
}
```

```java
// Bad - 함수 사용
orderService.createOrder(order);
```

- 운영 코드에서 바로 외부 라이브러리를 실험한다.
- Logger 설정이 제대로 되었는지 모른 채 사용한다.
- 문제가 발생하면 우리 코드 문제인지 log4j 문제인지 구분하기 어렵다.
- 라이브러리 사용법이 서비스 코드 안에 섞인다.

```java
// Good - 학습 테스트
public class Log4jLearningTest {
    private Logger logger;

    @Before
    public void setUp() {
        logger = Logger.getLogger("testLogger");
        logger.removeAllAppenders();
    }

    @Test
    public void consoleAppender로_로그를_출력한다() {
        logger.addAppender(new ConsoleAppender(
            new PatternLayout("%p %t %m%n"),
            ConsoleAppender.SYSTEM_OUT
        ));

        logger.info("hello");
    }
}
```

- 외부 API 사용법을 테스트 코드에서 먼저 확인한다.
- 운영 코드는 log4j 세부 설정을 몰라도 된다.
- 라이브러리 사용 지식이 테스트로 기록된다.
- 문제가 생기면 경계 테스트에서 먼저 확인할 수 있다.

## 예제 2

```java
// Bad - 함수 정의
public class PaymentService {
    private final ExternalPaymentClient client;

    public void pay(Order order) {
        PaymentRequest request = new PaymentRequest();
        request.setAmount(order.getTotalPrice());
        request.setCustomerId(order.getCustomerId());

        PaymentResponse response = client.execute(request);

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getMessage());
        }
    }
}
```

```java
// Bad - 함수 사용
paymentService.pay(order);
```

- 외부 결제 API 사용법을 운영 코드에서 바로 검증한다.
- 요청 필드 중 무엇이 필수인지 테스트로 확인되어 있지 않다.
- 응답이 어떤 형태로 오는지 서비스 코드가 직접 의존한다.
- 외부 API 변경 시 서비스 코드가 흔들린다.

```java
// Good - 학습 테스트
public class PaymentClientLearningTest {

    @Test
    public void 결제_승인_요청은_금액과_고객ID가_필요하다() {
        ExternalPaymentClient client = new ExternalPaymentClient();

        PaymentRequest request = new PaymentRequest();
        request.setAmount(10_000);
        request.setCustomerId("C-100");

        PaymentResponse response = client.execute(request);

        assertTrue(response.isSuccess());
    }

    @Test
    public void 고객ID가_없으면_결제_승인이_실패한다() {
        ExternalPaymentClient client = new ExternalPaymentClient();

        PaymentRequest request = new PaymentRequest();
        request.setAmount(10_000);

        PaymentResponse response = client.execute(request);

        assertFalse(response.isSuccess());
    }
}
```

- 외부 API의 동작을 작은 테스트로 먼저 배운다.
- 외부 API 지식이 학습 테스트에 갇힌다.
- 라이브러리 변경 시 학습 테스트가 경고 역할을 한다.

## 예제 3

```java
// Bad - 함수 정의
public class JsonUserReader {
    private final ObjectMapper mapper = new ObjectMapper();

    public User read(String json) throws JsonProcessingException {
        return mapper.readValue(json, User.class);
    }
}
```

```java
// Bad - 함수 사용
User user = jsonUserReader.read("""
    {"id":1,"name":"Kim"}
""");
```

- Jackson이 어떤 필드 매핑 규칙을 갖는지 확인하지 않았다.
- 없는 필드, 알 수 없는 필드, 날짜 형식 처리 방식을 모른 채 사용한다.
- 운영 코드에서 JSON 라이브러리 동작을 추측한다.

```java
// Good - 학습 테스트
public class JacksonLearningTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void json을_User로_변환한다() throws Exception {
        String json = """
            {"id":1,"name":"Kim"}
        """;

        User user = mapper.readValue(json, User.class);

        assertEquals(1L, user.getId());
        assertEquals("Kim", user.getName());
    }

    @Test
    public void 알수없는_필드가_있으면_실패하는지_확인한다() {
        String json = """
            {"id":1,"name":"Kim","unknown":"value"}
        """;

        assertThrows(JsonProcessingException.class, () -> {
            mapper.readValue(json, User.class);
        });
    }
}
```

- 라이브러리 동작을 테스트로 확인한다.
- JSON 처리 규칙이 명확해진다.
- 라이브러리 업그레이드 시 기존 기대 동작을 검증할 수 있다.

## 나쁜 코드 vs 좋은 코드

| 구분        | 나쁜 코드            | 좋은 코드      |
| --------- | ---------------- | ---------- |
| 학습 위치     | 운영 코드            | 학습 테스트     |
| 외부 API 이해 | 추측               | 테스트로 검증    |
| 디버깅       | 우리 코드와 외부 코드가 섞임 | 경계에서 분리    |
| 변경 대응     | 변경 후 장애로 발견      | 테스트 실패로 발견 |
| 운영 코드     | 외부 API 세부사항 노출   | 래퍼/어댑터로 격리 |

## 핵심 원칙

피해야 할 것:

- 외부 라이브러리를 운영 코드에서 바로 실험하는 것
- 문서만 읽고 동작을 추측하는 것
- 외부 API 사용법을 서비스 코드 전체에 퍼뜨리는 것
- 라이브러리 오류와 우리 코드 오류가 섞이게 만드는 것

지켜야 할 것:

- 외부 API는 작은 학습 테스트로 먼저 탐색한다.
- 우리가 사용할 방식만 테스트한다.
- 알게 된 사용법을 테스트 코드로 기록한다.
- 운영 코드에서는 래퍼나 어댑터 뒤에 숨긴다.
- 라이브러리 업그레이드 때 학습 테스트를 다시 실행한다.