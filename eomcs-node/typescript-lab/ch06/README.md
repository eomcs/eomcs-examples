# Chapter 6. Advanced Types

이번 장에서는 다음과 같이 TypeScript의 고급 타입 기능을 다룬다. 

- 타입스크립트 개념 발전:
  - 서브타입화, 할당성, 가변성, 넓히기 
- 타입스크립트의 제어 흐름 기반의 타입 확인 기능:
  - 타입 정제, 종합성 
- 타입스크립트 고급 개념:
  - 객체 타입을 키로 활용하고 매핑하는 방법
  - 조건부 타입 사용, 자신만의 타입 안전 장치 정의
  - 타입 어서션, 확실한 할당 어셔션
- 타입 안전성 강화를 위한 고급 패턴:
  - 컴패니언 객체 패턴
  - 튜플 타입의 추론 개선
  - 이름 기반 타입 흉내내기
  - 안전하게 프로토타입 확장하기

---

## 1. 타입 간의 관계 (Relationships Between Types)

### 서브타입과 슈퍼타입 (Subtypes and Supertypes)

**서브타입(Subtype)**:
타입 A와 B가 있을 때, B가 A의 서브타입이라면 A가 필요한 곳에 B를 안전하게 사용할 수 있다.

TypeScript의 서브타입 관계 예시:
- `Array`는 `Object`의 서브타입
- `Tuple`은 `Array`의 서브타입
- 모든 것은 `any`의 서브타입
- `never`는 모든 것의 서브타입
- `Bird extends Animal`이면 `Bird`는 `Animal`의 서브타입

**슈퍼타입(Supertype)**:
타입 A와 B가 있을 때, B가 A의 슈퍼타입이라면 A가 필요한 곳에 B를 안전하게 사용할 수 없다(반대 방향).

표기법 (TypeScript 문법이 아닌 설명용):
- `A <: B` : "A는 B의 서브타입이거나 같다"
- `A >: B` : "A는 B의 슈퍼타입이거나 같다"

---

### 가변성 (Variance)

복잡한 타입의 서브타입 관계를 다룰 때 사용하는 개념:

| 변성 | 의미 |
|------|------|
| **불변(Invariance)** | 정확히 `T`여야 함 |
| **공변(Covariance)** | `<:T` 이면 됨 (서브타입 허용) |
| **반변(Contravariance)** | `>:T` 이면 됨 (슈퍼타입 허용) |
| **이변(Bivariance)** | `<:T` 또는 `>:T` 모두 허용 |

**TypeScript의 변성 규칙**:
- 객체, 클래스, 배열, 함수 반환 타입 → **공변(Covariant)**
- 함수 파라미터 타입 → **반변(Contravariant)**

#### 형태(Shape)와 배열 가변성

```typescript
type ExistingUser = { id: number; name: string }
type NewUser = { name: string }

function deleteUser(user: {id?: number, name: string}) {
  delete user.id
}

let existingUser: ExistingUser = { id: 123456, name: 'Ima User' }
deleteUser(existingUser) // OK: ExistingUser <: {id?: number, name: string}

type LegacyUser = { id?: number | string; name: string }
let legacyUser: LegacyUser = { id: '793331', name: 'Xin Yang' }
deleteUser(legacyUser) // Error TS2345: string은 number | undefined의 슈퍼타입
```

객체 타입은 프로퍼티 타입에 대해 **공변(covariant)**이다. 프로퍼티 타입이 기대값의 서브타입이면 OK, 슈퍼타입이면 오류.

#### 함수 가변성

```typescript
class Animal {}
class Bird extends Animal { chirp() {} }
class Crow extends Bird { caw() {} }
// Crow <: Bird <: Animal

function clone(f: (b: Bird) => Bird): void { /* ... */ }

// 반환 타입: 공변 (Bird의 서브타입이어야 함)
function birdToCrow(d: Bird): Crow { /* ... */ }
clone(birdToCrow) // OK

function birdToAnimal(d: Bird): Animal { /* ... */ }
clone(birdToAnimal) // Error: Animal은 Bird의 슈퍼타입

// 파라미터 타입: 반변 (Bird의 슈퍼타입이어야 함)
function animalToBird(a: Animal): Bird { /* ... */ }
clone(animalToBird) // OK

function crowToBird(c: Crow): Bird { /* ... */ }
clone(crowToBird) // Error: Crow는 Bird의 서브타입
```

