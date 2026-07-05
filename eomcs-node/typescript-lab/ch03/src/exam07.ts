/**
 * string
 *
 * string 타입은 모든 문자열의 집합이다.
 * TypeScript는 선언 방식에 따라 string 또는 더 좁은 문자열 리터럴 타입으로 추론한다.
 */

// 1. let으로 선언하면 string으로 추론한다.
let stringA = 'hello'

stringA = 'TypeScript'
stringA = stringA.toUpperCase()

console.log(stringA) // TYPESCRIPT

// stringA = 100
// Error: Type 'number' is not assignable to type 'string'.

// 2. const로 선언하면 문자열 리터럴 타입으로 추론한다.
const stringB = '!'

console.log(stringB) // !

// stringB의 타입은 string이 아니라 '!'이다.
// const는 값을 바꿀 수 없으므로 TypeScript가 가장 좁은 타입으로 추론한다.

// 3. 타입을 직접 명시할 수 있다.
let stringC: string = 'world'

stringC = 'TypeScript world'

console.log(stringC) // TypeScript world

// 4. 문자열 리터럴 타입을 직접 명시할 수 있다.
let stringD: 'john' = 'john'

console.log(stringD) // john

// stringD = 'zoe'
// Error: Type '"zoe"' is not assignable to type '"john"'.

// 5. string 타입은 문자열 연산과 메서드를 사용할 수 있다.
let firstName = 'Ada'
let lastName = 'Lovelace'
let fullName = firstName + ' ' + lastName

console.log(fullName) // Ada Lovelace
console.log(fullName.toUpperCase()) // ADA LOVELACE
console.log(fullName.toLowerCase()) // ada lovelace
console.log(fullName.includes('Love')) // true
console.log(fullName.concat('!')) // Ada Lovelace!

// 6. 템플릿 문자열도 string 타입이다.
let greeting = `Hello, ${fullName}`

console.log(greeting) // Hello, Ada Lovelace

// 7. 문자열 리터럴 타입은 string에 할당 가능하다.
let exactStatus: 'success' = 'success'
let generalStatus: string = exactStatus

console.log(generalStatus) // success

// 하지만 string은 특정 문자열 리터럴 타입에 바로 할당할 수 없다.
let maybeSuccess: string = Math.random() > 0.5 ? 'success' : 'fail'

// let onlySuccess: 'success' = maybeSuccess
// Error: Type 'string' is not assignable to type '"success"'.

if (maybeSuccess === 'success') {
  let onlySuccess: 'success' = maybeSuccess
  console.log(onlySuccess) // success
}

// 8. 가능하면 불필요한 타입 표기보다 추론을 활용한다.
let inferredMessage = 'TypeScript가 string으로 추론한다.'
let annotatedMessage: string = '명시적으로 string이라고 적을 수도 있다.'

console.log(inferredMessage)
console.log(annotatedMessage)
