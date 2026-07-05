# Chapter 10. Namespaces.Modules

> 코드를 캡슐화하는 방법: 함수 → 클래스 → 모듈/네임스페이스 → 패키지. TypeScript는 ES2015 표준 모듈 시스템과 네임스페이스를 모두 지원한다.

---

## JavaScript 모듈의 역사 (A Brief History of JavaScript Modules)

| 시기 | 방식 | 특징 |
|------|------|------|
| 1995 | 전역 변수 (`window.module`) | 모듈 시스템 없음, 이름 충돌 발생 |
| 2004–2009 | LABjs, YUI, Dojo | 동적(지연) 로딩 모듈 |
| 2009 | CommonJS (NodeJS) | `require()` / `module.exports` |
| 2008 | AMD (RequireJS) | 브라우저용 비동기 모듈 |
| 2011 | Browserify | 프론트엔드에서 CommonJS 사용 가능 |
| 2015 | ES2015 (ESM) | `import` / `export` 표준, 정적 분석 가능 |
| 2016–2018 | Webpack 2, Rollup | 트리 쉐이킹(tree-shaking) 지원, ESM 번들링 주류화 |
| 2018 | dynamic `import()` | ES2020에 표준화, 코드 분할·지연 로딩 공식 지원 |
| 2019–2020 | Node.js ESM 지원 | Node.js 12+에서 `.mjs` 또는 `"type": "module"` 설정으로 ESM 사용 가능 |
| 2020 | `import.meta` | 모듈 내부에서 현재 파일의 URL 등 메타정보 접근 (`import.meta.url`) |
| 2021 | Vite, esbuild | 네이티브 ESM 기반 차세대 번들러 등장, 빌드 속도 10~100배 향상 |
| 2022 | Node.js 18 LTS | ESM 완전 안정화, `--experimental` 플래그 없이 사용 가능 |
| 2023 | Import Maps (브라우저) | `<script type="importmap">`으로 브라우저에서 npm 스타일 모듈 이름 사용 가능, 모든 주요 브라우저 지원 |
| 2023 | Deno 1.x, Bun 1.0 | URL 기반 임포트 / Node.js 호환 ESM을 기본 모듈 시스템으로 채택 |
| 2024–2025 | Node.js 22/23 | `require()`로 ESM 파일 직접 로드 가능 (`--experimental-require-module`), CommonJS ↔ ESM 상호운용성 개선 |
| 2025–2026 | TypeScript ESM 완전 전환 | `"module": "nodenext"` / `"node16"` 설정이 주류, `.js` 확장자 명시 임포트 권장 |

---

## 1. import / export (ES2015 모듈)

> **권장**: CommonJS, 전역 변수, 네임스페이스보다 ES2015 모듈을 사용하라.

### 기본 내보내기/가져오기

```typescript
// a.ts
export function foo() {}
export function bar() {}

// b.ts
import {foo, bar} from './a'
foo()
export let result = bar()
```

### 기본 내보내기 (default export)

```typescript
// c.ts
export default function meow(loudness: number) {}

// d.ts
import meow from './c'   // 중괄호 없음
meow(11)
```

### 와일드카드 가져오기

```typescript
// e.ts
import * as a from './a'
a.foo()
a.bar()
```

### 재내보내기 (Re-export)

```typescript
// f.ts
export * from './a'           // a의 모든 것을 재내보냄
export {result} from './b'    // b의 result만
export meow from './c'        // c의 default를
```

### 타입과 값 동시 내보내기

타입과 값은 별도의 네임스페이스에 존재하므로 같은 이름을 사용해도 된다:

```typescript
// g.ts
export let X = 3
export type X = {y: string}

// h.ts
import {X} from './g'

let a = X + 1          // X는 값 (number)
let b: X = {y: 'z'}   // X는 타입
```

---

## 2. 동적 임포트 (Dynamic Imports)

앱이 커질수록 초기 로딩 시간이 증가한다. **코드 분할(code splitting)**과 **지연 로딩(lazy loading)**으로 해결한다.

```typescript
let locale = await import('locale_us-en')
```

`import`를 **구문**으로 쓰면 정적 임포트, **함수**로 쓰면 Promise를 반환하는 동적 임포트가 된다.

### 타입 안전한 동적 임포트

