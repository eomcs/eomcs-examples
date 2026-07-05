/**
 * 타입 별칭 (Type Aliases)
 *
 * type 키워드를 사용하면 타입에 이름을 붙일 수 있다.
 * 같은 타입을 반복해서 쓰지 않고 이름으로 재사용할 수 있으므로
 * DRY(Don't Repeat Yourself) 원칙을 지키는 데 도움이 된다.
 */

// 1. 원시 타입에 이름 붙이기
type AliasAge = number

let driverAge: AliasAge = 55

console.log(driverAge) // 55

// driverAge = '55'
// Error: Type 'string' is not assignable to type 'number'.

// 2. 객체 타입에 이름 붙이기
type AliasPerson = {
  name: string
  age: AliasAge
}

let driver: AliasPerson = {
  name: 'James May',
  age: 55
}

console.log(`${driver.name}: ${driver.age}`)

// driver = {
//   name: 'Richard Hammond'
// }
// Error: Property 'age' is missing.

// 3. 타입 별칭을 함수 매개변수와 반환 타입에 사용할 수 있다.
function describePerson(person: AliasPerson): string {
  return `${person.name} is ${person.age} years old.`
}

console.log(describePerson(driver))

// describePerson({
//   name: 'Jeremy Clarkson',
//   age: 'old'
// })
// Error: Type 'string' is not assignable to type 'number'.

// 4. 같은 타입을 여러 곳에서 재사용할 수 있다.
type AliasProductId = string

type AliasProduct = {
  id: AliasProductId
  name: string
  price: number
}

type AliasOrder = {
  orderId: string
  productId: AliasProductId
  quantity: number
}

let product: AliasProduct = {
  id: 'book-001',
  name: 'Programming TypeScript',
  price: 32_000
}

let order: AliasOrder = {
  orderId: 'order-001',
  productId: product.id,
  quantity: 2
}

console.log(product)
console.log(order)

// 5. 같은 스코프에서 같은 이름의 타입 별칭을 두 번 선언할 수 없다.
// type AliasAge = string
// Error: Duplicate identifier 'AliasAge'.

// 6. 타입 별칭은 블록 스코프를 따른다.
{
  type AliasAge = string

  let ageText: AliasAge = 'fifty-five'

  console.log(ageText) // fifty-five
}

// 블록 밖의 AliasAge는 여전히 number다.
let anotherAge: AliasAge = 40

console.log(anotherAge) // 40

// 7. 타입 별칭은 더 복잡한 객체 형태에도 이름을 붙일 수 있다.
type AliasAddress = {
  city: string
  zipCode: string
}

type AliasCustomer = {
  name: string
  age: AliasAge
  address: AliasAddress
}

let customer: AliasCustomer = {
  name: 'Ada Lovelace',
  age: 36,
  address: {
    city: 'London',
    zipCode: 'W1'
  }
}

console.log(customer.address.city) // London
