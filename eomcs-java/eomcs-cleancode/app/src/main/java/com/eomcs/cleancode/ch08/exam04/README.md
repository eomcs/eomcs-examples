# 학습 테스트는 공짜 이상이다 (Learning Tests Are Better Than Free)

> **외부 라이브러리를 배우기 위해 작성한 테스트는 비용이 아니라 자산이다.**

어차피 외부 API는 배워야 한다. 이때 학습 테스트를 작성하면,

- 외부 라이브러리에 대한 이해를 높이는 정밀한 실험이다.
- 나중에 라이브러리 버전이 바뀌었을 때 기존 동작이 유지되는지도 확인할 수 있다.

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
// Bad - 함수 정의
public class JsonUserParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public User parse(String json) throws JsonProcessingException {
        return mapper.readValue(json, User.class);
    }
}
```

```java
// Bad - 함수 사용
User user = jsonUserParser.parse("""
    {"id":1,"name":"Kim","unknown":"value"}
""");
```

- Jackson이 알 수 없는 필드를 허용하는지 거부하는지 모른 채 사용한다.
- 라이브러리 동작을 운영 코드에서 확인하게 된다.
- 버전 업그레이드 후 동작이 바뀌어도 바로 알기 어렵다.

```java
// Good - 학습 테스트
class JacksonLearningTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void unknownField가_있으면_예외가_발생한다() {
        String json = """
            {"id":1,"name":"Kim","unknown":"value"}
        """;

        assertThrows(JsonProcessingException.class, () -> {
            mapper.readValue(json, User.class);
        });
    }
}
```

- 라이브러리 동작을 작은 테스트로 명확히 확인한다.
- 테스트가 사용법 문서 역할을 한다.
- 라이브러리 업그레이드 후 동작이 바뀌면 테스트 실패로 바로 알 수 있다.

## 나쁜 코드 vs 좋은 코드

| 구분        | 나쁜 코드      | 좋은 코드              |
| --------- | ---------- | ------------------ |
| 학습 방식     | 운영 코드에서 실험 | 학습 테스트로 실험         |
| 테스트 가치    | 없음         | 사용법 문서 + 회귀 테스트    |
| 업그레이드 대응  | 실제 장애로 발견  | 테스트 실패로 발견         |
| 외부 API 의존 | 코드 전체로 확산  | 경계에 격리             |
| 비용        | 디버깅 비용 증가  | 어차피 배울 내용을 테스트로 보존 |

## 핵심 원칙

피해야 할 것:

- 외부 라이브러리 동작을 운영 코드에서 처음 확인하는 것
- 배운 사용법을 개발자 기억에만 의존하는 것
- 라이브러리 업그레이드를 테스트 없이 진행하는 것
- 외부 API 세부사항을 애플리케이션 전체에 퍼뜨리는 것

지켜야 할 것:

- 외부 API는 학습 테스트로 먼저 탐색한다.
- 우리가 실제 사용할 방식만 테스트한다.
- 학습 테스트를 라이브러리 사용법 문서로 남긴다.
- 라이브러리 업그레이드 때 학습 테스트를 먼저 실행한다.
- 경계 테스트로 외부 코드가 여전히 기대대로 동작하는지 확인한다.