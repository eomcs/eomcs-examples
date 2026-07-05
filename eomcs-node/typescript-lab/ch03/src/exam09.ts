/**
 * object
 *
 * TypeScript의 객체 타입은 구조적 타이핑(structural typing)을 따른다.
 * 객체의 이름이 아니라 객체가 가진 프로퍼티의 형태(shape)로 타입을 판단한다.
 */

// 1. object 타입은 "원시 값이 아닌 값" 정도만 표현한다.
let objectA: object = { b: 'x' }

console.log(objectA) // { b: 'x' }

// objectA.b
// Error: Property 'b' does not exist on type 'object'.
//
// object 타입은 이 값이 객체라는 사실만 알고,
// b라는 프로퍼티가 있는지는 알지 못한다.

// objectA = 10
// Error: Type 'number' is not assignable to type 'object'.

objectA = [1, 2, 3]
objectA = () => 'function is also object'

console.log(objectA)

// 2. 객체 리터럴을 사용하면 TypeScript가 구체적인 형태를 추론한다.
let objectB = {
  c: {
    d: 'f'
  }
}

console.log(objectB.c.d.toUpperCase()) // F

// objectB.c.d = 100
// Error: Type 'number' is not assignable to type 'string'.

// 3. 객체의 형태를 명시적으로 선언할 수 있다.
let objectPerson: {
  firstName: string
  lastName: string
} = {
  firstName: 'john',
  lastName: 'barrowman'
}

console.log(`${objectPerson.firstName} ${objectPerson.lastName}`)

// objectPerson = {
//   firstName: 'john'
// }
// Error: Property 'lastName' is missing.

// 4. 구조적 타이핑: 이름이 없어도 형태가 같으면 할당 가능하다.
function printObjectPerson(person: { firstName: string; lastName: string }) {
  console.log(`${person.firstName} ${person.lastName}`)
}

const objectActor = {
  firstName: 'Ada',
  lastName: 'Lovelace',
  job: 'mathematician'
}

printObjectPerson(objectActor) // 필요한 형태를 만족하므로 OK

// printObjectPerson({
//   firstName: 'Grace'
// })
// Error: Property 'lastName' is missing.

// 5. 옵셔널 프로퍼티는 있어도 되고 없어도 된다.
let objectConfig: {
  port: number
  host?: string
} = {
  port: 3000
}

console.log(objectConfig.port) // 3000
console.log(objectConfig.host ?? 'localhost') // localhost

objectConfig = {
  port: 8080,
  host: '127.0.0.1'
}

console.log(objectConfig.host) // 127.0.0.1

// 6. 인덱스 시그니처는 여러 프로퍼티의 타입 규칙을 표현한다.
let objectFlags: {
  enabled: boolean
  [key: string]: boolean
} = {
  enabled: true,
  visible: false,
  archived: false
}

console.log(objectFlags.enabled) // true
console.log(objectFlags.visible) // false

objectFlags.selected = true

console.log(objectFlags.selected) // true

// objectFlags.label = 'active'
// Error: Type 'string' is not assignable to type 'boolean'.

// 7. number 인덱스 시그니처도 사용할 수 있다.
let objectNumberMap: {
  [key: number]: boolean
} = {
  1: true,
  2: false
}

console.log(objectNumberMap[1]) // true

// objectNumberMap[3] = 'yes'
// Error: Type 'string' is not assignable to type 'boolean'.

// 8. readonly 프로퍼티는 처음 할당한 뒤 바꿀 수 없다.
let objectUser: {
  readonly firstName: string
  lastName: string
} = {
  firstName: 'abby',
  lastName: 'smith'
}

objectUser.lastName = 'stone'

console.log(`${objectUser.firstName} ${objectUser.lastName}`)

// objectUser.firstName = 'abbey'
// Error: Cannot assign to 'firstName' because it is a read-only property.

// 9. 빈 객체 리터럴 타입 {}는 대부분의 값을 허용하므로 피하는 편이 좋다.
let objectEmpty: {} = { name: 'wide type' }

objectEmpty = 123
objectEmpty = 'hello'

console.log(objectEmpty) // hello

// objectEmpty = null
// objectEmpty = undefined
// Error: Type 'null' or 'undefined' is not assignable to type '{}'.
