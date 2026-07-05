/**
 * 컴패니언 객체 패턴 (Companion Object Pattern)
 *
 * TypeScript에서 타입과 값은 별도의 네임스페이스에 존재한다.
 * 그래서 같은 이름으로 타입 별칭과 값 객체를 함께 선언할 수 있다.
 */

export {}

type Currency = {
  unit: 'EUR' | 'GBP' | 'JPY' | 'USD'
  value: number
}

let Currency: {
  DEFAULT: Currency['unit']
  from(value: number, unit?: Currency['unit']): Currency
  format(currency: Currency): string
  convert(currency: Currency, unit: Currency['unit'], rate: number): Currency
} = {
  DEFAULT: 'USD' as const,

  from(value: number, unit: Currency['unit'] = 'USD'): Currency {
    return {
      unit,
      value,
    }
  },

  format(currency: Currency): string {
    return `${currency.value.toLocaleString('en-US')} ${currency.unit}`
  },

  convert(currency: Currency, unit: Currency['unit'], rate: number): Currency {
    return {
      unit,
      value: currency.value * rate,
    }
  },
}

// 1. Currency는 타입 위치에서 타입으로 사용된다.
let amountDue: Currency = {
  unit: 'JPY',
  value: 83733.1,
}

// 2. Currency는 값 위치에서 객체로 사용된다.
let defaultAmount = Currency.from(330)
let euroAmount = Currency.from(330, 'EUR')
let dollarAmount = Currency.convert(amountDue, 'USD', 0.0068)

console.log(Currency.format(amountDue))
console.log(Currency.format(defaultAmount))
console.log(Currency.format(euroAmount))
console.log(Currency.format(dollarAmount))

// 3. 타입과 객체가 같은 이름을 공유해도 서로 다른 네임스페이스라 충돌하지 않는다.
function pay(amount: Currency) {
  console.log(`Pay ${Currency.format(amount)}`)
}

pay(Currency.from(120, 'GBP'))

// 4. 객체의 유틸리티 메서드는 타입 규칙을 그대로 따른다.
// Currency.from(100, 'KRW')
// Error: Argument of type '"KRW"' is not assignable to parameter of type '"EUR" | "GBP" | "JPY" | "USD"'.

// let invalidAmount: Currency = {
//   unit: 'KRW',
//   value: 1000,
// }
// Error: Type '"KRW"' is not assignable to type '"EUR" | "GBP" | "JPY" | "USD"'.

// 5. 컴패니언 객체는 타입과 의미적으로 가까운 기본값, 생성 함수, 변환 함수를 묶을 때 유용하다.
type UserId = {
  value: string
}

let UserId = {
  from(value: string): UserId {
    return { value }
  },

  equals(left: UserId, right: UserId): boolean {
    return left.value === right.value
  },
}

let firstUserId: UserId = UserId.from('user-1')
let secondUserId = UserId.from('user-2')

console.log(firstUserId.value)
console.log(UserId.equals(firstUserId, secondUserId))
