/**
 * 예외 던지기 (Throwing Exceptions)
 *
 * 실패 이유를 예외 타입과 메시지로 전달하는 방식이다.
 * 성공 경로의 반환 타입은 Date로 단순해지지만, TypeScript는 던질 수 있는 예외를
 * 함수 시그니처에 인코딩하지 않는다.
 */

export {}

class InvalidDateFormatError extends RangeError {}
class DateIsInTheFutureError extends RangeError {}

function ask(input: string): string {
  return input
}

function isValid(date: Date) {
  return (
    Object.prototype.toString.call(date) === '[object Date]' &&
    !Number.isNaN(date.getTime())
  )
}

/**
 * @throws {InvalidDateFormatError} 날짜 형식이 잘못된 경우
 * @throws {DateIsInTheFutureError} 미래 날짜를 입력한 경우
 */
function parse(birthday: string): Date {
  let date = new Date(birthday)

  if (!isValid(date)) {
    throw new InvalidDateFormatError('Enter a date in the form YYYY/MM/DD')
  }

  if (date.getTime() > Date.now()) {
    throw new DateIsInTheFutureError('Are you a timelord?')
  }

  return date
}

function printBirthday(input: string) {
  try {
    let date = parse(ask(input))

    console.info('Date is', date.toISOString())
  } catch (error) {
    if (error instanceof InvalidDateFormatError) {
      console.error(error.message)
      return
    }

    if (error instanceof DateIsInTheFutureError) {
      console.info(error.message)
      return
    }

    throw error
  }
}

printBirthday('1990/05/12')
printBirthday('not a date')
printBirthday('2999/01/01')

// 1. 성공 경로에서는 null 체크 없이 Date처럼 사용할 수 있다.
try {
  let date = parse('2000/01/01')

  console.log(date.toISOString())
} catch (error) {
  console.error('Unexpected parse error:', (error as Error).message)
}

// 2. 여러 연산을 하나의 try/catch로 감쌀 수 있어 체이닝이 쉽다.
function getBirthYear(input: string) {
  let date = parse(input)

  return date.getFullYear()
}

try {
  let year = getBirthYear('2026/06/30')

  console.log(`year: ${year}`)
} catch (error) {
  console.error((error as Error).message)
}

// 3. 단점: TypeScript는 예외를 반환 타입에 표시하지 않는다.
// 아래 코드는 타입 오류 없이 컴파일되지만, 런타임에서는 예외가 발생할 수 있다.
try {
  let date = parse('invalid')

  console.log(date.toISOString())
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

