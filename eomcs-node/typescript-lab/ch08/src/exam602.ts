/**
 * 이벤트 이미터: 매핑 타입으로 타입 안전하게 구현
 *
 * 이벤트 이름과 payload 타입을 객체 타입으로 매핑하면,
 * on/emit에서 이벤트 이름에 맞는 인자 타입을 정확히 강제할 수 있다.
 */

export {}

type Events = {
  ready: void
  error: Error
  reconnecting: { attempt: number; delay: number }
}

type RedisClient = {
  on<E extends keyof Events>(event: E, f: (arg: Events[E]) => void): void
  emit<E extends keyof Events>(event: E, arg: Events[E]): void
}

class TypedRedisClient implements RedisClient {
  private listeners: {
    [E in keyof Events]: ((arg: Events[E]) => void)[]
  } = {
    ready: [],
    error: [],
    reconnecting: [],
  }

  on<E extends keyof Events>(event: E, f: (arg: Events[E]) => void): void {
    this.listeners[event].push(f)
  }

  emit<E extends keyof Events>(event: E, arg: Events[E]): void {
    let listeners = this.listeners[event]

    listeners.forEach((listener) => listener(arg))
  }
}

let client = new TypedRedisClient()

client.on('ready', () => {
  console.info('Client is ready')
})

client.on('error', (error) => {
  console.error('An error occurred!', error.message)
})

client.on('reconnecting', (params) => {
  console.info('Reconnecting...', params.attempt, params.delay)
})

client.emit('ready', undefined)
client.emit('error', new Error('Connection refused'))
client.emit('reconnecting', { attempt: 3, delay: 1500 })

// client.emit('ready', 'done')
// Error: Argument of type '"done"' is not assignable to parameter of type 'void'.

// client.emit('error', 'Connection refused')
// Error: Argument of type 'string' is not assignable to parameter of type 'Error'.

// client.emit('reconnecting', { attempt: 3 })
// Error: Property 'delay' is missing in type '{ attempt: number; }'.

// client.on('reconecting', () => {})
// Error: Argument of type '"reconecting"' is not assignable to parameter of type 'keyof Events'.

// DOM 이벤트도 같은 패턴을 사용한다.
// 예를 들어 WindowEventMap은 이벤트 이름과 이벤트 객체 타입을 매핑하고,
// addEventListener는 이벤트 이름에 맞춰 콜백 매개변수 타입을 좁힌다.
if (typeof window !== 'undefined') {
  window.addEventListener('click', (event) => {
    console.info('DOM event type:', event.type)
  })
}
