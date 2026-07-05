/**
 * 옵셔널 파라미터와 기본값 (Optional and Default Parameters)
 *
 * 옵셔널 파라미터는 값을 전달하지 않아도 되는 매개변수다.
 * 기본값 파라미터는 인수를 생략했을 때 사용할 값을 함수 선언에서 정한다.
 */

// 1. 옵셔널 파라미터
// userId?: string은 string 또는 undefined로 취급된다.
function logMessage(message: string, userId?: string) {
  let time = new Date().toLocaleTimeString()
  let signedInUser = userId || 'Not signed in'

  console.log(time, message, signedInUser)
}

logMessage('Page loaded')
logMessage('User signed in', 'da763be')

// logMessage()
// Error: Expected 1-2 arguments, but got 0.

// logMessage('User signed in', 123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 필수 파라미터는 앞에, 옵셔널 파라미터는 뒤에 와야 한다.
// 아래처럼 옵셔널 파라미터 뒤에 필수 파라미터를 둘 수 없다.
//
// function sendEmail(subject?: string, body: string) {
//   console.log(subject, body)
// }
// Error: A required parameter cannot follow an optional parameter.

// 2. 기본값 파라미터
// 기본값을 지정하면 TypeScript가 기본값에서 타입을 추론한다.
function logWithDefaultUser(message: string, userId = 'Not signed in') {
  let time = new Date().toISOString()

  console.log(time, message, userId)
}

logWithDefaultUser('User signed out')
logWithDefaultUser('User signed in', 'da763be')

// logWithDefaultUser('User signed in', 123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 3. 기본값 파라미터는 파라미터 목록의 어디에나 올 수 있다.
function createGreeting(greeting = 'Hello', name: string) {
  return `${greeting}, ${name}!`
}

console.log(createGreeting('Hi', 'Ada')) // Hi, Ada!

// 앞쪽 기본값 파라미터를 생략하려면 undefined를 전달한다.
console.log(createGreeting(undefined, 'Grace')) // Hello, Grace!

// createGreeting('Ada')
// Error: Expected 2 arguments, but got 1.

// 4. 객체 기본값 파라미터
type LogContext = {
  appId?: string
  userId?: string
}

function logWithContext(message: string, context: LogContext = {}) {
  let appId = context.appId || 'unknown-app'
  let userId = context.userId || 'anonymous'

  console.log(new Date().toISOString(), appId, userId, message)
}

logWithContext('Application started')
logWithContext('Button clicked', { appId: 'admin', userId: 'u-123' })

// logWithContext('Button clicked', { userId: 123 })
// Error: Type 'number' is not assignable to type 'string'.
