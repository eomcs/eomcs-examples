# Chapter 4. Functions

이번 장에서는 다음 내용을 살펴본다.

- 타입스크립트에서 함수를 선언하고 실행하는 다양한 방법
- 시그니처 오버로딩
- 다형적 함수
- 다형적 타입 별칭

---

## 함수 선언과 호출 (Declaring and Invoking Functions)

TypeScript에서 함수를 선언하는 다섯 가지 방법:

```typescript
// 1. Named function (기명 함수)
function greet(name: string) {
  return 'hello ' + name
}

// 2. Function expression (함수 표현식)
let greet2 = function(name: string) {
  return 'hello ' + name
}

// 3. Arrow function expression (화살표 함수)
let greet3 = (name: string) => {
  return 'hello ' + name
}

// 4. Shorthand arrow function (단축 화살표 함수)
let greet4 = (name: string) => 'hello ' + name

// 5. Function constructor — 타입 불안전, 사용 금지
let greet5 = new Function('name', 'return "hello " + name')
```

매개변수 타입은 **반드시** 명시하고, 반환 타입은 TypeScript가 추론하므로 선택적으로 명시한다.

```typescript
function add(a: number, b: number): number {
  return a + b
}

add(1, 2)      // 3
add(1)         // Error TS2554: Expected 2 arguments, but got 1.
add(1, 'a')    // Error TS2345: Argument of type '"a"' is not assignable to parameter of type 'number'.
```

> **용어 정리**  
> - **parameter(매개변수)**: 함수 선언에 정의된 변수 (formal parameter)  
> - **argument(인수)**: 함수 호출 시 전달하는 값 (actual parameter)

---

## 옵셔널 파라미터와 기본값 (Optional and Default Parameters)

### 옵셔널 파라미터 (`?`)

필수 파라미터는 앞에, 옵셔널 파라미터는 **반드시 뒤에** 와야 한다.

```typescript
function log(message: string, userId?: string) {
  let time = new Date().toLocaleTimeString()
  console.log(time, message, userId || 'Not signed in')
}

log('Page loaded')               // "12:38:31 PM Page loaded Not signed in"
log('User signed in', 'da763be') // "12:38:31 PM User signed in da763be"
```

### 기본값 파라미터 (Default Parameters)

기본값을 설정하면 `?` 어노테이션이 불필요하다. TypeScript가 기본값에서 타입을 추론한다.

```typescript
function log(message: string, userId = 'Not signed in') {
  let time = new Date().toISOString()
  console.log(time, message, userId)
}

log('User signed out')  // userId = 'Not signed in'
```

기본값 파라미터는 옵셔널 파라미터와 달리 **파라미터 목록의 어디에나** 올 수 있다.

```typescript
type Context = {
  appId?: string
  userId?: string
}

function log(message: string, context: Context = {}) {
  console.log(new Date().toISOString(), message, context.userId)
}
```

---

## Rest 파라미터 (Rest Parameters)

가변 인수(variadic) 함수를 타입 안전하게 작성하는 방법.

```typescript
// 배열로 받는 방식 (고정 arity)
function sum(numbers: number[]): number {
  return numbers.reduce((total, n) => total + n, 0)
}
sum([1, 2, 3])  // 6

// rest 파라미터 방식 (가변 arity)
function sumVariadicSafe(...numbers: number[]): number {
  return numbers.reduce((total, n) => total + n, 0)
}
sumVariadicSafe(1, 2, 3)  // 6
```

> `arguments` 객체는 타입이 `any`로 추론되어 **타입 불안전**하다. 반드시 rest 파라미터를 사용하라.

rest 파라미터는 **하나만** 선언할 수 있으며, **파라미터 목록의 마지막**에 와야 한다.

```typescript
interface Console {
  log(message?: any, ...optionalParams: any[]): void
}
```

---

## call, apply, bind

```typescript
function add(a: number, b: number): number {
  return a + b
}

add(10, 20)                // 30
add.apply(null, [10, 20])  // 30 — this를 null로 바인드, 인수를 배열로 전달
add.call(null, 10, 20)     // 30 — this를 null로 바인드, 인수를 순서대로 전달
add.bind(null, 10, 20)()   // 30 — 새 함수를 반환 (즉시 호출하지 않음)
```

> **TSC 플래그**: `strictBindCallApply` — `.call`, `.apply`, `.bind` 타입 안전 강제. `strict: true`에 포함됨.

---

## this 타입 지정 (Typing this)

JavaScript에서 `this`는 함수를 **어떻게 호출했느냐**에 따라 달라진다.  
TypeScript는 함수의 **첫 번째 파라미터**로 `this`의 타입을 선언할 수 있다.

