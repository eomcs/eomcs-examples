/**
 * 인터페이스 (Interfaces)
 *
 * interface는 객체의 shape를 표현하는 타입 선언이다.
 * type 별칭과 비슷하지만 상속, 선언 병합 같은 고유한 특징이 있다.
 */

// 1. type 별칭 방식
type SushiTypeAlias = {
  calories: number
  salty: boolean
  tasty: boolean
}

let sushiByType: SushiTypeAlias = {
  calories: 200,
  salty: true,
  tasty: true,
}

console.log(sushiByType.tasty) // true

// 2. interface 방식
interface SushiInterface {
  calories: number
  salty: boolean
  tasty: boolean
}

let sushiByInterface: SushiInterface = {
  calories: 180,
  salty: true,
  tasty: true,
}

console.log(sushiByInterface.calories) // 180

// let invalidSushi: SushiInterface = {
//   calories: 180,
//   salty: true,
// }
// Error: Property 'tasty' is missing.

// 3. 인터페이스 상속
interface InterfaceFood {
  calories: number
  tasty: boolean
}

interface InterfaceSushi extends InterfaceFood {
  salty: boolean
}

interface InterfaceCake extends InterfaceFood {
  sweet: boolean
}

let salmonRoll: InterfaceSushi = {
  calories: 250,
  tasty: true,
  salty: true,
}

let chocolateCake: InterfaceCake = {
  calories: 450,
  tasty: true,
  sweet: true,
}

console.log(salmonRoll.salty) // true
console.log(chocolateCake.sweet) // true

// let badCake: InterfaceCake = {
//   calories: 450,
//   tasty: true,
//   salty: true,
// }
// Error: Object literal may only specify known properties, and 'salty' does not exist in type 'InterfaceCake'.

// 4. interface는 객체 타입과 클래스도 extends할 수 있다.
type NamedShape = {
  name: string
}

interface PricedFood extends NamedShape {
  price: number
}

let pricedCake: PricedFood = {
  name: 'Chocolate cake',
  price: 7000,
}

console.log(pricedCake.name) // Chocolate cake

class InterfaceRecipeBase {
  constructor(public title: string) {}

  describe() {
    return `Recipe: ${this.title}`
  }
}

interface DetailedRecipe extends InterfaceRecipeBase {
  ingredients: string[]
}

let pancakeRecipe: DetailedRecipe = {
  title: 'Pancake',
  ingredients: ['flour', 'milk', 'egg'],
  describe() {
    return `Recipe: ${this.title}`
  },
}

console.log(pancakeRecipe.describe()) // Recipe: Pancake

// 5. type만 가능한 표현
type IdValue = number | string
type NullableIdValue = IdValue | null

let idValue: NullableIdValue = 'u-123'

console.log(idValue) // u-123

// interface NullableIdValueInterface = number | string
// Error: interface는 객체 shape를 선언하는 문법이므로 union 타입 표현식 자체를 담을 수 없다.

// 6. interface extends는 멤버 호환성을 엄격하게 검사한다.
interface InterfaceBadBase {
  bad(x: number): string
}

// interface InterfaceBadChild extends InterfaceBadBase {
//   bad(x: string): string
// }
// Error: Interface 'InterfaceBadChild' incorrectly extends interface 'InterfaceBadBase'.

// type 교차 타입은 shape를 기계적으로 합성하므로 충돌이 더 늦게 드러날 수 있다.
type BadBaseAlias = {
  bad(x: number): string
}

type BadChildAlias = BadBaseAlias & {
  bad(x: string): string
}

let badChildAlias: BadChildAlias = {
  bad(x: number | string) {
    return String(x)
  },
}

console.log(badChildAlias.bad(100)) // 100
console.log(badChildAlias.bad('oops')) // oops

// 7. type 조합 vs interface 상속 vs class 상속
// 타입 조합은 여러 타입을 합쳐 새로운 타입을 만든다. 런타임 코드는 생성하지 않는다.
type CompositionPerson = {
  name: string
}

type CompositionEmployee = {
  employeeId: number
}

