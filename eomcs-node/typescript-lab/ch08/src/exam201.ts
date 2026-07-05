/**
 * 콜백 (Working with Callbacks)
 *
 * NodeJS 콜백 관례는 보통 (error | null, result | null) => void 형태다.
 * 비동기 작업은 호출 즉시 끝나지 않으므로, 콜백 안에서 error를 먼저 확인한 뒤 결과를 사용한다.
 */

import * as fs from 'node:fs'
import { tmpdir } from 'node:os'
import { join } from 'node:path'

const logPath = join(tmpdir(), 'typescript-lab-ch08-callback.log')

fs.writeFileSync(logPath, 'Initial access log entry\n', 'utf8')

// 1. readFile은 파일 읽기를 예약하고 즉시 반환한다.
fs.readFile(logPath, { encoding: 'utf8' }, (error, data) => {
  if (error) {
    console.error('error reading!', error.message)
    return
  }

  console.info('success reading!', data.trim())
})

// 2. appendFile도 쓰기 작업을 예약하고 즉시 반환한다.
fs.appendFile(logPath, 'New access log entry\n', (error) => {
  if (error) {
    console.error('error writing!', error.message)
    return
  }

  console.info('success writing!')
})

console.info('readFile and appendFile were scheduled')

// readFile과 appendFile은 둘 다 비동기로 실행된다.
// 위처럼 나란히 호출하면 어느 콜백이 먼저 실행될지 타입만 봐서는 알 수 없고,
// 읽기가 쓰기보다 먼저 끝나면 readFile 결과에 새 로그가 포함되지 않을 수 있다.