```typescript
function fancyDate(this: Date) {
  return `${this.getDate()}/${this.getMonth()}/${this.getFullYear()}`
}

fancyDate.call(new Date)  // 정상 동작
fancyDate()               // Error TS2684: The 'this' context of type 'void' is not assignable to method's 'this' of type 'Date'.
```

> `this`는 예약어로 실제 파라미터처럼 취급되지 않는다. 호출자에게는 보이지 않는다.  
> **TSC 플래그**: `noImplicitThis` — `strict: true`에 포함됨.

---

## 제너레이터 함수 (Generator Functions)

값을 **지연 생성(lazy)**하는 함수. `*`로 선언하고 `yield`로 값을 내보낸다.

```typescript
function* createFibonacciGenerator(): IterableIterator<number> {
  let a = 0
  let b = 1
  while (true) {
    yield a
    ;[a, b] = [b, a + b]
    // yield a[a, b]와 같이 해석할 수 있으므로 세미콜론을 붙여 문장을 구분한다.
    // 실무에서 종종 사용하는 관례로,
    // 배열 리터럴([), 즉시 실행 함수({), 템플릿 리터럴(`) 등으로 
    // 문장이 시작될 때 세미콜론을 앞에 붙인다.
  }
}

let fib = createFibonacciGenerator()
fib.next()  // {value: 0, done: false}
fib.next()  // {value: 1, done: false}
fib.next()  // {value: 1, done: false}
fib.next()  // {value: 2, done: false}
```

- TypeScript는 `yield`하는 값의 타입에서 `IterableIterator<T>`를 추론한다.
- 무한 루프도 `while(true)`와 `yield`의 조합으로 안전하게 표현 가능하다.

---

## 이터레이터 (Iterators)

| 개념 | 정의 |
|------|------|
| **Iterable** | `Symbol.iterator` 프로퍼티를 가지고, 호출하면 Iterator를 반환하는 객체 |
| **Iterator** | `next()` 메서드를 가지고, `{value, done}`을 반환하는 객체 |

```typescript
let numbers = {
  *[Symbol.iterator]() { // Generator Method
    for (let n = 1; n <= 10; n++) {
      yield n
    }
  }
}

// for-of로 순회
for (let a of numbers) { /* 1, 2, ..., 10 */ }

// 스프레드
let allNumbers = [...numbers]  // number[]

// 구조 분해
let [one, two, ...rest] = numbers  // [number, number, number[]]
```
### 객체 메서드(참고)

```typescript
{
  // 일반 함수
  hello1: function() {},
  hello2() {},

  // 제너레이터 함수
  hello3: function*() {},
  *hello4() {},

  // 계산된 속성 이름(Computed Property Names)
  // - expr을 계산한 결과가 메서드 이름이 된다.
  [expr]: function() {},
  [expr]() {},
  *[expr]() {},

  // 빌트인 심볼 메서드
  // - Symbol.iterator가 계산되어 
  //   메서드 이름은 Symbol.iterator가 된다.
  [Symbol.iterator]: function*() {},
}
```

### Generator 함수 이름

| 코드                     | 메서드 이름            |
| ---------------------- | ----------------- |
| `*hello()`             | `hello`           |
| `*sum()`               | `sum`             |
| `*next()`              | `next`            |
| `*[Symbol.iterator]()` | `Symbol.iterator` |

---

## 호출 시그니처 (Call Signatures)

함수 자체의 타입을 표현하는 방법. 함수를 인수로 전달하거나 반환할 때 사용한다.

```typescript
// 단축 호출 시그니처 (shorthand)
type Greet = (name: string) => string
type Log = (message: string, userId?: string) => void
type SumVariadicSafe = (...numbers: number[]) => number

// 전체 호출 시그니처 (full) — 단축과 완전히 동일한 의미
type Log = {
  (message: string, userId?: string): void
}
```

> 호출 시그니처는 타입 레벨 코드만 포함한다. **기본값은 값(value)이므로** 시그니처에 포함할 수 없다.

### 호출 시그니처로 함수를 구현하기

```typescript
type Log = (message: string, userId?: string) => void

let log: Log = (
  message,                    // 타입을 다시 명시할 필요 없음 (Log에서 추론)
  userId = 'Not signed in'    // 기본값은 구현부에서만 설정 가능
) => {
  let time = new Date().toISOString()
  console.log(time, message, userId)
}
```

### 문맥적 타입 추론 (Contextual Typing)

함수를 인라인으로 전달하면 파라미터 타입을 명시하지 않아도 TypeScript가 추론한다.

```typescript
function times(f: (index: number) => void, n: number) {
  for (let i = 0; i < n; i++) { f(i) }
}

