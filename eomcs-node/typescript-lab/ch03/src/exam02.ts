/**
 * any
 *
 * any는 모든 값의 집합이다.
 * 어떤 값이든 넣을 수 있고, 어떤 연산이든 시도할 수 있다.
 * 그 대신 타입 체커가 실수를 막아 주지 못한다.
 */

let a: any = 666
let b: any = ['danger']
let c = a + b

console.log(c) // "666danger"

// any는 다른 타입의 변수에도 자유롭게 할당할 수 있다.
// 아래 코드는 컴파일 오류가 나지 않지만, 실제 값은 number가 아니다.
let count: number = b

console.log(count) // ['danger']

// any 값에는 존재하지 않을 수도 있는 프로퍼티와 메서드도 사용할 수 있다.
// TypeScript는 통과시키지만, 실행 중 오류가 날 수 있다.
try {
  console.log(a.toUpperCase())
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// any가 섞이면 결과 타입도 any처럼 퍼져 나간다.
let result = a + b

// result.toFixed()
// result.trim()
//
// 위 두 호출은 모두 컴파일된다.
// 하지만 result의 실제 값은 문자열 "666danger"이므로 toFixed()는 런타임 오류가 난다.
// 안전하게 실행해 보고 싶다면 아래처럼 try/catch를 사용한다.
try {
  result.toFixed()
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

console.log(result.trim()) // "666danger"

// strict 모드에서는 암묵적 any를 오류로 처리한다.
// 따라서 any가 꼭 필요하다면 아래처럼 명시적으로 선언해야 한다.

// function log(value) {
//   console.log(value)
// }
// Error: Parameter 'value' implicitly has an 'any' type.

function log(value: any) {
  console.log(value)
}

log('명시적으로 any를 사용한 예')
log({ type: 'unknown data', payload: [1, 2, 3] })
