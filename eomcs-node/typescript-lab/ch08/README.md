# Chapter 8. Asynchronous Programming, Concurrency, and Parallelism

> JavaScript는 단일 스레드에서 이벤트 루프 기반으로 동시성을 처리한다. TypeScript는 타입 시스템과 async/await 지원으로 비동기 코드를 안전하게 표현할 수 있게 해준다.

---

## 비동기 프로그래밍 기법 개요

| 기법 | 용도 | 복잡도 |
|------|------|--------|
| 콜백(Callback) | 단순 비동기 작업 | 낮음 |
| Promise | 순서 있는 비동기 작업 | 중간 |
| async/await | Promise의 문법적 설탕 | 낮음 (읽기 쉬움) |
| 이벤트 이미터 | 반복 발생 이벤트 스트림 | 중간 |
| Web Worker / Child Process | 멀티스레드 병렬 처리 | 높음 |

---

## 1. JavaScript 이벤트 루프 (JavaScript's Event Loop)

JavaScript VM의 동시성 모델:

```
setTimeout(() => console.info('A'), 1)
setTimeout(() => console.info('B'), 2)
console.info('C')

// 출력 순서: C → A → B
```

**이벤트 루프 동작 원리**:

1. 메인 스레드가 `setTimeout`, `readFile` 등 **네이티브 비동기 API**를 호출한다.
2. 제어권이 즉시 메인 스레드로 반환되어 다음 코드를 실행한다.
3. 비동기 작업이 완료되면 플랫폼이 **이벤트 큐**에 태스크를 추가한다.
4. 메인 스레드의 콜 스택이 비면, 플랫폼이 이벤트 큐에서 태스크를 꺼내 콜백을 실행한다.
5. 콜 스택과 이벤트 큐가 모두 빌 때까지 이 루프를 반복한다.

> Java·C++의 멀티스레드 모델과 달리, JavaScript는 단일 스레드에서 이벤트 루프로 동시성을 구현한다. 공유 메모리 동기화, 뮤텍스, 세마포어 등이 필요 없다.

---

## 2. 콜백 (Working with Callbacks)

가장 기본적인 비동기 단위. NodeJS의 콜백 관례: `(error | null, result | null) => void`

```typescript
import * as fs from 'fs'

fs.readFile(
  '/var/log/apache2/access_log',
  {encoding: 'utf8'},
  (error, data) => {
    if (error) {
      console.error('error reading!', error)
      return
    }
    console.info('success reading!', data)
  }
)

fs.appendFile(
  '/var/log/apache2/access_log',
  'New access log entry',
  error => {
    if (error) {
      console.error('error writing!', error)
    }
  }
)
```

> **주의**: `readFile`과 `appendFile`은 비동기로 실행되므로 실행 순서가 보장되지 않는다.

**콜백의 단점**:
- 타입만 봐서는 함수가 동기인지 비동기인지 알 수 없음
- 순서가 필요한 작업에서 "콜백 피라미드" 발생

```typescript
// 콜백 피라미드 (Callback Hell)
async1((err1, res1) => {
  if (res1) {
    async2(res1, (err2, res2) => {
      if (res2) {
        async3(res2, (err3, res3) => {
          // ...
        })
      }
    })
  }
})
```

---

## 3. Promise

비동기 작업을 추상화하여 체이닝·시퀀싱을 가능하게 한다.

```typescript
// Promise 기반 API 사용 예
function appendAndReadPromise(path: string, data: string): Promise<string> {
  return appendPromise(path, data)
    .then(() => readPromise(path))
    .catch(error => console.error(error))
}
```

### Promise 타입 설계

```typescript
type Executor<T> = (
  resolve: (result: T) => void,
  reject: (error: unknown) => void
) => void

class Promise<T> {
  constructor(f: Executor<T>) {}

  then<U>(g: (result: T) => Promise<U>): Promise<U> {
    // ...
  }

  catch<U>(g: (error: unknown) => Promise<U>): Promise<U> {
    // ...
  }
}
```

### 콜백 기반 API를 Promise로 래핑

```typescript
import {readFile} from 'fs'

function readFilePromise(path: string): Promise<string> {
  return new Promise((resolve, reject) => {
    readFile(path, (error, result) => {
      if (error) {
        reject(error)
      } else {
        resolve(result)
      }
    })
  })
}
```

### Promise 상태 머신

```
pending → resolved (then 실행)
        → rejected (catch 실행)
```

- `then`: 성공 결과를 새로운 Promise로 매핑
- `catch`: 실패를 복구하여 새로운 Promise로 매핑
- `finally`: 성공/실패 여부와 관계없이 항상 실행

