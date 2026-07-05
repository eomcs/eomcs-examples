/**
 * final 클래스 시뮬레이션 (Simulating final Classes)
 *
 * TypeScript에는 final 키워드가 없다.
 * 하지만 private 생성자를 사용하면 외부에서 직접 생성하거나 상속하는 것을 막을 수 있다.
 */

class FinalMessageQueue {
  private constructor(private messages: string[]) {}

  // 정적 팩토리 메서드로만 인스턴스 생성을 허용한다.
  static create(messages: string[]) {
    return new FinalMessageQueue([...messages])
  }

  enqueue(message: string) {
    this.messages.push(message)
  }

  dequeue(): string | undefined {
    return this.messages.shift()
  }

  peek(): string | undefined {
    return this.messages[0]
  }

  size() {
    return this.messages.length
  }

  toArray() {
    return [...this.messages]
  }
}

let messageQueue = FinalMessageQueue.create(['first', 'second'])

console.log(messageQueue.peek()) // first
console.log(messageQueue.size()) // 2

messageQueue.enqueue('third')

console.log(messageQueue.toArray()) // ['first', 'second', 'third']
console.log(messageQueue.dequeue()) // first
console.log(messageQueue.toArray()) // ['second', 'third']

// new FinalMessageQueue([])
// Error: Constructor of class 'FinalMessageQueue' is private and only accessible within the class declaration.

// class BadMessageQueue extends FinalMessageQueue {}
// Error: Cannot extend a class 'FinalMessageQueue'. Class constructor is marked as private.

// 2. private 생성자는 클래스 내부 정적 메서드에서는 호출할 수 있다.
class FinalConfig {
  private constructor(
    public readonly mode: 'development' | 'production',
    public readonly debug: boolean
  ) {}

  static development() {
    return new FinalConfig('development', true)
  }

  static production() {
    return new FinalConfig('production', false)
  }

  describe() {
    return `${this.mode}, debug=${this.debug}`
  }
}

let developmentConfig = FinalConfig.development()
let productionConfig = FinalConfig.production()

console.log(developmentConfig.describe()) // development, debug=true
console.log(productionConfig.describe()) // production, debug=false

// new FinalConfig('development', true)
// Error: Constructor of class 'FinalConfig' is private and only accessible within the class declaration.

// class TestConfig extends FinalConfig {}
// Error: Cannot extend a class 'FinalConfig'. Class constructor is marked as private.

// 3. final 시뮬레이션은 런타임 기능보다 타입 레벨 설계에 가깝다.
// 정적 팩토리 메서드로 생성 경로를 제한하면 생성 규칙을 한 곳에 모을 수 있다.
class FinalUserId {
  private constructor(public readonly value: string) {}

  static create(value: string) {
    if (!value.startsWith('u-')) {
      throw new Error('User id must start with "u-".')
    }

    return new FinalUserId(value)
  }

  toString() {
    return this.value
  }
}

let userId = FinalUserId.create('u-123')

console.log(userId.toString()) // u-123

try {
  FinalUserId.create('123')
} catch (error) {
  console.log((error as Error).message) // User id must start with "u-".
}

// FinalUserId.create(123)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.
