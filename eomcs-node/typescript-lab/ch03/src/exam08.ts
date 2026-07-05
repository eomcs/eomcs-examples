/**
 * symbol
 *
 * symbol은 ES2015에서 추가된 원시 타입이다.
 * 같은 설명(description)을 가진 symbol을 만들어도 두 값은 서로 다르다.
 * 주로 객체나 맵에서 문자열 키와 충돌하지 않는 고유 키로 사용한다.
 */

// 1. Symbol()로 symbol 값을 만든다.
let symbolA = Symbol('a')
let symbolB: symbol = Symbol('b')

console.log(symbolA) // Symbol(a)
console.log(symbolB) // Symbol(b)

// 2. 각 symbol은 고유하다.
let symbolC = Symbol('same')
let symbolD = Symbol('same')

// 같은 타입이라서 비교 연산은 할 수 있다.
console.log(symbolC === symbolD) // false

// 3. symbol은 문자열처럼 더하기 연산을 할 수 없다.
// let symbolText = symbolA + 'x'
// Error: The '+' operator cannot be applied to type 'symbol'.

console.log(symbolA.toString()) // Symbol(a)

// 4. const로 선언한 Symbol() 값은 unique symbol 타입으로 추론된다.
const symbolE = Symbol('e')
const symbolF: unique symbol = Symbol('f')

console.log(symbolE) // Symbol(e)
console.log(symbolF) // Symbol(f)

// console.log(symbolE === symbolF) 
// Error: unique symbol은 서로 타입이 다르기 때문에 비교 자체가 불가능

// unique symbol은 반드시 const 변수나 readonly static 프로퍼티에서만 사용할 수 있다.
// let symbolG: unique symbol = Symbol('g')
// Error: A variable whose type is a 'unique symbol' type must be 'const'.

// 5. 서로 다른 unique symbol은 절대 같은 값이 될 수 없다.
// let symbolComparison = symbolE === symbolF
// Error: This comparison appears to be unintentional because the types
// 'typeof symbolE' and 'typeof symbolF' have no overlap.

// 6. symbol은 객체의 고유한 프로퍼티 키로 사용할 수 있다.
const userId = Symbol('userId')

let symbolUser = {
  name: 'Ada',
  [userId]: 1001
}

console.log(symbolUser.name) // Ada
console.log(symbolUser[userId]) // 1001

// 문자열 키와 symbol 키는 서로 충돌하지 않는다.
let stringKey = 'userId'

let symbolRecord = {
  [stringKey]: '문자열 키의 값',
  [userId]: '심볼 키의 값'
}

console.log(symbolRecord.userId) // 문자열 키의 값
console.log(symbolRecord[userId]) // 심볼 키의 값

// 7. symbol 키는 일반적인 Object.keys() 결과에 나타나지 않는다.
console.log(Object.keys(symbolRecord)) // ['userId']
console.log(Object.getOwnPropertySymbols(symbolRecord)) // [Symbol(userId)]
