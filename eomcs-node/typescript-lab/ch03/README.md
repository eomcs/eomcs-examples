# Chapter 3. All About Types

> 어떤 값이 T 타입이라면, 이 값을 가지고 어떤 일을 할 수 있고 어떤 일을 할 수 없는지도 알 수 있다. 여기서 중요한 것은 **타입 검사기(Type Checker)를 이용해 유효하지 않은 동작이 실행되는 일을 예방하는 것**이다. 

이번 장에서는 타입스크립트에서 이용할 수 있는 타입을 살펴보고, 각각의 타입으로 무엇을 할 수 있는지 살펴본다.

---

## 타입(Type)이란?

> **TYPE**: 값들의 집합과 그 값들로 할 수 있는 연산들의 집합.

| 타입 | 값의 집합 | 주요 연산 |
|------|-----------|-----------|
| `boolean` | `true`, `false` | `||`, `&&`, `!` |
| `number` | 모든 숫자 (정수, 실수, Infinity, NaN 등) | `+`, `-`, `*`, `/`, `%` |
| `string` | 모든 문자열 | `+`, `.concat()`, `.toUpperCase()` 등 |

---

## 타입을 말하는 방법 (Talking About Types)

```typescript
function squareOf(n: number) {
  return n * n
}
squareOf(2)    // 4
squareOf('z')  // Error TS2345: Argument of type '"z"' is not assignable to parameter of type 'number'.
```

- **constrained(제약됨)**: `n`은 `number` 타입으로 제약되어 있다.
- **assignable(할당 가능)**: `2`의 타입은 `number`에 할당 가능(compatible)하다.

---

## 타입의 종류 (The ABCs of Types)

### any

- 모든 값의 집합. 어떤 연산도 허용한다.
- 타입 체커를 완전히 무력화시키므로 **사용을 피해야 한다**.
- 불가피한 경우에는 명시적으로 `: any`를 선언해야 한다.

```typescript
let a: any = 666         // any
let b: any = ['danger']  // any
let c = a + b            // any (오류 없음 — 타입 체커가 동작하지 않음)
```

> **TSC 플래그(tsconfig.json)**: `noImplicitAny` — TypeScript가 암묵적으로 `any`라고 추론되는 타입을 만나면 오류 발생를 발생시킨다. strict 패밀리에 속하므로, `strict: true`로 strict 모드를 활성화했다면 따로 이 플래그를 설정할 필요가 없다.

---

### unknown

- `any`처럼 모든 값을 표현하지만, **사용 전에 반드시 타입을 좁혀야(refine)** 한다.
- TypeScript는 `unknown`을 스스로 추론하지 않는다. 직접 명시해야 한다.

```typescript
let a: unknown = 30          // unknown
let b = a === 123            // boolean (비교는 가능)
let c = a + 10               // Error TS2571: Object is of type 'unknown'.
if (typeof a === 'number') {
  let d = a + 10             // number (타입을 좁힌 후에는 사용 가능)
}
```

---

### boolean

두 가지 값 `true`, `false`만 가진다.

```typescript
let a = true           // boolean (추론)
var b = false          // boolean (추론)
const c = true         // true   (리터럴 타입으로 추론 — const이므로)
let d: boolean = true  // boolean (명시)
let e: true = true     // true   (리터럴 타입 명시)
let f: true = false    // Error TS2322: Type 'false' is not assignable to type 'true'.
```

> **타입 리터럴(Type Literal)**: 오직 하나의 값을 나타내는 타입.  
> `const`로 선언하면 TypeScript가 가장 좁은 타입(리터럴 타입)을 추론한다.

---

### number

정수, 실수, `Infinity`, `NaN` 등 모든 숫자.

```typescript
let a = 1234           // number (추론)
const b = 5678         // 5678  (리터럴 타입)
const c:number = 5678  // number (명시)
let d: 26.218 = 26.218 // 26.218
let e: 26.218 = 10     // Error TS2322: Type '10' is not assignable to type '26.218'.

// 긴 숫자는 숫자 구분자(numeric separator)를 사용
let oneMillion = 1_000_000

// 숫자 구분자는 타입에도 사용할 수 있다.
let twoMillion: 2_000_000 = 2_000_000
```

---

### bigint

2⁵³보다 큰 정수를 정밀하게 다룰 수 있다.

```typescript
let a = 1234n           // bigint (추론)
const b = 5678n         // 5678n (리터럴 타입)
let c: bigint = 5678n  // bigint (명시)
let d = 88.5n           // Error TS1353: A bigint literal must be an integer.
let e: bigint = 100     // Error TS2322: Type '100' is not assignable to type 'bigint'.
```

---

### string

모든 문자열.

```typescript
let a = 'hello'         // string (추론)
const b = '!'           // '!'   (리터럴 타입)
let c: string = 'world'  // string (명시)
let d: 'john' = 'john'  // 'john'
let e: 'john' = 'zoe'   // Error TS2322: Type "zoe" is not assignable to type "john".
```

