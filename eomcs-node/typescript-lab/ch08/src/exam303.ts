/**
 * Promise 상태 머신
 *
 * Promise는 pending 상태에서 시작해 resolved 또는 rejected 중 하나로 끝난다.
 * then은 성공 값을 다음 Promise로 매핑하고, catch는 실패를 처리하거나 복구한다.
 * finally는 성공/실패 여부와 관계없이 항상 실행된다.
 */

export {}

function getMessage(shouldFail: boolean): Promise<string> {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (shouldFail) {
        reject(new Error('message unavailable'))
        return
      }

      resolve('TypeScript')
    }, 0)
  })
}

function countLetters(message: string): Promise<number> {
  return new Promise((resolve) => {
    setTimeout(() => resolve(message.length), 0)
  })
}

getMessage(false)
  .then(countLetters)
  .then((result) => console.info('Done', result))
  .catch((error: unknown) => {
    if (error instanceof Error) {
      console.error('Error', error.message)
      return
    }

    console.error('Unknown error', error)
  })
  .finally(() => console.info('Finished success path'))

getMessage(true)
  .then(countLetters)
  .then((result) => console.info('Done', result))
  .catch((error: unknown) => {
    if (error instanceof Error) {
      console.error('Error', error.message)
      return 0
    }

    console.error('Unknown error', error)
    return 0
  })
  .then((recovered) => console.info('Recovered value', recovered))
  .finally(() => console.info('Finished failure path'))

// Promise는 항상 reject될 수 있지만 TypeScript는 그 가능성을 함수 시그니처에 인코딩하지 않는다.
// 그래서 catch의 error는 unknown으로 받고, 사용 전에 Error인지 확인하는 편이 안전하다.

