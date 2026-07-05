# Chapter 2. TypeScript: A 10,000 Foot View

> **"1만 피트 상공에서 내려다본 모습"**<br>
> 즉, 세부 사항은 생략하고 TypeScript의 전체적인 구조와 동작 방식을 한눈에 보기

---

## 1. 컴파일러 (The Compiler)

프로그램이 실행되기까지의 일반적인 과정:

1. 소스코드 → **AST** (Abstract Syntax Tree) 파싱
2. AST → **바이트코드** 컴파일
3. 바이트코드 → **런타임** 평가 및 실행

TypeScript는 여기서 특별하다. 바이트코드 대신 **JavaScript 코드**로 컴파일된다.

### AST (Abstract Syntax Tree)

소스코드를 컴파일러가 이해하기 쉬운 구조로 바꾼 것  

소스 코드:

```typescript
let x = 1 + 2
```

AST:

```
변수 선언
└─ 이름: x
└─ 값: 덧셈
   ├─ 왼쪽: 1
   └─ 오른쪽: 2
```

### TypeScript 컴파일 전체 과정

```
TypeScript 소스코드
    ↓ (1) TSC: TS → AST
TypeScript AST
    ↓ (2) TSC: 타입 체킹
    ↓ (3) TSC: AST → JavaScript
JavaScript 소스코드
    ↓ (4) JS 엔진: JS → AST
JavaScript AST
    ↓ (5) JS 엔진: AST → 바이트코드
바이트코드
    ↓ (6) 런타임: 평가 및 실행
결과
```

> **핵심**: TSC(타입스크립트 컴파일러)가 TypeScript → JavaScript로 컴파일할 때 타입 정보는 사용하지 않는다. 타입은 오직 **타입 체킹 단계(2)** 에서만 사용된다. 즉, 타입을 변경해도 출력되는 JavaScript 코드에는 영향을 주지 않는다.

> **자바스크립트 엔진** = 자바스크립트 컴파일러 + 자바스크립트 런타임
---

## 2. 타입 시스템 (The Type System)

> **TYPE SYSTEM**: 타입 체커가 프로그램에 타입을 할당하는 데 사용하는 규칙의 집합.

타입 시스템에는 두 종류가 있다:
- **명시적(Explicit)**: 개발자가 직접 타입을 선언
- **추론(Inferred)**: 컴파일러가 자동으로 타입을 추론

TypeScript는 **두 방식을 모두 지원**한다.

```typescript
// 명시적 타입 어노테이션
let a: number = 1
let b: string = 'hello'
let c: boolean[] = [true, false]

// 타입 추론 (어노테이션 생략)
let a = 1          // number로 추론
let b = 'hello'    // string으로 추론
let c = [true, false] // boolean[]로 추론
```

