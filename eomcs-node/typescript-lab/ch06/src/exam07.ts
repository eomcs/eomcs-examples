/**
 * 타입 정제 (Refinement)
 *
 * TypeScript는 제어 흐름을 분석해서 유니온 타입을 더 구체적인 타입으로 좁힌다.
 * if, typeof, == null, truthy 검사 등을 사용하면 각 분기 안에서 타입이 정제된다.
 */

export {}

type Unit = 'cm' | 'px' | '%'

let units: Unit[] = ['cm', 'px', '%']

function parseUnit(value: string): Unit | null {
  for (let i = 0; i < units.length; i++) {
    if (value.endsWith(units[i])) {
      return units[i]
    }
  }

  return null
}

type Width = {
  unit: Unit
  value: number
}

function parseWidth(width: number | string | null | undefined): Width | null {
  if (width == null) {
    // width는 여기서 null | undefined로 정제된다.
    return null
  }

  if (typeof width === 'number') {
    // width는 여기서 number로 정제된다.
    return {
      unit: 'px',
      value: width,
    }
  }

  // null, undefined, number가 제거되었으므로 width는 여기서 string이다.
  let unit = parseUnit(width)

  if (unit) {
    // unit은 여기서 Unit으로 정제된다.
    return {
      unit,
      value: Number.parseFloat(width), // 문자열 앞쪽부터 숫자로 해석할 수 있는 부분문 읽는다.
    }
  }

  return null
}

console.log(parseWidth(null)) // null
console.log(parseWidth(undefined)) // null
console.log(parseWidth(120)) // { unit: 'px', value: 120 }
console.log(parseWidth('45%')) // { unit: '%', value: 45 }
console.log(parseWidth('10rem')) // null

// 1. typeof는 원시 타입 유니온을 정제한다.
function formatValue(value: string | number | boolean) {
  if (typeof value === 'string') {
    return value.toUpperCase()
  }

  if (typeof value === 'number') {
    return value.toFixed(2)
  }

  return value ? 'yes' : 'no'
}

console.log(formatValue('typescript'))
console.log(formatValue(3.14159))
console.log(formatValue(false))

// 2. in 연산자는 객체 유니온에서 프로퍼티 존재 여부로 타입을 정제한다.
type Success = {
  data: string
}

type Failure = {
  error: Error
}

function printResult(result: Success | Failure) {
  if ('data' in result) {
    console.log(result.data.toUpperCase())
    return
  }

  console.log(result.error.message)
}

printResult({ data: 'loaded' })
printResult({ error: new Error('network unavailable') })

// 3. instanceof는 클래스 인스턴스 유니온을 정제한다.
function printDateOrError(value: Date | Error) {
  if (value instanceof Date) {
    console.log(value.toISOString())
    return
  }

  console.log(value.message)
}

printDateOrError(new Date('2026-06-30T00:00:00.000Z'))
printDateOrError(new Error('invalid date'))

