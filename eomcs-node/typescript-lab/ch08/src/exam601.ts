/**
 * 이벤트 이미터: 기본 인터페이스
 *
 * 이벤트 이미터는 이벤트 이름(channel)에 콜백을 등록하고,
 * 나중에 같은 이름으로 값을 발행(emit)하면 등록된 콜백을 실행하는 패턴이다.
 */

export {}

interface Emitter {
  emit(channel: string, value: unknown): void
  on(channel: string, f: (value: unknown) => void): void
}

class BasicEmitter implements Emitter {
  private listeners: Record<string, ((value: unknown) => void)[]> = {}

  emit(channel: string, value: unknown): void {
    let listeners = this.listeners[channel] ?? []

    listeners.forEach((listener) => listener(value))
  }

  on(channel: string, f: (value: unknown) => void): void {
    let listeners = this.listeners[channel] ?? []

    listeners.push(f)
    this.listeners[channel] = listeners
  }
}

let emitter = new BasicEmitter()

emitter.on('ready', () => {
  console.info('Client is ready')
})

emitter.on('error', (value) => {
  if (value instanceof Error) {
    console.error('An error occurred!', value.message)
    return
  }

  console.error('Unknown error value', value)
})

emitter.on('reconnecting', (value) => {
  if (
    typeof value === 'object' &&
    value !== null &&
    'attempt' in value &&
    'delay' in value
  ) {
    let params = value as { attempt: number; delay: number }

    console.info('Reconnecting...', params.attempt, params.delay)
  }
})

emitter.emit('ready', undefined)
emitter.emit('error', new Error('Connection refused'))
emitter.emit('reconnecting', { attempt: 2, delay: 1000 })

// 기본 인터페이스는 channel이 string이고 value가 unknown이다.
// 그래서 오타나 잘못된 payload를 타입 검사 단계에서 잡지 못한다.
emitter.emit('redy', undefined)
emitter.emit('reconnecting', 'not reconnect params')

// 위 두 호출은 타입 검사에는 통과한다.
// 대신 콜백 안에서 unknown 값을 직접 좁히거나 단언해야 하므로 사용자가 더 많은 책임을 진다.

