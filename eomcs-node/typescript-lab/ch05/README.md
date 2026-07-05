# Chapter 5. Classes and Interfaces

이번 장에서는 다음 내용을 살펴본다.

- 클래스 선언과 상속, 접근 제어자
- `abstract` 클래스와 인터페이스
- 구조적 타이핑과 클래스
- 제네릭 클래스
- Mixin, 데코레이터, `final` 클래스 시뮬레이션
- 팩토리 패턴 / 빌더 패턴

---

## 클래스와 상속 (Classes and Inheritance)

체스 엔진 예제로 TypeScript 클래스의 핵심 기능을 살펴본다.

```typescript
type Color = 'Black' | 'White'
type File = 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H'
type Rank = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8

class Position {
  constructor(
    private file: File,
    private rank: Rank
  ) {}

  distanceFrom(position: Position) {
    return {
      rank: Math.abs(position.rank - this.rank),
      file: Math.abs(position.file.charCodeAt(0) - this.file.charCodeAt(0))
    }
  }
}

abstract class Piece {
  protected position: Position

  constructor(
    private readonly color: Color,
    file: File,
    rank: Rank
  ) {
    this.position = new Position(file, rank)
  }

  moveTo(position: Position) {
    this.position = position
  }

  abstract canMoveTo(position: Position): boolean
}

class King extends Piece {
  canMoveTo(position: Position) {
    let distance = this.position.distanceFrom(position)
    return distance.rank < 2 && distance.file < 2
  }
}
```

### 접근 제어자 (Access Modifiers)

| 제어자 | 접근 범위 |
|--------|-----------|
| `public` | 어디서든 접근 가능 (기본값) |
| `protected` | 해당 클래스 및 서브클래스 인스턴스에서만 접근 가능 |
| `private` | 해당 클래스 인스턴스에서만 접근 가능 |

- `private readonly`: 생성자에서 초기화 후 읽기 전용
- 생성자 파라미터에 접근 제어자를 붙이면 `this.xxx = xxx` 자동 할당

### abstract 클래스

```typescript
abstract class Piece {
  abstract canMoveTo(position: Position): boolean  // 서브클래스가 반드시 구현
  moveTo(position: Position) { /* 기본 구현 */ }   // 선택적으로 오버라이드
}

new Piece('White', 'E', 1)  // Error TS2511: Cannot create an instance of an abstract class.
```

- 직접 인스턴스화 불가, 상속을 통해서만 사용
- `abstract` 메서드는 서브클래스에서 반드시 구현해야 한다
- 구현하지 않으면 컴파일 오류 발생

### super

- `super.method()`: 부모 클래스의 메서드 호출
- `super()`: 생성자에서 부모 생성자 호출 (자식 클래스에 생성자가 있으면 **반드시** 호출)
- `super`로는 메서드만 접근 가능하고, 프로퍼티에는 접근 불가

---

## this를 반환 타입으로 사용 (Using this as a Return Type)

메서드 체이닝(method chaining) API를 구현할 때 유용하다.

```typescript
class Set {
  has(value: number): boolean { /* ... */ }

  add(value: number): this {  // Set 대신 this로 반환
    /* ... */
    return this
  }
}

class MutableSet extends Set {
  delete(value: number): boolean { /* ... */ }
  // add를 오버라이드할 필요 없음 — this가 MutableSet을 자동으로 가리킴
}
```

`this`를 반환 타입으로 사용하면 서브클래스에서 반환 타입을 재정의할 필요가 없다.

---

## 인터페이스 (Interfaces)

`type` 별칭과 유사하지만 몇 가지 차이가 있다.

```typescript
// type 별칭 방식
type Sushi = {
  calories: number
  salty: boolean
  tasty: boolean
}

// interface 방식 (거의 동일)
interface Sushi {
  calories: number
  salty: boolean
  tasty: boolean
}
```

### 인터페이스 상속

```typescript
interface Food {
  calories: number
  tasty: boolean
}

interface Sushi extends Food {
  salty: boolean
}

interface Cake extends Food {
  sweet: boolean
}
```

인터페이스는 다른 인터페이스, 객체 타입, 클래스 모두를 `extends`할 수 있다.

### type vs interface 차이점

| 구분 | type | interface |
|------|------|-----------|
| 오른쪽에 타입 표현식 가능 | ✅ (`type A = number \| string`) | ❌ (shape만 가능) |
| extends 시 할당 가능성 검사 | ❌ (교차 타입으로 병합) | ✅ (오류 발생) |
| 동일 이름 중복 선언 | ❌ (컴파일 오류) | ✅ (선언 병합) |

