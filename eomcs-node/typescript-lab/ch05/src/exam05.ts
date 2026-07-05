/**
 * 구조적 타이핑과 클래스 (Classes Are Structurally Typed)
 *
 * TypeScript는 클래스도 이름이 아니라 구조(shape)로 비교한다.
 * public 멤버가 같으면 서로 다른 클래스의 인스턴스도 같은 타입처럼 사용할 수 있다.
 */

// 1. 이름이 달라도 public 구조가 같으면 호환된다.
class StructuralZebra {
  trot() {
    console.log('Zebra trots.')
  }
}

class StructuralPoodle {
  trot() {
    console.log('Poodle trots.')
  }
}

function ambleAround(animal: StructuralZebra) {
  animal.trot()
}

ambleAround(new StructuralZebra()) // Zebra trots.
ambleAround(new StructuralPoodle()) // Poodle trots.

let zebraLike: StructuralZebra = new StructuralPoodle()

zebraLike.trot() // Poodle trots.

// 2. 클래스 인스턴스뿐 아니라 같은 shape의 객체 리터럴도 호환된다.
let objectThatTrots: StructuralZebra = {
  trot() {
    console.log('Object trots.')
  },
}

ambleAround(objectThatTrots) // Object trots.

// let invalidZebraLike: StructuralZebra = {
//   run() {
//     console.log('run')
//   },
// }
// Error: Object literal may only specify known properties, and 'run' does not exist in type 'StructuralZebra'.

// 3. 필요한 public 멤버가 더 많으면 호환되지 않는다.
class StructuralHorse {
  trot() {
    console.log('Horse trots.')
  }

  gallop() {
    console.log('Horse gallops.')
  }
}

let horse = new StructuralHorse()
let zebraFromHorse: StructuralZebra = horse

zebraFromHorse.trot() // Horse trots.

// let horseFromZebra: StructuralHorse = new StructuralZebra()
// Error: Property 'gallop' is missing in type 'StructuralZebra'.

// 4. private 필드가 있으면 구조만 같아도 호환되지 않는다.
class PrivateTaggedAnimalA {
  private tag = 'animal'

  trot() {
    console.log(`A ${this.tag} trots.`)
  }
}

class PrivateTaggedAnimalB {
  private tag = 'animal'

  trot() {
    console.log(`B ${this.tag} trots.`)
  }
}

class PrivateTaggedAnimalChild extends PrivateTaggedAnimalA {}

function acceptPrivateTaggedAnimal(animal: PrivateTaggedAnimalA) {
  animal.trot()
}

acceptPrivateTaggedAnimal(new PrivateTaggedAnimalA()) // A animal trots.
acceptPrivateTaggedAnimal(new PrivateTaggedAnimalChild()) // A animal trots.

// acceptPrivateTaggedAnimal(new PrivateTaggedAnimalB())
// Error: Types have separate declarations of a private property 'tag'.

// acceptPrivateTaggedAnimal({
//   tag: 'animal',
//   trot() {
//     console.log('Object trots.')
//   },
// })
// Error: Property 'tag' is private in type 'PrivateTaggedAnimalA' but not in type ...

// 5. protected 필드도 같은 계통에서 온 타입만 호환된다.
class ProtectedTaggedAnimalA {
  protected tag = 'protected animal'

  trot() {
    console.log(`A ${this.tag} trots.`)
  }
}

class ProtectedTaggedAnimalB {
  protected tag = 'protected animal'

  trot() {
    console.log(`B ${this.tag} trots.`)
  }
}

class ProtectedTaggedAnimalChild extends ProtectedTaggedAnimalA {
  showTag() {
    console.log(this.tag)
  }
}

let protectedAnimalA: ProtectedTaggedAnimalA = new ProtectedTaggedAnimalChild()

protectedAnimalA.trot() // A protected animal trots.

// let protectedAnimalB: ProtectedTaggedAnimalA = new ProtectedTaggedAnimalB()
// Error: Property 'tag' is protected but type 'ProtectedTaggedAnimalB' is not a class derived from 'ProtectedTaggedAnimalA'.

// 6. private/protected가 없으면 constructor가 달라도 인스턴스 shape만 같으면 호환된다.
class StructuralUserFromApi {
  constructor(
    public id: string,
    public name: string
  ) {}

  displayName() {
    return this.name
  }
}

class StructuralUserFromCache {
  constructor(
    public id: string,
    public name: string,
    public cachedAt: Date
  ) {}

  displayName() {
    return this.name
  }
}

function printStructuralUser(user: StructuralUserFromApi) {
  console.log(`${user.id}: ${user.displayName()}`)
}

printStructuralUser(new StructuralUserFromApi('u-1', 'Ada')) // u-1: Ada
printStructuralUser(new StructuralUserFromCache('u-2', 'Grace', new Date())) // u-2: Grace

// 7. 구조적 타이핑은 타입 검사 규칙이고, 런타임 instanceof와는 다르다.
let cachedUser = new StructuralUserFromCache('u-3', 'Linus', new Date())
let apiUserLike: StructuralUserFromApi = cachedUser

console.log(apiUserLike.displayName()) // Linus
console.log(apiUserLike instanceof StructuralUserFromApi) // false
console.log(cachedUser instanceof StructuralUserFromCache) // true
