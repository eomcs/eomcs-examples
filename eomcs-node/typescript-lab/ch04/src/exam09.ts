/**
 * 호출 시그니처 (Call Signatures)
 *
 * 호출 시그니처는 함수 자체의 타입을 표현한다.
 * 함수를 변수에 담거나, 인수로 전달하거나, 반환할 때 유용하다.
 */

// 1. 단축 호출 시그니처
type GreetingFunction = (name: string) => string
type AuditLogFunction = (message: string, userId?: string) => void
type SumManyFunction = (...numbers: number[]) => number

let greetBySignature: GreetingFunction = name => `hello ${name}`

console.log(greetBySignature('Ada')) // hello Ada

// greetBySignature(123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// let wrongGreeting: GreetingFunction = name => name.length
// Error: Type 'number' is not assignable to type 'string'.

let sumManyBySignature: SumManyFunction = (...numbers) => {
  return numbers.reduce((total, n) => total + n, 0)
}

console.log(sumManyBySignature(1, 2, 3, 4)) // 10

// sumManyBySignature(1, '2', 3)
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 2. 전체 호출 시그니처
// 단축 호출 시그니처와 같은 의미지만, 객체 타입 문법으로 함수 호출 형태를 표현한다.
type FullGreetingFunction = {
  (name: string): string
}

let greetByFullSignature: FullGreetingFunction = name => {
  return `hi ${name}`
}

console.log(greetByFullSignature('Grace')) // hi Grace

type FullAuditLogFunction = {
  (message: string, userId?: string): void
}

let auditLog: FullAuditLogFunction = (message, userId = 'Not signed in') => {
  let time = new Date().toISOString()

  console.log(time, message, userId)
}

auditLog('Page loaded')
auditLog('User signed in', 'u-123')

// auditLog('User signed in', 123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 3. 호출 시그니처에는 타입 레벨 코드만 쓴다.
// 기본값은 값(value)이므로 시그니처에 넣을 수 없고, 구현부에서만 설정한다.
//
// type InvalidLogFunction = {
//   (message: string, userId = 'Not signed in'): void
// }
// Error: A parameter initializer is only allowed in a function or constructor implementation.

// 4. 호출 시그니처로 함수를 구현하면 파라미터 타입을 다시 적지 않아도 된다.
type FormatterFunction = (value: number, unit?: string) => string

let formatMeasurement: FormatterFunction = (value, unit = 'px') => {
  return `${value}${unit}`
}

console.log(formatMeasurement(12)) // 12px
console.log(formatMeasurement(1.5, 'rem')) // 1.5rem

// formatMeasurement('12')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 5. 함수를 인수로 받기
function runWithIndex(callback: (index: number) => void, count: number) {
  for (let i = 0; i < count; i++) {
    callback(i)
  }
}

runWithIndex(index => {
  console.log(index.toFixed(0)) // index는 number로 추론된다.
}, 3)

// runWithIndex(index => {
//   console.log(index.toUpperCase())
// }, 3)
// Error: Property 'toUpperCase' does not exist on type 'number'.

// 6. 함수를 반환하기
type NumberPredicate = (value: number) => boolean

function createGreaterThanPredicate(minimum: number): NumberPredicate {
  return value => value > minimum
}

let greaterThanTen = createGreaterThanPredicate(10)

console.log(greaterThanTen(15)) // true
console.log(greaterThanTen(5)) // false

// greaterThanTen('15')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 7. 배열 메서드에서도 문맥적 타입 추론이 동작한다.
let pricesForSignature = [100, 200, 300]
let discountedPrices = pricesForSignature.map(price => price * 0.9)

console.log(discountedPrices) // [90, 180, 270]

// pricesForSignature.map(price => price.toUpperCase())
// Error: Property 'toUpperCase' does not exist on type 'number'.
