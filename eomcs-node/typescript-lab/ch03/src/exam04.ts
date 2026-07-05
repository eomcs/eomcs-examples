/**
 * boolean
 *
 * boolean 타입은 true와 false 두 가지 값만 가진다.
 * TypeScript는 선언 방식에 따라 boolean 또는 더 좁은 리터럴 타입으로 추론한다.
 */

// 1. let, var로 선언하면 boolean으로 추론한다.
let a = true
var b = false

a = false
b = true

console.log(a) // false
console.log(b) // true

// 2. const로 선언하면 값이 바뀔 수 없으므로 true 리터럴 타입으로 추론한다.
const c = true

console.log(c) // true

// c = false
// Error: Cannot assign to 'c' because it is a constant.
//
// c의 타입은 boolean보다 좁은 true다.

// 3. 타입을 직접 명시할 수 있다.
let d: boolean = true

d = false

console.log(d) // false

// 4. 리터럴 타입을 직접 명시할 수도 있다.
let e: true = true

console.log(e) // true

// e = false
// Error: Type 'false' is not assignable to type 'true'.

// 5. false 리터럴 타입도 가능하다.
let isDisabled: false = false

console.log(isDisabled) // false

// isDisabled = true
// Error: Type 'true' is not assignable to type 'false'.

// 6. boolean 값으로 할 수 있는 대표적인 연산
let isSignedIn = true
let hasPermission = false

console.log(isSignedIn && hasPermission) // false
console.log(isSignedIn || hasPermission) // true
console.log(!isSignedIn) // false

// 7. true 리터럴 타입은 boolean에 할당 가능하다.
let literalTrue: true = true
let booleanValue: boolean = literalTrue

console.log(booleanValue) // true

// 하지만 boolean은 true 리터럴 타입에 바로 할당할 수 없다.
let maybeTrue: boolean = Math.random() > 0.5

// let onlyTrue: true = maybeTrue
// Error: Type 'boolean' is not assignable to type 'true'.

if (maybeTrue === true) {
  let onlyTrue: true = maybeTrue
  console.log(onlyTrue) // true
}
