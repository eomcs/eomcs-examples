/**
 * Option 타입 (The Option Type)
 *
 * 실패할 수 있는 값을 컨테이너에 담아 반환하는 함수형 오류 처리 패턴이다.
 * 값이 있으면 Some<T>, 값이 없으면 None으로 표현한다.
 */

export {}

function isValid(date: Date) {
  return (
    Object.prototype.toString.call(date) === '[object Date]' &&
    !Number.isNaN(date.getTime())
  )
}

// 1. 배열로 Option 흉내내기
function parseAsArray(birthday: string): Date[] {
  let date = new Date(birthday)

  if (!isValid(date)) {
    return []
  }

  return [date]
}

function askAsArray(input: string | null): string[] {
  if (input === null) {
    return []
  }

  return [input]
}

function flatten<T>(array: T[][]): T[] {
  return Array.prototype.concat.apply([], array)
}

flatten(askAsArray('1990/05/12').map(parseAsArray))
  .map((date) => date.toISOString())
  .forEach((date) => console.info('Date is', date))

flatten(askAsArray('not a date').map(parseAsArray))
  .map((date) => date.toISOString())
  .forEach((date) => console.info('Date is', date))

// 배열 방식은 동작하지만, 실패 여부를 표현하려고 []와 [value] 규칙을 직접 지켜야 한다.

// 2. 정식 Option 타입 구현
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
    return f(this.value)
  }

  getOrElse(_value?: T): T {
    return this.value
  }
}

class None implements Option<never> {
  flatMap<U>(f: (value: never) => None): None
  flatMap<U>(f: (value: never) => Option<U>): None
  flatMap(): None {
    return this
  }

  getOrElse<U>(value: U): U {
    return value
  }
}

// 3. Option 생성 함수 (컴패니언 객체 패턴)
function Option<T>(value: null | undefined): None
function Option<T>(value: T): Some<T>
function Option<T>(value: T | null | undefined): Option<T> {
  if (value == null) {
    return new None()
  }

  return new Some(value as T)
}

function ask(input: string | null): Option<string> {
  if (input === null) {
    return new None()
  }

  return Option(input)
}

function parse(birthday: string): Option<Date> {
  let date = new Date(birthday)

  if (!isValid(date)) {
    return new None()
  }

  return new Some(date)
}

// 4. flatMap 결과 타입 행렬
let someToSome = Option(6).flatMap((number) => Option(number * 3)) // Some<number>
let someToNone = Option(6).flatMap(() => new None()) // None
let noneToSome = new None().flatMap(() => Option(18)) // None
let noneToNone = new None().flatMap(() => new None()) // None

console.log(someToSome.getOrElse(7))
console.log(someToNone.getOrElse(7))
console.log(noneToSome.getOrElse(7))
console.log(noneToNone.getOrElse(7))

// 5. 사용 예시
let result = Option(6)
  .flatMap((number) => Option(number * 3))
  .flatMap(() => new None())
  .getOrElse(7)

console.log(result) // 7

let validMessage = ask('1990/05/12')
  .flatMap(parse)
  .flatMap((date) => new Some(date.toISOString()))
  .flatMap((date) => new Some('Date is ' + date))
  .getOrElse('Error parsing date for some reason')

console.log(validMessage)

let invalidMessage = ask('not a date')
  .flatMap(parse)
  .flatMap((date) => new Some(date.toISOString()))
  .flatMap((date) => new Some('Date is ' + date))
  .getOrElse('Error parsing date for some reason')

console.log(invalidMessage)

let missingInputMessage = ask(null)
  .flatMap(parse)
  .flatMap((date) => new Some(date.toISOString()))
  .flatMap((date) => new Some('Date is ' + date))
  .getOrElse('Error parsing date for some reason')

console.log(missingInputMessage)

// 6. Option은 타입 안전하게 체이닝할 수 있지만, 실패 이유는 전달하지 않는다.
let failedByInvalidDate = parse('invalid')
let failedByMissingInput = ask(null).flatMap(parse)

console.log(failedByInvalidDate.getOrElse(new Date(0)).toISOString())
console.log(failedByMissingInput.getOrElse(new Date(0)).toISOString())

// 두 실패 모두 None이라서 호출자는 왜 실패했는지 알 수 없다.
