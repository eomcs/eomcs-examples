/**
 * 유니온 타입 (Union Types)과 인터섹션 타입 (Intersection Types)
 *
 * 유니온 타입 A | B는 A 또는 B를 의미한다.
 * 인터섹션 타입 A & B는 A이면서 동시에 B를 의미한다.
 */

// 1. 유니온 타입: Cat 또는 Dog
type UnionCat = {
  name: string
  purrs: boolean
}

type UnionDog = {
  name: string
  barks: boolean
  wags: boolean
}

type UnionCatOrDog = UnionCat | UnionDog

let unionCat: UnionCatOrDog = {
  name: 'Bonkers',
  purrs: true
}

let unionDog: UnionCatOrDog = {
  name: 'Domino',
  barks: true,
  wags: true
}

let unionCatDog: UnionCatOrDog = {
  name: 'CatDog',
  purrs: true,
  barks: true,
  wags: true
}

console.log(unionCat.name) // Bonkers
console.log(unionDog.name) // Domino
console.log(unionCatDog.name) // CatDog

// 유니온 타입에서는 모든 멤버가 공통으로 가진 프로퍼티만 바로 접근할 수 있다.
function describeUnionAnimal(animal: UnionCatOrDog) {
  console.log(animal.name)

  // console.log(animal.purrs)
  // Error: Property 'purrs' does not exist on type 'UnionCatOrDog'.
  //
  // console.log(animal.barks)
  // Error: Property 'barks' does not exist on type 'UnionCatOrDog'.

  if ('purrs' in animal) {
    console.log(`${animal.name} purrs: ${animal.purrs}`)
  }

  if ('barks' in animal) {
    console.log(`${animal.name} barks: ${animal.barks}`)
    console.log(`${animal.name} wags: ${animal.wags}`)
  }
}

describeUnionAnimal(unionCat)
describeUnionAnimal(unionDog)
describeUnionAnimal(unionCatDog)

// 2. 유니온 타입은 반환 타입에서도 자주 사용한다.
function trueOrNull(isTrue: boolean): string | null {
  if (isTrue) return 'true'
  return null
}

let unionResult = trueOrNull(Math.random() > 0.5)

// console.log(unionResult.toUpperCase())
// Error: 'unionResult' is possibly 'null'.

if (unionResult !== null) {
  console.log(unionResult.toUpperCase())
} else {
  console.log('null result')
}

// 3. 리터럴 유니온 타입은 정해진 값 중 하나만 허용한다.
type UnionStatus = 'loading' | 'success' | 'error'

let unionStatus: UnionStatus = 'loading'

unionStatus = 'success'

console.log(unionStatus) // success

// unionStatus = 'done'
// Error: Type '"done"' is not assignable to type 'UnionStatus'.

// 4. 인터섹션 타입: Cat이면서 Dog
type IntersectionCatAndDog = UnionCat & UnionDog

let intersectionAnimal: IntersectionCatAndDog = {
  name: 'BonkersAndDomino',
  barks: true,
  purrs: true,
  wags: true
}

console.log(intersectionAnimal.name) // Domino
console.log(intersectionAnimal.purrs) // true
console.log(intersectionAnimal.barks) // true
console.log(intersectionAnimal.wags) // true

// let incompleteIntersectionAnimal: IntersectionCatAndDog = {
//   name: 'Almost',
//   purrs: true
// }
// Error: Type is missing the following properties: barks, wags.

// 5. 인터섹션 타입은 여러 객체 타입을 합쳐 더 구체적인 타입을 만들 때 유용하다.
type IntersectionUser = {
  id: string
  name: string
}

type IntersectionAdmin = {
  role: 'admin'
  permissions: string[]
}

type IntersectionAdminUser = IntersectionUser & IntersectionAdmin

let adminUser: IntersectionAdminUser = {
  id: 'user-001',
  name: 'Ada',
  role: 'admin',
  permissions: ['read', 'write']
}

console.log(adminUser.name) // Ada
console.log(adminUser.permissions.join(', ')) // read, write

// 6. 유니온과 인터섹션의 차이
function printUnion(value: UnionCat | UnionDog) {
  console.log(value.name)
}

function printIntersection(value: UnionCat & UnionDog) {
  console.log(value.name)
  console.log(value.purrs)
  console.log(value.barks)
  console.log(value.wags)
}

printUnion(unionCat)
printUnion(unionDog)
printIntersection(intersectionAnimal)
