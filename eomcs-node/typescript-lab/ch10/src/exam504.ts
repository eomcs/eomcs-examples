/**
 * 네임스페이스: 별칭 (Alias)
 *
 * import 이름 = 긴.네임스페이스.경로 형태로 긴 네임스페이스 경로를 짧게 줄일 수 있다.
 * ES2015 import { d }와는 다른 TypeScript namespace alias 문법이다.
 */

export {}

namespace A {
  export namespace B {
    export namespace C {
      export let d = 3

      export function triple(value: number) {
        return value * 3
      }
    }
  }
}

import d = A.B.C.d
import triple = A.B.C.triple

let e = d * 3

console.info('e:', e)
console.info('triple(d):', triple(d))

// import d = A.B.C.d
// let e = d * 3
//
// 이 문법은 구조 분해가 아니라 namespace 멤버에 대한 별칭이다.

