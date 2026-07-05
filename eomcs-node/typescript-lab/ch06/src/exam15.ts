/**
 * 튜플 타입 추론 개선
 *
 * TypeScript는 배열 리터럴을 기본적으로 배열 타입으로 추론한다.
 * 위치별 타입이 중요한 값은 제네릭 rest parameter 헬퍼로 튜플 타입을 보존할 수 있다.
 */

export {}

// 1. 배열 리터럴은 보수적으로 배열 타입으로 추론된다.
let array = [1, true] // (number | boolean)[]

array.push(2)
array.push(false)

console.log(array)

let first = array[0] // number | boolean
let second = array[1] // number | boolean

console.log(first, second)

// let numberOnly: number = first
// Error: Type 'number | boolean' is not assignable to type 'number'.
//
// array[0]의 실제 값은 1이지만, 타입은 배열의 모든 요소 타입을 합친 number | boolean이다.

// 2. 명시적 튜플 타입을 쓰면 위치별 타입을 보존할 수 있다.
let explicitTuple: [number, boolean] = [1, true]

let explicitFirst = explicitTuple[0] // number
let explicitSecond = explicitTuple[1] // boolean

console.log(explicitFirst.toFixed(1))
console.log(explicitSecond.valueOf())

// explicitTuple = [1, true, 3]
// Error: Source has 3 element(s) but target allows only 2.

// explicitTuple = [true, 1]
// Error: Type 'boolean' is not assignable to type 'number'.

// 3. tuple 헬퍼를 사용하면 타입 어노테이션 없이 튜플로 추론할 수 있다.
function tuple<T extends unknown[]>(...values: T): T {
  return values
}

let inferredTuple = tuple(1, true) // [number, boolean]

let inferredFirst = inferredTuple[0] // number
let inferredSecond = inferredTuple[1] // boolean

console.log(inferredFirst.toFixed(1))
console.log(inferredSecond.valueOf())

// inferredTuple[0] = false
// Error: Type 'boolean' is not assignable to type 'number'.

// inferredTuple[1] = 2
// Error: Type 'number' is not assignable to type 'boolean'.

// 4. 함수 인자 목록처럼 순서가 의미 있는 값에 튜플 추론이 유용하다.
function call<Args extends unknown[], Result>(
  fn: (...args: Args) => Result,
  args: Args
): Result {
  return fn(...args)
}

function formatUser(id: number, name: string, active: boolean) {
  return `${id}: ${name} (${active ? 'active' : 'inactive'})`
}

let formatUserArgs = tuple(1, 'Ada', true) // [number, string, boolean]
let formattedUser = call(formatUser, formatUserArgs)

console.log(formattedUser)

// let wrongArgs = tuple('Ada', 1, true)
// call(formatUser, wrongArgs)
// Error: Type 'string' is not assignable to type 'number'.

// 5. as const도 튜플을 만들지만 readonly 리터럴 튜플로 추론된다.
let readonlyTuple = [1, true] as const // readonly [1, true]

console.log(readonlyTuple[0], readonlyTuple[1])

// readonlyTuple[0] = 2
// Error: Cannot assign to '0' because it is a read-only property.