type CompositionDeveloper = CompositionPerson & CompositionEmployee & {
  language: string
}

let typeComposedDeveloper: CompositionDeveloper = {
  name: 'Ada',
  employeeId: 101,
  language: 'TypeScript',
}

console.log(typeComposedDeveloper.name) // Ada
console.log(typeComposedDeveloper.language) // TypeScript

// let invalidTypeComposedDeveloper: CompositionDeveloper = {
//   name: 'Ada',
//   employeeId: 101,
// }
// Error: Property 'language' is missing.

// 클래스 상속은 타입뿐 아니라 구현도 상속한다. 런타임에도 클래스가 존재한다.
class ClassInheritancePerson {
  constructor(public name: string) {}

  introduce() {
    return `My name is ${this.name}.`
  }
}

class ClassInheritanceEmployee extends ClassInheritancePerson {
  constructor(
    name: string,
    public employeeId: number
  ) {
    super(name)
  }

  badge() {
    return `#${this.employeeId}`
  }
}

let classInheritedEmployee = new ClassInheritanceEmployee('Grace', 202)

console.log(classInheritedEmployee.introduce()) // My name is Grace.
console.log(classInheritedEmployee.badge()) // #202
console.log(classInheritedEmployee instanceof ClassInheritancePerson) // true
console.log(classInheritedEmployee instanceof ClassInheritanceEmployee) // true

// 인터페이스 상속은 타입만 상속한다. 구현은 없고 런타임에는 사라진다.
interface InterfaceInheritancePerson {
  name: string
}

interface InterfaceInheritanceEmployee extends InterfaceInheritancePerson {
  employeeId: number
}

interface InterfaceInheritanceDeveloper extends InterfaceInheritanceEmployee {
  language: string
}

let interfaceInheritedDeveloper: InterfaceInheritanceDeveloper = {
  name: 'Linus',
  employeeId: 303,
  language: 'C',
}

console.log(interfaceInheritedDeveloper.name) // Linus
console.log(interfaceInheritedDeveloper.employeeId) // 303
console.log(interfaceInheritedDeveloper.language) // C

function printEmployeeBadge(employee: InterfaceInheritanceEmployee) {
  console.log(`${employee.name}: #${employee.employeeId}`)
}

printEmployeeBadge(interfaceInheritedDeveloper) // Linus: #303
printEmployeeBadge(classInheritedEmployee) // Grace: #202

// interface InterfaceInheritanceBrokenEmployee extends InterfaceInheritancePerson {
//   name: number
// }
// Error: Interface 'InterfaceInheritanceBrokenEmployee' incorrectly extends interface 'InterfaceInheritancePerson'.

// 타입 조합은 매우 유연하지만 충돌하는 프로퍼티를 조합하면 사용하기 어려운 never 타입이 될 수 있다.
type ConflictingName = { name: string } & { name: number }

// let impossibleName: ConflictingName = {
//   name: 'Ada',
// }
// Error: Type 'string' is not assignable to type 'never'.

// ConflictingName 타입은 컴파일 후 런타임에 남지 않는다.
// ClassInheritanceEmployee 클래스는 JavaScript 클래스 코드로 남는다.

// 8. 선언 병합
// 같은 이름의 인터페이스를 여러 번 선언하면 하나로 병합된다.
interface InterfaceUser {
  name: string
}

interface InterfaceUser {
  age: number
}

let mergedUser: InterfaceUser = {
  name: 'Ashley',
  age: 30,
}

console.log(mergedUser.name) // Ashley
console.log(mergedUser.age) // 30

// interface InterfaceUser {
//   age: string
// }
// Error: Subsequent property declarations must have the same type.

// 9. type 별칭은 동일 이름으로 중복 선언할 수 없다.
type SingleDeclarationOnly = {
  name: string
}

// type SingleDeclarationOnly = {
//   age: number
// }
// Error: Duplicate identifier 'SingleDeclarationOnly'.

let singleDeclarationValue: SingleDeclarationOnly = {
  name: 'Only once',
}

console.log(singleDeclarationValue.name) // Only once