> **TSC Flag**: `strictFunctionTypes: true` (또는 `strict: true`)를 활성화해야 함수 파라미터의 반변성이 적용된다.

---

### 할당 가능성 (Assignability)

> 타입 A를 타입 B가 필요한 곳에 사용할 수 있는지를 결정하는 타입스크립트 규칙

**일반 타입 (enum 제외)**:
- `A <: B` 이거나
- `A`가 `any`이면

**enum 타입**:
- A가 enum B의 멤버이거나
- B에 숫자 멤버가 하나 이상 있고, A가 number이면

---

## 2. 타입 넓히기 (Type Widening)

TypeScript는 타입을 추론할 때 가능한 일반적인(넓은) 타입으로 추론한다.

```typescript
// let 또는 var 으로 선언 → 리터럴 값이 속한 기본 타입으로 넓혀진다
let a = 'x'           // string
let b = 3             // number
var c = true          // boolean
const d = {x: 3}      // {x: number}

// const로 선언 → 좁은 타입 (리터럴)
const a = 'x'         // 'x'
const b = 3           // 3
const c = true        // true
```

**명시적 타입 어노테이션으로 넓히기 방지**:

```typescript
let a: 'x' = 'x'      // 'x' (넓혀지지 않음)
let b: 3 = 3          // 3
```

**null/undefined 초기화 → any로 넓혀짐**:

```typescript
let a = null          // any
a = 3                 // any
a = 'b'               // any

function x() {
  let a = null        // any (함수 내부)
  a = 3
  a = 'b'
  return a
}
x()                   // string (스코프를 벗어나면 확정 타입)
```

### const 타입

`as const`를 사용하면 타입 넓히기를 방지하고, 재귀적으로 `readonly` 처리된다:

```typescript
let a = {x: 3}           // {x: number}
let b: {x: 3}            // {x: 3}
let c = {x: 3} as const  // {readonly x: 3}

let d = [1, {x: 2}]          // (number | {x: number})[]
let e = [1, {x: 2}] as const // readonly [1, {readonly x: 2}]
```

### 초과 프로퍼티 검사 (Excess Property Checking)

```typescript
type Options = {
  baseURL: string
  cacheSize?: number
  tier?: 'prod' | 'dev'
}

class API {
  constructor(private options: Options) {}
}

// OK: 유효한 프로퍼티만 있음
new API({ baseURL: 'https://api.mysite.com', tier: 'prod' })

// Error: 오타 감지 (tierr는 Options에 없음)
new API({ baseURL: 'https://api.mysite.com', tierr: 'prod' })
// Error TS2345: Object literal may only specify known properties, but 'tierr' does not exist

// OK: 타입 단언 → fresh 객체 아님
new API({ baseURL: 'https://api.mysite.com', badTier: 'prod' } as Options)

// OK: 변수에 할당 → fresh 객체 아님
let badOptions = { baseURL: 'https://api.mysite.com', badTier: 'prod' }
new API(badOptions)

// Error: 변수에 명시적 타입 어노테이션 → fresh 객체 검사
let options: Options = {
  baseURL: 'https://api.mysite.com',
  badTier: 'prod'  // Error TS2322
}
```

> **fresh 객체 리터럴 타입**: 타입이 추론되고, 변수에 할당되지 않았으며, 타입 단언도 없는 객체 리터럴. fresh 객체에만 초과 프로퍼티 검사가 적용된다.

---

## 3. 타입 정제 (Refinement)

TypeScript는 **흐름 기반 타입 추론(flow-based type inference)**을 통해 제어 흐름 분석으로 타입을 좁힌다.

`if`, `?`, `||`, `switch`, `typeof`, `instanceof`, `in` 등을 사용하여 타입을 정제한다.

```typescript
type Unit = 'cm' | 'px' | '%'
let units: Unit[] = ['cm', 'px', '%']

function parseUnit(value: string): Unit | null {
  for (let i = 0; i < units.length; i++) {
    if (value.endsWith(units[i])) return units[i]
  }
  return null
}

type Width = { unit: Unit; value: number }

function parseWidth(width: number | string | null | undefined): Width | null {
  if (width == null) {        // null | undefined 제거 → number | string
    return null
  }

  if (typeof width === 'number') {  // number 확정
    return {unit: 'px', value: width}
  }

  let unit = parseUnit(width)  // 여기서 width는 string
  if (unit) {                  // unit: Unit | null → Unit
    return {unit, value: parseFloat(width)}
  }

  return null
}
```