```typescript
import {locale} from './locales/locale-us'   // 타입 용도로만 사용

async function main() {
  let userLocale = await getUserLocale()
  let path = `./locales/locale-${userLocale}`
  let localeUS: typeof locale = await import(path)  // 타입을 명시
}
```

> 임의의 표현식을 `import()`에 전달하면 타입 안전성을 잃는다. 타입 안전성을 유지하려면 문자열 리터럴을 직접 전달하거나, 위처럼 `typeof`로 타입을 명시한다.

> **TSC 설정**: 동적 임포트는 `{"module": "esnext"}` 설정에서만 지원된다.

---

## 3. CommonJS / AMD 코드 사용

```typescript
// CommonJS/AMD 모듈도 ES2015 스타일로 가져올 수 있음
import {something} from './a/legacy/commonjs/module'

// CommonJS 기본 내보내기는 와일드카드로 가져와야 함 (기본 설정)
import * as fs from 'fs'
fs.readFile('some/file.txt')

// esModuleInterop: true 설정 시 기본 내보내기처럼 가져올 수 있음
import fs from 'fs'
fs.readFile('some/file.txt')
```

> `tsconfig.json`에 `{"esModuleInterop": true}` 설정 시 CommonJS 모듈을 ES2015 기본 임포트처럼 사용할 수 있다.

---

## 4. 모듈 모드 vs 스크립트 모드 (Module Mode vs Script Mode)

TypeScript는 파일에 `import`나 `export`가 있으면 **모듈 모드**, 없으면 **스크립트 모드**로 파싱한다.

| 구분 | 모듈 모드 | 스크립트 모드 |
|------|---------|------------|
| 변수 범위 | 파일 내부에만 존재 | 전역에서 접근 가능 |
| 임포트 방식 | `import` 필수 | UMD 전역 변수 직접 사용 가능 |
| 주요 용도 | 실제 애플리케이션 코드 | 빠른 프로토타입, 타입 선언 파일 |

> **실무에서는 항상 모듈 모드를 사용한다.**

---

## 5. 네임스페이스 (Namespaces)

TypeScript만의 코드 캡슐화 방법. Java의 패키지, C#의 namespace와 유사하다.

> **권장**: 모듈을 선호하고, 불필요한 경우 네임스페이스는 피하라.

### 기본 사용

```typescript
// Get.ts
namespace Network {
  export function get<T>(url: string): Promise<T> {
    // ...
  }
}

// App.ts
namespace App {
  Network.get<GitRepo>('https://api.github.com/repos/Microsoft/typescript')
}
```

- `export`된 것만 외부에서 접근 가능 (미내보낸 것은 private)
- 네임스페이스 안에 네임스페이스를 중첩할 수 있음

### 중첩 네임스페이스

```typescript
namespace Network {
  export namespace HTTP {
    export function get<T>(url: string): Promise<T> {
      // ...
    }
  }
  export namespace TCP {
    export function listenOn(port: number): Connection {
      // ...
    }
  }
  export namespace UDP { /* ... */ }
}

// 사용
Network.HTTP.get<Dog[]>('http://url.com/dogs')
Network.TCP.listenOn(8080)
```

### 파일 분리 및 병합

인터페이스처럼 같은 이름의 네임스페이스는 자동으로 병합된다:

```typescript
// HTTP.ts
namespace Network {
  export namespace HTTP {
    export function get<T>(url: string): Promise<T> { /* ... */ }
  }
}

// UDP.ts
namespace Network {
  export namespace UDP {
    export function send(url: string, packets: Buffer): Promise<void> { /* ... */ }
  }
}

// MyApp.ts
Network.HTTP.get<Dog[]>('http://url.com/dogs')
Network.UDP.send('http://url.com/cats', new Buffer(123))
```

### 별칭 (Alias)

긴 네임스페이스 계층을 짧게 줄일 수 있다:

```typescript
// A.ts
namespace A {
  export namespace B {
    export namespace C {
      export let d = 3
    }
  }
}

// MyApp.ts
import d = A.B.C.d    // 구조 분해 아님! import = 별칭

let e = d * 3
```

> ES2015 모듈의 `import {d}` 구조 분해와 다르다. 네임스페이스 별칭은 `import 이름 = 경로` 형태다.

### 충돌 (Collisions)

같은 이름의 내보내기는 허용되지 않는다:

