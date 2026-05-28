# Clean Code - Robert C. Martin

## 책 구성

### 1부 — 원칙, 패턴, 실천법 (Chapters 1–13)

클린 코드를 작성하는 방법을 설명하는 핵심 이론 파트다.

- [Chapter 1: 깨끗한 코드 (Clean Code)](app/src/main/java/com/eomcs/cleancode/ch01/README.md)
- [Chapter 2: 의미있는 이름 짓기 (Meaningful Names)](app/src/main/java/com/eomcs/cleancode/ch02/README.md)
- [Chapter 3: 함수 (Functions)](app/src/main/java/com/eomcs/cleancode/ch03/README.md)
- [Chapter 4: 주석 (Comments)](app/src/main/java/com/eomcs/cleancode/ch04/README.md)
- [Chapter 5: 형식 맞추기 (Formatting)](app/src/main/java/com/eomcs/cleancode/ch05/README.md)
- [Chapter 6: 객체와 자료구조 (Objects and Data Structures)](app/src/main/java/com/eomcs/cleancode/ch06/README.md)
- [Chapter 7: 오류 처리 (Error Handling)](app/src/main/java/com/eomcs/cleancode/ch07/README.md)
- [Chapter 8: 경계 (Boundaries)](app/src/main/java/com/eomcs/cleancode/ch08/README.md)
- [Chapter 9: 단위 테스트 (Unit Tests)](app/src/main/java/com/eomcs/cleancode/ch09/README.md)
- [Chapter 10: 클래스 (Classes)](app/src/main/java/com/eomcs/cleancode/ch10/README.md)
- [Chapter 11: 시스템 (Systems)](app/src/main/java/com/eomcs/cleancode/ch11/README.md)
- [Chapter 12: 창발성 (Emergence)](app/src/main/java/com/eomcs/cleancode/ch12/README.md)
- [Chapter 13: 동시성 (Concurrency)](app/src/main/java/com/eomcs/cleancode/ch13/README.md)

### 2부 — 케이스 스터디 (Chapters 14–16)

실제 코드를 직접 리팩터링하는 과정을 단계별로 보여준다. 

- [Chapter 14: 점진적 개선 (Successive Refinement)](app/src/main/java/com/eomcs/cleancode/ch14/README.md)
- [Chapter 15: JUnit 들여다보기 (JUnit Internals)](app/src/main/java/com/eomcs/cleancode/ch15/README.md)
- [Chapter 16: SerialDate 리팩터링 (Refactoring SerialDate)](app/src/main/java/com/eomcs/cleancode/ch16/README.md)

### 3부 — 냄새와 휴리스틱 (Chapter 17 - Smells and Heuristics)

케이스 스터디를 진행하며 도출한 코드 스멜(bad smells) 과 휴리스틱(판단 기준) 목록이다. 주석(C), 환경(E), 함수(F), 일반(G), 자바(J), 이름(N), 테스트(T) 등 카테고리별로 정리되어 있다.


## 실행 

```zsh
# 프로젝트 소스 코드 컴파일
./gradlew compileJava 2>&1 | tail -10
```

## 도구 준비

### macOS 패키지 관리자 설치

0. Xcode Command Line Tools (Homebrew 설치에 필요)
    ```bash
    xcode-select --install
    ```

1. Homebrew 설치 (macOS 패키지 관리자,JDK + SDK 포함)
    ```bash
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    ```
2. SDKMAN 설치 (JDK, SDK 전용 패키지 관리자) - JDK 패키지 관리가 Homebrew보다 더 편리함
    ```bash
    curl -s "https://get.sdkman.io" | bash
    ```

### Git 설치

- macOS:
    - Xcode Command Line Tools을 설치하면 Git이 기본 설치됨
- Windows:
    - [Git 공식 웹사이트](https://git-scm.com/download/win)에서 다운로드해서 설치

### JDK 21 이상 설치

- macOS:
    ```bash
    sdk install java 21.0.10-tem
    ```
- Windows:
    - [Oracle JDK 21 다운로드](https://www.oracle.com/java/technologies/downloads/#java21)

### Gradle 설치

- macOS:
    ```bash
    sdk install gradle
    ```
- Windows:
    - [Gradle 공식 웹사이트](https://gradle.org/install/)에서 다운로드해서 설치

### VSCode 설치

- [VSCode 공식 웹사이트](https://code.visualstudio.com/)에서 다운로드해서 설치
- 기본 확장 프로그램:
    - Extension Pack for Java (Microsoft 제공)
    - Gradle for Java (Microsoft 제공)
    - Checkstyle for Java (ShengChen 제공)
    - Google Java Format for VS Code (Jose V Sebastian 제공)
    - SonarQube for IDE (SonarSource 제공)
    - Korean Language Pack for Visual Studio Code (Microsoft 제공)
    - Oracle SQL Developer Extension for VSCode (Oracle 제공)
- AI 코드 어시스턴트 확장 프로그램:
    - GitHub Copilot Chat (GitHub 제공)
    - Claude Code for VS Code (Anthropic 제공)
    - Codex - OpenAI's Code Assistant (OpenAI 제공)

## 단위 테스트 준비

### 의존 라이브러리 추가

**libs.versions.toml:**

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

**build.gradle:**

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

### 단위 테스트 실행

```zsh
./gradlew test --rerun-tasks  --tests "com.eomcs.cleancode.ch09.examXX.*" 2>&1
```

### 단위 테스트 소스 코드 위치

`app/src/test/java/com/eomcs/cleancode/ch09/examXX`

## Spring Framework AOP + AspectJ 준비

### 의존 라이브러리 추가

**libs.versions.toml:**

```toml
[versions]
spring = "7.0.7"
aspectjweaver = "1.9.24"

[libraries]
spring-context = { module = "org.springframework:spring-context", version.ref = "spring" }
aspectjweaver = { module = "org.aspectj:aspectjweaver", version.ref = "aspectjweaver" }
```

**build.gradle:**

```groovy
dependencies {
    implementation libs.spring.context
    implementation libs.aspectjweaver
}
```

## 예제 실행

### `gradle.build` 설정

```groovy
application {
    mainClass = project.findProperty('mainClass') ?: 'com.eomcs.cleancode.App'
}
```

### 애플리케이션 실행 
빌드 스크립트 파일에 설정된 기본 메인 클래스 실행:
```bash
./gradlew -q run
```

특정 클래스 실행:
```bash
./gradlew -q run -PmainClass=com.eomcs.cleancode.App2
```

프로그램 아규먼트 넘기기:
```bash
./gradlew -q run -PmainClass=com.eomcs.cleancode.App3 --args="홍길동 20"
```