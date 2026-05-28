# log4j 익히기 (Learning log4j)

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
log4j = "2.26.0"

[libraries]
junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit-jupiter" }
jackson-databind = { module = "com.fasterxml.jackson.core:jackson-databind", version.ref = "jackson" }
log4j-core = { module = "org.apache.logging.log4j:log4j-core", version.ref = "log4j" }

```

build.gradle:

```groovy
dependencies {
    testImplementation libs.junit.jupiter
    testImplementation libs.jackson.databind
    testImplementation libs.log4j.core

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
./gradlew test --rerun-tasks  --tests "com.eomcs.cleancode.ch08.exam03.*" 2>&1
```

### 학습 테스트 소스 코드 위치

`app/src/test/java/com/eomcs/cleancode/ch08/exam03`

## 예제 1

```java
// Bad - 운영 코드에서 바로 log4j 사용
public class OrderService {
    private final Logger logger = Logger.getLogger(OrderService.class);

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

- log4j 설정이 제대로 되었는지 모른 채 운영 코드에 넣는다.
- 로그가 안 나오면 우리 코드 문제인지 log4j 설정 문제인지 구분하기 어렵다.
- 외부 라이브러리 학습과 비즈니스 코드 구현이 섞인다.
- log4j 사용법이 서비스 코드에 직접 퍼진다.

```java
// Good - 1단계: 가장 단순한 학습 테스트
@Test
public void testLogCreate() {
    Logger logger = Logger.getLogger("MyLogger");

    logger.info("hello");
}
```

- 먼저 가장 단순한 방식으로 log4j를 실행해 본다.
- 이 테스트를 통해 Appender 설정이 필요하다는 사실을 발견한다.
- 운영 코드가 아니라 테스트 코드에서 안전하게 실패를 경험한다.

## 예제 2

```java
// Bad - 운영 코드에서 설정까지 직접 처리
public class OrderService {
    private final Logger logger = Logger.getLogger("OrderService");

    public void createOrder(Order order) {
        ConsoleAppender appender = new ConsoleAppender();
        logger.addAppender(appender);

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

- 서비스 코드가 log4j 설정 세부사항을 직접 안다.
- ConsoleAppender가 어떻게 동작하는지 확인되지 않았다.
- 주문 생성 로직보다 로깅 설정 코드가 더 눈에 띈다.
- 설정 코드가 여러 서비스에 중복될 수 있다.

```java
// Good - 2단계: ConsoleAppender를 추가해 보는 학습 테스트
@Test
public void testLogAddAppender() {
    Logger logger = Logger.getLogger("MyLogger");

    ConsoleAppender appender = new ConsoleAppender();
    logger.addAppender(appender);

    logger.info("hello");
}
```

- ConsoleAppender가 필요하다는 가설을 테스트한다.
- 실행해 보면 출력 스트림이나 레이아웃 설정이 더 필요하다는 사실을 알 수 있다.
- 라이브러리의 실제 동작을 문서가 아니라 실험으로 확인한다.

## 예제 3

```java
// Bad - log4j 설정 지식이 비즈니스 코드에 섞임
public class PaymentService {
    private final Logger logger = Logger.getLogger("PaymentService");

    public void pay(Order order) {
        logger.removeAllAppenders();
        logger.addAppender(new ConsoleAppender(
            new PatternLayout("%p %t %m%n"),
            ConsoleAppender.SYSTEM_OUT
        ));

        logger.info("결제 시작");
        paymentGateway.pay(order);
        logger.info("결제 완료");
    }
}
```

```java
// Bad - 함수 사용
paymentService.pay(order);
```

- 결제 서비스가 log4j 초기화 방식까지 책임진다.
- PatternLayout, ConsoleAppender.SYSTEM_OUT 같은 외부 API 세부사항이 노출된다.
- 다른 서비스에서도 같은 설정 코드가 반복될 가능성이 높다.
- log4j를 다른 로깅 도구로 바꾸기 어렵다.

```java
// Good - 3단계: 실제 필요한 설정을 학습 테스트로 확정
public class LogTest {
    private Logger logger;

    @Before
    public void initialize() {
        logger = Logger.getLogger("logger");
        logger.removeAllAppenders();
        Logger.getRootLogger().removeAllAppenders();
    }

    @Test
    public void basicLogger() {
        BasicConfigurator.configure();

        logger.info("basicLogger");
    }

    @Test
    public void addAppenderWithStream() {
        logger.addAppender(new ConsoleAppender(
            new PatternLayout("%p %t %m%n"),
            ConsoleAppender.SYSTEM_OUT
        ));

        logger.info("addAppenderWithStream");
    }

    @Test
    public void addAppenderWithoutStream() {
        logger.addAppender(new ConsoleAppender(
            new PatternLayout("%p %t %m%n")
        ));

        logger.info("addAppenderWithoutStream");
    }
}
```

- log4j 초기화 방법을 테스트로 기록한다.
- BasicConfigurator, ConsoleAppender, PatternLayout의 차이를 확인한다.
- 나중에 log4j 버전을 올릴 때 이 테스트가 깨지면 변경점을 바로 알 수 있다.
- 학습한 내용을 운영 코드가 아니라 테스트 코드에 남긴다.

## 예제 4

```java
// Good - 우리 애플리케이션용 로거
public class AppLogger {
    private final Logger logger;

    public AppLogger(String name) {
        this.logger = Logger.getLogger(name);
        this.logger.removeAllAppenders();
        this.logger.addAppender(new ConsoleAppender(
            new PatternLayout("%p %t %m%n"),
            ConsoleAppender.SYSTEM_OUT
        ));
    }

    public void info(String message) {
        logger.info(message);
    }
}
```

```java
// Good - 함수 정의
public class OrderService {
    private final AppLogger logger;

    public OrderService(AppLogger logger) {
        this.logger = logger;
    }

    public void createOrder(Order order) {
        logger.info("주문 생성 시작");

        orderRepository.save(order);

        logger.info("주문 생성 완료");
    }
}
```

```java
// Good - 함수 사용
AppLogger logger = new AppLogger("OrderService");
OrderService orderService = new OrderService(logger);

orderService.createOrder(order);
```

- 운영 코드는 log4j 세부 API를 모른다.
- log4j 지식이 AppLogger 안에 캡슐화된다.
- 서비스 코드는 비즈니스 흐름만 표현한다.
- 로깅 라이브러리를 교체해도 변경 범위가 작다.

## 나쁜 코드 vs 좋은 코드

| 구분        | 나쁜 코드         | 좋은 코드               |
| --------- | ------------- | ------------------- |
| 학습 위치     | 운영 코드         | 학습 테스트              |
| log4j API | 서비스 코드에 직접 노출 | `AppLogger` 내부로 캡슐화 |
| 실패 경험     | 운영 코드 구현 중 발생 | 테스트에서 안전하게 확인       |
| 변경 대응     | 여러 서비스 수정     | 래퍼 클래스 수정           |
| 지식 기록     | 개발자 머릿속       | 테스트 코드              |
| 목적        | 기능 구현과 학습이 섞임 | 학습 후 통합             |

## 핵심 원칙

피해야 할 것:

- log4j를 운영 코드에서 바로 실험하는 것
- 라이브러리 사용법을 서비스 코드에 퍼뜨리는 것
- 설정 코드와 비즈니스 로직을 섞는 것
- 문서만 보고 동작을 추측하는 것

지켜야 할 것:

- 작은 학습 테스트로 log4j 동작을 먼저 확인한다.
- 실패하는 테스트를 통해 필요한 설정을 찾아간다.
- 알게 된 사용법을 테스트 코드로 남긴다.
- 학습한 내용은 래퍼 클래스로 캡슐화한다.
- 애플리케이션 코드는 log4j 경계 인터페이스로부터 격리한다.