# Appendix (부록)

TypeScript의 타입 연산자, 유틸리티 타입, 선언 범위, 서드파티 타입 선언 작성법, 트리플 슬래시 지시자, 안전성 관련 컴파일러 플래그, TSX 훅 등을 정리한 레퍼런스.

---

## Appendix A. 타입 연산자 (Type Operators)

| 타입 연산자 | 문법 | 적용 대상 |
|------------|------|---------|
| 타입 쿼리 | `typeof`, `instanceof` | 모든 타입 |
| 키 추출 | `keyof` | 객체 타입 |
| 프로퍼티 접근 | `O[K]` | 객체 타입 |
| 매핑 타입 | `[K in O]` | 객체 타입 |
| 수정자 추가 | `+` | 객체 타입 |
| 수정자 제거 | `-` | 객체 타입 |
| 읽기 전용 수정자 | `readonly` | 객체, 배열, 튜플 타입 |
| 선택적 수정자 | `?` | 객체 타입, 튜플, 함수 파라미터 타입 |
| 조건부 타입 | `T extends U ? X : Y` | 제네릭 타입, 타입 별칭, 함수 파라미터 |
| 비-null 단언 | `!` | nullable 타입 |
| 제네릭 타입 파라미터 기본값 | `=` | 제네릭 타입 |
| 타입 단언 | `as`, `<>` | 모든 타입 |
| 타입 가드 | `is` | 함수 반환 타입 |

---

## Appendix B. 내장 타입 유틸리티 (Type Utilities)

TypeScript 표준 라이브러리에 포함된 내장 타입 유틸리티. 최신 목록은 `es5.d.ts` 참고.

| 유틸리티 타입 | 적용 대상 | 설명 |
|-------------|---------|------|
| `ConstructorParameters<T>` | 클래스 생성자 타입 | 생성자 파라미터 타입의 튜플 |
| `Exclude<T, U>` | 유니온 타입 | `T`에서 `U`에 할당 가능한 타입 제거 |
| `Extract<T, U>` | 유니온 타입 | `T`에서 `U`에 할당 가능한 서브타입만 선택 |
| `InstanceType<T>` | 클래스 생성자 타입 | `new`로 생성한 인스턴스 타입 |
| `NonNullable<T>` | nullable 타입 | `null`과 `undefined` 제거 |
| `Parameters<T>` | 함수 타입 | 함수 파라미터 타입의 튜플 |
| `Partial<T>` | 객체 타입 | 모든 프로퍼티를 선택적으로 만듦 |
| `Pick<T, K>` | 객체 타입 | 지정한 키만 포함하는 서브타입 |
| `Readonly<T>` | 배열, 객체, 튜플 타입 | 모든 프로퍼티를 읽기 전용으로 만듦 |
| `ReadonlyArray<T>` | 모든 타입 | 불변 배열 타입 |
| `Record<K, V>` | 객체 타입 | 키 타입 → 값 타입 매핑 |
| `Required<T>` | 객체 타입 | 모든 프로퍼티를 필수로 만듦 |
| `ReturnType<T>` | 함수 타입 | 함수의 반환 타입 |

---

## Appendix C. 선언 범위 (Scoped Declarations)

### 타입과 값 생성 여부

| 키워드 | 타입 생성 | 값 생성 |
|--------|:-------:|:------:|
| `class` | ✓ | ✓ |
| `const`, `let`, `var` | ✗ | ✓ |
| `enum` | ✓ | ✓ |
| `function` | ✗ | ✓ |
| `interface` | ✓ | ✗ |
| `namespace` | ✓ | ✓ |
| `type` | ✓ | ✗ |

### 선언 병합 가능 여부

|  | 값 | 클래스 | enum | 함수 | 타입 별칭 | 인터페이스 | 네임스페이스 | 모듈 |
|--|:--:|:----:|:----:|:----:|:-------:|:--------:|:----------:|:---:|
| **값** | ✗ | ✗ | ✗ | ✗ | ✓ | ✓ | ✓ | — |
| **enum** | — | — | ✓ | ✗ | ✗ | ✗ | ✓ | — |
| **인터페이스** | — | — | — | — | ✗ | ✓ | ✓ | — |
| **네임스페이스** | — | — | — | — | — | — | ✓ | — |
| **모듈** | — | — | — | — | — | — | — | ✓ |

---

## Appendix D. 서드파티 JavaScript 모듈 타입 선언 작성법

