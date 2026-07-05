/**
 * Promise 타입 설계
 *
 * Promise<T>는 "미래에 T 값을 만들거나, 실패할 수 있는 작업"을 표현한다.
 * 생성자는 resolve/reject 함수를 받는 executor를 실행하고,
 * then/catch는 성공 또는 실패 결과를 새로운 Promise로 이어 붙인다.
 */

export {}

type Executor<T> = (
  resolve: (result: T) => void,
  reject: (error: unknown) => void
) => void

class SimplePromise<T> {
  constructor(private executor: Executor<T>) {}

  then<U>(onResolved: (result: T) => U | Promise<U>): Promise<U> {
    return new Promise((resolve, reject) => {
      this.executor(
        (result) => {
          try {
            resolve(onResolved(result))
          } catch (error) {
            reject(error)
          }
        },
        reject
      )
    })
  }

  catch<U>(onRejected: (error: unknown) => U | Promise<U>): Promise<T | U> {
    return new Promise((resolve, reject) => {
      this.executor(resolve, (error) => {
        try {
          resolve(onRejected(error))
        } catch (nextError) {
          reject(nextError)
        }
      })
    })
  }
}

let successfulTask = new SimplePromise<string>((resolve) => {
  setTimeout(() => resolve('hello'), 0)
})

successfulTask
  .then((message) => message.toUpperCase())
  .then((message) => console.info('resolved:', message))

let failedTask = new SimplePromise<string>((_resolve, reject) => {
  setTimeout(() => reject(new Error('network unavailable')), 0)
})

failedTask
  .then((message) => message.toUpperCase())
  .catch((error) => {
    if (error instanceof Error) {
      return `recovered: ${error.message}`
    }

    return 'recovered from unknown error'
  })
  .then((message) => console.info(message))

// 이 예제의 SimplePromise는 Promise의 타입 구조를 보여주기 위한 작은 스케치다.
// 실제 코드에서는 JavaScript 내장 Promise를 사용한다.

