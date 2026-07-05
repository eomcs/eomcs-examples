/**
 * 디자인 패턴 (Design Patterns)
 *
 * TypeScript의 클래스와 타입 시스템을 사용하면 객체 생성 패턴도 타입 안전하게 표현할 수 있다.
 * 여기서는 팩토리 패턴과 빌더 패턴을 살펴본다.
 */

// 1. 팩토리 패턴
// 어떤 구체적인 객체를 만들지 호출자가 직접 new 하지 않고 팩토리에 위임한다.
type PatternShoe = {
  purpose: string
}

class PatternBalletFlat implements PatternShoe {
  purpose = 'dancing'
}

class PatternBoot implements PatternShoe {
  purpose = 'woodcutting'
}

class PatternSneaker implements PatternShoe {
  purpose = 'walking'
}

type PatternShoeType = 'balletFlat' | 'boot' | 'sneaker'

// companion object pattern:
// 타입 PatternShoe와 값 PatternShoeFactory가 함께 객체 생성 규칙을 표현한다.
let PatternShoeFactory = {
  create(type: PatternShoeType): PatternShoe {
    switch (type) {
      case 'balletFlat':
        return new PatternBalletFlat()
      case 'boot':
        return new PatternBoot()
      case 'sneaker':
        return new PatternSneaker()
    }
  },
}

let dancingShoe = PatternShoeFactory.create('balletFlat')
let workingShoe = PatternShoeFactory.create('boot')
let walkingShoe = PatternShoeFactory.create('sneaker')

console.log(dancingShoe.purpose) // dancing
console.log(workingShoe.purpose) // woodcutting
console.log(walkingShoe.purpose) // walking

// PatternShoeFactory.create('sandal')
// Error: Argument of type '"sandal"' is not assignable to parameter of type 'PatternShoeType'.

// 2. 팩토리 패턴은 생성 규칙을 한 곳에 모을 수 있다.
type PatternNotification = {
  send(message: string): void
}

class EmailNotification implements PatternNotification {
  constructor(private address: string) {}

  send(message: string) {
    console.log(`Email to ${this.address}: ${message}`)
  }
}

class SmsNotification implements PatternNotification {
  constructor(private phoneNumber: string) {}

  send(message: string) {
    console.log(`SMS to ${this.phoneNumber}: ${message}`)
  }
}

type NotificationChannel =
  | { type: 'email'; address: string }
  | { type: 'sms'; phoneNumber: string }

let NotificationFactory = {
  create(channel: NotificationChannel): PatternNotification {
    switch (channel.type) {
      case 'email':
        return new EmailNotification(channel.address)
      case 'sms':
        return new SmsNotification(channel.phoneNumber)
    }
  },
}

let emailNotification = NotificationFactory.create({
  type: 'email',
  address: 'ada@example.com',
})

let smsNotification = NotificationFactory.create({
  type: 'sms',
  phoneNumber: '010-1234-5678',
})

emailNotification.send('Build succeeded')
smsNotification.send('Deploy finished')

// NotificationFactory.create({ type: 'email', phoneNumber: '010-1234-5678' })
// Error: Object literal may only specify known properties, and 'phoneNumber' does not exist in type ...

// 3. 빌더 패턴
// 객체 생성 로직을 메서드 체이닝으로 분리한다.
type RequestMethod = 'get' | 'post'

type BuiltRequest = {
  url: string
  method: RequestMethod
  data: object | null
}

class PatternRequestBuilder {
  private data: object | null = null
  private method: RequestMethod | null = null
  private url: string | null = null

  setMethod(method: RequestMethod): this {
    this.method = method
    return this
  }

  setData(data: object): this {
    this.data = data
    return this
  }

  setURL(url: string): this {
    this.url = url
    return this
  }

  build(): BuiltRequest {
    if (this.url === null) {
      throw new Error('URL is required.')
    }

    if (this.method === null) {
      throw new Error('Method is required.')
    }

    return {
      url: this.url,
      method: this.method,
      data: this.data,
    }
  }

  send() {
    let request = this.build()

    console.log(`${request.method.toUpperCase()} ${request.url}`, request.data)
  }
}

new PatternRequestBuilder()
  .setURL('/users')
  .setMethod('get')
  .setData({ firstName: 'Anna' })
  .send()

// new PatternRequestBuilder()
//   .setURL('/users')
//   .setMethod('put')
// Error: Argument of type '"put"' is not assignable to parameter of type 'RequestMethod'.

try {
  new PatternRequestBuilder()
    .setURL('/users')
    .send()
} catch (error) {
  console.log((error as Error).message) // Method is required.
}

// 4. 반환 타입을 this로 지정하면 서브클래스에서도 체이닝이 자연스럽게 동작한다.
class AuthenticatedRequestBuilder extends PatternRequestBuilder {
  private token: string | null = null

  setToken(token: string): this {
    this.token = token
    return this
  }

  send() {
    if (this.token === null) {
      throw new Error('Token is required.')
    }

    console.log(`Authorization: Bearer ${this.token}`)
    super.send()
  }
}

new AuthenticatedRequestBuilder()
  .setURL('/admin/users')
  .setMethod('post')
  .setData({ name: 'Grace' })
  .setToken('secret-token')
  .send()

// new AuthenticatedRequestBuilder()
//   .setURL('/admin/users')
//   .setMethod('post')
//   .setData({ name: 'Grace' })
//   .send()
// Runtime Error: Token is required.
