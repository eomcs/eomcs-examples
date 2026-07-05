/**
 * bigint
 *
 * bigint 타입은 number로 안전하게 표현하기 어려운 큰 정수를 다룰 때 사용한다.
 * bigint 리터럴은 정수 뒤에 n을 붙여서 만든다.
 */

// 1. let으로 선언하면 bigint로 추론한다.
let bigintA = 1234n

bigintA = 5678n

console.log(bigintA) // 5678n

// bigintA = 5678
// Error: Type 'number' is not assignable to type 'bigint'.

// 2. const로 선언하면 bigint 리터럴 타입으로 추론한다.
const bigintB = 5678n

console.log(bigintB) // 5678n

// bigintB의 타입은 bigint가 아니라 5678n이다.
// const는 값을 바꿀 수 없으므로 TypeScript가 가장 좁은 타입으로 추론한다.

// 3. 타입을 직접 명시할 수 있다.
let bigintC: bigint = 5678n

bigintC = 9007199254740993n

console.log(bigintC) // 9007199254740993n

// 4. bigint 리터럴 타입을 직접 명시할 수 있다.
let bigintD: 88n = 88n

console.log(bigintD) // 88n

// bigintD = 89n
// Error: Type '89n' is not assignable to type '88n'.

// 5. bigint 리터럴은 반드시 정수여야 한다.
// let bigintE = 88.5n
// Error: A bigint literal must be an integer.

// 6. bigint와 number는 서로 다른 타입이다.
// let bigintF: bigint = 100
// Error: Type 'number' is not assignable to type 'bigint'.

// let numberFromBigint: number = 100n
// Error: Type 'bigint' is not assignable to type 'number'.

// 7. bigint도 정수 산술 연산을 할 수 있다.
let accountBalance = 9_007_199_254_740_993n
let deposit = 1_000n
let withdrawal = 500n

console.log(accountBalance + deposit) // 9007199254741993n
console.log(accountBalance - withdrawal) // 9007199254740493n
console.log(deposit * 3n) // 3000n
console.log(deposit / 3n) // 333n
console.log(deposit % 3n) // 1n

// 8. bigint와 number는 산술 연산에서 섞어 쓸 수 없다.
// console.log(accountBalance + 1000)
// Error: Operator '+' cannot be applied to types 'bigint' and 'number'.

console.log(accountBalance + BigInt(1000)) // 9007199254741993n

// 9. bigint 리터럴 타입은 bigint에 할당 가능하다.
let exactBigint: 10n = 10n
let generalBigint: bigint = exactBigint

console.log(generalBigint) // 10n

// 하지만 bigint는 특정 bigint 리터럴 타입에 바로 할당할 수 없다.
let maybeTen: bigint = BigInt(Math.round(Math.random() * 20))

// let onlyTen: 10n = maybeTen
// Error: Type 'bigint' is not assignable to type '10n'.

if (maybeTen === 10n) {
  let onlyTen: 10n = maybeTen
  console.log(onlyTen) // 10n
}
