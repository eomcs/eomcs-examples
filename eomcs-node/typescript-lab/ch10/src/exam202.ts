/**
 * 타입 안전한 동적 임포트
 *
 * import()에 임의의 문자열 표현식을 넘기면 타입 안전성을 잃기 쉽다.
 * 문자열 리터럴 기반 로더 맵과 typeof를 사용하면 동적으로 로딩하면서도 타입을 유지할 수 있다.
 */

import type * as LocaleUSModule from './locales/locale-us'

type UserLocale = 'us' | 'ko'

async function getUserLocale(): Promise<UserLocale> {
  return 'ko'
}

let localeLoaders = {
  us: () => import('./locales/locale-us.js'),
  ko: () => import('./locales/locale-ko.js'),
} satisfies Record<UserLocale, () => Promise<typeof LocaleUSModule>>

async function main() {
  let userLocale = await getUserLocale()
  let localeModule: typeof LocaleUSModule = await localeLoaders[userLocale]()

  console.info(localeModule.locale.code)
  console.info(localeModule.locale.greeting)
  console.info(localeModule.formatName('길동', '홍'))
}

void main()

// 타입 안전성이 약한 방식:
// let userLocale = await getUserLocale()
// let path = `./locales/locale-${userLocale}`
// let localeModule = await import(path)
//
// path가 일반 string으로 계산되면 TypeScript는 어떤 모듈이 로딩될지 정확히 알기 어렵다.
// 위 예제처럼 허용 가능한 locale 키와 로더 함수를 매핑하면,
// 누락된 locale이나 잘못된 모듈 형태를 컴파일 타임에 확인할 수 있다.
