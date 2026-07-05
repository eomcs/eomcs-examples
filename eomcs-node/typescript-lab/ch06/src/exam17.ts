/**
 * 조건부 타입 (Conditional Types)
 *
 * 조건부 타입은 타입 레벨의 삼항 연산자다.
 * T가 U의 서브타입이면 A, 아니면 B를 선택한다.
 */

export {}

// 1. 기본 조건부 타입
type IsString<T> = T extends string ? true : false

type StringResult = IsString<string> // true
type NumberResult = IsString<number> // false

let stringResult: StringResult = true
let numberResult: NumberResult = false

console.log(stringResult, numberResult)

// let invalidStringResult: StringResult = false
// Error: Type 'false' is not assignable to type 'true'.

// let invalidNumberResult: NumberResult = true
// Error: Type 'true' is not assignable to type 'false'.

type TypeName<T> = T extends string
  ? 'string'
  : T extends number
    ? 'number'
    : T extends boolean
      ? 'boolean'
      : 'object'

type NameOfString = TypeName<string> // 'string'
type NameOfDate = TypeName<Date> // 'object'

let nameOfString: NameOfString = 'string'
let nameOfDate: NameOfDate = 'object'

console.log(nameOfString, nameOfDate)

// 2. 분배 조건부 타입
// T가 naked type parameter일 때, 유니온 타입은 각 멤버에 조건부 타입이 분배된다.
type Without<T, U> = T extends U ? never : T

type WithoutBoolean = Without<boolean | number | string, boolean>
// Without<boolean, boolean> | Without<number, boolean> | Without<string, boolean>
// = never | number | string
// = number | string

let valueWithoutBoolean: WithoutBoolean = 'hello'
valueWithoutBoolean = 123

console.log(valueWithoutBoolean)

// valueWithoutBoolean = true
// Error: Type 'boolean' is not assignable to type 'WithoutBoolean'.

type OnlyString = Extract<string | number | boolean, string>
type NoString = Exclude<string | number | boolean, string>

let onlyString: OnlyString = 'Ada'
let noString: NoString = 100
noString = false

console.log(onlyString, noString)

// onlyString = 100
// Error: Type 'number' is not assignable to type 'string'.

// noString = 'Grace'
// Error: Type '"Grace"' is not assignable to type 'NoString'.

// 3. infer 키워드
// 조건부 타입 안에서 추출할 타입을 인라인으로 선언할 수 있다.
type ElementType<T> = T extends unknown[] ? T[number] : T
type ElementTypeWithInfer<T> = T extends (infer U)[] ? U : T

type NumberElementA = ElementType<number[]> // number
type NumberElementB = ElementTypeWithInfer<number[]> // number
type StringFallback = ElementTypeWithInfer<string> // string

let numberElementA: NumberElementA = 1
let numberElementB: NumberElementB = 2
let stringFallback: StringFallback = 'not an array'

console.log(numberElementA, numberElementB, stringFallback)

type SecondArg<F> = F extends (a: any, b: infer B) => any ? B : never

type Slice = typeof Array['prototype']['slice']
type SliceSecondArg = SecondArg<Slice> // number | undefined

let sliceSecondArg: SliceSecondArg = 2
sliceSecondArg = undefined

console.log(sliceSecondArg)

function repeat(value: string, count: number) {
  return value.repeat(count)
}

type RepeatSecondArg = SecondArg<typeof repeat> // number

let repeatCount: RepeatSecondArg = 3

console.log(repeat('ha', repeatCount))

// repeatCount = undefined
// Error: Type 'undefined' is not assignable to type 'number'.

// 4. 내장 조건부 타입
type Excluded = Exclude<number | string, string> // number
type Extracted = Extract<number | string, string> // string
type RequiredValue = NonNullable<number | null | undefined> // number

let excluded: Excluded = 10
let extracted: Extracted = 'text'
let requiredValue: RequiredValue = 20

console.log(excluded, extracted, requiredValue)

// let nullableValue: RequiredValue = null
// Error: Type 'null' is not assignable to type 'number'.

function createUser(name: string) {
  return {
    id: name.toLowerCase(),
    name,
  }
}

type CreatedUser = ReturnType<typeof createUser>

let createdUser: CreatedUser = createUser('Ada')

console.log(createdUser.id, createdUser.name)

class Queue {
  private values: string[] = []

  push(value: string) {
    this.values.push(value)
  }

  pop() {
    return this.values.shift()
  }
}

type QueueInstance = InstanceType<typeof Queue>

let queue: QueueInstance = new Queue()

queue.push('first')

console.log(queue.pop())