---

### 판별 유니온 타입 (Discriminated Union Types)

유니온 타입의 각 케이스를 구분하기 위해 **태그(tag) 필드**를 사용한다.

좋은 태그의 조건:
- 유니온의 각 케이스에서 **같은 위치**에 있어야 함
- **리터럴 타입**으로 타입 지정
- 제네릭이 아닐 것
- **상호 배타적**일 것 (유니온 내에서 고유해야 함)

```typescript
// 태그 없는 경우: target 타입이 제대로 정제되지 않음
type UserTextEvent  = {value: string,           target: HTMLInputElement}
type UserMouseEvent = {value: [number, number], target: HTMLElement}

// 태그 있는 경우: 완벽한 정제
type UserTextEvent  = {type: 'TextEvent',  value: string,           target: HTMLInputElement}
type UserMouseEvent = {type: 'MouseEvent', value: [number, number], target: HTMLElement}
type UserEvent = UserTextEvent | UserMouseEvent

function handle(event: UserEvent) {
  if (event.type === 'TextEvent') {
    event.value   // string
    event.target  // HTMLInputElement
    return
  }
  event.value     // [number, number]
  event.target    // HTMLElement
}
```

> Flux 액션, Redux 리듀서, React의 `useReducer`에서 특히 유용하다.

---

### 완전성 검사 (Totality / Exhaustiveness Checking)

모든 케이스를 처리했는지 타입 체커가 확인한다.

```typescript
type Weekday = 'Mon' | 'Tue' | 'Wed' | 'Thu' | 'Fri'
type Day = Weekday | 'Sat' | 'Sun'

function getNextDay(w: Weekday): Day {
  switch (w) {
    case 'Mon': return 'Tue'
    // 나머지 케이스 누락!
  }
}
// Error TS2366: Function lacks ending return statement and
//              return type does not include 'undefined'.
```

> **TSC Flag**: `noImplicitReturns: true` — 모든 코드 경로가 값을 반환하는지 검사한다.

---

## 4. 고급 객체 타입 (Advanced Object Types)

### 타입 연산자 (Type Operators for Object Types)

#### 키 인덱싱 연산자 (Keying-in Operator)

중첩 타입에서 내부 타입을 추출할 때 사용한다:

```typescript
type APIResponse = {
  user: {
    userId: string
    friendList: {
      count: number
      friends: { firstName: string; lastName: string }[]
    }
  }
}

type FriendList = APIResponse['user']['friendList']
// { count: number; friends: { firstName: string; lastName: string }[] }

type Friend = FriendList['friends'][number]
// { firstName: string; lastName: string }
```

> 배열 타입의 요소 타입을 얻을 때는 `[number]`를 사용하고, 튜플은 `[0]`, `[1]` 등 인덱스를 사용한다.

#### keyof 연산자

객체의 모든 키를 string 리터럴 유니온으로 반환한다:

```typescript
type ResponseKeys   = keyof APIResponse                     // 'user'
type UserKeys       = keyof APIResponse['user']             // 'userId' | 'friendList'
type FriendListKeys = keyof APIResponse['user']['friendList'] // 'count' | 'friends'
```

**keyof + 키 인덱싱 조합으로 타입 안전한 getter 구현**:

```typescript
function get<O extends object, K extends keyof O>(o: O, k: K): O[K] {
  return o[k]
}

type ActivityLog = {
  lastEvent: Date
  events: { id: string; timestamp: Date; type: 'Read' | 'Write' }[]
}
let activityLog: ActivityLog = // ...
let lastEvent = get(activityLog, 'lastEvent') // Date
```

---

### Record 타입

객체의 키와 값 타입을 제한하는 방법:

```typescript
type Weekday = 'Mon' | 'Tue' | 'Wed' | 'Thu' | 'Fri'
type Day = Weekday | 'Sat' | 'Sun'

let nextDay: Record<Weekday, Day> = {
  Mon: 'Tue'
}
// Error TS2739: Type '{Mon: "Tue"}' is missing the following properties:
//              Tue, Wed, Thu, Fri
```

일반 인덱스 시그니처와의 차이: Record는 키 타입을 string/number/symbol의 **서브타입**으로 제한할 수 있다.

---

### 매핑 타입 (Mapped Types)

객체의 키와 값 타입을 순회하며 새로운 타입을 만든다:

