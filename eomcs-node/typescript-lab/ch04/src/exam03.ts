/**
 * Rest 파라미터 (Rest Parameters)
 *
 * rest 파라미터는 개수가 정해지지 않은 인수를 배열로 모아 받는다.
 * 가변 인수 함수를 타입 안전하게 작성할 때 사용한다.
 */

// 1. 배열로 받는 방식
// 함수는 인수를 하나만 받는다. 그 인수의 타입이 number[]이다.
function sumArray(numbers: number[]): number {
  return numbers.reduce((total, n) => total + n, 0)
}

console.log(sumArray([1, 2, 3])) // 6

// sumArray(1, 2, 3)
// Error: Expected 1 arguments, but got 3.

// sumArray([1, '2', 3])
// Error: Type 'string' is not assignable to type 'number'.

// 2. rest 파라미터로 받는 방식
// 호출하는 쪽에서는 값을 하나씩 전달하고, 함수 안에서는 number[]로 다룬다.
function sumVariadic(...numbers: number[]): number {
  return numbers.reduce((total, n) => total + n, 0)
}

console.log(sumVariadic()) // 0
console.log(sumVariadic(1, 2, 3)) // 6
console.log(sumVariadic(10, 20, 30, 40)) // 100

// sumVariadic([1, 2, 3])
// Error: Argument of type 'number[]' is not assignable to parameter of type 'number'.

// sumVariadic(1, '2', 3)
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 3. 일반 파라미터와 rest 파라미터를 함께 사용하기
function joinWithSeparator(separator: string, ...parts: string[]) {
  return parts.join(separator)
}

console.log(joinWithSeparator(', ', 'red', 'green', 'blue')) // red, green, blue
console.log(joinWithSeparator(' / ', 'home', 'users', 'ada')) // home / users / ada

// joinWithSeparator(', ', 'red', 100)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 4. rest 파라미터는 하나만 선언할 수 있으며, 반드시 마지막에 와야 한다.
//
// function invalidRest(...names: string[], lastName: string) {
//   return names.concat(lastName)
// }
// Error: A rest parameter must be last in a parameter list.

// function invalidRestCount(...names: string[], ...scores: number[]) {
//   return [names, scores]
// }
// Error: A rest parameter must be last in a parameter list.

// 5. arguments 객체 대신 rest 파라미터를 사용한다.
// arguments는 타입 정보가 부족해서 안전하게 다루기 어렵다.
function printAll(...values: string[]) {
  values.forEach(value => console.log(value.toUpperCase()))
}

printAll('type', 'safe', 'rest')

// printAll('type', 100)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 6. Console.log처럼 첫 번째 인수 뒤에 여러 값을 받을 수도 있다.
function logValues(message: string, ...optionalValues: unknown[]) {
  console.log(message, ...optionalValues)
}

logValues('User data:', { id: 'u-123' }, ['admin', 'editor'])
logValues('Retry count:', 3)
