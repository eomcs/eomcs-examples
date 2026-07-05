# Chapter 12. Building and Running TypeScript

이번 장에서는 TypeScript 애플리케이션을 프로덕션에 빌드하고 실행하는 방법을 다룬다.

- 모든 TypeScript 프로젝트에 공통으로 필요한 빌드 설정
- 서버(Node.js)에서 TypeScript 실행
- 브라우저에서 TypeScript 실행
- NPM에 TypeScript 라이브러리 배포

---

## 1. 프로젝트 레이아웃 (Project Layout)

권장 폴더 구조:

```
my-app/
├── dist/               ← 컴파일된 결과물 (생성 파일)
│   ├── index.d.ts
│   ├── index.js
│   └── services/
│       ├── foo.d.ts
│       └── foo.js
└── src/                ← TypeScript 소스 코드
    ├── index.ts
    └── services/
        └── foo.ts
```

- `src/`: TypeScript 소스 코드
- `dist/`: TSC가 생성한 결과물

소스와 생성 파일을 분리하면 소스 관리(`git`)와 빌드 도구 연동이 쉬워진다.

---

## 2. TSC 생성 아티팩트 (Artifacts)

| 종류 | 확장자 | tsconfig.json 설정 | 기본 생성 여부 |
|------|--------|-------------------|:---:|
| JavaScript | `.js` | `"emitDeclarationOnly": false` | ✓ |
| 소스 맵 | `.js.map` | `"sourceMap": true` | ✗ |
| 타입 선언 | `.d.ts` | `"declaration": true` | ✗ |
| 선언 맵 | `.d.ts.map` | `"declarationMap": true` | ✗ |

- **JavaScript**: 실행 가능한 코드
- **소스 맵**: 컴파일된 JS를 원본 TS 파일의 라인/컬럼에 연결 → 디버깅에 필수
- **타입 선언**: 다른 TypeScript 프로젝트에서 타입 정보를 활용
- **선언 맵**: 프로젝트 레퍼런스에서 빌드 속도 향상

---

## 3. 컴파일 대상 설정 (Dialing In Your Compile Target)

JavaScript는 실행 환경마다 지원하는 버전이 다르다. TypeScript는 두 가지 방법으로 대응한다:

1. **트랜스파일(Transpile)**: 최신 문법을 구 버전 JS로 자동 변환 (TSC가 지원)
2. **폴리필(Polyfill)**: 구 환경에 없는 런타임 기능을 직접 구현 제공 (TSC는 지원 안 함 — 직접 추가 필요)

### TSC가 트랜스파일하는 기능

| 버전 | 기능 |
|------|------|
| ES2015 | `const`/`let`, `for..of`, spread(`...`), 클래스, 제너레이터, 화살표 함수, 기본/나머지 파라미터, 구조 분해 |
| ES2016 | 지수 연산자 (`**`) |
| ES2017 | async 함수, await |
| ES2018 | async 이터레이터 |
| ES2019 | catch 절 선택적 파라미터 |
| ESNext | 숫자 구분자 (`123_456`) |

### TSC가 트랜스파일하지 않는 기능

| 버전 | 기능 |
|------|------|
| ES5 | Object getter/setter |
| ES2015 | 정규식 `y`, `u` 플래그 |
| ES2018 | 정규식 `s` 플래그 |
| ESNext | BigInt (`123n`) |

### target 설정

```json
{
  "compilerOptions": {
    "target": "es5"
  }
}
```

| 값 | 설명 |
|----|------|
| `es3` | ECMAScript 3 |
| `es5` | ECMAScript 5 (기본값으로 적합) |
| `es2015` / `es6` | ECMAScript 2015 |
| `es2017` | ECMAScript 2017 |
| `esnext` | 최신 ECMAScript |

### lib 설정

폴리필로 지원하는 기능을 TypeScript에 알려주는 설정. TSC가 실제로 구현을 제공하는 게 아니라, "이 기능이 사용 가능하다"고 알리는 것이다.