```typescript
type MyMappedType = {
  [Key in UnionType]: ValueType
}

// nextDay 예시
let nextDay: {[K in Weekday]: Day} = { Mon: 'Tue' }
// Error TS2739: 나머지 요일 누락
```

**매핑 타입 활용 예시**:

```typescript
type Account = {
  id: number
  isEmployee: boolean
  notes: string[]
}

// 모든 필드를 선택적으로
type OptionalAccount = {
  [K in keyof Account]?: Account[K]
}

// 모든 필드를 nullable로
type NullableAccount = {
  [K in keyof Account]: Account[K] | null
}

// 모든 필드를 읽기 전용으로
type ReadonlyAccount = {
  readonly [K in keyof Account]: Account[K]
}

// readonly 제거 (Account와 동일)
type Account2 = {
  -readonly [K in keyof ReadonlyAccount]: Account[K]
}

// optional 제거 (Account와 동일)
type Account3 = {
  [K in keyof OptionalAccount]-?: Account[K]
}
```

> `-readonly`, `-?` : minus(-) 연산자로 수정자를 제거한다.

**내장 매핑 타입**:

| 타입 | 설명 |
|------|------|
| `Record<Keys, Values>` | 키 타입과 값 타입을 지정한 객체 |
| `Partial<Object>` | 모든 필드를 선택적으로 |
| `Required<Object>` | 모든 필드를 필수로 |
| `Readonly<Object>` | 모든 필드를 읽기 전용으로 |
| `Pick<Object, Keys>` | 지정한 Keys만 포함한 서브타입 |

---

## 5. 컴패니언 객체 패턴 (Companion Object Pattern)

TypeScript에서 타입과 값은 **별도의 네임스페이스**에 존재한다. 이를 활용해 같은 이름으로 타입과 객체를 함께 선언할 수 있다:

```typescript
type Currency = {
  unit: 'EUR' | 'GBP' | 'JPY' | 'USD'
  value: number
}

let Currency = {
  DEFAULT: 'USD',
  from(value: number, unit = Currency.DEFAULT): Currency {
    return {unit, value}
  }
}
```

**사용 측에서 타입과 값을 한 번에 임포트**:

```typescript
import {Currency} from './Currency'

let amountDue: Currency = {       // 타입으로 사용
  unit: 'JPY',
  value: 83733.10
}
let otherAmountDue = Currency.from(330, 'EUR')  // 값으로 사용
```

> 타입과 객체가 의미적으로 연관되어 있고, 객체가 타입에 대한 유틸리티 메서드를 제공할 때 사용한다.

---

## 6. 고급 함수 타입 (Advanced Function Types)

### 튜플 타입 추론 개선

TypeScript는 배열 리터럴을 보수적으로 추론한다:

```typescript
let a = [1, true] // (number | boolean)[] — 배열로 추론
```

더 정확한 튜플 타입으로 추론하려면:

```typescript
function tuple<T extends unknown[]>(...ts: T): T {
  return ts
}

let a = tuple(1, true) // [number, boolean] — 튜플로 추론
```

---

### 사용자 정의 타입 가드 (User-Defined Type Guards)

타입 정제(refinement)는 현재 스코프에서만 유효하다. 함수로 분리하면 정제 결과가 유지되지 않는다:

```typescript
function isString(a: unknown): boolean {
  return typeof a === 'string'
}

function parseInput(input: string | number) {
  if (isString(input)) {
    input.toUpperCase() // Error: TypeScript가 string임을 모름
  }
}
```

**`is` 연산자로 사용자 정의 타입 가드 선언**:

```typescript
function isString(a: unknown): a is string {
  return typeof a === 'string'
}

function parseInput(input: string | number) {
  if (isString(input)) {
    input.toUpperCase() // OK: input은 string
  }
}
```

복잡한 타입에도 적용 가능:

```typescript
function isLegacyDialog(
  dialog: LegacyDialog | Dialog
): dialog is LegacyDialog {
  // ...
}
```

---

## 7. 조건부 타입 (Conditional Types)

타입 레벨의 삼항 연산자. "타입 T가 U의 서브타입이면 A, 아니면 B"를 표현한다:

```typescript
type IsString<T> = T extends string
  ? true
  : false

type A = IsString<string> // true
type B = IsString<number> // false
```

조건부 타입은 타입 별칭, 인터페이스, 클래스, 파라미터 타입, 제네릭 기본값 등 대부분의 위치에서 사용 가능하다.

