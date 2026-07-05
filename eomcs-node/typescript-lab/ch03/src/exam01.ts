/**
 * 타입을 말하는 방법 (Talking About Types)
 *
 * 매개변수 타입을 지정하지 않으면 TypeScript가 그 값의 타입을 알 수 없다.
 * strict 모드에서는 암묵적으로 any가 되는 매개변수를 오류로 처리한다.
 */

// 1. 매개변수 타입을 지정하지 않은 경우
// function squareOf(n) {
//   return n * n
// }
//
// Error: Parameter 'n' implicitly has an 'any' type.
//
// n의 타입을 모르기 때문에 TypeScript는 아래 호출도 막아 주지 못한다.
// console.log(squareOf(2))   // 4
// console.log(squareOf('z')) // NaN

// 2. 매개변수 타입을 지정한 경우
function squareOf(n: number) {
  return n * n
}

console.log(squareOf(2)) // 4

// console.log(squareOf('z'))
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.
//
// - n은 number 타입으로 제약된다(constrained).
// - 2는 number 타입에 할당 가능하다(assignable).
// - 'z'는 number 타입에 할당 가능하지 않다.
