/**
 * 콜백 기반 API를 Promise로 래핑
 *
 * Node 스타일 콜백 API는 (error, result) 형태로 성공과 실패를 전달한다.
 * Promise로 감싸면 resolve/reject를 사용해 then/catch 체이닝으로 다룰 수 있다.
 */

import { appendFile, readFile, writeFileSync } from 'node:fs'
import { tmpdir } from 'node:os'
import { join } from 'node:path'

const logPath = join(tmpdir(), 'typescript-lab-ch08-promise.log')

writeFileSync(logPath, 'Initial access log entry\n', 'utf8')

function readFilePromise(path: string): Promise<string> {
  return new Promise((resolve, reject) => {
    readFile(path, { encoding: 'utf8' }, (error, result) => {
      if (error) {
        reject(error)
        return
      }

      resolve(result)
    })
  })
}

function appendFilePromise(path: string, data: string): Promise<void> {
  return new Promise((resolve, reject) => {
    appendFile(path, data, (error) => {
      if (error) {
        reject(error)
        return
      }

      resolve()
    })
  })
}

function appendAndReadPromise(path: string, data: string): Promise<string> {
  return appendFilePromise(path, data).then(() => readFilePromise(path))
}

appendAndReadPromise(logPath, 'New access log entry\n')
  .then((data) => {
    console.info('success reading!')
    console.info(data.trim())
  })
  .catch((error: unknown) => {
    if (error instanceof Error) {
      console.error('error reading or writing!', error.message)
      return
    }

    console.error('unknown error!', error)
  })

// Promise로 래핑하면 appendFile이 끝난 뒤 readFile을 실행하는 순서를 then으로 표현할 수 있다.
// 콜백을 중첩하는 방식보다 흐름이 왼쪽에서 오른쪽으로 읽힌다.

