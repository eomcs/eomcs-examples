/**
 * 프로토타입 안전하게 확장하기 (Safely Extending the Prototype)
 *
 * TypeScript에서는 인터페이스 병합으로 내장 타입에 새 메서드 타입을 알려주고,
 * 런타임에서는 실제 prototype에 메서드를 구현할 수 있다.
 */

export {}

function tuple<T extends unknown[]>(...values: T): T {
  return values
}

// 1. TypeScript에 Array.prototype.zip의 타입 정보를 알린다.
declare global {
  interface Array<T> {
    zip<U>(list: U[]): [T, U][]
  }
}

// 2. 런타임에 Array.prototype.zip을 구현한다.
Array.prototype.zip = function <T, U>(this: T[], list: U[]): [T, U][] {
  return this.map((value, index) => tuple(value, list[index]))
}

let zippedNumbersAndLetters = [1, 2, 3]
  .map((number) => number * 2)
  .zip(['a', 'b', 'c'])

console.log(zippedNumbersAndLetters)

// zippedNumbersAndLetters는 [number, string][]로 추론된다.
let firstPair = zippedNumbersAndLetters[0]
let firstNumber = firstPair[0]
let firstLetter = firstPair[1]

console.log(firstNumber.toFixed(1))
console.log(firstLetter.toUpperCase())

// let invalidNumber: string = firstPair[0]
// Error: Type 'number' is not assignable to type 'string'.

// let invalidLetter: number = firstPair[1]
// Error: Type 'string' is not assignable to type 'number'.

// 3. 서로 다른 요소 타입도 튜플 쌍으로 보존된다.
let zippedNamesAndActive = ['Ada', 'Grace'].zip([true, false])

console.log(zippedNamesAndActive)

let nameAndActive = zippedNamesAndActive[0]

console.log(nameAndActive[0].toUpperCase())
console.log(nameAndActive[1].valueOf())

// 4. 프로토타입 확장은 전역 효과가 있으므로 명시적으로 가져와 사용하는 편이 안전하다.
// README의 권장 흐름:
// - zip.ts에 interface Array<T> 병합과 Array.prototype.zip 구현을 둔다.
// - tsconfig.json에서 자동 포함되지 않게 제외한다.
// - 필요한 파일에서만 import './zip'으로 명시적으로 활성화한다.

// 5. 타입 선언과 런타임 구현은 함께 있어야 한다.
// interface만 선언하고 prototype에 구현하지 않으면 타입 검사는 통과해도 런타임에서 실패한다.

