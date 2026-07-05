/**
 * unknown
 *
 * unknown은 any처럼 어떤 값이든 담을 수 있다.
 * 하지만 값을 사용하려면 먼저 타입을 좁혀야 한다.
 */

let a: unknown = 30

// unknown 값은 비교할 수 있다.
let b = a === 123

console.log(b) // false

// 하지만 타입을 좁히지 않고 바로 연산할 수는 없다.
// let c = a + 10
// Error: 'a' is of type 'unknown'.

if (typeof a === 'number') {
  let c = a + 10
  console.log(c) // 40
}

// unknown에는 어떤 값이든 넣을 수 있다.
a = 'hello'
a = true
a = { name: 'TypeScript' }
a = [1, 2, 3]

// 하지만 사용할 때마다 현재 타입을 확인해야 한다.
if (typeof a === 'string') {
  console.log(a.toUpperCase())
} else if (Array.isArray(a)) {
  console.log(a.length)
} else {
  console.log('아직 사용할 수 없는 타입:', a)
}

// any와 unknown의 차이
let anyValue: any = 'danger'
let unknownValue: unknown = 'safe'

console.log(anyValue.toUpperCase()) // 컴파일 OK
// console.log(unknownValue.toUpperCase())
// Error: 'unknownValue' is of type 'unknown'.

if (typeof unknownValue === 'string') {
  console.log(unknownValue.toUpperCase()) // 타입을 좁힌 후에는 OK
}

// unknown은 다른 타입 변수에 바로 할당할 수 없다.
// let message: string = unknownValue
// Error: Type 'unknown' is not assignable to type 'string'.

if (typeof unknownValue === 'string') {
  let message: string = unknownValue
  console.log(message)
}

// TypeScript는 unknown을 스스로 추론하지 않는다.
// unknown이 필요하다면 직접 명시한다.
let parsedJson: unknown = JSON.parse('{"name":"Boris","age":42}')

if (
  typeof parsedJson === 'object' &&
  parsedJson !== null &&
  'name' in parsedJson
) {
  console.log(parsedJson.name)
}
