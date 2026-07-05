/**
 * 데코레이터 (Decorators)
 *
 * 데코레이터는 클래스, 메서드, 프로퍼티, 파라미터에 대한 메타프로그래밍 문법이다.
 * 내부적으로는 대상을 감싸거나 관찰하는 함수 호출로 이해할 수 있다.
 *
 * 현재 ch05/tsconfig.json에는 experimentalDecorators가 켜져 있지 않다.
 * 따라서 이 파일에서는 @ 문법 대신 같은 의미의 일반 함수 호출로 예제를 작성한다.
 */

type DecoratorConstructor<T> = new (...args: any[]) => T

// 1. 클래스 데코레이터: 생성자를 받아 새 생성자를 반환할 수 있다.
function serializable<C extends DecoratorConstructor<{
  getValue(): object
}>>(Constructor: C) {
  return class extends Constructor {
    serialize() {
      return JSON.stringify(this.getValue())
    }
  }
}

type DecoratorPayload = {
  id: string
  body: object
}

// @serializable
// class APIPayload { ... }
//
// 위 문법은 대략 아래 함수 호출과 비슷하게 생각할 수 있다.
let SerializablePayload = serializable(class APIPayload {
  constructor(private payload: DecoratorPayload) {}

  getValue() {
    return this.payload
  }
})

let payload = new SerializablePayload({
  id: 'p-1',
  body: { message: 'hello' },
})

console.log(payload.getValue()) // { id: 'p-1', body: { message: 'hello' } }
console.log(payload.serialize()) // {"id":"p-1","body":{"message":"hello"}}

// serializable(class InvalidPayload {})
// Error: Property 'getValue' is missing.

// 2. 클래스 shape 변경을 실제 데코레이터 문법으로 하면 TypeScript가 추적하지 못할 수 있다.
// 일반 함수로 작성하면 반환 타입을 명시적으로 추론할 수 있어 더 안전하다.
let serializedText: string = payload.serialize()

console.log(serializedText.toUpperCase())

// 3. 메서드 데코레이터 시그니처 모양
// (classPrototype: {}, methodName: string, descriptor: PropertyDescriptor) => any
function logMethodCall(
  classPrototype: object,
  methodName: string,
  descriptor: PropertyDescriptor
) {
  let originalMethod = descriptor.value as (...args: unknown[]) => unknown

  descriptor.value = function(...args: unknown[]) {
    console.log(`Calling ${methodName}`, args)

    return originalMethod.apply(this, args)
  }

  Object.defineProperty(classPrototype, methodName, descriptor)
}

class DecoratedCalculator {
  add(a: number, b: number) {
    return a + b
  }
}

let addDescriptor = Object.getOwnPropertyDescriptor(
  DecoratedCalculator.prototype,
  'add'
)

if (addDescriptor) {
  logMethodCall(DecoratedCalculator.prototype, 'add', addDescriptor)
}

let calculator = new DecoratedCalculator()

console.log(calculator.add(10, 20)) // Calling add [10, 20] / 30

// calculator.add(10, '20')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 4. 프로퍼티 데코레이터 시그니처 모양
// (classPrototype: {}, propertyName: string) => any
let decoratedProperties: string[] = []

function collectPropertyName(classPrototype: object, propertyName: string) {
  decoratedProperties.push(`${classPrototype.constructor.name}.${propertyName}`)
}

class DecoratedConfig {
  url = 'https://example.com'
  timeout = 1000
}

collectPropertyName(DecoratedConfig.prototype, 'url')
collectPropertyName(DecoratedConfig.prototype, 'timeout')

console.log(decoratedProperties)
// ['DecoratedConfig.url', 'DecoratedConfig.timeout']

// 5. 파라미터 데코레이터 시그니처 모양
// (classPrototype: {}, paramName: string, index: number) => void
let decoratedParameters: string[] = []

function collectParameterIndex(
  classPrototype: object,
  methodName: string,
  index: number
) {
  decoratedParameters.push(
    `${classPrototype.constructor.name}.${methodName}[${index}]`
  )
}

class DecoratedController {
  handle(id: string, body: object) {
    console.log(id, body)
  }
}

collectParameterIndex(DecoratedController.prototype, 'handle', 0)
collectParameterIndex(DecoratedController.prototype, 'handle', 1)

console.log(decoratedParameters)
// ['DecoratedController.handle[0]', 'DecoratedController.handle[1]']

// 6. 실제 @ 문법을 사용하려면 tsconfig에 experimentalDecorators가 필요하다.
//
// {
//   "compilerOptions": {
//     "experimentalDecorators": true
//   }
// }
//
// 아직 실험적 기능이고 shape 변경 추적에 한계가 있으므로,
// 안정성이 중요하면 위 예제처럼 일반 함수로 작성하는 편이 낫다.
