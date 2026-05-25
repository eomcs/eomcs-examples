# 팀 규칙 (Team Rules)

> 팀은 한 가지 규칙에 합의해야 한다. 모든 팀원은 그 규칙을 따라야 하고, 소프트웨어가 일관적인 스타일을 유지하도록 자동화해야 한다.

## 1. 플러그인 설치

1.1 Extension Pack for Java(Microsoft) 설치

- Language support for Java ™ for Visual Studio Code
- Debugger for Java
- Test Runner for Java
- Maven for Java
- Gradle for Java
- Project Manager for Java

## 2. 프로젝트 품질 관리 플러그인 설치

실무에서 가장 많이 쓰는 “황금 조합”:

| 도구 | 역할 | 설명 |
| -------------- | -------------- | -------------- |
|google-java-format | 자동 포매팅 | 저장(ctrl+s)하면 코드 스타일 자동 통일 |
|Checkstyle | 스타일 규칙 검사 | 네이밍, 중괄호, Javadoc, import 규칙, 구조 검사 |
|SonarLint |논리적 오류/버그/보안 검사 | NPE 가능성, dead code, 보안 취약점, 코드 스멜 |

각 도구의 역할이 서로 겹치지 않고 보완적이다.

### 2.1 Google Java Format 적용하기

왜 필요한가?

- 사람이 스타일을 맞추려고 시간을 쓸 필요가 없음
- 팀원이 달라도 “딱 한 가지” 스타일로 통일됨. 즉, 팀 전체 코드 스타일이 자동으로 강제됨
- 코드 리뷰에서 “포매팅 언쟁”이 사라짐 → 오직 비즈니스 로직만 논의. 즉, “들여쓰기, 중괄호, 라인 길이” 같은 사소한 문제로 리뷰 시간을 낭비하지 않음
- PR에서 변경이 깔끔하게 보임 (불필요한 diff 제거)

#### 설치하는 방법:

