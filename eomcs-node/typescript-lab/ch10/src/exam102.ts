/**
 * import / export: 기본 내보내기 (default export)
 *
 * default export는 한 모듈의 대표 값을 내보낼 때 사용한다.
 * 가져올 때는 중괄호를 쓰지 않고 원하는 이름으로 받을 수 있다.
 */

import meow from './modules/c'

let loudness = meow(11)

console.info('returned loudness:', loudness)

import catSound from './modules/c'
catSound(11)
//
// default import는 가져오는 쪽에서 이름을 자유롭게 정할 수 있다.