```json
{
  "compilerOptions": {
    "lib": ["es2015", "es2016.array.includes", "dom"]
  }
}
```

- `dom`: 브라우저 환경에서 `window`, `document` 등 DOM API 타입 포함
- 폴리필 라이브러리: `core-js`, `@babel/polyfill`, `Polyfill.io`

---

## 4. 소스 맵 (Source Maps)

트랜스파일·번들링·최소화를 거친 코드에서 원본 TypeScript 소스로 디버깅을 가능하게 한다.

```json
{
  "compilerOptions": {
    "sourceMap": true
  }
}
```

- 개발 환경: 반드시 사용
- 프로덕션 환경: 서버·브라우저 모두 사용 권장
- **주의**: 브라우저 배포 시 소스 코드를 숨겨야 한다면 소스 맵을 제외할 것

런타임 소스 맵 지원: `source-map-support` npm 패키지 (Node.js 환경)

---

## 5. 프로젝트 레퍼런스 (Project References)

규모가 큰 프로젝트에서 TSC 빌드 속도를 크게 향상시키는 기능.

### 설정 방법

**1단계**: 프로젝트를 여러 하위 프로젝트로 분리

**2단계**: 각 하위 프로젝트의 `tsconfig.json`

```json
{
  "compilerOptions": {
    "composite": true,
    "declaration": true,
    "declarationMap": true,
    "rootDir": "."
  },
  "include": ["./**/*.ts"],
  "references": [
    {
      "path": "../myReferencedProject",
      "prepend": true
    }
  ]
}
```

핵심 설정:
- `composite`: 이 폴더가 더 큰 프로젝트의 하위 프로젝트임을 표시
- `declaration`: `.d.ts` 타입 선언 파일 생성 (다른 하위 프로젝트가 이를 참조하므로 재컴파일 범위 제한 → 속도 향상)
- `declarationMap`: 타입 선언 파일의 소스 맵 생성
- `references`: 이 하위 프로젝트가 의존하는 다른 하위 프로젝트 목록

**3단계**: 루트 `tsconfig.json`

```json
{
  "files": [],
  "references": [
    {"path": "./myProject"},
    {"path": "./mySecondProject"}
  ]
}
```

**4단계**: `--build` 플래그로 빌드

```bash
tsc --build   # 또는 tsc -b
```

### extends로 tsconfig 공통화

```json
// tsconfig.base.json (루트)
{
  "compilerOptions": {
    "composite": true,
    "declaration": true,
    "declarationMap": true,
    "lib": ["es2015", "es2016.array.include"],
    "sourceMap": true,
    "strict": true,
    "target": "es5"
  }
}

// 하위 프로젝트 tsconfig.json
{
  "extends": "../tsconfig.base",
  "include": ["./**/*.ts"],
  "references": [{"path": "../myReferencedProject", "prepend": true}]
}
```

---

## 6. 서버에서 TypeScript 실행 (Running TypeScript on the Server)

```json
{
  "compilerOptions": {
    "target": "es2015",
    "module": "commonjs"
  }
}
```

- `module: "commonjs"`: ES2015 `import`/`export`를 NodeJS의 `require`/`module.exports`로 변환
- 소스 맵 지원: `source-map-support` 패키지 설치 후 설정

---

## 7. 브라우저에서 TypeScript 실행 (Running TypeScript in the Browser)

### 모듈 포맷 선택

| 상황 | module 설정 |
|------|------------|
| NPM에 라이브러리 배포 | `umd` (최대 호환성) |
| Webpack / Rollup 번들러 | `es2015` 이상 |
| 동적 임포트 사용 시 | `esnext` |
| Browserify (CommonJS) | `commonjs` |
| RequireJS (AMD) | `amd` |
| SystemJS | `systemjs` |

### 번들러 플러그인

