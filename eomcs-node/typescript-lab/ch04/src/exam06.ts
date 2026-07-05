/**
 * 제너레이터 함수 (Generator Functions)
 *
 * 제너레이터 함수는 값을 한 번에 모두 만들지 않고, next()가 호출될 때마다
 * yield로 값을 하나씩 내보낸다. 이런 방식을 지연 생성(lazy)이라고 한다.
 */

// 1. 제너레이터 함수 선언하기
// function* 문법으로 선언하고, yield로 값을 내보낸다.
function* createNumberGenerator(): IterableIterator<number> {
  yield 1
  yield 2
  yield 3
}

let numberGenerator = createNumberGenerator()

console.log(numberGenerator.next()) // { value: 1, done: false }
console.log(numberGenerator.next()) // { value: 2, done: false }
console.log(numberGenerator.next()) // { value: 3, done: false }
console.log(numberGenerator.next()) // { value: undefined, done: true }

// 2. TypeScript는 yield하는 값의 타입을 검사한다.
function* createStringGenerator(): IterableIterator<string> {
  yield 'TypeScript'
  yield 'Generator'

  // yield 100
  // Error: Type 'number' is not assignable to type 'string'.
}

let stringGenerator = createStringGenerator()

console.log(stringGenerator.next()) // { value: 'TypeScript', done: false }
console.log(stringGenerator.next()) // { value: 'Generator', done: false }

// 3. 무한 제너레이터
// while (true)를 사용해도 yield가 한 번씩 멈추므로 필요한 만큼만 값을 꺼낼 수 있다.
function* createFibonacciSequence(): IterableIterator<number> {
  let a = 0
  let b = 1

  while (true) {
    yield a
    ;[a, b] = [b, a + b]
    // 배열 리터럴로 시작하는 문장이 앞의 yield와 이어져 해석되지 않도록 세미콜론을 붙인다.
  }
}

let fibonacciSequence = createFibonacciSequence()

console.log(fibonacciSequence.next()) // { value: 0, done: false }
console.log(fibonacciSequence.next()) // { value: 1, done: false }
console.log(fibonacciSequence.next()) // { value: 1, done: false }
console.log(fibonacciSequence.next()) // { value: 2, done: false }
console.log(fibonacciSequence.next()) // { value: 3, done: false }

// 4. 제너레이터는 iterable이므로 for...of로 순회할 수 있다.
function* createCountdown(from: number): IterableIterator<number> {
  for (let n = from; n >= 0; n--) {
    yield n
  }
}

for (let count of createCountdown(3)) {
  console.log(count) // 3, 2, 1, 0
}

// createCountdown('3')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 5. return은 마지막 next() 결과의 value가 되며, 순회 값에는 포함되지 않는다.
function* createDoneMessage(): Generator<string, string, unknown> {
  yield 'working'
  yield 'almost done'

  return 'done'
}

let doneMessage = createDoneMessage()

console.log(doneMessage.next()) // { value: 'working', done: false }
console.log(doneMessage.next()) // { value: 'almost done', done: false }
console.log(doneMessage.next()) // { value: 'done', done: true }

for (let messageFromGenerator of createDoneMessage()) {
  console.log(messageFromGenerator) // working, almost done
}
