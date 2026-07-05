/**
 * 가변성 (Variance): 함수 가변성
 *
 * 함수 반환 타입은 공변(covariant)이다.
 * 함수 파라미터 타입은 strictFunctionTypes에서 반변(contravariant)이다.
 */

export {}

class Animal {
  eat() {
    console.log('eat')
  }
}

class Bird extends Animal {
  chirp() {
    console.log('chirp')
  }
}

class Crow extends Bird {
  caw() {
    console.log('caw')
  }
}

// Crow <: Bird <: Animal

function clone(f: (bird: Bird) => Bird): void {
  let bird = new Bird()
  let clonedBird = f(bird)

  clonedBird.chirp()
}

// 1. 반환 타입은 공변이다.
// (bird: Bird) => Bird가 필요할 때, 반환 타입은 Bird의 서브타입이어도 된다.
function birdToCrow(bird: Bird): Crow {
  bird.chirp()

  return new Crow()
}

clone(birdToCrow) // OK: Crow <: Bird

// function birdToAnimal(bird: Bird): Animal {
//   bird.chirp()
//
//   return new Animal()
// }
//
// clone(birdToAnimal)
// Error: Type 'Animal' is not assignable to type 'Bird'.
//
// Animal은 Bird의 슈퍼타입이다.
// clone은 반환값에 chirp가 있다고 믿기 때문에 Animal을 반환하면 안전하지 않다.

// 2. 파라미터 타입은 반변이다.
// (bird: Bird) => Bird가 필요할 때, 파라미터 타입은 Bird의 슈퍼타입이어도 된다.
function animalToBird(animal: Animal): Bird {
  animal.eat()

  return new Bird()
}

clone(animalToBird) // OK: Animal >: Bird

// function crowToBird(crow: Crow): Bird {
//   crow.caw()
//
//   return new Bird()
// }
//
// clone(crowToBird)
// Error: Type 'Bird' is not assignable to type 'Crow'.
//
// Crow는 Bird의 서브타입이다.
// clone은 평범한 Bird를 넘길 수 있으므로 Crow만 받을 수 있는 함수는 안전하지 않다.

// 3. 타입 검사를 우회하면 왜 위험한지 런타임에서 확인할 수 있다.
function crowToBirdUnsafely(crow: Crow): Bird {
  crow.caw()

  return new Bird()
}

try {
  clone(crowToBirdUnsafely as (bird: Bird) => Bird) // Type assertion으로 타입 검사를 우회
  // Type Assertion:
  // "내가 책임질 테니까 타입 검사를 하지마!"라고 TypeScript에게 말하는 것과 같다.
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

