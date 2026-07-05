/**
 * 이터레이터 (Iterators)
 *
 * Iterable은 Symbol.iterator 메서드를 가진 객체다.
 * Iterator는 next() 메서드를 가지고, { value, done } 형태의 결과를 반환하는 객체다.
 */

// 1. Iterator 직접 만들기
function createRangeIterator(start: number, end: number): Iterator<number> {
  let current = start

  return {
    next() {
      if (current <= end) {
        let value = current
        current += 1

        return { value, done: false }
      }

      return { value: undefined, done: true }
    },
  }
}

let rangeIterator = createRangeIterator(1, 3)

console.log(rangeIterator.next()) // { value: 1, done: false }
console.log(rangeIterator.next()) // { value: 2, done: false }
console.log(rangeIterator.next()) // { value: 3, done: false }
console.log(rangeIterator.next()) // { value: undefined, done: true }

// createRangeIterator('1', 3)
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 2. Iterable 만들기
// Symbol.iterator 메서드가 Iterator를 반환하면 for...of, 스프레드, 구조 분해에 사용할 수 있다.
let oneToFive = {
  *[Symbol.iterator](): IterableIterator<number> {
    for (let n = 1; n <= 5; n++) {
      yield n
    }
  },
}

for (let rangeNumber of oneToFive) {
  console.log(rangeNumber) // 1, 2, 3, 4, 5
}

let allRangeNumbers = [...oneToFive]

console.log(allRangeNumbers) // [1, 2, 3, 4, 5]

let [firstRangeNumber, secondRangeNumber, ...remainingRangeNumbers] = oneToFive

console.log(firstRangeNumber) // 1
console.log(secondRangeNumber) // 2
console.log(remainingRangeNumbers) // [3, 4, 5]

// 3. Iterable과 Iterator의 차이
// rangeIterator는 next()는 있지만 Symbol.iterator가 없으므로 for...of로 순회할 수 없다.
//
// for (let n of rangeIterator) {
//   console.log(n)
// }
// Error: Type 'Iterator<number, any, any>' must have a '[Symbol.iterator]()' method that returns an iterator.

// 4. 직접 만든 Iterator를 Iterable하게 만들기
function createIterableRange(start: number, end: number): IterableIterator<number> {
  let current = start

  return {
    next() {
      if (current <= end) {
        let value = current
        current += 1

        return { value, done: false }
      }

      return { value: undefined, done: true }
    },
    [Symbol.iterator]() {
      return this
    },
  }
}

let iterableRange = createIterableRange(6, 8)

console.log(iterableRange.next()) // { value: 6, done: false }

for (let rangeValue of iterableRange) {
  console.log(rangeValue) // 7, 8
}

// 5. 문자열과 배열도 Iterable이다.
let languageName = 'TypeScript'
let languageLetters = [...languageName]

console.log(languageLetters) // ['T', 'y', 'p', 'e', 'S', 'c', 'r', 'i', 'p', 't']

let scoresForIterator = [10, 20, 30]
let scoreIterator = scoresForIterator[Symbol.iterator]()

console.log(scoreIterator.next()) // { value: 10, done: false }
console.log(scoreIterator.next()) // { value: 20, done: false }
console.log(scoreIterator.next()) // { value: 30, done: false }
console.log(scoreIterator.next()) // { value: undefined, done: true }
