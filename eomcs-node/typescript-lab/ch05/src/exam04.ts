/**
 * implements 키워드 (Implementations)
 *
 * implements는 클래스가 특정 인터페이스의 shape를 만족하는지
 * 컴파일 타임에 검사한다. 구현을 상속하지는 않는다.
 */

// 1. 하나의 클래스가 여러 인터페이스를 구현할 수 있다.
interface Animal {
  readonly name: string
  eat(food: string): void
  sleep(hours: number): void
}

interface Feline {
  meow(): void
}

class ImplementedCat implements Animal, Feline {
  readonly name = 'Whiskers'

  eat(food: string) {
    console.log(`${this.name} ate some ${food}.`)
  }

  sleep(hours: number) {
    console.log(`${this.name} slept for ${hours} hours.`)
  }

  meow() {
    console.log('Meow')
  }
}

let cat = new ImplementedCat()

cat.eat('tuna') // Whiskers ate some tuna.
cat.sleep(8) // Whiskers slept for 8 hours.
cat.meow() // Meow

// cat.name = 'Milo'
// Error: Cannot assign to 'name' because it is a read-only property.

// class InvalidCat implements Animal, Feline {
//   name = 'Invalid'
//   eat(food: string) {}
//   sleep(hours: number) {}
// }
// Error: Class 'InvalidCat' incorrectly implements interface 'Feline'.
// Property 'meow' is missing.

// 2. implements는 클래스의 public 인스턴스 멤버만 검사한다.
interface NamedRunner {
  name: string
  run(distance: number): void
}

class MarathonRunner implements NamedRunner {
  constructor(public name: string) {}

  run(distance: number) {
    console.log(`${this.name} ran ${distance}km.`)
  }
}

let runner: NamedRunner = new MarathonRunner('Ada')

runner.run(42) // Ada ran 42km.

// interface InvalidPrivateMember {
//   private secret: string
// }
// Error: 'private' modifier cannot appear on a type member.

// interface InvalidProtectedMember {
//   protected secret: string
// }
// Error: 'protected' modifier cannot appear on a type member.

// interface InvalidStaticMember {
//   static create(): NamedRunner
// }
// Error: 'static' modifier cannot appear on a type member.

// 3. implements는 메서드 본문을 제공하지 않는다.
interface LoggerShape {
  log(message: string): void
}

class ConsoleLogger implements LoggerShape {
  log(message: string) {
    console.log(`[log] ${message}`)
  }
}

let logger = new ConsoleLogger()

logger.log('implemented by class') // [log] implemented by class

// 4. abstract class는 기본 구현과 상태를 공유할 수 있다.
abstract class AbstractLogger {
  protected messages: string[] = []

  log(message: string) {
    this.messages.push(message)
    this.write(message)
  }

  getHistory() {
    return [...this.messages]
  }

  protected abstract write(message: string): void
}

class TimestampLogger extends AbstractLogger {
  protected write(message: string) {
    console.log(`${new Date(2026, 5, 29).toISOString()} ${message}`)
  }
}

let timestampLogger = new TimestampLogger()

timestampLogger.log('shared implementation')
timestampLogger.log('stored in history')

console.log(timestampLogger.getHistory()) // ['shared implementation', 'stored in history']

// new AbstractLogger()
// Error: Cannot create an instance of an abstract class.

// class InvalidLogger extends AbstractLogger {}
// Error: Non-abstract class 'InvalidLogger' does not implement inherited abstract member write.

// 5. interface는 클래스가 아닌 객체 shape도 표현할 수 있다.
let objectLogger: LoggerShape = {
  log(message: string) {
    console.log(`[object] ${message}`)
  },
}

objectLogger.log('plain object also matches') // [object] plain object also matches

function writeWithLogger(loggerToUse: LoggerShape, message: string) {
  loggerToUse.log(message)
}

writeWithLogger(new ConsoleLogger(), 'class instance') // [log] class instance
writeWithLogger(objectLogger, 'object literal') // [object] object literal

// writeWithLogger({ write(message: string) {} }, 'wrong shape')
// Error: Object literal may only specify known properties, and 'write' does not exist in type 'LoggerShape'.

// 6. abstract class와 interface를 함께 사용할 수도 있다.
interface SerializableShape {
  serialize(): string
}

abstract class EntityWithId {
  constructor(public readonly id: string) {}

  describe() {
    return `Entity(${this.id})`
  }
}

class UserEntity extends EntityWithId implements SerializableShape {
  constructor(
    id: string,
    public name: string
  ) {
    super(id)
  }

  serialize() {
    return JSON.stringify({
      id: this.id,
      name: this.name,
    })
  }
}

let userEntity = new UserEntity('u-1', 'Grace')

console.log(userEntity.describe()) // Entity(u-1)
console.log(userEntity.serialize()) // {"id":"u-1","name":"Grace"}
