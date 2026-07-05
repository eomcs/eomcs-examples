/**
 * import / export: 기본 내보내기/가져오기
 *
 * named export는 중괄호로 가져온다.
 * 한 모듈에서 여러 값을 내보내고, 필요한 이름만 골라 가져올 수 있다.
 */

import { bar, foo } from './modules/a'

let fooResult = foo()
let barResult = bar()

let result = fooResult + barResult

console.info('result:', result)

// import { foo, bar } from './modules/a'
// foo()
// export let result = bar()