> `boolean`과 `number` 처럼 `string` 타입도 네 가지 방법으로 선언할 수 있지만, 가능하다면 타입스크립트가 string 타입을 추론하도록 두는 것이 좋다.
---

### symbol

ES2015에서 추가된 타입. 실무에서는 자주 사용하지 않는 편이며, **객체나 맵에서 문자열 키를 대신하는 용도**로 사용한다.

```typescript
let a = Symbol('a')                    // symbol
let b:symbol = Symbol('b')             // symbol
var c = a === b                        // boolean
let d = a + 'x'                        // Error TS2469: The '+' operator cannot be applied to type 'symbol'.
const e: = Symbol('e')     // typeof e (unique symbol)
const f: unique symbol = Symbol('e')     // typeof f (unique symbol)
let g: unique symbol = Symbol('e')       // Error TS1332: must be 'const'.
let i = e === f                          // Error TS2367: 두 unique symbol은 타입 자체가 다르기 때문에 비교 연산이 불가능 하다.
```

> 다음과 같이 **“외부 코드와 이름이 겹치면 안 되는 내부용 키”** 를 만들 때 심볼을 쓴다.

```typescript
const userId = Symbol('userId')

const user = {
  userId: 'Ada',    // 문자열 키
  [userId]: 1001    // 심볼 키
}
```
---

### object

TypeScript의 `object` 타입은 **구조적 타이핑(structural typing)** 을 따른다.  
객체의 이름이 아니라 **형태(shape)** 로 타입을 판단한다.

```typescript
// object 타입 — 거의 쓸모없음
let a: object = { b: 'x' }
a.b  // Error TS2339: Property 'b' does not exist on type 'object'.

// 객체 리터럴 타입 (권장)
let b = { c: { d: 'f' } }  // {c: {d: string}}

// 명시적 형태 선언
let c: { firstName: string; lastName: string } = {
  firstName: 'john',
  lastName: 'barrowman'
}
```

#### 옵셔널 프로퍼티와 인덱스 시그니처

```typescript
let a: {
  b: number               // 필수
  c?: string              // 옵셔널
  [key: number]: boolean  // 인덱스 시그니처: number 키 → boolean 값
}
```

#### readonly 프로퍼티

```typescript
let user: { readonly firstName: string } = { firstName: 'abby' }
user.firstName = 'abbey'  // Error TS2540: Cannot assign to 'firstName' because it is a read-only property.
```

#### 객체 선언 방법 요약

| 방법 | 예시 | 권장 |
|------|------|------|
| 객체 리터럴 타입(shape) | `{ a: string }` | ✅ 권장 |
| 빈 객체 리터럴 | `{}` | ❌ 피할 것 |
| `object` 타입 | `object` | ✅ (필드 무관할 때) |
| `Object` 타입 | `Object` | ❌ 피할 것 |

---

## 타입 별칭, 유니온, 인터섹션

### 타입 별칭 (Type Aliases)

