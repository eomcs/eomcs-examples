/**
 * import / export: 타입과 값 동시 내보내기
 *
 * TypeScript에서 타입과 값은 별도의 네임스페이스에 존재한다.
 * 그래서 같은 이름 X를 값과 타입으로 동시에 내보낼 수 있다.
 */

import { X } from './modules/g'

let value = X + 1
let typedValue: X = {
  y: 'z',
}

console.info('value X + 1:', value)
console.info('type X value:', typedValue)

// let a = X + 1          // X는 값(number)
// let b: X = { y: 'z' }  // X는 타입

