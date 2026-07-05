/**
 * import / export: 재내보내기 (Re-export)
 *
 * 여러 모듈의 export를 한 모듈에서 다시 내보내면,
 * 사용하는 쪽은 하나의 진입점에서 필요한 값을 가져올 수 있다.
 */

import { bar, foo, meow, result } from './modules/f'

let total = foo() + bar() + result + meow(3)

console.info('re-export total:', total)

// modules/f.ts
// export * from './a'
// export { result } from './b'
// export { default as meow } from './c'

