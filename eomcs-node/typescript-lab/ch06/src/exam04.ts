/**
 * 할당 가능성 (Assignability)
 *
 * 타입 A를 타입 B가 필요한 곳에 사용할 수 있는지 판단하는 규칙이다.
 *
 * 일반 타입:
 * - A <: B 이거나
 * - A가 any이면 할당할 수 있다.
 *
 * enum 타입:
 * - A가 enum B의 멤버이거나
 * - B에 숫자 멤버가 하나 이상 있고 A가 number이면 할당할 수 있다.
 */

export {}

// 1. 일반 타입은 서브타입이면 할당할 수 있다.
type UserName = {
  name: string
}

type UserWithId = {
  id: number
  name: string
}

let userWithId: UserWithId = {
  id: 1,
  name: 'Ada',
}

let userName: UserName = userWithId // OK: UserWithId <: UserName

console.log(userName.name)

// let userWithIdAgain: UserWithId = userName
// Error: Property 'id' is missing in type 'UserName'.
//
// UserName은 UserWithId의 슈퍼타입이다.
// id가 필요한 곳에 id가 없는 값을 할당할 수 없다.

// 2. 클래스 상속 관계에서도 서브타입이면 할당할 수 있다.
class Animal {
  eat() {
    console.log('eat')
  }
}

class Bird extends Animal {
  fly() {
    console.log('fly')
  }
}

let bird: Bird = new Bird()
let animal: Animal = bird // OK: Bird <: Animal

animal.eat()

// let birdAgain: Bird = animal
// Error: Property 'fly' is missing in type 'Animal'.
//
// Animal은 Bird의 슈퍼타입이다.
// Bird가 필요한 곳에 Animal을 할당할 수 없다.

// 3. any는 할당 가능성 검사를 우회한다.
let anyValue: any = 'not a bird'

let birdFromAny: Bird = anyValue // OK: any는 Bird에 할당할 수 있다.

try {
  birdFromAny.fly()
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 4. enum 멤버는 그 enum 타입에 할당할 수 있다.
enum Status {
  Ready,
  Done,
}

let status: Status = Status.Ready

console.log(status) // 0

// 5. 숫자 멤버가 있는 enum에는 number도 할당할 수 있다.
let statusCode: number = 100
let numericStatus: Status = statusCode

console.log(numericStatus) // 100

// let literalStatus: Status = 100
// Error: Type '100' is not assignable to type 'Status'.
//
// 현재 TypeScript는 임의의 숫자 리터럴을 enum에 바로 할당하는 것은 제한한다.
// number 타입으로 추론된 값은 숫자 enum에 할당할 수 있다.

// 6. 문자열 enum에는 임의의 string을 할당할 수 없다.
enum Direction {
  Up = 'UP',
  Down = 'DOWN',
}

let direction: Direction = Direction.Up

console.log(direction) // UP

// let directionString: string = 'UP'
// let directionFromString: Direction = directionString
// Error: Type 'string' is not assignable to type 'Direction'.

// let directionFromString: Direction = 'UP'
// Error: Type '"UP"' is not assignable to type 'Direction'.
//
// 'UP'이라는 값이 같아 보여도 Direction.Up enum 멤버 자체는 아니다.
