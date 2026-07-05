/**
 * NodeJS: Child Processes
 *
 * Node의 child_process.fork도 Web Worker와 비슷하게 메시지 패싱으로 통신한다.
 * 부모 프로세스는 child.send(...)로 메시지를 보내고,
 * 자식 프로세스는 process.on('message', ...)로 메시지를 받는다.
 */

import { fork } from 'node:child_process'

type ParentToChild = {
  type: 'syn'
  data: [number]
}

type ChildToParent = {
  type: 'ack'
  data: [number]
}

function isParentToChild(message: unknown): message is ParentToChild {
  return (
    typeof message === 'object' &&
    message !== null &&
    'type' in message &&
    (message as { type: unknown }).type === 'syn' &&
    'data' in message &&
    Array.isArray((message as { data: unknown }).data)
  )
}

if (process.argv.includes('--child')) {
  process.on('message', (message: unknown) => {
    if (!isParentToChild(message)) {
      return
    }

    console.info('Parent process sent a message', message)

    let response: ChildToParent = {
      type: 'ack',
      data: message.data,
    }

    process.send?.(response)
  })
} else {
  let child = fork(__filename, ['--child'])

  child.on('message', (message: ChildToParent) => {
    console.info('Child process sent a message', message)
    child.disconnect()
  })

  let message: ParentToChild = {
    type: 'syn',
    data: [3],
  }

  child.send(message)
}

