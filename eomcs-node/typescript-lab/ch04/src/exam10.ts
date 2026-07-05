/**
 * 오버로드된 함수 타입 (Overloaded Function Types)
 *
 * 오버로드 함수는 여러 개의 호출 시그니처를 가진 함수다.
 * 입력 타입이나 인수 개수에 따라 반환 타입을 다르게 표현할 때 사용한다.
 */

// 1. 오버로드된 함수 타입
type ReservationRequest = {
  from: Date
  to?: Date
  destination: string
  roundTrip: boolean
}

type ReserveTrip = {
  (from: Date, to: Date, destination: string): ReservationRequest
  (from: Date, destination: string): ReservationRequest
}

// 구현부에서는 오버로드 시그니처를 모두 처리할 수 있는 하나의 시그니처를 작성한다.
let reserveTrip: ReserveTrip = (
  from: Date,
  toOrDestination: Date | string,
  destination?: string
) => {
  if (toOrDestination instanceof Date && destination !== undefined) {
    return {
      from,
      to: toOrDestination,
      destination,
      roundTrip: true,
    }
  }

  if (typeof toOrDestination === 'string') {
    return {
      from,
      destination: toOrDestination,
      roundTrip: false,
    }
  }

  throw new Error('Invalid reservation')
}

let roundTrip = reserveTrip(
  new Date(2026, 6, 1),
  new Date(2026, 6, 7),
  'Seoul'
)

let oneWayTrip = reserveTrip(new Date(2026, 6, 1), 'Busan')

console.log(roundTrip.roundTrip) // true
console.log(oneWayTrip.roundTrip) // false

// reserveTrip(new Date(2026, 6, 1))
// Error: Expected 2-3 arguments, but got 1.

// reserveTrip(new Date(2026, 6, 1), 'Busan', 'Seoul')
// Error: No overload expects 3 arguments, but overloads do exist that expect either 2 or 3 arguments.

// 구현부의 합쳐진 시그니처는 외부에서 보이지 않는다.
// 두 번째 인수가 Date이면 세 번째 인수도 반드시 필요하다.
//
// reserveTrip(new Date(2026, 6, 1), new Date(2026, 6, 7))
// Error: Argument of type 'Date' is not assignable to parameter of type 'string'.

// 2. 반환 타입이 입력 타입에 따라 달라지는 오버로드
type ParseValueSignature = {
  (value: string, type: 'number'): number
  (value: string, type: 'boolean'): boolean
  (value: string, type: 'string'): string
}

function parseValueByType(value: string, type: 'number'): number
function parseValueByType(value: string, type: 'boolean'): boolean
function parseValueByType(value: string, type: 'string'): string
function parseValueByType(
  value: string,
  type: 'number' | 'boolean' | 'string'
): number | boolean | string {
  if (type === 'number') {
    return Number(value)
  }

  if (type === 'boolean') {
    return value === 'true'
  }

  return value
}

let parseValue: ParseValueSignature = parseValueByType

let parsedNumber = parseValue('42', 'number')
let parsedBoolean = parseValue('true', 'boolean')
let parsedString = parseValue('TypeScript', 'string')

console.log(parsedNumber.toFixed(1)) // 42.0
console.log(parsedBoolean.valueOf()) // true
console.log(parsedString.toUpperCase()) // TYPESCRIPT

// parseValue('42', 'date')
// Error: No overload matches this call.

// parsedNumber.toUpperCase()
// Error: Property 'toUpperCase' does not exist on type 'number'.

// 3. DOM API 스타일 오버로드 예시
// 이 프로젝트의 예제는 Node.js에서 실행하므로 실제 document.createElement는 사용하지 않는다.
// 대신 DOM API처럼 리터럴 태그에 따라 반환 타입이 달라지는 오버로드를 흉내 낸다.
type ElementLike = {
  tagName: string
  dataset: Record<string, string>
}

type AnchorElementLike = ElementLike & {
  href: string
}

type CanvasElementLike = ElementLike & {
  width: number
}

type TableElementLike = ElementLike & {
  rows: number
}

// 리터럴 오버로드를 먼저 두고, 마지막에 넓은 fallback 시그니처를 둔다.
function createKnownElement(tag: 'a'): AnchorElementLike
function createKnownElement(tag: 'canvas'): CanvasElementLike
function createKnownElement(tag: 'table'): TableElementLike
function createKnownElement(tag: string): ElementLike
function createKnownElement(tag: string): ElementLike {
  return {
    tagName: tag.toUpperCase(),
    dataset: {},
  }
}

let anchorElement = createKnownElement('a')
let canvasElement = createKnownElement('canvas')
let unknownElement = createKnownElement('section')

anchorElement.href = 'https://www.typescriptlang.org/'
canvasElement.width = 300
unknownElement.dataset.section = 'intro'

console.log(anchorElement.tagName) // A
console.log(canvasElement.tagName) // CANVAS
console.log(unknownElement.tagName) // SECTION

// createKnownElement('a').width = 300
// Error: Property 'width' does not exist on type 'AnchorElementLike'.

// TypeScript는 오버로드를 선언 순서대로 해결한다.
// 좁은 리터럴 오버로드가 넓은 string 오버로드보다 앞에 있어야 한다.

// 4. 함수 프로퍼티 타이핑
type WarnOnce = {
  (warning: string): void
  wasCalled: boolean
}

let warnOnce: WarnOnce = Object.assign(
  (warning: string) => {
    if (warnOnce.wasCalled) {
      return
    }

    warnOnce.wasCalled = true
    console.warn(warning)
  },
  { wasCalled: false }
)

warnOnce('Check your input') // 출력
warnOnce('Check your input again') // 이미 호출되었으므로 출력하지 않음

console.log(warnOnce.wasCalled) // true

// warnOnce(123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// warnOnce.wasCalled = 'yes'
// Error: Type 'string' is not assignable to type 'boolean'.