```typescript
let a: () => Promise<string> = // ...
let b: (s: string) => Promise<number> = // ...

a()
  .then(b)
  .then(result => console.info('Done', result))
  .catch(e => console.error('Error', e))
  .finally(() => console.info('Finished'))
```

> **특징**: Promise는 항상 거부(reject)될 가능성이 있으며, TypeScript는 이를 함수 시그니처에 인코딩할 수 없다. 또한 `throw`로 어떤 값이든 던질 수 있으므로 에러 타입은 `unknown`으로 처리한다.

---

## 4. async / await

Promise에 대한 언어 레벨 문법적 설탕(syntactic sugar). `await`는 `.then`과 동일하고, `try/catch`는 `.catch`를 대체한다.

```typescript
// Promise 체이닝 방식
function getUser() {
  getUserID(18)
    .then(user => getLocation(user))
    .then(location => console.info('got location', location))
    .catch(error => console.error(error))
    .finally(() => console.info('done getting location'))
}

// async/await 방식 (더 읽기 쉬움)
async function getUser() {
  try {
    let user = await getUserID(18)
    let location = await getLocation(user)
    console.info('got location', location)
  } catch(error) {
    console.error(error)
  } finally {
    console.info('done getting location')
  }
}
```

> TypeScript는 async/await를 완전히 지원하며, 완전한 타입 안전성을 제공한다.

---

## 5. 비동기 스트림 (Async Streams)

단일 미래 값이 아니라 **시간에 걸쳐 여러 값**이 필요할 때 사용한다.

예시: 파일 읽기 청크, 동영상 스트리밍, 키 입력, 투표 집계

두 가지 접근 방식:
- **이벤트 이미터**: 가볍고 단순
- **RxJS 등 반응형 라이브러리**: 스트림 합성·시퀀싱 가능

---

## 6. 이벤트 이미터 (Event Emitters)

이벤트를 채널에 발행(emit)하고 구독(on)하는 패턴.

### 기본 인터페이스

```typescript
interface Emitter {
  emit(channel: string, value: unknown): void
  on(channel: string, f: (value: unknown) => void): void
}
```

기본 구현은 `string`과 `unknown`을 사용해 타입 안전하지 않다.

### 매핑 타입으로 타입 안전한 이벤트 이미터 구현

```typescript
// 이벤트 타입 정의
type Events = {
  ready: void
  error: Error
  reconnecting: {attempt: number, delay: number}
}

// 매핑 타입으로 on/emit을 타입 안전하게
type RedisClient = {
  on<E extends keyof Events>(
    event: E,
    f: (arg: Events[E]) => void
  ): void
  emit<E extends keyof Events>(
    event: E,
    arg: Events[E]
  ): void
}
```

**사용 예시 (NodeRedis)**:

```typescript
import Redis from 'redis'

let client = redis.createClient()

client.on('ready', () => console.info('Client is ready'))
client.on('error', e => console.error('An error occurred!', e))
client.on('reconnecting', params => console.info('Reconnecting...', params))
```

> DOM 이벤트도 동일한 패턴으로 타입이 정의된다: `WindowEventMap`에서 이벤트 이름과 타입을 매핑하고, `addEventListener`가 이를 활용한다.

---

## 7. 타입 안전한 멀티스레딩 (Typesafe Multithreading)

### 브라우저: Web Workers

Web Worker는 브라우저에서 CPU 집약적 작업을 별도 스레드에서 실행하는 방법이다. 스레드 간 통신은 **메시지 패싱**으로 이루어진다.

```typescript
// tsconfig.json
{ "compilerOptions": { "lib": ["dom", "es2015"] } }          // 메인 스레드
{ "compilerOptions": { "lib": ["webworker", "es2015"] } }    // 워커 스레드
```

**기본 메시지 패싱 (타입 불안전)**:

```typescript
// MainThread.ts
let worker = new Worker('WorkerScript.js')
worker.onmessage = e => console.log(e.data)
worker.postMessage('some data')

// WorkerScript.ts
onmessage = e => {
  console.log(e.data)      // 'some data'
  postMessage(`Ack: "${e.data}"`)
}
```

### 타입 안전한 SafeEmitter 구현

```typescript
import EventEmitter from 'events'

class SafeEmitter<
  Events extends Record<PropertyKey, unknown[]>
> {
  private emitter = new EventEmitter

  emit<K extends keyof Events>(
    channel: K,
    ...data: Events[K]
  ) {
    return this.emitter.emit(channel, ...data)
  }

  on<K extends keyof Events>(
    channel: K,
    listener: (...data: Events[K]) => void
  ) {
    return this.emitter.on(channel, listener)
  }
}
```

