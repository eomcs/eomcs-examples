/**
 * 타입 넓히기 (Type Widening)
 *
 * TypeScript는 타입을 추론할 때 가능한 일반적인 타입으로 넓혀서 추론한다.
 * const 선언과 명시적 타입 어노테이션, as const는 넓히기를 막을 수 있다.
 */

export {}

// 1. let과 var로 선언하면 값이 나중에 바뀔 수 있으므로 넓은 타입으로 추론한다.
// 즉 리터럴 값이 속한 기본 타입으로 넓혀진다.
let wideningString = 'x' // string
let wideningNumber = 3 // number
var wideningBoolean = true // boolean

wideningString = 'y'
wideningNumber = 4
wideningBoolean = false

console.log(wideningString, wideningNumber, wideningBoolean)

function acceptOnlyX(value: 'x') {
  console.log(`literal x: ${value}`)
}

function acceptOnlyThree(value: 3) {
  console.log(`literal 3: ${value}`)
}

// acceptOnlyX(wideningString)
// Error: Argument of type 'string' is not assignable to parameter of type '"x"'.

// acceptOnlyThree(wideningNumber)
// Error: Argument of type 'number' is not assignable to parameter of type '3'.

// 2. const로 선언한 원시 값은 리터럴 타입으로 추론한다.
const literalString = 'x' // 'x'
const literalNumber = 3 // 3
const literalBoolean = true // true

acceptOnlyX(literalString)
acceptOnlyThree(literalNumber)

console.log(literalBoolean)

// 3. const 객체의 프로퍼티는 기본적으로 넓혀진다.
const point = {
  x: 3,
}

point.x = 4 // OK: point.x는 number로 추론된다.

console.log(point)

// let exactPoint: { x: 3 } = point
// Error: Type '{ x: number; }' is not assignable to type '{ x: 3; }'.

// 4. 명시적 타입 어노테이션을 사용하면 let에서도 넓히기를 막을 수 있다.
let exactString: 'x' = 'x'
let exactNumber: 3 = 3

acceptOnlyX(exactString)
acceptOnlyThree(exactNumber)

// exactString = 'y'
// Error: Type '"y"' is not assignable to type '"x"'.

// exactNumber = 4
// Error: Type '4' is not assignable to type '3'.

// 5. null/undefined로 초기화하면 이후 할당을 따라 타입이 진화한다.
let nullValue = null
let undefinedValue = undefined

console.log(nullValue, undefinedValue)

nullValue = 3
console.log(nullValue.toFixed(1))

nullValue = 'b'
console.log(nullValue.toUpperCase())

undefinedValue = true
console.log(undefinedValue.valueOf())

undefinedValue = 'text'
console.log(undefinedValue.toUpperCase())

function getValue() {
  let value = null

  value = 3
  value = 'b'

  return value
}

let inferredString = getValue()

console.log(inferredString.toUpperCase())

// 명시적 타입 어노테이션을 붙이면 null/undefined 자체로 고정할 수 있다.
let exactNull: null = null
let exactUndefined: undefined = undefined

console.log(exactNull, exactUndefined)

// exactNull = 3
// Error: Type '3' is not assignable to type 'null'.

// exactUndefined = 'text'
// Error: Type '"text"' is not assignable to type 'undefined'.

// 6. as const를 사용하면 타입 넓히기를 막고 재귀적으로 readonly 처리한다.
let mutablePoint = { x: 3 } // { x: number }
let exactMutablePoint: { x: 3 } = { x: 3 }
let readonlyPoint = { x: 3 } as const // { readonly x: 3 }

mutablePoint.x = 10
exactMutablePoint.x = 3

console.log(mutablePoint, exactMutablePoint, readonlyPoint)

// exactMutablePoint.x = 4
// Error: Type '4' is not assignable to type '3'.

// readonlyPoint.x = 4
// Error: Cannot assign to 'x' because it is a read-only property.

let mutableList = [1, { x: 2 }] // (number | { x: number })[]
let readonlyTuple = [1, { x: 2 }] as const // readonly [1, { readonly x: 2 }]

mutableList.push(3)

console.log(mutableList)
console.log(readonlyTuple[0], readonlyTuple[1].x)

// readonlyTuple.push(3)
// Error: Property 'push' does not exist on type 'readonly [1, { readonly x: 2; }]'.

// readonlyTuple[1].x = 3
// Error: Cannot assign to 'x' because it is a read-only property.
