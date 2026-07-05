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

// 3. 순서가 필요한 작업은 다음 작업을 이전 콜백 안에서 시작해야 한다.
fs.appendFile(logPath, 'Ordered entry 1\n', (appendError) => {
  if (appendError) {
    console.error('error writing ordered entry!', appendError.message)
    return
  }

  console.info('1. success writing!')

  fs.readFile(logPath, { encoding: 'utf8' }, (readError, data) => {
    if (readError) {
      console.error('error reading ordered entry!', readError.message)
      return
    }

    console.info('ordered read contains entry 1:', data.includes('Ordered entry 1'))

    fs.appendFile(logPath, 'Ordered entry 2\n', (secondAppendError) => {
      if (secondAppendError) {
        console.error('error writing second ordered entry!', secondAppendError.message)
        return
      }

      console.info('2. success writing!')
      
      fs.readFile(logPath, { encoding: 'utf8' }, (secondReadError, secondData) => {
        if (secondReadError) {
          console.error('error reading second ordered entry!', secondReadError.message)
          return
        }

        console.info(
          'ordered read contains entry 2:',
          secondData.includes('Ordered entry 2')
        )
      })
    })
  })
})

// 위처럼 콜백 안에 다음 콜백을 계속 넣으면 순서는 보장할 수 있지만,
// 코드가 오른쪽으로 깊게 밀리는 "콜백 피라미드"가 생긴다.