---

### 분배 조건부 타입 (Distributive Conditional Types)

조건부 타입은 유니온 타입에 **분배 법칙**이 적용된다:

| 표현 | 동등한 표현 |
|------|-----------|
| `string extends T ? A : B` | `string extends T ? A : B` |
| `(string \| number) extends T ? A : B` | `(string extends T ? A : B) \| (number extends T ? A : B)` |

**활용 예시 - `Without<T, U>`**:

```typescript
type Without<T, U> = T extends U ? never : T

type A = Without<boolean | number | string, boolean>
// 계산 과정:
// Without<boolean, boolean> | Without<number, boolean> | Without<string, boolean>
// = never | number | string
// = number | string
```

---

### infer 키워드

조건부 타입 내에서 타입을 인라인으로 추론할 때 사용한다:

```typescript
// keying-in 방식
type ElementType<T> = T extends unknown[] ? T[number] : T
type A = ElementType<number[]> // number

// infer 방식 (동일한 결과)
type ElementType2<T> = T extends (infer U)[] ? U : T
type B = ElementType2<number[]> // number
```

**실용적인 예시**:

```typescript
type SecondArg<F> = F extends (a: any, b: infer B) => any ? B : never

// Array.slice의 두 번째 인자 타입
type F = typeof Array['prototype']['slice']
type A = SecondArg<F> // number | undefined
```

---

### 내장 조건부 타입

| 타입 | 설명 | 예시 |
|------|------|------|
| `Exclude<T, U>` | T에서 U에 해당하는 타입 제거 | `Exclude<number \| string, string>` → `number` |
| `Extract<T, U>` | T에서 U에 할당 가능한 타입만 추출 | `Extract<number \| string, string>` → `string` |
| `NonNullable<T>` | null과 undefined 제거 | `NonNullable<number \| null>` → `number` |
| `ReturnType<F>` | 함수의 반환 타입 | `ReturnType<() => string>` → `string` |
| `InstanceType<C>` | 클래스 생성자의 인스턴스 타입 | `InstanceType<typeof MyClass>` |

---

## 8. 탈출구 (Escape Hatches)

> 가능한 한 사용하지 않는 것이 좋다. 자주 쓴다면 코드 구조를 재검토해야 한다.

### 타입 단언 (Type Assertions)

타입 A와 B의 관계에서 (`A <: B` 또는 `A >: B`) 한쪽 방향으로 단언할 수 있다:

```typescript
let input = getUserInput() // string | number

formatInput(input as string)  // as 문법 (권장)
formatInput(<string>input)    // 앵글 브라켓 문법 (레거시, TSX와 충돌 가능)

// 관계없는 타입 단언: any를 경유
addToList('this is really,' as any, 'really unsafe')
```

### 논널(Non-null) 단언

`!` 연산자로 `null | undefined`가 아님을 단언한다:

```typescript
type Dialog = { id?: string }

function closeDialog(dialog: Dialog) {
  if (!dialog.id) return
  setTimeout(() =>
    removeFromDOM(
      dialog,
      document.getElementById(dialog.id!)! // id가 null이 아님, getElementById 결과도 null이 아님
    )
  )
}

function removeFromDOM(dialog: Dialog, element: Element) {
  element.parentNode!.removeChild(element) // parentNode가 null이 아님
  delete dialog.id
}
```

논널 단언이 많이 필요하다면, 유니온 타입으로 리팩토링을 고려:

```typescript
type VisibleDialog  = {id: string}
type DestroyedDialog = {}
type Dialog = VisibleDialog | DestroyedDialog
```

### 확정 할당 단언 (Definite Assignment Assertions)

변수 선언 시 `!`를 붙여 나중에 반드시 할당될 것임을 알린다:

```typescript
let userId!: string   // 반드시 할당될 것임을 단언
fetchUser()

userId.toUpperCase()  // OK

function fetchUser() {
  userId = globalCache.get('userId')
}
```

---

## 9. 명목적 타입 시뮬레이션 (Simulating Nominal Types)

TypeScript는 **구조적 타입 시스템**이지만, 때로는 명목적 타입(이름 기반 타입)이 유용하다.

**문제**: 타입 별칭만으로는 혼용을 막을 수 없음

```typescript
type CompanyID = string
type UserID = string

function queryForUser(id: UserID) { /* ... */ }

let companyId: CompanyID = 'b4843361'
queryForUser(companyId) // OK (의도하지 않은 사용!)
```

