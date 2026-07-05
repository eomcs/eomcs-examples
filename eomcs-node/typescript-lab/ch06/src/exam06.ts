/**
 * 초과 프로퍼티 검사 (Excess Property Checking)
 *
 * fresh 객체 리터럴에는 타입에 없는 프로퍼티가 들어 있는지 검사한다.
 * 변수에 먼저 담거나 타입 단언을 사용하면 fresh 객체가 아니므로 이 검사가 적용되지 않는다.
 */

export {}

type Options = {
  baseURL: string
  cacheSize?: number
  tier?: 'prod' | 'dev'
}

class API {
  constructor(private options: Options) {}

  connect() {
    let tier = this.options.tier ?? 'dev'
    let cacheSize = this.options.cacheSize ?? 0

    console.log(`[${tier}] ${this.options.baseURL} cache=${cacheSize}`)
  }
}

// 1. 객체 리터럴에 유효한 프로퍼티만 있으면 OK다.
let productionAPI = new API({
  baseURL: 'https://api.mysite.com',
  tier: 'prod',
})

productionAPI.connect()

// 2. 객체 리터럴을 바로 넘기면 초과 프로퍼티 검사가 적용된다.
// new API({
//   baseURL: 'https://api.mysite.com',
//   tierr: 'prod',
// })
// Error: Object literal may only specify known properties, but 'tierr' does not exist in type 'Options'.
//
// tierr는 tier의 오타일 가능성이 높으므로 TypeScript가 fresh 객체 리터럴 단계에서 잡아준다.

// 3. 타입 단언을 사용하면 fresh 객체가 아니므로 초과 프로퍼티 검사가 적용되지 않는다.
let assertedAPI = new API({
  baseURL: 'https://api.mysite.com',
  badTier: 'prod',
} as Options)

assertedAPI.connect()

// badTier는 런타임 객체에는 남아 있지만 Options 타입에는 없는 프로퍼티다.
let assertedOptions = {
  baseURL: 'https://api.mysite.com',
  badTier: 'prod',
} as Options

console.log(assertedOptions)

// 4. 변수에 먼저 할당하면 fresh 객체가 아니므로 초과 프로퍼티 검사가 적용되지 않는다.
let badOptions = {
  baseURL: 'https://api.mysite.com',
  badTier: 'prod',
}

let variableAPI = new API(badOptions)

variableAPI.connect()
console.log(badOptions)

// 5. 변수 선언에 명시적 타입 어노테이션을 붙이면 그 위치에서 초과 프로퍼티 검사가 적용된다.
// let options: Options = {
//   baseURL: 'https://api.mysite.com',
//   badTier: 'prod',
// }
// Error: Object literal may only specify known properties, but 'badTier' does not exist in type 'Options'.

// 6. 초과 프로퍼티 검사는 오타를 잡기 위한 별도 검사이고, 구조적 타입 규칙 자체를 바꾸지는 않는다.
type LooseOptions = {
  baseURL: string
  badTier: string
}

let looseOptions: LooseOptions = {
  baseURL: 'https://api.mysite.com',
  badTier: 'prod',
}

let structuralAPI = new API(looseOptions) // OK: 필요한 baseURL 프로퍼티가 있다.

structuralAPI.connect()