| 번들러 | TypeScript 플러그인 |
|--------|-------------------|
| Webpack | `ts-loader` |
| Browserify | `tsify` |
| Babel | `@babel/preset-typescript` |
| Gulp | `gulp-typescript` |
| Grunt | `grunt-ts` |

### 브라우저 빌드 최적화 팁

- 코드를 **모듈화**하여 트리 쉐이킹이 잘 동작하게 할 것
- **동적 임포트**로 초기 로딩에 불필요한 코드는 지연 로딩
- 빌드 도구의 **자동 코드 분할** 활용
- 페이지 로드 시간을 측정할 **전략 수립** (New Relic, Datadog 등)
- 개발 빌드와 프로덕션 빌드를 **최대한 유사하게** 유지
- 브라우저 기능 누락에 대한 **폴리필 전략** 수립

---

## 8. NPM에 배포 (Publishing to NPM)

### tsconfig.json 설정

```json
{
  "compilerOptions": {
    "declaration": true,
    "module": "umd",
    "sourceMap": true,
    "target": "es5"
  }
}
```

### .npmignore / .gitignore 설정

```
# .npmignore
*.ts       # .ts 파일 제외
!*.d.ts    # .d.ts 파일은 허용

# .gitignore
*.d.ts     # 생성된 타입 선언 제외
*.js       # 생성된 JS 제외
```

`src/` / `dist/` 구조 사용 시:

```
# .npmignore
src/   # 소스 파일 제외

# .gitignore
dist/  # 생성 파일 제외
```

### package.json 설정

```json
{
  "name": "my-awesome-typescript-project",
  "version": "1.0.0",
  "main": "dist/index.js",
  "types": "dist/index.d.ts",
  "scripts": {
    "prepublishOnly": "tsc -d"
  }
}
```

- `"types"`: TypeScript 소비자에게 타입 선언 파일 위치 안내
- `"prepublishOnly"`: `npm publish` 전에 자동으로 컴파일 실행

---

## 9. 트리플 슬래시 지시자 (Triple-Slash Directives)

TypeScript 전용 특수 주석 형태의 컴파일러 지시자.

### types 지시자

모듈 전체를 임포트(사이드 이펙트용)하되, 컴파일 결과에 JS `import`/`require`를 생성하지 않고 싶을 때 사용.

```typescript
/// <reference types="./global" />        // 로컬 앰비언트 타입 선언 참조
/// <reference types="jasmine" />         // @types/jasmine 참조
```

> **Import Elision(임포트 생략)**: `import`한 것이 타입 위치에서만 사용되면 TSC가 자동으로 해당 임포트를 JS 출력에서 제거한다.

### amd-module 지시자

AMD 모듈 포맷으로 컴파일할 때, 익명 AMD 모듈에 이름을 붙인다.

```typescript
/// <amd-module name="LogService" />
export let LogService = {
  log() {
    // ...
  }
}
```

컴파일 결과:

```javascript
define('LogService', ['require', 'exports'], function(require, exports) {
  exports.__esModule = true
  exports.LogService = { log() { /* ... */ } }
})
```

---

## 핵심 요약

| 주제 | 핵심 설정 / 도구 |
|------|----------------|
| 컴파일 대상 | `target`, `lib` |
| 소스 맵 | `"sourceMap": true` + `source-map-support` |
| 빌드 가속 | 프로젝트 레퍼런스 (`composite`, `tsc -b`) |
| 서버 실행 | `"module": "commonjs"` |
| 브라우저 실행 | 번들러(Webpack/Rollup) + `ts-loader` 등 |
| NPM 배포 | `declaration: true`, `types` 필드, `prepublishOnly` |
| 에러 모니터링 | Sentry, Bugsnag |

> TypeScript 코드를 JavaScript로 컴파일하면 Node.js든 브라우저든 실행 방식은 일반 JS와 동일하다. TypeScript는 빌드 타임에 타입 안전성을 보장하고, 런타임은 컴파일된 JS가 담당한다.