**해결**: 타입 브랜딩(Type Branding)

```typescript
type CompanyID = string & {readonly brand: unique symbol}
type OrderID  = string & {readonly brand: unique symbol}
type UserID   = string & {readonly brand: unique symbol}
type ID = CompanyID | OrderID | UserID

// 생성자 함수 (컴패니언 객체 패턴 활용)
function CompanyID(id: string) { return id as CompanyID }
function OrderID(id: string)   { return id as OrderID }
function UserID(id: string)    { return id as UserID }

// 사용
function queryForUser(id: UserID) { /* ... */ }

let companyId = CompanyID('8a6076cf')
let userId    = UserID('d21b1dbf')

queryForUser(userId)    // OK
queryForUser(companyId) // Error TS2345: CompanyID는 UserID에 할당 불가
```

> 런타임 오버헤드 없음. 브랜드는 컴파일 타임에만 존재하고, 런타임에는 단순 string이다.

---

## 10. 프로토타입 안전하게 확장하기 (Safely Extending the Prototype)

TypeScript와 함께라면 내장 타입의 프로토타입을 안전하게 확장할 수 있다.

```typescript
// 1. zip.ts: TypeScript에 .zip 타입 정보 알리기 (인터페이스 병합 활용)
interface Array<T> {
  zip<U>(list: U[]): [T, U][]
}

// 2. Array.prototype에 zip 구현
Array.prototype.zip = function<T, U>(
  this: T[],
  list: U[]
): [T, U][] {
  return this.map((v, k) => tuple(v, list[k]))
}
```

`zip.ts`를 프로젝트에서 자동으로 포함되지 않도록 `tsconfig.json`에서 제외:

```json
{
  "exclude": ["./zip.ts"]
}
```

사용 시에는 명시적으로 임포트:

```typescript
import './zip'

[1, 2, 3]
  .map(n => n * 2)       // number[]
  .zip(['a', 'b', 'c'])  // [number, string][]

// 결과: [[2, 'a'], [4, 'b'], [6, 'c']]
```

---

## 핵심 요약

| 개념 | 내용 |
|------|------|
| **서브타입/슈퍼타입** | A <: B이면 B가 필요한 곳에 A를 사용 가능 |
| **변성** | 객체·배열·반환 타입은 공변, 함수 파라미터는 반변 |
| **타입 넓히기** | `let`은 넓은 타입, `const`는 좁은 타입으로 추론 |
| **`as const`** | 타입 넓히기 방지 + 재귀적 readonly |
| **타입 정제** | 제어 흐름 분석으로 타입을 좁힘 |
| **판별 유니온** | 태그 필드로 유니온 케이스를 정확히 구분 |
| **완전성 검사** | 모든 케이스 처리 여부를 컴파일 타임에 검사 |
| **keyof + keying-in** | 객체 타입의 키/값 타입을 동적으로 참조 |
| **매핑 타입** | 기존 타입을 변환하여 새 타입 생성 |
| **조건부 타입** | 타입 레벨 삼항 연산자, 분배 법칙 적용 |
| **infer** | 조건부 타입 내에서 타입을 인라인 추론 |
| **타입 브랜딩** | 구조적 타입 시스템에서 명목적 타입 시뮬레이션 |

---

## 연습 문제

1. 다음 타입 쌍에서 첫 번째 타입이 두 번째 타입에 할당 가능한지 판단하고 이유를 설명하라:
   - `1` → `number`
   - `number` → `1`
   - `string` → `number | string`
   - `boolean` → `number`
   - `number[]` → `(number | string)[]`
   - `(number | string)[]` → `number[]`
   - `{a: true}` → `{a: boolean}`
   - `(a: number) => string` → `(b: number) => string`
   - `(a: number) => string` → `(a: string) => string`
   - `(a: number | string) => string` → `(a: string) => string`

2. `type O = {a: {b: {c: string}}}`에서 `keyof O`와 `O['a']['b']`의 타입은 무엇인가?

3. `Exclusive<T, U>` 타입을 구현하라: T 또는 U에 있지만 **둘 다에는 없는** 타입을 계산한다. `Exclusive<1 | 2 | 3, 2 | 3 | 4>`는 `1 | 4`가 되어야 한다.

4. 확정 할당 단언 예시를 단언 없이 리팩토링하라.