times(n => console.log(n), 4)  // n의 타입이 number로 자동 추론
```

---

## 오버로드된 함수 타입 (Overloaded Function Types)

**오버로드 함수**: 여러 개의 호출 시그니처를 가지는 함수. 입력 타입에 따라 출력 타입이 달라질 때 사용한다.

```typescript
type Reserve = {
  (from: Date, to: Date, destination: string): Reservation  // 왕복
  (from: Date, destination: string): Reservation            // 편도
}
```

구현부에서는 **두 시그니처를 합친 단일 시그니처**를 직접 작성해야 한다. 합쳐진 시그니처는 외부에서 보이지 않는다.

```typescript
let reserve: Reserve = (
  from: Date,
  toOrDestination: Date | string,
  destination?: string
) => {
  if (toOrDestination instanceof Date && destination !== undefined) {
    // 왕복 예약
  } else if (typeof toOrDestination === 'string') {
    // 편도 예약
  }
}
```

### DOM API 오버로드 예시

```typescript
type CreateElement = {
  (tag: 'a'): HTMLAnchorElement
  (tag: 'canvas'): HTMLCanvasElement
  (tag: 'table'): HTMLTableElement
  (tag: string): HTMLElement       // 폴백 — 알 수 없는 태그
}

let createElement: CreateElement = (tag: string): HTMLElement => {
  // ...
}
```

TypeScript는 오버로드를 **선언 순서대로** 해결한다. (리터럴 오버로드는 비리터럴 오버로드보다 먼저 검사)

### 함수 프로퍼티 타이핑

```typescript
type WarnUser = {
  (warning: string): void
  wasCalled: boolean
}

let warnUser: WarnUser = (warning: string) => {
  if (warnUser.wasCalled) return
  warnUser.wasCalled = true
  alert(warning)
}
warnUser.wasCalled = false
```

---

## 제네릭 (Generics / Polymorphism)

**구체적인 타입(concrete type)** 대신, 호출 시점에 타입이 결정되는 **타입 파라미터**를 사용해 범용적인 함수를 작성한다.

> **GENERIC TYPE PARAMETER**: 여러 곳에서 타입 수준의 제약을 강제하는 자리표시자 타입. 다형적 타입 파라미터(polymorphic type parameter)라고도 한다.

```typescript
type Filter = {
  <T>(array: T[], f: (item: T) => boolean): T[]
}

let filter: Filter = (array, f) => {
  let result = []
  for (let i = 0; i < array.length; i++) {
    if (f(array[i])) result.push(array[i])
  }
  return result
}

filter([1, 2, 3], _ => _ > 2)                              // T = number → number[]
filter(['a', 'b'], _ => _ !== 'b')                         // T = string → string[]
filter([{firstName: 'beth'}], _ => _.firstName === 'beth') // T = {firstName: string}
```

`T`는 호출 시점마다 새로 바인딩된다.

### 제네릭 선언 위치와 바인딩 시점

```typescript
// 1. 시그니처에 선언 (<T> 위치: 호출 시 바인딩)
type Filter = { <T>(array: T[], f: (item: T) => boolean): T[] }

// 2. 타입 별칭에 선언 (타입 사용 시 명시적으로 바인딩 필요)
type Filter<T> = { (array: T[], f: (item: T) => boolean): T[] }
let numberFilter: Filter<number> = (array, f) => // ...

// 3. 함수 선언에 직접 선언
function filter<T>(array: T[], f: (item: T) => boolean): T[] { /* ... */ }
```

### 여러 제네릭 파라미터: map 구현

```typescript
function map<T, U>(array: T[], f: (item: T) => U): U[] {
  let result = []
  for (let i = 0; i < array.length; i++) {
    result[i] = f(array[i])
  }
  return result
}
```

`T`: 입력 배열 요소 타입, `U`: 출력 배열 요소 타입.

### 제네릭 타입 추론

```typescript
map(['a', 'b', 'c'], _ => _ === 'a')               // T=string, U=boolean 자동 추론
map<string, boolean>(['a', 'b', 'c'], _ => _ === 'a') // 명시적 바인딩 (전부 or 없음)

// 추론이 불충분한 경우 명시 필요 (Promise 등)
let promise = new Promise<number>(resolve => resolve(45))
promise.then(result => result * 4)  // result: number
```

---

## 제네릭 타입 별칭 (Generic Type Aliases)

```typescript
type MyEvent<T> = {
  target: T
  type: string
}

