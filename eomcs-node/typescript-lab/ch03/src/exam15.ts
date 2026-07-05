/**
 * null, undefined, void, never
 *
 * null은 의도적으로 값이 없음을 나타낸다.
 * undefined는 아직 값이 할당되지 않았음을 나타낸다.
 * void는 명시적인 return 값이 없는 함수의 반환 타입이다.
 * never는 절대 정상적으로 반환하지 않는 함수의 반환 타입이다.
 */

// 1. null: 값의 부재를 의도적으로 표현한다.
function nullableFindSmallNumber(x: number): number | null {
  if (x < 10) return x
  return null
}

let nullableResult = nullableFindSmallNumber(12)

// console.log(nullableResult.toFixed(2))
// Error: 'nullableResult' is possibly 'null'.

if (nullableResult !== null) {
  console.log(nullableResult.toFixed(2))
} else {
  console.log('값이 없습니다.')
}

// strictNullChecks가 켜져 있으면 null을 number에 바로 할당할 수 없다.
// let nullableNumber: number = null
// Error: Type 'null' is not assignable to type 'number'.

let nullableNumberOrNull: number | null = null

nullableNumberOrNull = 5

console.log(nullableNumberOrNull) // 5

// 2. undefined: 아직 값이 할당되지 않았거나 명시적으로 undefined를 반환한다.
function nullableReturnUndefined(): undefined {
  return undefined
}

let nullableUndefinedValue = nullableReturnUndefined()

console.log(nullableUndefinedValue) // undefined

// let nullableText: string = undefined
// Error: Type 'undefined' is not assignable to type 'string'.

let nullableOptionalText: string | undefined

console.log(nullableOptionalText) // undefined

nullableOptionalText = 'hello'

console.log(nullableOptionalText.toUpperCase()) // HELLO

// 3. void: 명시적인 return 값이 없는 함수의 반환 타입이다.
function nullableLogMessage(message: string): void {
  console.log(message)
}

let nullableVoidResult = nullableLogMessage('void 함수는 값을 반환하지 않는다.')

console.log(nullableVoidResult) // undefined

// let nullableStringFromVoid: string = nullableLogMessage('hello')
// Error: Type 'void' is not assignable to type 'string'.

// 4. never: 절대 정상적으로 반환하지 않는 함수의 반환 타입이다.
function nullableAlwaysError(): never {
  throw TypeError('I always error')
}

function nullableForever(): never {
  while (true) {
    // 무한 루프
  }
}

// nullableAlwaysError()
// nullableForever()
//
// 위 함수들은 호출하면 이후 코드로 정상 복귀하지 않는다.

try {
  nullableAlwaysError()
} catch (error) {
  console.log((error as Error).message) // I always error
}

// 5. never는 모든 타입의 서브타입(bottom type)이다.
function nullableFail(message: string): never {
  throw new Error(message)
}

function nullableGetLength(value: string | null): number {
  if (value !== null) {
    return value.length
  }

  return nullableFail('문자열이 없습니다.')
}

try {
  console.log(nullableGetLength(null))
} catch (error) {
  console.log((error as Error).message) // 문자열이 없습니다.
}

console.log(nullableGetLength('TypeScript')) // 10

// 6. 유니온 타입을 모두 처리한 뒤 남는 값은 never가 된다.
type NullableStatus = 'loading' | 'success' | 'error'

function nullableAssertNever(value: never): never {
  throw new Error(`처리하지 않은 값: ${value}`)
}

function nullablePrintStatus(status: NullableStatus): void {
  switch (status) {
    case 'loading':
      console.log('로딩 중')
      return
    case 'success':
      console.log('성공')
      return
    case 'error':
      console.log('실패')
      return
    default:
      nullableAssertNever(status)
  }
}

nullablePrintStatus('loading')
nullablePrintStatus('success')
nullablePrintStatus('error')
