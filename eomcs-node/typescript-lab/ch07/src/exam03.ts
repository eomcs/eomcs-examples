/**
 * 예외 반환 (Returning Exceptions)
 *
 * 예외를 던지지 않고 반환 타입의 유니온에 포함시키는 방식이다.
 * 소비자는 Date를 사용하기 전에 Error 케이스를 처리해야 한다.
 */

export {}

class InvalidDateFormatError extends RangeError {}
class DateIsInTheFutureError extends RangeError {}
class EmptyInputError extends RangeError {}

function ask(input: string): string {
  return input
}

function isValid(date: Date) {
  return (
    Object.prototype.toString.call(date) === '[object Date]' &&
    !Number.isNaN(date.getTime())
  )
}

function parse(
  birthday: string
): Date | InvalidDateFormatError | DateIsInTheFutureError {
  let date = new Date(birthday)

  if (!isValid(date)) {
    return new InvalidDateFormatError('Enter a date in the form YYYY/MM/DD')
  }

  if (date.getTime() > Date.now()) {
    return new DateIsInTheFutureError('Are you a timelord?')
  }

  return date
}

let result = parse(ask('1990/05/12'))

if (result instanceof InvalidDateFormatError) {
  console.error(result.message)
} else if (result instanceof DateIsInTheFutureError) {
  console.info(result.message)
} else {
  console.info('Date is', result.toISOString())
}

// 1. 가능한 오류 유형을 개별적으로 처리할 수 있다.
function printBirthday(input: string) {
  let result = parse(ask(input))

  if (result instanceof InvalidDateFormatError) {
    console.error(result.message)
    return
  }

  if (result instanceof DateIsInTheFutureError) {
    console.info(result.message)
    return
  }

  console.info('Date is', result.toISOString())
}

printBirthday('not a date')
printBirthday('2999/01/01')

// 2. 개별 오류 처리가 필요 없다면 Error로 한 번에 묶어 처리할 수 있다.
function printBirthdayWithError(input: string) {
  let result = parse(ask(input))

  if (result instanceof Error) {
    console.error(result.message)
    return
  }

  console.info('Date is', result.toISOString())
}

printBirthdayWithError('2000/01/01')
printBirthdayWithError('invalid')

// 3. 반환 타입이 오류 처리를 강제한다.
let parsedDate = parse('2026/06/30')

// parsedDate.toISOString()
// Error: Property 'toISOString' does not exist on type
// 'Date | InvalidDateFormatError | DateIsInTheFutureError'.

if (!(parsedDate instanceof Error)) {
  console.log(parsedDate.toISOString())
}

// 4. 단점: 연산을 체이닝할수록 오류 목록이 길어진다.
function parseRequired(
  input: string
): Date | EmptyInputError | InvalidDateFormatError | DateIsInTheFutureError {
  if (input.trim() === '') {
    return new EmptyInputError('Birthday is required')
  }

  return parse(input)
}

class UnsupportedBirthYearError extends RangeError {}

function getBirthYear(
  input: string
):
  | number
  | EmptyInputError
  | InvalidDateFormatError
  | DateIsInTheFutureError
  | UnsupportedBirthYearError {
  let date = parseRequired(input)

  if (date instanceof Error) {
    return date
  }

  let year = date.getFullYear()

  if (year < 1900) {
    return new UnsupportedBirthYearError('Birth year must be 1900 or later')
  }

  return year
}

function printBirthYear(input: string) {
  let result = getBirthYear(input)

  if (result instanceof Error) {
    console.error(result.message)
    return
  }

  console.log(`year: ${result}`)
}

printBirthYear('')
printBirthYear('1888/01/01')
printBirthYear('2026/06/30')