변수처럼 타입에 이름을 붙인다. **DRY(Don't Repeat Yourself) 원칙** 적용.

```typescript
type Age = number

type Person = {
  name: string
  age: Age
}

let driver: Person = {
  name: 'James May',
  age: 55
}
```

- 같은 이름으로 두 번 선언 불가 (`Error TS2300: Duplicate identifier`)
- 블록 스코프를 따른다

---

### 유니온 타입 (Union Types) — `|`

A 또는 B (합집합). 실무에서 가장 자주 등장하는 타입 연산.

```typescript
type Cat = { name: string; purrs: boolean }
type Dog = { name: string; barks: boolean; wags: boolean }
type CatOrDog = Cat | Dog

function trueOrNull(isTrue: boolean): string | null {
  if (isTrue) return 'true'
  return null
}
```

---

### 인터섹션 타입 (Intersection Types) — `&`

A이면서 동시에 B (교집합).

```typescript
type CatAndDog = Cat & Dog

let b: CatAndDog = {
  name: 'Domino',
  barks: true,
  purrs: true,
  wags: true
}
```

---

## 배열 (Arrays)

```typescript
let a = [1, 2, 3]       // number[]
let b = ['a', 'b']      // string[]
let c: string[] = ['a'] // string[]
let d = [1, 'a']        // (string | number)[]

let g = []              // any[] → 요소 추가에 따라 타입이 확장됨
g.push(1)               // number[]
g.push('red')           // (string | number)[]
```

> **원칙**: 배열은 동일한 타입의 요소로 구성(homogeneous)하는 것이 좋다.  
> 두 가지 문법은 동일하다: `T[]` = `Array<T>`

---

## 튜플 (Tuples)

고정 길이 배열. 각 인덱스의 타입이 명확하게 정해진다.  
배열과 문법이 같아서 **반드시 명시적으로 타입을 선언해야 한다.**

```typescript
let a: [number] = [1]
let b: [string, string, number] = ['malcolm', 'gladwell', 1963]

// 옵셔널 요소
let trainFares: [number, number?][] = [
  [3.75],
  [8.25, 7.70]
]

// rest 요소 (최소 길이 보장)
let friends: [string, ...string[]] = ['Sara', 'Tali', 'Chloe']
let list: [number, boolean, ...string[]] = [1, false, 'a', 'b']
```

---

## 읽기 전용 배열과 튜플 (Read-only Arrays and Tuples)

```typescript
let as: readonly number[] = [1, 2, 3]
let bs = as.concat(4)   // readonly number[] (새 배열 반환)
as[4] = 5               // Error TS2542: Index signature only permits reading.
as.push(6)              // Error TS2339: Property 'push' does not exist.

// 동등한 선언 방법
type A = readonly string[]       // readonly string[]
type B = ReadonlyArray<string>   // readonly string[]
type C = Readonly<string[]>      // readonly string[]
type D = readonly [number, string]
type E = Readonly<[number, string]>
```

---

## null, undefined, void, never

| 타입 | 의미 |
|------|------|
| `null` | 값의 부재 (의도적으로 비어있음) |
| `undefined` | 아직 값이 할당되지 않은 변수 |
| `void` | 명시적 `return`이 없는 함수의 반환 타입 |
| `never` | 절대 반환하지 않는 함수의 반환 타입 |

```typescript
function a(x: number): number | null {
  if (x < 10) return x
  return null
}

function b(): undefined {
  return undefined
}

function c(): void {
  let a = 2 + 2  // return 없음
}

function d(): never {
  throw TypeError('I always error')
}

function e(): never {
  while (true) { /* 무한루프 */ }
}
```

> `never`는 모든 타입의 **서브타입(bottom type)**. 어디에든 할당 가능하다.  
> `unknown`은 모든 타입의 **슈퍼타입(top type)**.

> **`strictNullChecks`**: `strict: true`에 포함됨. 이 옵션이 없으면 `null`이 모든 타입의 서브타입이 되어 런타임 null pointer exception이 발생할 수 있다.

---

## 열거형 (Enums)

키를 값에 매핑하는 순서 없는 자료구조. 컴파일 타임에 키가 고정된다.

```typescript
enum Language {
  English,  // 0
  Spanish,  // 1
  Russian   // 2
}

let myLang = Language.Russian      // Language
let myLang2 = Language['English']  // Language
```

### const enum (더 안전한 방법)

역방향 조회를 막아 타입 안전성을 높인다.

```typescript
const enum Language {
  English,
  Spanish,
  Russian
}

let a = Language.English   // Language
let b = Language.Tagalog   // Error TS2339: Property 'Tagalog' does not exist.
let c = Language[0]        // Error TS2476: const enum은 역방향 조회 불가.
```

### 문자열 값 enum (숫자 enum의 위험성 회피)

숫자 값 enum은 임의의 숫자도 할당 가능해 타입 불안전하다.  
**문자열 값을 사용해야 안전하다.**

```typescript
const enum Flippable {
  Burger = 'Burger',
  Chair = 'Chair',
  Cup = 'Cup'
}

function flip(f: Flippable) { return 'flipped it' }

flip(Flippable.Chair)  // OK
flip(12)               // Error TS2345: Argument of type '12' is not assignable.
flip('Hat')            // Error TS2345: Argument of type '"Hat"' is not assignable.
```

> **경고**: enum은 함정이 많으므로 가능하면 사용을 피하는 것이 좋다.

---

## 타입과 서브타입 요약

| 타입 | 서브타입 (더 구체적) |
|------|---------------------|
| `boolean` | Boolean 리터럴 (`true`, `false`) |
| `number` | Number 리터럴 (`1`, `26.218`, ...) |
| `bigint` | BigInt 리터럴 (`1n`, ...) |
| `string` | String 리터럴 (`'hello'`, ...) |
| `symbol` | `unique symbol` |
| `object` | Object 리터럴 |
| `Array` | Tuple |
| `enum` | `const enum` |

---

## 연습문제

**1. 각 값의 추론 타입은?**

```typescript
let a = 1042                  // ?
let b = 'apples and oranges'  // ?
const c = 'pineapples'        // ?
let d = [true, true, false]   // ?
let e = { type: 'ficus' }     // ?
let f = [1, false]            // ?
const g = [3]                 // ?
let h = null                  // ?
```

**2. 각 오류가 발생하는 이유는?**

```typescript
// a: 리터럴 타입에 다른 값 할당
let i: 3 = 3
i = 4  // Error TS2322: Type '4' is not assignable to type '3'.

// b: 추론된 배열 타입과 다른 타입의 요소 추가
let j = [1, 2, 3]
j.push(4)
j.push('5')  // Error TS2345: Argument of type '"5"' is not assignable to parameter of type 'number'.

// c: never는 아무것도 할당할 수 없는 bottom type
let k: never = 4  // Error TS2322: Type '4' is not assignable to type 'never'.

// d: unknown은 타입을 좁히기 전에 연산 불가
let l: unknown = 4
let m = l * 2  // Error TS2571: Object is of type 'unknown'.
```
