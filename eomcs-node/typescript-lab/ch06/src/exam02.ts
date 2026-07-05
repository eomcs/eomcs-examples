/**
 * 가변성 (Variance): 형태(Shape)와 배열 가변성
 *
 * 객체 타입은 프로퍼티 타입에 대해 공변(covariant)이다.
 * 배열도 요소 타입에 대해 공변으로 취급되지만, mutable 배열에서는 안전하지 않은 상황이 생길 수 있다.
 */

export {}

// 1. 더 구체적인 형태는 더 넓은 형태가 필요한 곳에 사용할 수 있다.
type ExistingUser = {
  id: number
  name: string
}

type NewUser = {
  name: string
}

function printNewUser(user: NewUser) {
  console.log(`new user: ${user.name}`)
}

let existingUser: ExistingUser = {
  id: 123456,
  name: 'Ima User',
}

printNewUser(existingUser) // ExistingUser <: NewUser

// 2. 프로퍼티 타입이 기대 타입의 서브타입이면 할당할 수 있다.
function deleteUser(user: { id?: number; name: string }) {
  delete user.id
}

deleteUser(existingUser) // OK: ExistingUser <: { id?: number; name: string }

console.log(existingUser) // { name: 'Ima User' }
console.log(existingUser.id) // undefined

// TypeScript는 existingUser.id를 여전히 number로 본다.
// 값의 형태가 런타임에 바뀌면 타입 선언과 실제 값이 어긋날 수 있다.
try {
  console.log(existingUser.id.toFixed(0))
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 3. 프로퍼티 타입이 기대 타입의 슈퍼타입이면 안전하지 않으므로 오류가 난다.
type LegacyUser = {
  id?: number | string
  name: string
}

let legacyUser: LegacyUser = {
  id: '793331',
  name: 'Hong Gil-dong',
}

console.log(legacyUser)

// deleteUser(legacyUser)
// Error: Argument of type 'LegacyUser' is not assignable to parameter of type '{ id?: number; name: string; }'.
//
// legacyUser.id는 number일 수도 있지만 string일 수도 있다.
// number | string은 number | undefined의 서브타입이 아니라 슈퍼타입 쪽에 더 가깝다.

// 4. 배열은 요소 타입에 대해 공변으로 취급된다.
let existingUsers: ExistingUser[] = [
  { id: 1, name: 'Ada' },
  { id: 2, name: 'Grace' },
]

let newUsers: NewUser[] = existingUsers // ExistingUser[] <: NewUser[]

newUsers.push({ name: 'No ID Yet' })

console.log(existingUsers)
console.log(existingUsers[2].id) // undefined

// existingUsers의 타입은 ExistingUser[]이므로 모든 요소에 id: number가 있다고 믿는다.
// 하지만 같은 배열을 NewUser[]로 다룬 뒤 id가 없는 값을 넣을 수 있다.
try {
  console.log(existingUsers[2].id.toFixed(0))
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 5. 배열을 바꾸지 않아야 한다면 readonly 배열로 받는다.
function printUserNames(users: readonly NewUser[]) {
  users.forEach((user) => console.log(user.name))

  // users.push({ name: 'Blocked' })
  // Error: Property 'push' does not exist on type 'readonly NewUser[]'.
}

printUserNames(existingUsers)

