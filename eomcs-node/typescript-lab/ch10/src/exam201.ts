/**
 * 동적 임포트 (Dynamic Imports)
 *
 * import를 구문으로 쓰면 정적 임포트이고,
 * 함수처럼 import(...)로 호출하면 Promise를 반환하는 동적 임포트다.
 * 동적 임포트는 필요한 시점에 모듈을 지연 로딩할 때 사용한다.
 */

export {}

async function main() {
  console.info('before dynamic import')

  let localeModule = await import('./locales/locale-us.js')

  console.info('after dynamic import')
  console.info(localeModule.locale.greeting)
  console.info(localeModule.formatName('Ada', 'Lovelace'))
}

void main()

// 정적 임포트:
// import { locale } from './locales/locale-us'
//
// 동적 임포트:
// let localeModule = await import('./locales/locale-us.js')
//
// 동적 임포트는 Promise를 반환하므로 await 또는 then으로 결과를 꺼내야 한다.