```typescript
// type만 가능한 표현
type A = number
type B = A | string

// interface extends 시 엄격한 검사
interface A {
  bad(x: number): string
}
interface B extends A {
  bad(x: string): string  // Error TS2430: 'number'는 'string'에 할당 불가
}
```

### type 조합 vs interface 상속 vs class 상속

| 구분             | 타입 조합(Type Composition) | 클래스 상속(Class Inheritance) | 인터페이스 상속(Interface Inheritance) |
| -------------- | ----------------------- | ------------------------- | ------------------------------- |
| 목적             | 새로운 타입 생성               | 코드 재사용 및 구현 상속            | 타입 재사용                          |
| 대상             | 타입(`type`, `interface`) | 클래스(`class`)              | 인터페이스(`interface`)              |
| 구현 상속          | X                       | O                         | X                               |
| 타입 상속          | O                       | O                         | O                               |
| 런타임 영향         | 없음                      | 있음                        | 없음                              |
| TypeScript 권장도 | ★★★★★                   | ★★☆☆☆                     | ★★★★☆                           |

**타입 조합**:

```typescript
type Person = {
  name: string
}

type Employee = {
  employeeId: number
}

type Developer = Person & Employee

// 결과:
// type Developer = {
//   name: string
//   employeeId: number
// }
```

- 구현을 상속하지 않는다.
- 타입만 조합한다.
- 매우 유연하다.
- 여러 타입을 자유롭게 결합할 수 있다.

**클래스 상속**:

```typescript
class Person {
  constructor(public name: string) {}
}

class Employee extends Person {
  constructor(
    name: string,
    public employeeId: number
  ) {
    super(name)
  }
}
```

- 구현까지 상속
- 메서드도 상속
- 런타임에도 존재
- 부모 클래스와 강하게 결합됨

**인터페이스 상속**:

```typescript
interface Person {
  name: string
}

interface Employee extends Person {
  employeeId: number
}

// 결과:
// interface Employee {
//     name
//     employeeId
// }
```

- 구현이 없음
- 타입만 상속
- 런타임에는 사라짐

### 선언 병합 (Declaration Merging)

동일한 이름의 인터페이스를 여러 번 선언하면 자동으로 병합된다.

```typescript
interface User {
  name: string
}
interface User {
  age: number
}

let a: User = {
  name: 'Ashley',
  age: 30   // 두 선언이 병합됨
}
```

같은 프로퍼티를 다른 타입으로 선언하면 오류:

```typescript
interface User { age: string }
interface User { age: number }  // Error TS2717: 타입이 일치해야 함
```

---

## implements 키워드 (Implementations)

클래스가 특정 인터페이스를 만족하는지 컴파일 타임에 검증한다.

```typescript
interface Animal {
  readonly name: string
  eat(food: string): void
  sleep(hours: number): void
}

interface Feline {
  meow(): void
}

class Cat implements Animal, Feline {  // 여러 인터페이스 구현 가능
  name = 'Whiskers'
  eat(food: string) { console.info('Ate some', food) }
  sleep(hours: number) { console.info('Slept for', hours, 'hours') }
  meow() { console.info('Meow') }
}
```

- 인터페이스는 `private`, `protected`, `static` 선언 불가
- `readonly` 프로퍼티 선언 가능

### abstract 클래스 vs interface

| 구분 | abstract class | interface |
|------|---------------|-----------|
| 모델링 대상 | 클래스만 | 객체, 배열, 함수, 클래스 등 모든 shape |
| 런타임 코드 생성 | ✅ (JavaScript 클래스로 컴파일) | ❌ (컴파일 타임에만 존재) |
| 생성자, 기본 구현 | ✅ | ❌ |
| 접근 제어자 | ✅ | ❌ |
| 여러 구현 공유 | ✅ | ❌ |

> 여러 클래스에서 구현을 공유할 때 → **abstract class**  
> "이 클래스는 T이다"를 가볍게 표현할 때 → **interface**

---

## 구조적 타이핑과 클래스 (Classes Are Structurally Typed)

TypeScript는 클래스도 **구조(shape)** 로 비교한다. 이름이 달라도 형태가 같으면 호환된다.

```typescript
class Zebra {
  trot() { /* ... */ }
}

class Poodle {
  trot() { /* ... */ }
}

function ambleAround(animal: Zebra) {
  animal.trot()
}

ambleAround(new Zebra)   // OK
ambleAround(new Poodle)  // OK — Poodle이 Zebra와 같은 형태이므로
```

단, `private` 또는 `protected` 필드가 있으면 예외:

```typescript
class A {
  private x = 1
}
class B extends A {}

function f(a: A) {}

f(new A)   // OK
f(new B)   // OK (서브클래스이므로)
f({x: 1})  // Error TS2345: 'x'가 A에서 private이지만 '{x: number}'에서는 아님
```

