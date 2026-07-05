/**
 * number
 *
 * number 타입은 정수, 실수, Infinity, NaN을 포함한다.
 * TypeScript는 선언 방식에 따라 number 또는 더 좁은 숫자 리터럴 타입으로 추론한다.
 */

// 1. let으로 선언하면 number로 추론한다.
let numberA = 1234

numberA = 5678
numberA = 12.34
numberA = Infinity
numberA = NaN

console.log(numberA)

// numberA = '1234'
// Error: Type 'string' is not assignable to type 'number'.

// 2. const로 선언하면 숫자 리터럴 타입으로 추론한다.
const numberB = 5678

console.log(numberB) // 5678

// numberB의 타입은 number가 아니라 5678이다.
// const는 값을 바꿀 수 없으므로 TypeScript가 가장 좁은 타입으로 추론한다.

// 3. const라도 타입을 명시하면 number 타입이 된다.
const numberC: number = 5678

console.log(numberC) // 5678

// 4. 숫자 리터럴 타입을 직접 명시할 수 있다.
let numberD: 26.218 = 26.218

console.log(numberD) // 26.218

// numberD = 10
// Error: Type '10' is not assignable to type '26.218'.

// 5. number 타입은 산술 연산을 할 수 있다.
let width = 20
let height = 10
let area = width * height

console.log(area) // 200
console.log(width + height) // 30
console.log(width - height) // 10
console.log(width / height) // 2
console.log(width % 3) // 2

// 6. 긴 숫자는 숫자 구분자(numeric separator)를 사용하면 읽기 쉽다.
let oneMillion = 1_000_000

console.log(oneMillion) // 1000000

// 숫자 구분자는 타입에도 사용할 수 있다.
let twoMillion: 2_000_000 = 2_000_000

console.log(twoMillion) // 2000000

// twoMillion = 2_000_001
// Error: Type '2000001' is not assignable to type '2000000'.

// 7. 숫자 리터럴 타입은 number에 할당 가능하다.
let exactCount: 3 = 3
let generalCount: number = exactCount

console.log(generalCount) // 3

// 하지만 number는 특정 숫자 리터럴 타입에 바로 할당할 수 없다.
let maybeThree: number = Math.round(Math.random() * 5)

// let onlyThree: 3 = maybeThree
// Error: Type 'number' is not assignable to type '3'.

if (maybeThree === 3) {
  let onlyThree: 3 = maybeThree
  console.log(onlyThree) // 3
}
