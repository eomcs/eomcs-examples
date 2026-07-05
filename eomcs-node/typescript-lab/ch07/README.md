# Chapter 7. Handling Errors

이번 장에서는 타입스크립트에서 에러를 표현하고 처리하는 가장 일반적인 패턴 네 가지를 소개한다.

- null 반환
- 예외 던지기
- 예외 반환
- Option 타입

---

## 오류 처리 방법 개요

| 방법 | 실패 이유 전달 | 소비자 강제 처리 | 체이닝 용이성 |
|------|:---------:|:-----------:|:---------:|
| null 반환 | ✗ | ✗ | ✗ |
| 예외 던지기 | ✓ | ✗ | ✓ |
| 예외 반환 | ✓ | ✓ | △ |
| Option 타입 | ✗ | ✓ | ✓ |

---

## 1. null 반환 (Returning null)

오류 발생 시 `null`을 반환하는 가장 단순한 방법이다.

```typescript
function ask() {
  return prompt('When is your birthday?')
}

function isValid(date: Date) {
  return Object.prototype.toString.call(date) === '[object Date]'
      && !Number.isNaN(date.getTime())
}

function parse(birthday: string): Date | null {
  let date = new Date(birthday)
  if (!isValid(date)) {
    return null
  }
  return date
}

let date = parse(ask())
if (date) {
  console.info('Date is', date.toISOString())
} else {
  console.error('Error parsing date for some reason')
}
```

**장점**:
- 타입 안전한 방식으로 오류를 처리하는 가장 가벼운 방법
- 소비자가 null 체크를 강제로 해야 함

**단점**:
- 왜 실패했는지 이유를 전달할 수 없음
- 중첩·체이닝 시 null 체크가 반복되어 장황해짐

---

## 2. 예외 던지기 (Throwing Exceptions)

실패 이유를 예외 메시지로 전달한다.

```typescript
class InvalidDateFormatError extends RangeError {}
class DateIsInTheFutureError extends RangeError {}

function parse(birthday: string): Date {
  let date = new Date(birthday)
  if (!isValid(date)) {
    throw new InvalidDateFormatError('Enter a date in the form YYYY/MM/DD')
  }
  if (date.getTime() > Date.now()) {
    throw new DateIsInTheFutureError('Are you a timelord?')
  }
  return date
}

try {
  let date = parse(ask())
  console.info('Date is', date.toISOString())
} catch (e) {
  if (e instanceof InvalidDateFormatError) {
    console.error(e.message)
  } else if (e instanceof DateIsInTheFutureError) {
    console.info(e.message)
  } else {
    throw e  // 다른 예외는 다시 던짐
  }
}
```

**커스텀 에러 클래스 사용 이유**:
- 오류 유형별로 다른 처리가 가능
- 서버 로그에서 오류를 구분하기 쉬움
- 사용자에게 구체적인 피드백 제공 가능

**JSDoc으로 예외 문서화**:

```typescript
/**
 * @throws {InvalidDateFormatError} 날짜 형식이 잘못된 경우
 * @throws {DateIsInTheFutureError} 미래 날짜를 입력한 경우
 */
function parse(birthday: string): Date {
  // ...
}
```

**장점**:
- 실패 이유를 메시지와 타입으로 전달 가능
- 여러 연산을 하나의 `try/catch`로 감쌀 수 있음 (체이닝 용이)

**단점**:
- TypeScript는 예외를 함수 시그니처에 인코딩하지 않음
- 소비자가 `try/catch`를 쓰지 않아도 타입 오류가 나지 않음 → 처리 누락 가능

---

## 3. 예외 반환 (Returning Exceptions)

유니온 타입으로 예외를 반환 타입에 포함시켜 소비자가 반드시 처리하게 만든다.

```typescript
function parse(
  birthday: string
): Date | InvalidDateFormatError | DateIsInTheFutureError {
  let date = new Date(birthday)
  if (!isValid(date)) {
    return new InvalidDateFormatError('Enter a date in the form YYYY/MM/DD')
  }
  if (date.getTime() > Date.now()) {
    return new DateIsInTheFutureError('Are you a timelord?')
  }
  return date
}

let result = parse(ask())
if (result instanceof InvalidDateFormatError) {
  console.error(result.message)
} else if (result instanceof DateIsInTheFutureError) {
  console.info(result.message)
} else {
  console.info('Date is', result.toISOString())
}
```

소비자가 개별 오류 처리를 피하고 싶다면 `Error`로 묶어서 처리할 수 있다:

```typescript
let result = parse(ask())
if (result instanceof Error) {
  console.error(result.message)
} else {
  console.info('Date is', result.toISOString())
}
```

**장점**:
- 타입 시스템이 소비자에게 오류 처리를 강제함
- 가능한 예외 유형을 함수 시그니처에서 명확히 알 수 있음