type ButtonEvent = MyEvent<HTMLButtonElement>

// 제네릭 기본값 추가
type MyEvent<T extends HTMLElement = HTMLElement> = {
  target: T
  type: string
}

let myEvent: MyEvent = {        // T = HTMLElement (기본값)
  target: myElement,
  type: 'click'
}

// 타입 합성
type TimedEvent<T> = {
  event: MyEvent<T>
  from: Date
  to: Date
}

function triggerEvent<T>(event: MyEvent<T>): void { /* ... */ }
```

---

## 제한된 다형성 (Bounded Polymorphism)

`extends`로 제네릭 타입에 **상한(upper bound)** 을 설정한다.  
"T는 반드시 U이거나 U의 서브타입이어야 한다"는 제약 표현.

```typescript
type TreeNode = { value: string }
type LeafNode = TreeNode & { isLeaf: true }
type InnerNode = TreeNode & { children: [TreeNode] | [TreeNode, TreeNode] }

function mapNode<T extends TreeNode>(
  node: T,
  f: (value: string) => string
): T {
  return { ...node, value: f(node.value) }
}

let a: TreeNode = { value: 'a' }
let b: LeafNode = { value: 'b', isLeaf: true }

let a1 = mapNode(a, _ => _.toUpperCase())  // TreeNode  (타입 보존)
let b1 = mapNode(b, _ => _.toUpperCase())  // LeafNode  (타입 보존!)
```

### 여러 조건을 동시에 제한

```typescript
type HasSides = { numberOfSides: number }
type SidesHaveLength = { sideLength: number }

function logPerimeter<Shape extends HasSides & SidesHaveLength>(s: Shape): Shape {
  console.log(s.numberOfSides * s.sideLength)
  return s
}
```

### 가변 인수 arity 모델링

```typescript
function call<T extends unknown[], R>(
  f: (...args: T) => R,
  ...args: T
): R {
  return f(...args)
}

function fill(length: number, value: string): string[] {
  return Array.from({ length }, () => value)
}

let a = call(fill, 10, 'a')       // string[]
let b = call(fill, 10)            // Error TS2554: Expected 3 arguments; got 2.
let c = call(fill, 10, 'a', 'z')  // Error TS2554: Expected 3 arguments; got 4.
```

---

## 제네릭 기본값 (Generic Type Defaults)

```typescript
// 기본값이 있는 제네릭은 기본값이 없는 제네릭 뒤에 와야 한다
type MyEvent<
  Type extends string,
  Target extends HTMLElement = HTMLElement,  // 기본값 있음 → 뒤에
> = {
  target: Target
  type: Type
}
```

---

## 타입 주도 개발 (Type-Driven Development)

> **TYPE-DRIVEN DEVELOPMENT**: 타입 시그니처를 먼저 설계하고, 구현을 나중에 채우는 프로그래밍 스타일.

```typescript
// 구현 없이도 시그니처만으로 의미가 명확하다
function map<T, U>(array: T[], f: (item: T) => U): U[] {
  // ...
}
```

TypeScript 프로그램 작성 순서:
1. 함수의 타입 시그니처를 먼저 정의한다
2. 전체 구조가 타입 수준에서 맞는지 확인한다
3. 구현을 채운다

---

## 연습문제

**1.** TypeScript가 함수에서 추론하는 것은 파라미터 타입인가, 반환 타입인가, 아니면 둘 다인가?

**2.** JavaScript의 `arguments` 객체는 타입 안전한가? 안전하지 않다면 무엇을 사용해야 하는가?

**3.** 아래 `reserve` 함수에 세 번째 오버로드 시그니처를 추가하라: 목적지만 전달하면 즉시 출발하는 여행을 예약한다.

```typescript
type Reservation = { /* ... */ }
type Reserve = {
  (from: Date, to: Date, destination: string): Reservation
  (from: Date, destination: string): Reservation
  // (destination: string): Reservation  ← 추가할 시그니처
}
```

**4. [Hard]** 앞에서 만든 `call` 함수를 수정하여 **두 번째 인수가 `string`인 함수에만** 동작하도록 제한하라.

**5.** 타입 안전한 assertion 라이브러리 `is`를 구현하라.

```typescript
is('string', 'otherstring')   // false
is(true, false)               // false
is(42, 42)                    // true
is(10, 'foo')                 // Error TS2345: 타입이 다르면 컴파일 오류
is([1], [1, 2], [1, 2, 3])    // [Hard] 가변 인수 지원
```