---

## 클래스는 값과 타입 동시에 선언 (Classes Declare Both Values and Types)

> 타입스크립트의 거의 모든 것은 값 아니면 타입이다.

```typescript
let a = 1999
function b() {}

// 타입
type a = number
interface b {
  (): void
}
```

- 타입스크립트에서 값과 타입은 별도 네임스페이스에 존재한다.
- 문맥을 보고 타입인지 값인지 구분한다.

```typescript
if (a + 1 > 3) { /* ... */ }  // 문맥상 a를 값으로 추론
let x: a = 3                  // 문맥상 a를 타입으로 추론
```

클래스와 열거형은 **타입 네임스페이스에 타입**을, **값 네임스페이스에 값을** 동시에 등록된다.

```typescript
type State = { 
  [key: string]: string 
}

class StringDatabase {
  state: State = {}
  get(key: string): string | null { /* ... */ }
  set(key: string, value: string): void { /* ... */ }
  static from(state: State): StringDatabase { /* ... */ }
}
```

위 클래스 선언이 생성하는 두 가지 타입:

```typescript
// 1. 인스턴스 타입: StringDatabase
interface StringDatabase {
  state: State
  get(key: string): string | null
  set(key: string, value: string): void
}

// 2. 생성자 타입: typeof StringDatabase
interface StringDatabaseConstructor {
  new(): StringDatabase
  from(state: State): StringDatabase
}
```

`new()` — **생성자 시그니처(constructor signature)**: `new` 연산자로 인스턴스를 만들 수 있다는 의미.

---

## 제네릭 클래스 (Polymorphism)

클래스와 인터페이스에서도 제네릭 타입 파라미터를 사용할 수 있다.

```typescript
class MyMap<K, V> {
  constructor(initialKey: K, initialValue: V) { /* ... */ }

  get(key: K): V { /* ... */ }
  set(key: K, value: V): void { /* ... */ }

  // 인스턴스 메서드는 클래스 제네릭 + 자체 제네릭 모두 사용 가능
  merge<K1, V1>(map: MyMap<K1, V1>): MyMap<K | K1, V | V1> { /* ... */ }

  // 정적 메서드는 클래스 제네릭에 접근 불가, 자체 제네릭 선언 필요
  static of<K, V>(k: K, v: V): MyMap<K, V> { /* ... */ }
}

// 명시적 바인딩
let a = new MyMap<string, number>('k', 1)  // MyMap<string, number>

// 추론
let b = new MyMap('k', true)               // MyMap<string, boolean>
```

> 생성자 안에서는 제네릭 선언 불가 → 클래스 선언부에 선언해야 한다.  
> 정적 메서드는 클래스 레벨 제네릭에 접근 불가.

---

## Mixin 패턴

JavaScript/TypeScript는 `mixin` 키워드가 없지만, **함수**로 구현할 수 있다.

> **Mixin**: 클래스 생성자를 받아서 새로운 클래스 생성자를 반환하는 함수.  
> "is-a" 대신 "can-do", "has-a" 관계를 표현한다.

```typescript
type ClassConstructor<T> = new(...args: any[]) => T

function withEZDebug<C extends ClassConstructor<{
  getDebugValue(): object
}>>(Class: C) {
  return class extends Class {
    debug() {
      let Name = Class.constructor.name
      let value = this.getDebugValue()
      return Name + '(' + JSON.stringify(value) + ')'
    }
  }
}

class HardToDebugUser {
  constructor(
    private id: number,
    private firstName: string,
    private lastName: string
  ) {}

  getDebugValue() {
    return {
      id: this.id,
      name: this.firstName + ' ' + this.lastName
    }
  }
}

let User = withEZDebug(HardToDebugUser)
let user = new User(3, 'Emma', 'Gluzman')
user.debug()  // 'User({"id": 3, "name": "Emma Gluzman"})'
```

Mixin 규칙:
- 상태(인스턴스 프로퍼티)를 가질 수 있다
- 구체적인 메서드만 제공 (추상 메서드 불가)
- 생성자를 가질 수 있으며, 클래스가 믹스인된 순서로 호출됨

---

## 데코레이터 (Decorators)

클래스, 메서드, 프로퍼티, 파라미터에 대한 **메타프로그래밍** 문법.  
내부적으로는 대상을 감싸는 함수 호출이다.

```typescript
@serializable
class APIPayload {
  getValue(): Payload { /* ... */ }
}

// 위와 동일한 의미
let APIPayload = serializable(class APIPayload {
  getValue(): Payload { /* ... */ }
})
```

