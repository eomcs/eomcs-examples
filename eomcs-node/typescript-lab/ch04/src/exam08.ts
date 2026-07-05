/**
 * 객체 메서드와 Generator 함수 이름
 *
 * 객체 리터럴에서는 일반 함수, 단축 메서드, 제너레이터 함수,
 * 계산된 속성 이름을 모두 메서드로 정의할 수 있다.
 */

let computedMethodName = 'helloFromComputedName'
let computedGeneratorName = 'numbersFromComputedName'

let methodExamples = {
  // 1. 일반 함수 프로퍼티
  hello1: function() {
    return 'hello1'
  },

  // 2. 단축 메서드 문법
  hello2() {
    return 'hello2'
  },

  // 3. 제너레이터 함수 프로퍼티
  hello3: function*(): IterableIterator<string> {
    yield 'hello3'
  },

  // 4. 제너레이터 단축 메서드 문법
  *hello4(): IterableIterator<string> {
    yield 'hello4'
  },

  // 5. 계산된 속성 이름
  [computedMethodName]: function() {
    return 'computed method'
  },

  [computedGeneratorName]: function*(): IterableIterator<number> {
    yield 1
    yield 2
    yield 3
  },

  // 6. 빌트인 심볼 메서드
  // Symbol.iterator가 계산되어 이 객체를 Iterable로 만든다.
  *[Symbol.iterator](): IterableIterator<string> {
    yield 'first'
    yield 'second'
  },
}

console.log(methodExamples.hello1()) // hello1
console.log(methodExamples.hello2()) // hello2
console.log([...methodExamples.hello3()]) // ['hello3']
console.log([...methodExamples.hello4()]) // ['hello4']

console.log(methodExamples[computedMethodName]()) // computed method
console.log(methodExamples.helloFromComputedName()) // computed method

console.log([...methodExamples[computedGeneratorName]()]) // [1, 2, 3]
console.log([...methodExamples.numbersFromComputedName()]) // [1, 2, 3]

// Symbol.iterator 메서드가 있으므로 객체 자체를 순회할 수 있다.
for (let methodValue of methodExamples) {
  console.log(methodValue) // first, second
}

console.log([...methodExamples]) // ['first', 'second']

// methodExamples.helloFromComputedName()
// Error: Element implicitly has an 'any' type because expression of type '"helloFromComputedName"' can't be used to index type ...
//
// computedMethodName은 let으로 선언되어 string 타입으로 추론된다.
// 그래서 TypeScript는 정확한 프로퍼티 이름을 보장할 수 없다.

// 7. 계산된 이름을 리터럴 타입으로 보존하기
const fixedComputedMethodName = 'sumFromComputedName'

let fixedComputedMethodExamples = {
  [fixedComputedMethodName](a: number, b: number) {
    return a + b
  },
}

console.log(fixedComputedMethodExamples.sumFromComputedName(10, 20)) // 30

// fixedComputedMethodExamples.sumFromComputedName(10, '20')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 8. Generator 함수 이름 확인하기
let generatorNameExamples = {
  *hello(): IterableIterator<string> {
    yield 'hello'
  },

  *sum(): IterableIterator<number> {
    yield 10
    yield 20
  },

  *next(): IterableIterator<string> {
    yield 'next'
  },

  *[Symbol.iterator](): IterableIterator<string> {
    yield 'iterator'
  },
}

console.log(generatorNameExamples.hello.name) // hello
console.log(generatorNameExamples.sum.name) // sum
console.log(generatorNameExamples.next.name) // next
console.log(generatorNameExamples[Symbol.iterator].name) // [Symbol.iterator]

console.log([...generatorNameExamples.hello()]) // ['hello']
console.log([...generatorNameExamples.sum()]) // [10, 20]
console.log([...generatorNameExamples.next()]) // ['next']
console.log([...generatorNameExamples]) // ['iterator']

// 9. 객체의 문자열 이름과 심볼 이름은 따로 조회된다.
console.log(Object.getOwnPropertyNames(generatorNameExamples))
// ['hello', 'sum', 'next']

console.log(Object.getOwnPropertySymbols(generatorNameExamples))
// [Symbol(Symbol.iterator)]
