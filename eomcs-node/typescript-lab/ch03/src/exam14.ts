/**
 * 읽기 전용 배열과 튜플 (Read-only Arrays and Tuples)
 *
 * readonly 배열과 튜플은 요소를 읽을 수는 있지만,
 * 인덱스로 값을 바꾸거나 push 같은 변경 메서드를 사용할 수 없다.
 */

// 1. readonly number[]는 읽기 전용 숫자 배열이다.
let readonlyNumbers: readonly number[] = [1, 2, 3]

console.log(readonlyNumbers[0]) // 1
console.log(readonlyNumbers.length) // 3

// readonlyNumbers[0] = 10
// Error: Index signature in type 'readonly number[]' only permits reading.
//
// readonlyNumbers.push(4)
// Error: Property 'push' does not exist on type 'readonly number[]'.

// 2. concat은 기존 배열을 바꾸지 않고 새 배열을 반환한다.
let readonlyMoreNumbers = readonlyNumbers.concat(4)

console.log(readonlyNumbers) // [1, 2, 3]
console.log(readonlyMoreNumbers) // [1, 2, 3, 4]

// readonlyMoreNumbers[0] = 10
// Error: Index signature only permits reading.

// 3. 읽기 전용 배열은 읽기 작업과 순회는 가능하다.
readonlyNumbers.forEach((number) => {
  console.log(number.toFixed(2))
})

let readonlySum = readonlyNumbers.reduce((sum, number) => sum + number, 0)

console.log(readonlySum) // 6

// 4. readonly string[]와 ReadonlyArray<string>은 같은 의미다.
type ReadonlyStringArrayA = readonly string[]
type ReadonlyStringArrayB = ReadonlyArray<string>
type ReadonlyStringArrayC = Readonly<string[]>

let readonlyNamesA: ReadonlyStringArrayA = ['Ada', 'Grace']
let readonlyNamesB: ReadonlyStringArrayB = ['Linus', 'Ken']
let readonlyNamesC: ReadonlyStringArrayC = ['Barbara', 'Margaret']

console.log(readonlyNamesA.join(', ')) // Ada, Grace
console.log(readonlyNamesB.join(', ')) // Linus, Ken
console.log(readonlyNamesC.join(', ')) // Barbara, Margaret

// readonlyNamesA.push('Alan')
// Error: Property 'push' does not exist on type 'readonly string[]'.

// 5. 일반 배열은 읽기 전용 배열에 할당할 수 있다.
let mutableNumbers = [10, 20, 30]
let readonlyFromMutable: readonly number[] = mutableNumbers

console.log(readonlyFromMutable[1]) // 20

mutableNumbers.push(40)

console.log(readonlyFromMutable) // [10, 20, 30, 40]

// readonlyFromMutable.push(50)
// Error: Property 'push' does not exist on type 'readonly number[]'.

// 6. 읽기 전용 배열은 일반 배열에 할당할 수 없다.
// let mutableFromReadonly: number[] = readonlyNumbers
// Error: The type 'readonly number[]' is 'readonly' and cannot be assigned
// to the mutable type 'number[]'.

// 7. 읽기 전용 튜플도 만들 수 있다.
type ReadonlyTupleA = readonly [number, string]
type ReadonlyTupleB = Readonly<[number, string]>

let readonlyTupleA: ReadonlyTupleA = [1, 'one']
let readonlyTupleB: ReadonlyTupleB = [2, 'two']

console.log(readonlyTupleA[0]) // 1
console.log(readonlyTupleA[1].toUpperCase()) // ONE
console.log(readonlyTupleB[0]) // 2
console.log(readonlyTupleB[1].toUpperCase()) // TWO

// readonlyTupleA[0] = 10
// Error: Cannot assign to '0' because it is a read-only property.
//
// readonlyTupleA.push('extra')
// Error: Property 'push' does not exist on type 'ReadonlyTupleA'.

// 8. 함수가 배열을 바꾸면 안 될 때 readonly 매개변수를 사용한다.
function printReadonlyScores(scores: readonly number[]) {
  console.log(scores.join(', '))

  // scores.push(100)
  // Error: Property 'push' does not exist on type 'readonly number[]'.
}

printReadonlyScores([90, 85, 100])
printReadonlyScores(readonlyNumbers)