> **TSC 플래그**: `"experimentalDecorators": true` — 아직 실험적 기능이므로 사용 주의.

데코레이터 타입별 시그니처 요약:

| 대상 | 시그니처 |
|------|---------|
| 클래스 | `(Constructor: {new(...any[]) => any}) => any` |
| 메서드 | `(classPrototype: {}, methodName: string, descriptor: PropertyDescriptor) => any` |
| 프로퍼티 | `(classPrototype: {}, propertyName: string) => any` |
| 파라미터 | `(classPrototype: {}, paramName: string, index: number) => void` |

> 데코레이터는 클래스 shape 변경을 TypeScript가 추적하지 않으므로, 안정화될 때까지는 **일반 함수** 사용을 권장한다.

---

## final 클래스 시뮬레이션 (Simulating final Classes)

TypeScript에는 `final` 키워드가 없지만 `private` 생성자로 시뮬레이션할 수 있다.

```typescript
class MessageQueue {
  private constructor(private messages: string[]) {}

  // 정적 팩토리 메서드로 인스턴스 생성 허용
  static create(messages: string[]) {
    return new MessageQueue(messages)
  }
}

class BadQueue extends MessageQueue {}  // Error TS2675: 생성자가 private

MessageQueue.create([])                 // OK — 정적 메서드로만 생성
```

---

## 디자인 패턴 (Design Patterns)

### 팩토리 패턴 (Factory Pattern)

어떤 구체적인 객체를 생성할지 팩토리에 위임하는 패턴.

```typescript
type Shoe = { purpose: string }

class BalletFlat implements Shoe { purpose = 'dancing' }
class Boot implements Shoe { purpose = 'woodcutting' }
class Sneaker implements Shoe { purpose = 'walking' }

// companion object pattern: 타입 Shoe와 값 Shoe가 같은 이름을 공유
let Shoe = {
  create(type: 'balletFlat' | 'boot' | 'sneaker'): Shoe {
    switch (type) {
      case 'balletFlat': return new BalletFlat
      case 'boot': return new Boot
      case 'sneaker': return new Sneaker
    }
  }
}

Shoe.create('boot')  // Shoe
```

유니온 타입으로 유효하지 않은 타입 전달을 컴파일 타임에 방지한다.

---

### 빌더 패턴 (Builder Pattern)

객체 생성 로직을 메서드 체이닝으로 분리하는 패턴.

```typescript
class RequestBuilder {
  private data: object | null = null
  private method: 'get' | 'post' | null = null
  private url: string | null = null

  setMethod(method: 'get' | 'post'): this {
    this.method = method
    return this
  }

  setData(data: object): this {
    this.data = data
    return this
  }

  setURL(url: string): this {
    this.url = url
    return this
  }

  send() { /* ... */ }
}

new RequestBuilder()
  .setURL('/users')
  .setMethod('get')
  .setData({ firstName: 'Anna' })
  .send()
```

반환 타입을 `this`로 지정하면 서브클래스에서도 체이닝이 자연스럽게 동작한다.

---

## 핵심 요약

- `class` + `extends` 로 상속. `abstract`로 직접 인스턴스화 금지.
- 접근 제어자: `public`(기본) / `protected` / `private`
- `readonly` 로 초기화 후 변경 불가 설정
- `implements` 로 클래스가 인터페이스를 충족하는지 컴파일 타임 검증
- `interface`는 선언 병합 지원, `type`은 더 풍부한 표현 지원
- TypeScript의 클래스는 **구조적 타이핑**을 따른다 (이름이 아닌 형태로 비교)
- 클래스 선언은 **타입과 값을 동시에** 생성한다
- Mixin으로 다중 상속 효과를 타입 안전하게 구현할 수 있다

---

## 연습문제

**1.** 클래스와 인터페이스의 차이점은 무엇인가?

**2.** 생성자를 `private` 대신 `protected`로 선언하면 어떻게 되는가? 코드 에디터에서 직접 실험해보라.

**3.** 팩토리 패턴 구현을 개선하라: `Shoe.create('boot')`가 `Shoe`가 아닌 `Boot`를 반환하도록 타입 시그니처를 수정하라. (힌트: 오버로드 활용)

**4. [Hard]** 빌더 패턴을 타입 안전하게 개선하라.

a. `.send()` 호출 전에 최소한 URL과 method가 반드시 설정되어 있어야 한다는 것을 컴파일 타임에 보장하라. (순서를 강제한다면 더 쉬울까?)

b. **[Harder]** 메서드 호출 순서에 제약 없이 위 조건을 보장하려면 어떻게 해야 하는가? (힌트: 각 메서드 호출마다 `this` 타입에 정보를 "추가"하는 TypeScript 기능은?)
