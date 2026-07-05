/**
 * import / export: 와일드카드 가져오기
 *
 * `import * as name`은 모듈의 named export 전체를 하나의 네임스페이스 객체로 가져온다.
 */

import * as a from './modules/a'

let fooResult = a.foo()
let barResult = a.bar()

console.info('namespace result:', fooResult + barResult)

// a.foo()
// a.bar()
//
// default export는 이 방식에서 default 프로퍼티로 접근할 수 있다.

