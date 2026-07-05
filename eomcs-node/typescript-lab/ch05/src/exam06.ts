/**
 * 클래스는 값과 타입 동시에 선언 (Classes Declare Both Values and Types)
 *
 * TypeScript의 이름은 값 네임스페이스와 타입 네임스페이스에 따로 존재한다.
 * 클래스와 enum은 두 네임스페이스에 동시에 이름을 등록한다.
 */

// 1. 값과 타입은 별도 네임스페이스에 존재한다.
let namespaceExample = 1999

type namespaceExample = number

function callableNamespaceExample() {
  console.log('value namespace')
}

interface callableNamespaceExample {
  (): void
}

// 값 위치에서는 namespaceExample이 let 변수로 해석된다.
if (namespaceExample + 1 > 3) {
  console.log(namespaceExample) // 1999
}

// 타입 위치에서는 namespaceExample이 type 별칭으로 해석된다.
let namespaceTypedValue: namespaceExample = 3

console.log(namespaceTypedValue) // 3

// 값 위치에서는 callableNamespaceExample이 함수로 해석된다.
callableNamespaceExample() // value namespace

// 타입 위치에서는 callableNamespaceExample이 호출 시그니처 인터페이스로 해석된다.
let callableValue: callableNamespaceExample = () => {
  console.log('typed callable value')
}

callableValue() // typed callable value

// 2. 클래스 이름은 타입 네임스페이스와 값 네임스페이스에 동시에 등록된다.
class DualNamespaceCustomer {
  constructor(
    public id: string,
    public name: string
  ) {}

  displayName() {
    return `${this.name} (${this.id})`
  }
}

// 타입 위치: DualNamespaceCustomer는 인스턴스 타입이다.
let customer: DualNamespaceCustomer = new DualNamespaceCustomer('c-1', 'Ada')

// 값 위치: DualNamespaceCustomer는 new로 호출할 수 있는 클래스 값이다.
let anotherCustomer = new DualNamespaceCustomer('c-2', 'Grace')

console.log(customer.displayName()) // Ada (c-1)
console.log(anotherCustomer instanceof DualNamespaceCustomer) // true

// let invalidCustomer: DualNamespaceCustomer = {
//   id: 'c-3',
//   name: 'Linus',
// }
// Error: Property 'displayName' is missing.

// 3. 클래스 자체의 타입은 typeof로 참조한다.
type DualNamespaceCustomerConstructor = typeof DualNamespaceCustomer

let CustomerClassValue: DualNamespaceCustomerConstructor = DualNamespaceCustomer
let customerFromClassValue = new CustomerClassValue('c-3', 'Matz')

console.log(customerFromClassValue.displayName()) // Matz (c-3)

// let invalidCustomerClassValue: DualNamespaceCustomerConstructor = customer
// Error: Property 'prototype' is missing.

// 4. StringDatabase 클래스 선언이 만드는 인스턴스 타입
type StringDatabaseState = {
  [key: string]: string
}

class ValueTypeStringDatabase {
  state: StringDatabaseState = {}

  get(key: string): string | null {
    return this.state[key] ?? null
  }

  set(key: string, value: string): void {
    this.state[key] = value
  }

  static from(state: StringDatabaseState): ValueTypeStringDatabase {
    let database = new ValueTypeStringDatabase()
    database.state = { ...state }

    return database
  }
}

let stringDatabase: ValueTypeStringDatabase = new ValueTypeStringDatabase()

stringDatabase.set('language', 'TypeScript')

console.log(stringDatabase.get('language')) // TypeScript
console.log(stringDatabase.get('missing')) // null

// ValueTypeStringDatabase를 타입 위치에서 쓰면 아래 인스턴스 shape를 뜻한다.
interface ValueTypeStringDatabaseInstance {
  state: StringDatabaseState
  get(key: string): string | null
  set(key: string, value: string): void
}

let databaseInstanceShape: ValueTypeStringDatabaseInstance = stringDatabase

console.log(databaseInstanceShape.get('language')) // TypeScript

// databaseInstanceShape.from({ language: 'TypeScript' })
// Error: Property 'from' does not exist on type 'ValueTypeStringDatabaseInstance'.

// 5. typeof ClassName은 생성자 타입과 static 멤버를 포함한다.
type ValueTypeStringDatabaseConstructor = typeof ValueTypeStringDatabase

let StringDatabaseClassValue: ValueTypeStringDatabaseConstructor =
  ValueTypeStringDatabase

let databaseFromStaticFactory = StringDatabaseClassValue.from({
  framework: 'TypeScript',
})

console.log(databaseFromStaticFactory.get('framework')) // TypeScript

// typeof ValueTypeStringDatabase는 아래 생성자 shape와 비슷하다.
interface ValueTypeStringDatabaseConstructorShape {
  new(): ValueTypeStringDatabase
  from(state: StringDatabaseState): ValueTypeStringDatabase
}

function createStringDatabase(
  DatabaseClass: ValueTypeStringDatabaseConstructorShape,
  state: StringDatabaseState
) {
  let database = new DatabaseClass()

  for (let key in state) {
    database.set(key, state[key])
  }

  return database
}

let databaseFromConstructorSignature = createStringDatabase(
  ValueTypeStringDatabase,
  { runtime: 'Node.js' }
)

console.log(databaseFromConstructorSignature.get('runtime')) // Node.js

// createStringDatabase(stringDatabase, { runtime: 'Node.js' })
// Error: ValueTypeStringDatabase 인스턴스는 new()로 호출할 수 있는 생성자 타입이 아니다.

// 6. new() 생성자 시그니처는 new 연산자로 인스턴스를 만들 수 있다는 의미다.
type NoArgumentConstructor<T> = {
  new(): T
}

function createNoArgumentInstance<T>(Constructor: NoArgumentConstructor<T>): T {
  return new Constructor()
}

let createdDatabase = createNoArgumentInstance(ValueTypeStringDatabase)

createdDatabase.set('created', 'yes')

console.log(createdDatabase.get('created')) // yes

// createNoArgumentInstance(DualNamespaceCustomer)
// Error: DualNamespaceCustomer 생성자는 id와 name 인수가 필요하다.

// 7. 생성자 인수가 있는 클래스는 그에 맞는 생성자 시그니처를 사용한다.
type CustomerConstructorWithArguments = {
  new(id: string, name: string): DualNamespaceCustomer
}

function createCustomer(
  CustomerConstructor: CustomerConstructorWithArguments,
  id: string,
  name: string
) {
  return new CustomerConstructor(id, name)
}

let constructedCustomer = createCustomer(
  DualNamespaceCustomer,
  'c-4',
  'Brendan'
)

console.log(constructedCustomer.displayName()) // Brendan (c-4)