- [Google Java Format 사이트 참조](https://github.com/google/google-java-format)
  - [google-java-format-for-vs-code 확장 설치](https://marketplace.visualstudio.com/items?itemName=JoseVSeb.google-java-format-for-vs-code)
- `settings.json` 파일에 다음 설정 추가:

  ```json
  {
    // 저장할 때 포매팅 적용
    "editor.formatOnSave": true,

    // 100자 이상 넘어가는 라인에 가이드라인 표시
    "editor.rulers": [100],

    // VSCode Java 확장팩의 Built-in Formatter 비활성
    "java.format.enabled": false,

    // Google Java Format 확장팩을 기본 포매터로 설정
    "[java]": {
      "editor.defaultFormatter": "josevseb.google-java-format-for-vs-code"
    },

    // 저장할 때 import 문 정리
    "editor.codeActionsOnSave": {
      "source.organizeImports": "explicit"
    }
  }
  ```

### 2.2 Checkstyle 적용하기

Checkstyle은 팀의 코드 스타일 규칙을 자동으로 강제하여
코드 리뷰 부담을 줄이고, 유지보수성을 높이고, 개발자 간 일관성을 만들어주는 도구다.

#### 설치하는 방법:

- [Checkstyle for VSCode 확장 설치](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle)
  - Checkstyle 규칙 위반을 실시간으로 표시 → 실무에서 가장 자주 쓰는 VSCode Java lint 확장
- Checkstyle 설정 파일 지정
  - [Checkstyle 설정 파일 다운로드](https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml)
    - 프로젝트 루트에 `checkstyle.xml` 파일명으로 저장
  - `checkstyle.xml` 파일에 대해 컨텍스트 메뉴에서 "Set as Checkstyle Configuration File" 선택
  - 또는 직접 `.vscode/settings.json` 파일에 다음 설정 추가:
    ```json
    {
      "java.checkstyle.configuration": "${workspaceFolder}/checkstyle.xml"
    }
    ```
- Checkstyle 버전 설정
  - VSCode 명령 팔레트 > "Checkstyle: Set the Checkstyle Version" 선택
    - JRE 21+ 경우: `13.x` 이상 선택
    - JRE 17+ 경우: `11.x`, `12.x` 이상 선택
    - JRE 11+ 경우: `10.x` 선택
    - JRE 8+ 경우: `7.x`, `8.x`, `9.x` 선택
  - 또는 직접 `.vscode/settings.json` 파일에 다음 설정 추가:
    ```json
    {
      "java.checkstyle.version": "12.3.1"
    }
    ```

#### Checkstyle 규칙 커스터마이징

- 이유?
  - Checkstyle의 구글 스타일 규칙은 너무 엄격하고, 자동 포맷터와 미묘하게 다름
  - google-java-format과 충돌하는 규칙이 다수 존재
  - 팀마다 코딩 스타일이 다르기 때문에 “맞춤형 규칙 파일”이 필요함
  - 네트워크 의존성 회피 (오프라인에서도 동작)
  - 규칙 파일을 Git 저장소에 보관할 수 있음
- `google-java-format`과 충돌이 발생하는 규칙은 제거
  - LineLength
  - CustomImportOrder
  - Indentation
  - WhitespaceAround
  - WhitespaceAfter
  - NoLineWrap
  - OperatorWrap
- 교과서스럽고 엄청 엄격해서 경고 폭탄을 맞을 수 있는, 실무에 맞지 않는 규칙은 제거
  - Javadoc을 과하게 강제하는 규칙들
  - 라인, 줄바꿈 스타일을 과하게 간섭하는 일부 규칙들
  - TODO 형식까지 강제하는 TodoComment 규칙 등
- 실무에 맞춰 편집한 [checkstyle.xml](checkstyle.xml) 파일 보기

### 2.3 SonarLint 적용하기

#### “코드를 이해하면서 버그/보안/냄새를 찾는 정적 분석기”:

- 사용은 하는데 의미 없는 조건, 항상 true/false인 if
- NPE 가능성 높은 코드 패턴
- 잘못된 equals/hashCode 구현
- 잠재적 resource leak (스트림/파일 닫지 않음 등)
- 보안 취약점 패턴 (하드코딩 비밀번호, 취약한 API 호출 등)
- 복잡도 과도한 메서드, 중복 코드

#### Checkstyle vs SonarLint:

- Checkstyle: 코드 스타일/모양/네이밍 경찰
- SonarLint: 코드 품질/버그 사냥꾼
- 둘 다 사용하면 시너지 효과 극대화 가능

#### 설치하는 방법:

- [SonarLint for VSCode 확장 설치](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
  - SonarQube/SonarCloud와 연동 가능
  - 실시간으로 코드 품질 문제 표시하는 IDE 전용 정적 분석기
  - 다양한 언어 지원 (Java, JavaScript, Python 등)
  - 실무에서 널리 사용되는 정적 분석 도구
  - 설치 후 별도 설정 없이 바로 사용 가능
- `settings.json` 파일에 다음 설정 추가:

  ```json
  {
    "sonarlint.analyzerProperties": {
      "sonar.java.source": "21" // 프로젝트 JDK 버전에 맞추기
    },

    // 너무 시끄러운 규칙 일부 비활성화 (원하는 경우)
    "sonarlint.rules": {
      "java:S106": { "level": "off" }, // System.out.println 사용 금지 규칙 끄기
      "java:S125": { "level": "off" } // 주석 안에 코드가 있어도 경고 끄기
    }
  }
  ```

### 2.4 팀 권장 확장 설치 자동 안내

- 프로젝트 루트에 `.vscode/extensions.json` 파일 생성
- 다음 내용 추가:
    ```json
    // .vscode/extensions.json
    {
        "recommendations": [
            "vscjava.vscode-java-pack",
            "josevseb.google-java-format-for-vs-code",
            "shengchen.vscode-checkstyle",
            "SonarSource.sonarlint-vscode"
        ]
    }
    ```

## 단축키

### auto indent

- Windows : Shift + Alt + F
- macOS : Shift + Option + F
- Ubuntu : Ctrl + Shift + I