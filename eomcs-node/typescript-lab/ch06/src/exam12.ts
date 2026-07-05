/**
 * Record 타입
 *
 * Record<K, V>는 키 타입 K와 값 타입 V를 갖는 객체 타입을 만든다.
 * K는 string, number, symbol의 서브타입이어야 하며,
 * 유니온 키를 사용하면 그 유니온의 모든 키를 반드시 작성해야 한다.
 */

export {}

type Weekday = 'Mon' | 'Tue' | 'Wed' | 'Thu' | 'Fri'
type Day = Weekday | 'Sat' | 'Sun'

// 1. Record<Weekday, Day>는 모든 Weekday 키를 요구한다.
let nextDay: Record<Weekday, Day> = {
  Mon: 'Tue',
  Tue: 'Wed',
  Wed: 'Thu',
  Thu: 'Fri',
  Fri: 'Sat',
}

console.log(nextDay.Mon)
console.log(nextDay.Fri)

// let incompleteNextDay: Record<Weekday, Day> = {
//   Mon: 'Tue',
// }
// Error: Type '{ Mon: "Tue"; }' is missing the following properties:
// Tue, Wed, Thu, Fri

// 2. 값 타입도 제한된다.
// let invalidNextDay: Record<Weekday, Day> = {
//   Mon: 'Tue',
//   Tue: 'Wed',
//   Wed: 'Thu',
//   Thu: 'Fri',
//   Fri: 'Holiday',
// }
// Error: Type '"Holiday"' is not assignable to type 'Day'.

// 3. Record는 키 타입을 string 리터럴 유니온으로 좁힐 수 있다.
type Feature = 'search' | 'share' | 'sync'

let featureFlags: Record<Feature, boolean> = {
  search: true,
  share: false,
  sync: true,
}

console.log(featureFlags.search)
console.log(featureFlags.share)

// featureFlags.export = true
// Error: Property 'export' does not exist on type 'Record<Feature, boolean>'.

// 4. 일반 인덱스 시그니처는 특정 string 리터럴 키 집합만 요구하기 어렵다.
type BooleanDictionary = {
  [key: string]: boolean
}

let looseFlags: BooleanDictionary = {
  search: true,
}

looseFlags.export = true

console.log(looseFlags)

// 5. number 리터럴 유니온도 Record 키로 사용할 수 있다.
type HttpStatus = 200 | 404 | 500

let statusMessages: Record<HttpStatus, string> = {
  200: 'OK',
  404: 'Not Found',
  500: 'Internal Server Error',
}

console.log(statusMessages[200])
console.log(statusMessages[404])

// let missingStatusMessages: Record<HttpStatus, string> = {
//   200: 'OK',
//   404: 'Not Found',
// }
// Error: Property '500' is missing.

// 6. 키 타입은 string, number, symbol의 서브타입이어야 한다.
// type InvalidRecord = Record<{ id: number }, string>
// Error: Type '{ id: number; }' does not satisfy the constraint 'string | number | symbol'.