### `.ts` vs `.d.ts` 선언 비교

| `.ts` | `.d.ts` |
|-------|---------|
| `var a = 1` | `declare var a: number` |
| `let a = 1` | `declare let a: number` |
| `const a = 1` | `declare const a: 1` |
| `function a(b) { ... }` | `declare function a(b: number): string` |
| `class A { b() { return 3 } }` | `declare class A { b(): number }` |
| `namespace A {}` | `declare namespace A {}` |
| `type A = number` | `type A = number` |
| `interface A { b?: string }` | `interface A { b?: string }` |

### 내보내기 방식별 선언 패턴

#### 전역(Global) 선언

`import`/`export` 없이 스크립트 모드 파일로 작성. `declare`를 붙이면 전역 사용 가능.

```typescript
declare let someGlobal: GlobalType
declare class GlobalClass {}
declare function globalFunction(): string
enum GlobalEnum { A, B, C }
namespace GlobalNamespace {}
type GlobalType = number
interface GlobalInterface {}
```

#### ES2015 exports

```typescript
export default defaultExport
export class SomeExport { a: SomeType }
export function exportedFunction(): string
export type SomeType = { a: number }
export interface SomeOtherType { b: string }
```

#### CommonJS exports

```typescript
declare let defaultExport: SomeType
export = defaultExport
```

복수 export는 네임스페이스로 묶어 `export =`로 내보냄:

```typescript
declare namespace MyNamedExports {
  export let someExport: SomeType
  export type SomeType = number
  export class OtherExport { otherType: string }
}
export = MyNamedExports
```

기본 export + named export 동시 지원 시 선언 병합 활용:

```typescript
declare namespace MyExports {
  export let someExport: SomeType
  export type SomeType = number
}
declare function MyExports(a: number): string
export = MyExports
```

#### UMD exports

ES2015와 동일하되, 스크립트 모드에서 전역 사용을 허용하려면 `export as namespace` 추가:

```typescript
export class SomeExport { a: SomeType }
export type SomeType = { a: number }
export as namespace MyModule   // 스크립트 모드에서 MyModule.SomeExport로 접근 가능
```

### 모듈 확장 (Extending a Module)

#### 전역(Interface/Namespace) 확장

스크립트 모드 파일에서 같은 이름의 `interface`나 `namespace`를 선언하면 병합된다:

```typescript
// jquery-extensions.d.ts (스크립트 모드)
interface JQuery {
  marquee(speed: number): JQuery<HTMLElement>
}
```

#### 모듈 exports 확장

```typescript
// react-extensions.d.ts
import 'react'   // 모듈 모드로 전환

declare module 'react' {
  interface Component<P, S> {
    reducer(action: object, state: S): S
  }
}
```

> **주의**: 모듈 확장은 로드 순서에 의존하고 원본 타입 선언이 변경되면 깨질 수 있다. 가능하면 합성(composition)을 사용하라.

---

## Appendix E. 트리플 슬래시 지시자 (Triple-Slash Directives)

파일 최상단에 작성하는 TypeScript 전용 컴파일러 지시자. 코드보다 앞에 위치해야 한다.

```typescript
/// <directive attr="value" />
```

### 주요 지시자

| 지시자 | 문법 | 용도 |
|--------|------|------|
| `amd-module` | `/// <amd-module name="MyComponent" />` | AMD 모듈 컴파일 시 내보내기 이름 선언 |
| `lib` | `/// <reference lib="dom" />` | 의존하는 TypeScript 내장 lib 선언 |
| `path` | `/// <reference path="./path.ts" />` | 의존하는 TypeScript 파일 선언 (`outFile` 사용 시) |
| `type` | `/// <reference types="./path.d.ts" />` | 의존하는 타입 선언 파일 선언 |

### 내부 지시자 (거의 사용 안 함)

| 지시자 | 문법 | 용도 |
|--------|------|------|
| `no-default-lib` | `/// <reference no-default-lib="true" />` | 이 파일에 lib 전혀 사용 안 함 |

### 더 이상 사용하지 않는 지시자

| 지시자 | 대신 사용 |
|--------|---------|
| `amd-dependency` | 일반 `import` 사용 |

---

## Appendix F. 안전성 관련 TSC 컴파일러 플래그

