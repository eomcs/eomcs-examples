/**
 * null 반환 (Returning null)
 *
 * 오류가 발생했을 때 null을 반환하는 가장 단순한 오류 처리 방식이다.
 * 반환 타입에 null이 포함되므로 소비자는 값을 사용하기 전에 null 체크를 해야 한다.
 */

export {}

function ask(input: string | null): string | null {
  return input
}

function isValid(date: Date) {
  return (
    Object.prototype.toString.call(date) === '[object Date]' &&
    !Number.isNaN(date.getTime())
  )
}

function parse(birthday: string | null): Date | null {
  if (birthday === null) {
    return null
  }

  let date = new Date(birthday)

  if (!isValid(date)) {
    return null
  }

  return date
}

function printBirthday(input: string | null) {
  let date = parse(ask(input))

  if (date) {
    console.info('Date is', date.toISOString())
    return
  }

  console.error('Error parsing date for some reason')
}

printBirthday('1990/05/12')
printBirthday('not a date')
printBirthday(null)

// 1. null 체크를 하지 않으면 Date처럼 사용할 수 없다.
let parsedDate = parse('2000/01/01')

// parsedDate.toISOString()
// Error: 'parsedDate' is possibly 'null'.

if (parsedDate !== null) {
  console.log(parsedDate.toISOString())
}

// 2. null 반환은 가볍지만 실패 이유를 구분하기 어렵다.
let invalidFormat = parse('hello')
let emptyInput = parse(null)

console.log(invalidFormat) // null
console.log(emptyInput) // null

// 두 경우 모두 null이라서 호출자는 "왜 실패했는지" 알 수 없다.

// 3. 여러 단계를 체이닝하면 null 체크가 반복된다.
function getYear(input: string | null): number | null {
  let date = parse(input)

  if (date === null) {
    return null
  }

  return date.getFullYear()
}

let validYear = getYear('2026/06/30')
let invalidYear = getYear('invalid')

if (validYear !== null) {
  console.log(`year: ${validYear}`)
}

if (invalidYear !== null) {
  console.log(`year: ${invalidYear}`)
} else {
  console.log('year is unavailable')
}