**커맨드/이벤트 타입 정의**:

```typescript
// 메인 → 워커
type Commands = {
  sendMessageToThread: [ThreadID, Message]
  createThread: [Participants]
  addUserToThread: [ThreadID, UserID]
  removeUserFromThread: [ThreadID, UserID]
}

// 워커 → 메인
type Events = {
  receivedMessage: [ThreadID, UserID, Message]
  createdThread: [ThreadID, Participants]
  addedUserToThread: [ThreadID, UserID]
  removedUserFromThread: [ThreadID, UserID]
}
```

**워커 스크립트에서 SafeEmitter 활용**:

```typescript
// WorkerScript.ts
let commandEmitter = new SafeEmitter<Commands>()
let eventEmitter   = new SafeEmitter<Events>()

// 메인 스레드로부터 커맨드 수신
onmessage = command =>
  commandEmitter.emit(command.data.type, ...command.data.data)

// 메인 스레드로 이벤트 전송
eventEmitter.on('receivedMessage', data =>
  postMessage({type: 'receivedMessage', data})
)

// 커맨드 처리
commandEmitter.on('sendMessageToThread', (threadID, message) =>
  console.log(`Sending message to thread ${threadID}`)
)

// 이벤트 발행
eventEmitter.emit('createdThread', 123, [456, 789])
```

---

### 타입 안전한 프로토콜 (Typesafe Protocols)

커맨드와 응답을 1:1로 매핑하는 요청-응답 프로토콜.

```typescript
type Matrix = number[][]

type MatrixProtocol = {
  determinant: {
    in: [Matrix]
    out: number
  }
  'dot-product': {
    in: [Matrix, Matrix]
    out: Matrix
  }
  invert: {
    in: [Matrix]
    out: Matrix
  }
}
```

**제네릭 프로토콜 생성 함수**:

```typescript
type Protocol = {
  [command: string]: {
    in: unknown[]
    out: unknown
  }
}

function createProtocol<P extends Protocol>(script: string) {
  return <K extends keyof P>(command: K) =>
    (...args: P[K]['in']) =>
      new Promise<P[K]['out']>((resolve, reject) => {
        let worker = new Worker(script)
        worker.onerror = reject
        worker.onmessage = event => resolve(event.data.data)
        worker.postMessage({command, args})
      })
}

// 사용
let runWithMatrixProtocol = createProtocol<MatrixProtocol>('MatrixWorkerScript.js')
let parallelDeterminant = runWithMatrixProtocol('determinant')

parallelDeterminant([[1, 2], [3, 4]])
  .then(determinant => console.log(determinant)) // -2
```

---

### NodeJS: Child Processes

브라우저의 Web Worker와 동일한 메시지 패싱 방식이다.

```typescript
// MainThread.ts
import {fork} from 'child_process'

let child = fork('./ChildThread.js')

child.on('message', data =>
  console.info('Child process sent a message', data)
)

child.send({type: 'syn', data: [3]})

// ChildThread.ts
process.on('message', data =>
  console.info('Parent process sent a message', data)
)

process.send({type: 'ack', data: [3]})
```

---

## 핵심 요약

| 상황 | 권장 기법 |
|------|---------|
| 단순 비동기 작업 | 콜백 |
| 순서 있는 비동기 작업 | Promise / async·await |
| 이벤트가 여러 번 발생 | 이벤트 이미터 / RxJS |
| 멀티스레드 병렬 처리 | Web Worker + 타입 안전 프로토콜 |
| 실제 서비스 간 통신 | Swagger / gRPC / GraphQL |

**설계 원칙**: 안전하지 않은 메시지 패싱 API를 직접 사용하지 말고, 타입 안전한 래퍼(SafeEmitter, createProtocol 등)로 추상화한다.

---

## 연습 문제

1. 단일 인수와 콜백을 받는 함수를 Promise를 반환하는 함수로 변환하는 범용 `promisify` 함수를 구현하라:

```typescript
import {readFile} from 'fs'

let readFilePromise = promisify(readFile)

readFilePromise('./myfile.ts')
  .then(result => console.log('success reading file', result.toString()))
  .catch(error => console.error('error reading file', error))
```

2. 타입 안전한 행렬 연산 프로토콜의 메인 스레드 측 구현을 바탕으로, Web Worker 스레드 측 구현을 완성하라.

3. 매핑 타입을 활용하여 NodeJS `child_process`에 대한 타입 안전한 메시지 패싱 프로토콜을 구현하라.