> 전체 플래그 목록은 [TypeScript Handbook 웹사이트](https://www.typescriptlang.org/tsconfig) 참고.

`strict` 플래그를 켜면 아래 `strict*` 플래그들이 한 번에 활성화된다.

| 플래그 | 설명 |
|--------|------|
| `alwaysStrict` | `'use strict'` 구문 자동 삽입 |
| `noEmitOnError` | 타입 오류가 있으면 JavaScript 출력 안 함 |
| `noFallthroughCasesInSwitch` | `switch`의 모든 `case`에 `return` 또는 `break` 강제 |
| `noImplicitAny` | 타입이 `any`로 추론되면 오류 |
| `noImplicitReturns` | 모든 코드 경로에서 명시적 `return` 강제 |
| `noImplicitThis` | `this` 타입을 명시하지 않으면 오류 |
| `noUnusedLocals` | 미사용 지역 변수 경고 |
| `noUnusedParameters` | 미사용 함수 파라미터 경고 (이름 앞에 `_` 붙이면 무시) |
| `strictBindCallApply` | `bind`, `call`, `apply`에 타입 안전성 강제 |
| `strictFunctionTypes` | 함수 파라미터와 `this` 타입에 반변성(contravariance) 적용 |
| `strictNullChecks` | `null`과 `undefined`를 별도 타입으로 승격 |
| `strictPropertyInitialization` | 클래스 프로퍼티가 nullable이 아니면 초기화 강제 |

---

## Appendix G. TSX

> React를 사용하는 경우 이 훅을 직접 다룰 필요는 없다. TypeScript로 TSX를 사용하는 **라이브러리를 직접 만들 때** 필요한 저수준 레퍼런스.

TypeScript는 `global.JSX` 네임스페이스의 특수 타입들을 TSX 타입 추론의 기준으로 삼는다.

### TSX 요소 종류

| 종류 | 이름 규칙 | 예시 |
|------|---------|------|
| 내재적 요소 (Intrinsic) | 소문자 | `<div>`, `<li>`, `<h1>` |
| 값 기반 요소 (Value-based) | PascalCase | `<MyComponent>` |

### JSX 네임스페이스 훅

```typescript
declare global {
  namespace JSX {
    interface Element extends React.ReactElement<any> {}
    // 값 기반 TSX 요소의 타입

    interface ElementClass extends React.Component<any> {
      render(): React.ReactNode
    }
    // 클래스 컴포넌트 인스턴스 타입 (<MyComp />의 클래스가 만족해야 함)

    interface ElementAttributesProperty { props: {} }
    // 컴포넌트가 지원하는 속성 이름을 어디서 찾을지 지정 (React: props)

    interface ElementChildrenAttribute { children: {} }
    // 자식 요소 타입을 어디서 찾을지 지정 (React: children)

    type LibraryManagedAttributes<C, P> = // ...
    // propTypes, defaultProps 같은 라이브러리 관리 속성 선언 위치

    interface IntrinsicAttributes extends React.Attributes {}
    // 모든 내재 요소가 공통으로 지원하는 속성 (React: key)

    interface IntrinsicClassAttributes<T> extends React.ClassAttributes<T> {}
    // 모든 클래스 컴포넌트가 공통으로 지원하는 속성 (React: ref)

    interface IntrinsicElements {
      a: React.DetailedHTMLProps<React.AnchorHTMLAttributes<HTMLAnchorElement>, HTMLAnchorElement>
      div: React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement>
      // ... 모든 HTML 요소 열거
    }
    // 사용 가능한 HTML 태그명 → 속성/자식 타입 매핑
  }
}
```

### JSX 훅 요약

| 훅 | 역할 |
|----|------|
| `JSX.Element` | 값 기반 TSX 요소의 타입 |
| `JSX.ElementClass` | 클래스 컴포넌트 인스턴스 타입 |
| `JSX.ElementAttributesProperty` | 컴포넌트 속성을 읽어올 프로퍼티 이름 |
| `JSX.ElementChildrenAttribute` | 자식 타입을 읽어올 프로퍼티 이름 |
| `JSX.LibraryManagedAttributes` | `propTypes`, `defaultProps` 등 라이브러리 관리 속성 |
| `JSX.IntrinsicAttributes` | 모든 내재 요소 공통 속성 |
| `JSX.IntrinsicClassAttributes` | 모든 클래스 컴포넌트 공통 속성 |
| `JSX.IntrinsicElements` | 사용 가능한 HTML 요소 목록 및 속성 타입 |

> `global.JSX` 네임스페이스에 이 타입들을 선언하면 TSX 타입 검사 동작을 커스터마이징할 수 있다.
