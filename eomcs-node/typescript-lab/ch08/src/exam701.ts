/**
 * 타입 안전한 멀티스레딩: 브라우저 Web Workers
 *
 * Web Worker는 브라우저에서 CPU 집약적 작업을 별도 스레드에서 실행하는 방법이다.
 * 메인 스레드와 워커 스레드는 공유 메모리 대신 메시지 패싱으로 통신한다.
 */

export {}

type WorkerMessageEvent = {
  data: unknown
}

class FakeWorker {
  onmessage: ((event: WorkerMessageEvent) => void) | null = null

  postMessage(data: unknown) {
    setTimeout(() => {
      console.info('WorkerScript received:', data)

      this.onmessage?.({
        data: `Ack: "${String(data)}"`,
      })
    }, 0)
  }
}

// MainThread.ts에 해당하는 코드
let worker = new FakeWorker()

worker.onmessage = (event) => {
  console.info('MainThread received:', event.data)
}

worker.postMessage('some data')

// 실제 브라우저 코드에서는 다음처럼 사용할 수 있다.
// let worker = new Worker('WorkerScript.js')
// worker.onmessage = (event) => console.log(event.data)
// worker.postMessage('some data')
//
// WorkerScript.ts에서는 전역 onmessage/postMessage를 사용한다.
// onmessage = (event) => {
//   console.log(event.data)
//   postMessage(`Ack: "${event.data}"`)
// }

// 기본 메시지 패싱은 data가 unknown에 가깝기 때문에 타입 안전하지 않다.
// 어떤 메시지 형태가 오가는지 별도 프로토콜 타입으로 정의해야 실수를 줄일 수 있다.