```typescript
// Error TS2393: Duplicate function implementation.
namespace Network {
  export function request<T>(url: string): T { /* ... */ }
}
namespace Network {
  export function request<T>(url: string): T { /* ... */ }
}
```

단, **오버로드 앰비언트 함수 선언**은 예외적으로 허용된다:

```typescript
namespace Network {
  export function request<T>(url: string): T
}
namespace Network {
  export function request<T>(url: string, priority: number): T
}
namespace Network {
  export function request<T>(url: string, algo: 'SHA1' | 'SHA256'): T
}
```

### 컴파일 출력

네임스페이스는 `tsconfig.json`의 `module` 설정을 무시하고 항상 **전역 변수**로 컴파일된다:

```typescript
// 입력: Flowers.ts
namespace Flowers {
  export function give(count: number) {
    return count + ' flowers'
  }
}
```

```javascript
// 출력: Flowers.js
let Flowers
;(function (Flowers) {          // IIFE로 클로저 생성
  function give(count) {
    return count + ' flowers'
  }
  Flowers.give = give           // export된 것만 할당
})(Flowers || (Flowers = {}))   // 기존 Flowers 확장 또는 새로 생성
```

---

## 6. 선언 병합 (Declaration Merging)

TypeScript는 같은 이름의 여러 선언을 병합할 수 있다.

### 병합 가능 여부 (일부)

| | 값 | 클래스 | enum | 함수 | 타입 별칭 | 인터페이스 | 네임스페이스 |
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **값** | ✗ | ✗ | ✗ | ✗ | ✓ | ✓ | ✓ |
| **enum** | — | — | ✓ | ✗ | ✗ | ✗ | ✓ |
| **인터페이스** | — | — | — | — | ✗ | ✓ | ✓ |
| **네임스페이스** | — | — | — | — | — | — | ✓ |

**활용 패턴**:
- **값 + 타입 별칭** → 컴패니언 객체 패턴 (같은 이름으로 값과 타입 동시 사용)
- **enum + 네임스페이스** → enum에 정적 메서드 추가
- **인터페이스 + 네임스페이스** → 컴패니언 객체 패턴의 대안
- **모듈 + 모듈** → 서드파티 모듈 선언 확장

```typescript
// enum + namespace 병합으로 정적 메서드 추가
enum Direction {
  Up = 'UP',
  Down = 'DOWN'
}

namespace Direction {
  export function opposite(d: Direction): Direction {
    return d === Direction.Up ? Direction.Down : Direction.Up
  }
}

Direction.opposite(Direction.Up) // Direction.Down
```

---

## 7. moduleResolution 플래그

`tsconfig.json`의 `moduleResolution` 설정으로 모듈 이름 해석 알고리즘을 제어한다.

| 모드 | 설명 | 권장 여부 |
|------|------|---------|
| `node` | NodeJS의 모듈 해석 알고리즘 사용. `.ts` → `.tsx` → `.d.ts` → `.js` 순서로 탐색 | ✓ **항상 이것 사용** |
| `classic` | 현재 폴더에서 상위로 파일을 탐색. 빌드 도구와 호환성 문제 발생 | ✗ 사용 금지 |

---

## 핵심 요약

| 개념 | 내용 |
|------|------|
| **ES2015 모듈** | `import`/`export` — 권장 방식, 정적 분석 가능 |
| **동적 임포트** | `import()` — 코드 분할·지연 로딩, `esnext` 모드 필요 |
| **CommonJS 호환** | `esModuleInterop: true`로 기본 임포트처럼 사용 |
| **모듈 모드** | `import`/`export` 있으면 자동 선택, 변수 범위가 파일로 제한 |
| **네임스페이스** | TypeScript 전용 캡슐화, 항상 전역 변수로 컴파일됨 |
| **선언 병합** | 같은 이름의 타입/값/네임스페이스를 병합하는 TypeScript 특성 |

> 모듈과 네임스페이스 중 선택해야 한다면 **모듈을 선택**하라. 명시적 의존성, 모듈 격리, 정적 분석, 성능(트리 쉐이킹) 모든 면에서 유리하다.

---

## 연습 문제

1. 선언 병합을 활용하여:
   - a. 값과 타입 별칭 대신 **네임스페이스와 인터페이스**로 컴패니언 객체 패턴을 다시 구현하라.
   - b. **enum과 네임스페이스를 병합**하여 enum에 정적 메서드를 추가하라.
