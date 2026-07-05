/**
 * 타입 안전한 SafeEmitter 구현
 *
 * EventEmitter 자체는 이벤트 이름과 인자 목록을 강하게 연결하지 않는다.
 * 매핑 타입을 사용하면 이벤트 이름별 인자 튜플을 타입으로 강제할 수 있다.
 */

import EventEmitter from 'node:events'

class SafeEmitter<Events extends { [K in keyof Events]: unknown[] }> {
  private emitter = new EventEmitter()

  emit<K extends keyof Events>(channel: K, ...data: Events[K]) {
    return this.emitter.emit(channel as string | symbol, ...data)
  }

  on<K extends keyof Events>(
    channel: K,
    listener: (...data: Events[K]) => void
  ) {
    return this.emitter.on(channel as string | symbol, listener)
  }
}

type ThreadID = number
type UserID = number
type Message = string
type Participants = UserID[]

// 메인 -> 워커
type Commands = {
  sendMessageToThread: [ThreadID, Message]
  createThread: [Participants]
  addUserToThread: [ThreadID, UserID]
  removeUserFromThread: [ThreadID, UserID]
}

// 워커 -> 메인
type Events = {
  receivedMessage: [ThreadID, UserID, Message]
  createdThread: [ThreadID, Participants]
  addedUserToThread: [ThreadID, UserID]
  removedUserFromThread: [ThreadID, UserID]
}

let commandEmitter = new SafeEmitter<Commands>()
let eventEmitter = new SafeEmitter<Events>()

eventEmitter.on('receivedMessage', (threadID, userID, message) => {
  console.info('receivedMessage:', threadID, userID, message)
})

eventEmitter.on('createdThread', (threadID, participants) => {
  console.info('createdThread:', threadID, participants.join(', '))
})

commandEmitter.on('sendMessageToThread', (threadID, message) => {
  console.info(`Sending message to thread ${threadID}: ${message}`)
  eventEmitter.emit('receivedMessage', threadID, 456, message)
})

commandEmitter.on('createThread', (participants) => {
  console.info('Creating thread:', participants.join(', '))
  eventEmitter.emit('createdThread', 123, participants)
})

commandEmitter.emit('sendMessageToThread', 123, 'Hello from main thread')
commandEmitter.emit('createThread', [456, 789])

// commandEmitter.emit('sendMessageToThread', '123', 'Hello')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// commandEmitter.emit('createThread', 456, 789)
// Error: Expected 2 arguments, but got 3.

// eventEmitter.emit('createdThread', 123, 456)
// Error: Argument of type 'number' is not assignable to parameter of type 'Participants'.