> 가능한 한 TypeScript의 타입 추론에 맡기고, 명시적 타입 어노테이션은 최소화하는 것이 좋은 스타일이다. 이유는 **DRY(Don't Repeat Yourself) 원칙** 때문이다. 타입을 중복해서 작성하면 코드가 장황해지고, 타입이 변경될 때마다 여러 곳을 수정해야 한다.

---

## 3. TypeScript vs JavaScript 타입 시스템 비교

| 구분 | JavaScript | TypeScript |
|------|-----------|------------|
| 타입 바인딩 시점 | 동적 (런타임) | 정적 (컴파일 타임) |
| 자동 타입 변환 | Yes (암묵적) | No (명시적만 허용) |
| 타입 검사 시점 | 런타임 | 컴파일 타임 |
| 오류 표면화 시점 | 런타임 | 컴파일 타임 |

### 암묵적 타입 변환 예시

```javascript
// JavaScript: 암묵적으로 "31"을 반환 (오류 없음)
3 + [1]

// TypeScript: 즉시 오류 발생
3 + [1]
// Error TS2365: Operator '+' cannot be applied to types '3' and 'number[]'.

// 명시적으로 변환하면 TypeScript도 허용
(3).toString() + [1].toString() // "31"
```

### 오류 감지 시점

- **JavaScript**: 스택 오버플로, 네트워크 오류 등은 여전히 런타임에 발생
- **TypeScript**: 타입 관련 오류와 문법 오류를 **컴파일 타임에 에디터 내에서** 즉시 감지

---

## 4. 개발 환경 설정 (Code Editor Setup)

### react-workspace.zip 압축 해제

```
~/react-workspace
├── download/
│   ├── macOS            ← macOS 용 프로그램
│   ├── Windows          ← Windows 용 프로그램
├── typescript-lab/      ← TypeScript 실습 프로젝트
```

### Nodejs 설치

각 운영체제에 맞는 Nodejs를 react-workspace/ 에 압축 해제한다.

```
~/react-workspace
├── node/                ← nodejs 압축 해제 폴더
```

환경 변수에 nodejs 경로를 추가한다.

```bash
# macOS
export PATH=$PATH:~/react-workspace/node/bin

# Windows
set PATH=%PATH%;C:\react-workspace\node\bin
```

node 설치 확인

```bash
node -v
npm -v
```

### 프로젝트 폴더 설정

```bash
# react-workspace/ 루트 폴더에서 실행
mkdir typescript-lab
cd typescript-lab
npm init -y

# TypeScript 컴파일러, ESLint, Node 타입 선언 설치
npm install --save-dev \
typescript \
eslint \
@eslint/js \
globals \
typescript-eslint \
@types/node
```

| 모듈 | 설명 |
|------|------|
| `typescript` | TypeScript 컴파일러(TSC) |
| `eslint` | 코드 품질 검사기(Linter) |
| `@eslint/js` | ESLint에서 제공하는 JavaScript 기본 규칙 모음 |
| `globals` | Node.js, 브라우저 등 실행 환경에서 제공하는 전역 객체(Global Variables) 정의 |
| `typescript-eslint` | TypeScript용 ESLint 기능을 제공하는 패키지 |
| `@types/node` | Node.js 타입 선언 파일 |

### tsconfig.base.json (루트 공통 설정)

`typescript-lab/tsconfig.base.json`

```json
{
  "compilerOptions": {
    "lib": ["es2024", "dom"],
    "module": "NodeNext",
    "sourceMap": true,
    "strict": true,
    "target": "es2024",
    "types": ["node"]
  }
}
```

| 옵션 | 설명 |
|------|------|
| `lib` | TypeScript가 사용할 JavaScript 표준 라이브러리(Type Definitions) 를 지정 |
| `module` | 컴파일 대상 모듈 시스템 (CommonJS, ESM 등) |
| `sourceMap` | 소스맵 생성 여부 (디버깅에 유용) |
| `strict` | 엄격한 타입 검사 활성화 (권장) |
| `target` | 컴파일 대상 JavaScript 버전 |
| `types` | 포함할 타입 선언 파일 목록. 예) Node.js 타입 정의만 사용하라고 알려주는 옵션 |

### tsconfig.json (각 장 폴더)

`typescript-lab/ch02/tsconfig.json`

```json
{
  "extends": "../tsconfig.base.json",
  "compilerOptions": {
    "rootDir": "src",
    "outDir": "dist"
  },
  "include": ["src"]
}
```

- extends: 공통 설정 상속
- rootDir: 컴파일 대상 소스코드 폴더
- outDir: 컴파일된 JavaScript 파일이 생성될 폴더
- include: 컴파일 대상 소스코드 폴더. rootDir 밖에 있는 .ts 파일을 지정하면 오류 발생

ch03, ch04 등 다른 장도 동일하게 `"extends": "../tsconfig.base.json"`으로 작성한다.

### eslint.config.mjs (루트 공통 설정)

`typescript-lab/eslint.config.mjs` 생성:
```bash
# typescript-lab/ 루트 폴더에서 실행
./node_modules/.bin/eslint --init
```

`typescript-lab/eslint.config.mjs` 변경:
```javascript
import js from "@eslint/js";
import globals from "globals";
import tseslint from "typescript-eslint";
import { defineConfig } from "eslint/config";

export default defineConfig([
  {
    files: ["ch*/src/**/*.ts"],
    plugins: { js },
    extends: ["js/recommended"],
    languageOptions: {
      globals: globals.node,
      parserOptions: {
        project: ["./ch*/tsconfig.json"]
      }
    }
  },
  ...tseslint.configs.recommended
]);
```

전체 구조:
```
ESLint 설정

├── JavaScript 기본 규칙
├── Node.js 실행 환경 설정
└── TypeScript 권장 규칙
```

**`import js from '@eslint/js'`**:

- ESLint가 제공하는 JavaScript 기본 규칙을 가져옵니다.
- 예를 들어 다음과 같은 규칙이 포함됩니다.
  - 사용하지 않는 변수 검사
  - 선언되지 않은 변수 사용 검사
  - 도달할 수 없는 코드 검사
  - 상수 조건 검사
