# Clean Code - Robert C. Martin

## 책 구성

### 1부 — 원칙, 패턴, 실천법 (Chapters 1–13)

클린 코드를 작성하는 방법을 설명하는 핵심 이론 파트다.

- [Chapter 1: Clean Code — 클린 코드란 무엇인가](app/src/main/java/com/eomcs/cleancode/ch01/README.md)
- [Chapter 2: Meaningful Names — 의미 있는 이름 짓기](app/src/main/java/com/eomcs/cleancode/ch02/README.md)
- [Chapter 3: Functions — 함수 설계 원칙](app/src/main/java/com/eomcs/cleancode/ch03/README.md)
- [Chapter 4: Comments — 주석을 잘 쓰는 법](app/src/main/java/com/eomcs/cleancode/ch04/README.md)
- [Chapter 5: Formatting — 코드 형식](app/src/main/java/com/eomcs/cleancode/ch05/README.md)
- [Chapter 6: Objects and Data Structures](app/src/main/java/com/eomcs/cleancode/ch06/README.md)
- [Chapter 7: Error Handling](app/src/main/java/com/eomcs/cleancode/ch07/README.md)
- [Chapter 8: Boundaries](app/src/main/java/com/eomcs/cleancode/ch08/README.md)
- [Chapter 9: Unit Tests & TDD](app/src/main/java/com/eomcs/cleancode/ch09/README.md)
- [Chapter 10: Classes](app/src/main/java/com/eomcs/cleancode/ch10/README.md)
- [Chapter 11: Systems](app/src/main/java/com/eomcs/cleancode/ch11/README.md)
- [Chapter 12: Emergence](app/src/main/java/com/eomcs/cleancode/ch12/README.md)
- [Chapter 13: Concurrency](app/src/main/java/com/eomcs/cleancode/ch13/README.md)

### 2부 — 케이스 스터디 (Chapters 14–16)

실제 코드를 직접 리팩터링하는 과정을 단계별로 보여준다. 

- [Chapter 14: Successive Refinement (Args 파서 리팩터링)](app/src/main/java/com/eomcs/cleancode/ch14/README.md)
- [Chapter 15: JUnit Internals](app/src/main/java/com/eomcs/cleancode/ch15/README.md)
- [Chapter 16: Refactoring SerialDate](app/src/main/java/com/eomcs/cleancode/ch16/README.md)

### 3부 — 냄새와 휴리스틱 (Chapter 17)

케이스 스터디를 진행하며 도출한 코드 스멜(bad smells) 과 휴리스틱(판단 기준) 목록이다. 주석(C), 환경(E), 함수(F), 일반(G), 자바(J), 이름(N), 테스트(T) 등 카테고리별로 정리되어 있다.


## 실행 

```zsh
# 프로젝트 소스 코드 컴파일
./gradlew compileJava 2>&1 | tail -10
```

## 교육 준비

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
