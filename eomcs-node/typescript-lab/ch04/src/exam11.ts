/**
 * 제네릭 (Generics / Polymorphism)
 *
 * 제네릭은 구체적인 타입을 미리 정하지 않고, 호출 시점에 타입을 정하는 방법이다.
 * 타입 파라미터를 사용하면 같은 구현을 여러 타입에 안전하게 재사용할 수 있다.
 */

// 1. 호출 시점에 타입이 결정되는 filter
type GenericFilterFunction = {
  <T>(array: T[], f: (item: T) => boolean): T[]
}

let genericFilter: GenericFilterFunction = (array, f) => {
  let result = []

  for (let i = 0; i < array.length; i++) {
    if (f(array[i])) {
      result.push(array[i])
    }
  }

  return result
}

let filteredNumbers = genericFilter([1, 2, 3], n => n > 2)
let filteredStrings = genericFilter(['a', 'b', 'c'], letter => letter !== 'b')
let filteredUsers = genericFilter(
  [{ firstName: 'beth' }, { firstName: 'cait' }],
  user => user.firstName === 'beth'
)

console.log(filteredNumbers) // [3]
console.log(filteredStrings) // ['a', 'c']
console.log(filteredUsers) // [{ firstName: 'beth' }]

// genericFilter([1, 2, 3], n => n.toUpperCase())
// Error: Property 'toUpperCase' does not exist on type 'number'.

// genericFilter(['a', 'b'], letter => letter > 1)
// Error: Operator '>' cannot be applied to types 'string' and 'number'.

// T는 호출할 때마다 새로 바인딩된다.
// 위 세 호출에서 T는 각각 number, string, { firstName: string }으로 추론된다.

// 2. 제네릭 선언 위치: 시그니처에 선언하기
// <T>가 호출 시그니처에 있으므로, 호출할 때마다 T가 새로 결정된다.
type SignatureLevelFilter = {
  <T>(array: T[], f: (item: T) => boolean): T[]
}

let signatureLevelFilter: SignatureLevelFilter = genericFilter

console.log(signatureLevelFilter([10, 20, 30], n => n >= 20)) // [20, 30]
console.log(signatureLevelFilter(['ts', 'js'], word => word.length === 2)) // ['ts', 'js']

// 3. 제네릭 선언 위치: 타입 별칭에 선언하기
// T가 타입 별칭에 있으므로, 타입을 사용할 때 T를 먼저 정해야 한다.
type AliasLevelFilter<T> = {
  (array: T[], f: (item: T) => boolean): T[]
}

let numberOnlyFilter: AliasLevelFilter<number> = (array, f) => {
  let result = []

  for (let item of array) {
    if (f(item)) {
      result.push(item)
    }
  }

  return result
}

console.log(numberOnlyFilter([1, 2, 3, 4], n => n % 2 === 0)) // [2, 4]

// numberOnlyFilter(['a', 'b'], letter => letter === 'a')
// Error: Type 'string' is not assignable to type 'number'.

// 4. 제네릭 선언 위치: 함수 선언에 직접 선언하기
function filterValues<T>(array: T[], f: (item: T) => boolean): T[] {
  let result = []

  for (let item of array) {
    if (f(item)) {
      result.push(item)
    }
  }

  return result
}

console.log(filterValues([true, false, true], value => value)) // [true, true]

// 5. 여러 제네릭 파라미터: map 구현
// T는 입력 배열 요소 타입이고, U는 변환 후 배열 요소 타입이다.
function mapValues<T, U>(array: T[], f: (item: T) => U): U[] {
  let result = []

  for (let i = 0; i < array.length; i++) {
    result[i] = f(array[i])
  }

  return result
}

let stringLengths = mapValues(['a', 'bb', 'ccc'], word => word.length)
let numberLabels = mapValues([1, 2, 3], n => `#${n}`)
let userNames = mapValues(
  [{ name: 'Ada', age: 36 }, { name: 'Grace', age: 85 }],
  user => user.name
)

console.log(stringLengths) // [1, 2, 3]
console.log(numberLabels) // ['#1', '#2', '#3']
console.log(userNames) // ['Ada', 'Grace']

// mapValues([1, 2, 3], n => n.toUpperCase())
// Error: Property 'toUpperCase' does not exist on type 'number'.

// 6. 제네릭 타입 추론
// TypeScript가 T와 U를 자동으로 추론한다.
let isAList = mapValues(['a', 'b', 'c'], letter => letter === 'a')

console.log(isAList) // [true, false, false]

// 타입 인수를 직접 명시할 수도 있다. 명시하려면 필요한 타입 인수를 모두 적는다.
let explicitIsAList = mapValues<string, boolean>(
  ['a', 'b', 'c'],
  letter => letter === 'a'
)

console.log(explicitIsAList) // [true, false, false]

// mapValues<string, boolean>(['a', 'b', 'c'], letter => letter.length)
// Error: Type 'number' is not assignable to type 'boolean'.

// 7. 추론이 불충분한 경우에는 타입 인수를 명시한다.
let numericPromise = new Promise<number>(resolve => {
  resolve(45)
})

numericPromise.then(result => {
  console.log(result * 4) // 180
})

// let unknownPromise = new Promise(resolve => {
//   resolve(45)
// })
//
// unknownPromise.then(result => {
//   console.log(result * 4)
// })
// Error: 'result' is of type 'unknown'.
