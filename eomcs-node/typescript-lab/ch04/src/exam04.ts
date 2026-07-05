/**
 * call, apply, bind
 *
 * JavaScript 함수는 call, apply, bind 메서드로 호출 방식과 this 값을 지정할 수 있다.
 * strict 모드에서는 strictBindCallApply 옵션이 함께 켜져 타입 안전하게 검사된다.
 */

// 1. 일반 함수 호출
function addNumbers(a: number, b: number): number {
  return a + b
}

console.log(addNumbers(10, 20)) // 30

// 2. apply
// 첫 번째 인수는 this 값이고, 두 번째 인수는 함수에 전달할 인수 배열이다.
console.log(addNumbers.apply(null, [10, 20])) // 30

// addNumbers.apply(null, [10])
// Error: Argument of type '[number]' is not assignable to parameter of type '[a: number, b: number]'.

// addNumbers.apply(null, [10, '20'])
// Error: Type 'string' is not assignable to type 'number'.

// 3. call
// 첫 번째 인수는 this 값이고, 나머지 인수는 함수에 순서대로 전달된다.
console.log(addNumbers.call(null, 10, 20)) // 30

// addNumbers.call(null, 10)
// Error: Expected 3 arguments, but got 2.

// addNumbers.call(null, 10, '20')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 4. bind
// bind는 함수를 즉시 실행하지 않고, this와 일부 인수가 고정된 새 함수를 반환한다.
let addTen = addNumbers.bind(null, 10)

console.log(addTen(20)) // 30

let addTenAndTwenty = addNumbers.bind(null, 10, 20)

console.log(addTenAndTwenty()) // 30

// addTen('20')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// addTenAndTwenty(30)
// Error: Expected 0 arguments, but got 1.

// 5. this를 사용하는 함수에서 call, apply, bind 사용하기
type Counter = {
  value: number
}

function increaseBy(this: Counter, amount: number) {
  this.value += amount
  return this.value
}

let counterA: Counter = { value: 0 }
let counterB: Counter = { value: 100 }

console.log(increaseBy.call(counterA, 5)) // 5
console.log(increaseBy.apply(counterB, [10])) // 110

let increaseCounterABy = increaseBy.bind(counterA)

console.log(increaseCounterABy(3)) // 8

// increaseBy.call(null, 5)
// Error: Argument of type 'null' is not assignable to parameter of type 'Counter'.

// increaseBy.call(counterA, '5')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// increaseBy.apply(counterB, ['10'])
// Error: Type 'string' is not assignable to type 'number'.
