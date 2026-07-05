/**
 * 명목적 타입 시뮬레이션 (Simulating Nominal Types)
 *
 * TypeScript는 구조적 타입 시스템이다.
 * 같은 구조를 가진 타입은 이름이 달라도 서로 할당할 수 있다.
 * 타입 브랜딩을 사용하면 구조적 타입 위에 이름 기반 구분을 흉내낼 수 있다.
 */

export {}

// 1. 타입 별칭만으로는 같은 구조의 값을 구분할 수 없다.
type LooseCompanyID = string
type LooseUserID = string

function queryForLooseUser(id: LooseUserID) {
  console.log(`query loose user: ${id}`)
}

let looseCompanyId: LooseCompanyID = 'company-b4843361'

queryForLooseUser(looseCompanyId) // OK: 둘 다 string이라 의도하지 않은 혼용을 막지 못한다.

// 2. 타입 브랜딩으로 이름 기반 구분을 흉내낸다.
type CompanyID = string & { readonly brand: unique symbol }
type OrderID = string & { readonly brand: unique symbol }
type UserID = string & { readonly brand: unique symbol }
type ID = CompanyID | OrderID | UserID

function CompanyID(id: string): CompanyID {
  return id as CompanyID
}

function OrderID(id: string): OrderID {
  return id as OrderID
}

function UserID(id: string): UserID {
  return id as UserID
}

function queryForUser(id: UserID) {
  console.log(`query user: ${id}`)
}

function queryForCompany(id: CompanyID) {
  console.log(`query company: ${id}`)
}

function printId(id: ID) {
  console.log(`id: ${id}`)
}

let companyId = CompanyID('8a6076cf')
let orderId = OrderID('order-100')
let userId = UserID('d21b1dbf')

queryForUser(userId)
queryForCompany(companyId)

printId(companyId)
printId(orderId)
printId(userId)

// queryForUser(companyId)
// Error: Argument of type 'CompanyID' is not assignable to parameter of type 'UserID'.

// queryForCompany(userId)
// Error: Argument of type 'UserID' is not assignable to parameter of type 'CompanyID'.

// let userIdFromString: UserID = 'plain-string'
// Error: Type 'string' is not assignable to type 'UserID'.
//
// 브랜드가 붙은 타입은 생성자 함수를 통해 만들도록 제한할 수 있다.

// 3. 런타임 오버헤드는 없다. 브랜드는 컴파일 타임에만 존재한다.
console.log(typeof companyId)
console.log(companyId.toUpperCase())

// 4. 브랜드 타입은 원래 타입의 기능을 그대로 사용할 수 있다.
function normalizeUserId(id: UserID) {
  return id.trim().toLowerCase()
}

console.log(normalizeUserId(UserID(' USER-123 ')))

// 5. 단언을 직접 쓰면 브랜딩도 우회할 수 있으므로 생성자 함수 안에 감추는 편이 낫다.
let forgedUserId = 'company-b4843361' as UserID

queryForUser(forgedUserId)

