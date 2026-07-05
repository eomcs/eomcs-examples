/**
 * 타입 주도 개발 (Type-Driven Development)
 *
 * 타입 주도 개발은 타입 시그니처를 먼저 설계하고, 구현을 나중에 채우는 방식이다.
 * 함수가 무엇을 받아 무엇을 반환하는지 먼저 정하면 구현 방향이 선명해진다.
 */

// 1. 타입 시그니처를 먼저 정의한다.
type TypeDrivenMap = {
  <T, U>(array: T[], f: (item: T) => U): U[]
}

// 2. 전체 구조가 타입 수준에서 맞는지 확인한다.
// map은 T[]를 받아 각 T를 U로 바꾸고, 최종적으로 U[]를 반환해야 한다.

// 3. 구현을 채운다.
let typeDrivenMap: TypeDrivenMap = (array, f) => {
  let result = []

  for (let i = 0; i < array.length; i++) {
    result[i] = f(array[i])
  }

  return result
}

let typeDrivenLengths = typeDrivenMap(['a', 'bb', 'ccc'], value => value.length)
let typeDrivenLabels = typeDrivenMap([1, 2, 3], value => `item-${value}`)

console.log(typeDrivenLengths) // [1, 2, 3]
console.log(typeDrivenLabels) // ['item-1', 'item-2', 'item-3']

// let invalidTypeDrivenMap: TypeDrivenMap = (array, f) => {
//   return array
// }
// Error: Type 'T[]' is not assignable to type 'U[]'.

// typeDrivenMap([1, 2, 3], value => value.toUpperCase())
// Error: Property 'toUpperCase' does not exist on type 'number'.

// 4. 타입이 구현의 체크리스트가 된다.
type GroupBy = {
  <Item, Key extends string | number | symbol>(
    items: Item[],
    getKey: (item: Item) => Key
  ): Record<Key, Item[]>
}

let groupBy: GroupBy = (items, getKey) => {
  let groups = {} as Record<ReturnType<typeof getKey>, typeof items>

  for (let item of items) {
    let key = getKey(item)
    let group = groups[key] || []

    group.push(item)
    groups[key] = group
  }

  return groups
}

let usersForGrouping = [
  { name: 'Ada', role: 'admin' },
  { name: 'Grace', role: 'editor' },
  { name: 'Linus', role: 'admin' },
]

let usersByRole = groupBy(usersForGrouping, user => user.role)

console.log(usersByRole.admin.map(user => user.name)) // ['Ada', 'Linus']
console.log(usersByRole.editor.map(user => user.name)) // ['Grace']

// groupBy(usersForGrouping, user => user.age)
// Error: Property 'age' does not exist on type '{ name: string; role: string; }'.

// 5. 타입 시그니처가 도메인 규칙을 표현한다.
type UserRecordForTypeDriven = {
  id: string
  name: string
}

type UserStore = {
  add(user: UserRecordForTypeDriven): void
  findById(id: string): UserRecordForTypeDriven | undefined
  findAll(): UserRecordForTypeDriven[]
}

function createUserStore(): UserStore {
  let users: UserRecordForTypeDriven[] = []

  return {
    add(user) {
      users.push(user)
    },

    findById(id) {
      return users.find(user => user.id === id)
    },

    findAll() {
      return [...users]
    },
  }
}

let userStore = createUserStore()

userStore.add({ id: 'u-1', name: 'Ada' })
userStore.add({ id: 'u-2', name: 'Grace' })

console.log(userStore.findById('u-1')?.name) // Ada
console.log(userStore.findAll().length) // 2

// userStore.add({ id: 'u-3' })
// Error: Property 'name' is missing.

// userStore.findById(1)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 6. 시그니처를 먼저 정하면 구현 실수도 빠르게 잡힌다.
type TypeDrivenPredicate<T> = (item: T) => boolean

function rejectItems<T>(items: T[], predicate: TypeDrivenPredicate<T>): T[] {
  let result = []

  for (let item of items) {
    if (!predicate(item)) {
      result.push(item)
    }
  }

  return result
}

let nonAdminUsers = rejectItems(usersForGrouping, user => user.role === 'admin')

console.log(nonAdminUsers.map(user => user.name)) // ['Grace']

// rejectItems(usersForGrouping, user => user.disabled)
// Error: Property 'disabled' does not exist on type '{ name: string; role: string; }'.
