/**
 * 네임스페이스: 컴파일 출력
 *
 * namespace는 JavaScript로 컴파일될 때 IIFE 형태로 변환된다.
 * export된 멤버만 namespace 객체에 할당되고, export하지 않은 값은 클로저 안에 머문다.
 */

export {}

namespace Flowers {
  let unit = 'flowers'

  export function give(count: number) {
    return `${count} ${unit}`
  }
}

console.info(Flowers.give(3))

// 위 TypeScript namespace는 대략 다음 JavaScript 형태로 컴파일된다.
//
// let Flowers
// ;(function (Flowers) {
//   let unit = 'flowers'
//   function give(count) {
//     return `${count} ${unit}`
//   }
//   Flowers.give = give
// })(Flowers || (Flowers = {}))
//
// 현재 파일은 export {} 때문에 모듈 모드다.
// 그래서 컴파일된 Flowers 변수는 이 파일 모듈 스코프에 머무르고 globalThis에 자동으로 붙지 않는다.

console.info('globalThis.Flowers:', (globalThis as any).Flowers)

