/**
 * 서브타입과 슈퍼타입 (Subtypes and Supertypes)
 *
 * B가 A의 서브타입이면, A가 필요한 곳에 B를 안전하게 사용할 수 있다.
 * 반대로 A가 B의 슈퍼타입이면, B가 필요한 곳에 A를 안전하게 사용할 수 없다.
 */

// 1. 서브타입은 슈퍼타입이 필요한 곳에 사용할 수 있다.
function printObject(value: object) {
  console.log(value)
}

let numbers = [1, 2, 3]
let tuple: [string, number] = ['age', 36]

printObject(numbers) // Array는 object의 서브타입이다.
printObject(tuple) // Tuple은 Array의 서브타입이고, Array는 object의 서브타입이다.

let arrayFromTuple: Array<string | number> = tuple

console.log(arrayFromTuple) // ['age', 36]

// let tupleFromArray: [string, number] = ['age', 36, 100]
// Error: Source has 3 element(s) but target allows only 2.
//
// Array는 Tuple의 슈퍼타입이다.
// 슈퍼타입은 서브타입이 필요한 곳에 안전하게 사용할 수 없다.

// 2. 구조적 타입에서 더 구체적인 타입은 더 넓은 타입의 서브타입이다.
type BasicUser = {
  name: string
}

type ExistingUser = {
  id: number
  name: string
}

function greetBasicUser(user: BasicUser) {
  console.log(`Hello, ${user.name}`)
}

let existingUser: ExistingUser = {
  id: 123,
  name: 'Ada',
}

greetBasicUser(existingUser) // ExistingUser <: BasicUser

let basicUser: BasicUser = {
  name: 'Grace',
}

// let existingUserRequired: ExistingUser = basicUser
// Error: Property 'id' is missing in type 'BasicUser'.
//
// BasicUser은 ExistingUser의 슈퍼타입이다.
// id가 필요한 곳에 id가 없을 수도 있는 값을 넣을 수 없다.

// 3. 클래스 상속에서도 서브타입 관계가 생긴다.
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

class Crow extends Bird {
  caw() {
    console.log('caw')
  }
}

function feedAnimal(animal: Animal) {
  animal.eat()
}

function makeBirdFly(bird: Bird) {
  bird.fly()
}

let animal = new Animal()
let bird = new Bird()
let crow = new Crow()

feedAnimal(animal) // OK
feedAnimal(bird) // Bird <: Animal
feedAnimal(crow) // Crow <: Bird <: Animal

makeBirdFly(bird) // OK
makeBirdFly(crow) // Crow <: Bird

// makeBirdFly(animal)
// Error: Property 'fly' is missing in type 'Animal'.
//
// Animal은 Bird의 슈퍼타입이다.
// Bird가 필요한 곳에 Animal을 안전하게 사용할 수 없다.

// 4. never는 모든 타입의 서브타입이다.
function fail(message: string): never {
  throw new Error(message)
}

function getNameOrFail(name?: string): string {
  if (name === undefined) {
    return fail('name is required')
  }

  return name
}

let name: string = getNameOrFail('TypeScript')

console.log(name) // TypeScript

// never는 number, string, object 등 어디에나 할당 가능하다.
// let neverAsNumber: number = fail('number is unavailable')
// let neverAsString: string = fail('string is unavailable')
//
// 위 코드는 타입 검사에는 통과하지만, 실행하면 예외가 발생하므로 주석으로 둔다.

// 5. any는 타입 관계를 우회한다.
let anyValue: any = 'not a number'

let numberFromAny: number = anyValue
let stringFromAny: string = anyValue

console.log(numberFromAny) // not a number
console.log(stringFromAny.toUpperCase()) // NOT A NUMBER

// any는 컴파일러의 안전 검사를 통과하지만 실제 값이 기대 타입이라는 뜻은 아니다.
try {
  numberFromAny.toFixed(2)
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 6. 표기법은 설명용이다.
// A <: B  = A는 B의 서브타입이거나 같다.
// A >: B  = A는 B의 슈퍼타입이거나 같다.
//
// 예:
// Crow <: Bird <: Animal
// Animal >: Bird >: Crow
