/**
 * 튜플 (Tuples)
 *
 * 튜플은 고정 길이 배열이다.
 * 각 인덱스마다 타입이 명확하게 정해진다.
 * 배열과 문법이 같기 때문에 튜플로 쓰려면 타입을 명시해야 한다.
 */

// 1. 길이가 1이고 첫 번째 요소가 number인 튜플
let tupleA: [number] = [1]

console.log(tupleA[0]) // 1

// tupleA = []
// Error: Source has 0 element(s) but target requires 1.
//
// tupleA = [1, 2]
// Error: Source has 2 element(s) but target allows only 1.
//
// tupleA = ['1']
// Error: Type 'string' is not assignable to type 'number'.

// 2. 각 위치마다 타입이 정해진 튜플
let tupleB: [string, string, number] = ['malcolm', 'gladwell', 1963]

console.log(tupleB[0].toUpperCase()) // MALCOLM
console.log(tupleB[1].toUpperCase()) // GLADWELL
console.log(tupleB[2].toFixed(0)) // 1963

// tupleB = ['malcolm', 1963, 'gladwell']
// Error: 각 위치의 타입이 맞지 않는다.

// 3. 타입을 명시하지 않으면 일반 배열로 추론된다.
let tupleLikeArray = ['malcolm', 'gladwell', 1963]

tupleLikeArray.push('writer')
tupleLikeArray.push(2024)

console.log(tupleLikeArray)

// tupleLikeArray의 타입은 (string | number)[] 이다.
// 길이나 각 인덱스의 의미가 고정되지 않는다.

// 4. 옵셔널 요소: 두 번째 요소는 있어도 되고 없어도 된다.
let trainFares: [number, number?][] = [
  [3.75],
  [8.25, 7.7]
]

trainFares.push([12.5])
trainFares.push([15, 13.5])

console.log(trainFares)

// trainFares.push([])
// Error: Source has 0 element(s) but target requires 1.
//
// trainFares.push([10, 20, 30])
// Error: Source has 3 element(s) but target allows only 2.

// 5. rest 요소: 최소 길이를 보장하면서 뒤에는 같은 타입을 여러 개 받을 수 있다.
let friends: [string, ...string[]] = ['Sara', 'Tali', 'Chloe']

friends.push('Rin')

console.log(friends) // ['Sara', 'Tali', 'Chloe', 'Rin']

// friends = []
// Error: Source has 0 element(s) but target requires 1.
//
// friends = [123]
// Error: Type 'number' is not assignable to type 'string'.

// 6. 앞부분은 고정하고 나머지는 rest 요소로 열어 둘 수 있다.
let tupleList: [number, boolean, ...string[]] = [1, false, 'a', 'b']

tupleList = [2, true]
tupleList = [3, false, 'x', 'y', 'z']

console.log(tupleList)

// tupleList = [1]
// Error: Source has 1 element(s) but target requires 2.
//
// tupleList = [1, 'false']
// Error: Type 'string' is not assignable to type 'boolean'.

// 7. 튜플은 함수 반환값의 위치별 의미를 표현할 때 유용하다.
function getTupleUser(): [string, number] {
  return ['Ada', 36]
}

let tupleUser = getTupleUser()

console.log(tupleUser[0].toUpperCase()) // ADA
console.log(tupleUser[1].toFixed(0)) // 36

// let tupleUserName: number = tupleUser[0]
// Error: Type 'string' is not assignable to type 'number'.