- 대표 규칙
  - no-unused-vars
  - no-undef
  - no-unreachable
  - no-constant-condition

**`import globals from "globals"`**:

- 실행 환경에서 제공하는 전역 객체(Global Variables) 를 정의한 라이브러리입니다.
- globals.node 를 사용하므로, Node.js에서 기본 제공하는 객체들을 사용할 수 있습니다.
  - console.log(process.version)
  - Buffer.from("hello")
  - setTimeout(() => {})
  - 등 모두 정상적으로 인식된다.
- globals.browser 를 사용한다면, 
  - window.location
  - document.title
  - navigator.userAgent
  - 등 브라우저 객체를 사용할 수 있습니다.

**`import tseslint from "typescript-eslint"`**:

- TypeScript를 ESLint가 검사할 수 있도록 하는 공식 패키지입니다.
- 내부적으로
  - TypeScript Parser
  - TypeScript Plugin
  - Flat Config
  - 를 모두 제공합니다.

**`import { defineConfig }`**

- Flat Config를 정의하는 함수입니다.
- defineConfig()를 사용하면
  - 타입 추론
  - 자동 완성
  - 설정 검증
  - 등의 이점을 얻을 수 있습니다.

**`defineConfig([...])`**:

- ESLint 설정 객체들의 배열입니다.

**`files: ["ch*/src/**/*.ts"]`**:

- 이 설정을 적용할 파일을 지정합니다.

**`plugins: { js }`**:

- JavaScript 규칙을 제공하는 Plugin을 등록합니다.
- 이 Plugin 안에 js.configs.recommended 가 들어 있습니다.

**`extends: ["js/recommended"]`**:

- JavaScript 권장 규칙을 활성화합니다.

**`languageOptions`**:

- ESLint가 코드를 어떤 환경에서 해석할지 지정합니다.

**`globals: globals.node`**:

- Node.js의 전역 객체를 등록합니다.
- 대표적인 객체
  - console
  - process
  - Buffer
  - setTimeout
  - clearTimeout
  - 이 객체들을 선언하지 않고 바로 사용할 수 있습니다.

**`parserOptions`**:

- TypeScript Parser에게 전달하는 옵션입니다.

**`project: ["./ch*/tsconfig.json"]`**:

- 각 Chapter의 tsconfig.json을 읽습니다.
- 이를 Type-aware Linting이라고 합니다.

**`...tseslint.configs.recommended`**:

- TypeScript에서 권장하는 ESLint 규칙을 활성화합니다.
- 대표적인 규칙
  - @typescript-eslint/no-unused-vars
  - @typescript-eslint/no-explicit-any
  - @typescript-eslint/no-empty-function
  - @typescript-eslint/ban-ts-comment
- `...` 를 사용하는 이유는 recommended가 배열이기 때문입니다.


### 최종 프로젝트 구조

```
typescript-lab/
├── node_modules/        ← 모든 장이 공유
├── package.json
├── tsconfig.base.json   ← 공통 컴파일 옵션
├── eslint.config.mjs    ← 공통 린트 설정
├── ch02/
│   ├── tsconfig.json    ← tsconfig.base.json 상속
│   ├── src/
│   │   └── index.ts
│   └── dist/            ← 컴파일 후 생성됨
├── ch03/
│   ├── tsconfig.json
│   └── src/
└── ch04/ ...
```

### 첫 TypeScript 실행

```typescript
// ch02/src/index.ts
console.log('Hello TypeScript!')
```

```bash
# ch02/ 폴더에서 컴파일
cd ch02
../node_modules/.bin/tsc

# 실행
node ./dist/index.js

# 코드 품질 검사
npx eslint src
```

---

## 5. 타입 추론 실습

```typescript
let a = 1 + 2        // number
let b = a + 3        // number
let c = {
  apple: a,          // { apple: number, banana: number }
  banana: b
}
let d = c.apple * 4  // number
```

TypeScript는 변수 위에 마우스를 올리면 추론된 타입을 에디터에서 바로 확인할 수 있다.

---

## 핵심 요약

- TypeScript는 **JavaScript로 컴파일**되며, 타입 정보는 컴파일 결과물에 포함되지 않는다.
- **타입 체킹**은 컴파일 단계에서 수행되어 에디터에서 즉시 오류를 확인할 수 있다.
- **타입 추론**을 적극 활용하고, 명시적 어노테이션은 필요할 때만 사용한다.
- `tsconfig.json`의 `strict: true` 옵션은 항상 활성화하는 것을 권장한다.