**단점**:
- 연산을 체이닝할수록 반환 타입의 오류 목록이 길어짐

```typescript
function x(): T | Error1 { /* ... */ }
function y(): U | Error1 | Error2 {
  let a = x()
  if (a instanceof Error) return a
  // a로 무언가를 함
}
function z(): U | Error1 | Error2 | Error3 {
  let a = y()
  if (a instanceof Error) return a
  // a로 무언가를 함
}
```

---

## 4. Option 타입 (The Option Type)

> Haskell, OCaml, Scala, Rust 등에서 가져온 함수형 패턴

실패할 수 있는 값을 **컨테이너**에 담아 반환한다. 컨테이너에 값이 있으면 `Some<T>`, 없으면 `None`이다.

```
Option<T>
├── Some<T>  — 성공, 값 T를 포함
└── None     — 실패, 값 없음
```

### 배열로 Option 흉내내기

```typescript
function parse(birthday: string): Date[] {
  let date = new Date(birthday)
  if (!isValid(date)) return []
  return [date]
}

function ask(): string[] {
  let result = prompt('When is your birthday?')
  if (result === null) return []
  return [result]
}

// 체이닝 (flatten 필요)
flatten(ask().map(parse))
  .map(date => date.toISOString())
  .forEach(date => console.info('Date is', date))

function flatten<T>(array: T[][]): T[] {
  return Array.prototype.concat.apply([], array)
}
```

### 정식 Option 타입 구현

```typescript
interface Option<T> {
  flatMap<U>(f: (value: T) => None): None
  flatMap<U>(f: (value: T) => Option<U>): Option<U>
  getOrElse(value: T): T
}

class Some<T> implements Option<T> {
  constructor(private value: T) {}

  flatMap<U>(f: (value: T) => None): None
  flatMap<U>(f: (value: T) => Some<U>): Some<U>
  flatMap<U>(f: (value: T) => Option<U>): Option<U> {
    return f(this.value)  // 값이 있으면 f를 적용
  }

  getOrElse(): T {
    return this.value     // 값을 그대로 반환
  }
}

class None implements Option<never> {
  flatMap(): None {
    return this           // 실패 상태는 그대로 전파
  }

  getOrElse<U>(value: U): U {
    return value          // 기본값 반환
  }
}
```

### Option 생성 함수 (컴패니언 객체 패턴)

```typescript
function Option<T>(value: null | undefined): None
function Option<T>(value: T): Some<T>
function Option<T>(value: T): Option<T> {
  if (value == null) {
    return new None
  }
  return new Some(value)
}
```

### flatMap 결과 타입 행렬

| | `f`가 `Some<U>` 반환 | `f`가 `None` 반환 |
|---|---|---|
| `Some<T>.flatMap(f)` | `Some<U>` | `None` |
| `None.flatMap(f)` | `None` | `None` |

### 사용 예시

```typescript
// 기본 사용
let result = Option(6)           // Some<number>
  .flatMap(n => Option(n * 3))   // Some<number>
  .flatMap(n => new None)        // None
  .getOrElse(7)                  // 7

// 생일 입력 예시
ask()                                              // Option<string>
  .flatMap(parse)                                  // Option<Date>
  .flatMap(date => new Some(date.toISOString()))   // Option<string>
  .flatMap(date => new Some('Date is ' + date))    // Option<string>
  .getOrElse('Error parsing date for some reason') // string
```

**장점**:
- 연속적인 연산을 체이닝할 때 매우 강력함
- 타입 안전성 우수

**단점**:
- 왜 실패했는지 이유를 알 수 없음 (`null` 반환과 동일한 단점)
- Option을 사용하지 않는 코드와 상호운용이 불편함 (래핑 필요)

---

## 핵심 요약

TypeScript에서 오류를 다루는 방법 선택 기준:

- **실패 이유가 필요 없다면**: `null` 반환 또는 `Option` 타입
- **실패 이유를 전달해야 한다면**: 예외 던지기 또는 예외 반환
- **소비자가 반드시 오류를 처리해야 한다면**: 예외 반환 또는 `Option` 타입
- **오류를 체이닝해야 한다면**: 예외 던지기 또는 `Option` 타입

---

## 연습 문제

다음 API에 대해 이 장에서 다룬 패턴 중 하나를 선택하여 오류 처리를 설계하라. 각 연산은 실패할 수 있다. 로그인한 사용자 ID를 가져오고, 친구 목록을 조회하고, 각 친구의 이름을 가져오는 시나리오를 고려하라:

```typescript
class API {
  getLoggedInUserID(): UserID
  getFriendIDs(userID: UserID): UserID[]
  getUserName(userID: UserID): string
}
```
