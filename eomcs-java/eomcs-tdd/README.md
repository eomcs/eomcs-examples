# Test-Driven Development By Example - Kent Beck

## 1. TDD의 목표: “Clean code that works”

“Clean code that works” is the goal of Test-Driven Development. - Ron Jeffries

즉 TDD의 목표는 단순히 작동하는 코드(working code)가 아니라 **“깨끗하면서도 동작하는 코드”**이다.

깨끗한 코드가 주는 이점:
- 예측 가능한 개발 방식: 언제 작업이 끝났는지 알 수 있음
- 코드가 가르쳐주는 교훈을 학습: 처음 떠오른 설계보다 더 나은 설계를 발견할 수 있음
- 사용자에게 더 좋은 소프트웨어 제공하고, 팀원 간 신뢰 형성하게 해주고, 코드를 작성하는 만족감을 줌


## 2. TDD의 2가지 핵심 규칙과 만트라(mantra)    

TDD의 규칙:
- 규칙 1: 자동화된 테스트가 실패할 때만 새로운 코드를 작성한다    
- 규칙 2: 중복을 제거한다

이 두 규칙은 프로그래밍의 작업의 순서를 결정하며, TDD의 핵심 사이클인 Red → Green → Refactor를 만들어 낸다.

TDD 사이클(TDD 만트라):
1. **Red**: 작동하지 않고 처음에는 컴파일조차 되지 않을 수 있는 작은 테스트를 작성한다.
2. **Green**: 과정 중에 어떤 죄(편법)를 저지르든 가장 빠르게 테스트가 작동하게 만든다.
3. **Refactor**: 테스트를 통과시키는 과정에서 발생한 모든 중복을 제거합니다. 그리고 코드를 개선한다.

정리:
| TDD 단계       | 집중하는 것     | 고민의 종류                 |
| ------------ | ---------- | ---------------------- |
| **Red**      | 요구되는 기능 정의 | “무엇을 만들 것인가?”          |
| **Green**    | 기능 구현      | “어떻게 동작하게 만들 것인가?”     |
| **Refactor** | 설계 개선      | “코드를 어떻게 더 좋게 만들 것인가?” |


## 3. TDD가 미치는 영향

기술적 영향: 
- 코드를 실행하여 얻는 피드백으로 결정을 내리며 설계를 유기적으로 발전시킨다.
- 또한 테스트를 쉽게 만들기 위해 응집도가 높고 결합도가 낮은 컴포넌트들로 설계하도록 유도한다.

사회적 영향: 
- 코드의 결함 밀도를 크게 낮출 수 있다면, QA 부서는 사후 대응이 아닌 사전 예방 업무로 전환될 수 있다.
- 또한 프로젝트 관리자는 정확한 추정을 통해 고객을 매일 참여시킬 수 있고, 개발자들은 긴밀하게 협력하며 매일 새로운 기능이 추가된 배포 가능한 소프트웨어를 만들어낼 수 있다.



## 4. 두려움 관리와 용기(Courage)

Kent Beck은 프로그래밍 중 느끼는 막막함과 '두려움'을 관리하는 방법이 바로 TDD라고 설명한다.

- 두려움은 사람을 망설이게 하고 소통과 피드백을 피하게 만든다.
- 하지만 TDD는 이런 상황에서 주저하는 대신 구체적인 학습을 시작하고, 더 명확하게 소통하며, 확실한 피드백을 찾도록 도와준다.
- 이를 **우물에서 무거운 물통을 끌어올릴 때 쉬어갈 수 있게 해주는 톱니바퀴 장치(ratchet)**에 비유한다.
- 자동화된 테스트 하나가 통과할 때마다 모든 것이 제대로 작동한다는 사실을 알게 되고, 한 걸음씩 목표에 가까워질 수 있다는 것이다.

## 5. 책 구성

- Part I (Money Example): 
    - 다중 통화(Multi-currency) 산술 예제를 통해 코드를 작성하기 전 테스트를 작성하고 설계를 유기적으로 성장시키는 방법을 배운다
- Part II (The xUnit Example): 
    - 자동화 테스트 프레임워크인 xUnit을 직접 구축해보면서 더 복잡한 로직을 첫 번째 예제보다 더 작은 단계로 나누어 작업하는 방법을 배운다
- Part III (Patterns for TDD): 
    - 어떤 테스트를 작성할지 결정하는 전략과 예제에서 사용된 핵심 디자인 패턴 및 리팩토링 기법들을 소개한다

## 실행 

```
./gradlew test --tests "com.eomcs.tdd.ch01.*" 2>&1 | tail -20
```

오류 메시지를 보고 싶다면 `tail` 대신 `head`를 사용한다.
```
./gradlew test --tests "com.eomcs.tdd.ch10.step02_refactor.*" 2>&1 | head -30
```

### 빌드 스크립트 설정 

`build.gradle`에 다음 설정을 추가하여 테스트 실패 시 전체 스택 트레이스 대신 오류 메시지만 출력하도록 할 수 있다.

```gradle
tasks.named('test') {
    useJUnitPlatform()

    testLogging {
        events = ["failed"]
        exceptionFormat = "full"
        showExceptions = true
        showCauses = true
        showStackTraces = false  // 스택트레이스 생략, 오류 메시지만 출력
    }
}
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
