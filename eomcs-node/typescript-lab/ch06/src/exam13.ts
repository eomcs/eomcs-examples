/**
 * 매핑 타입 (Mapped Types)
 *
 * 매핑 타입은 객체의 키를 순회하며 새로운 객체 타입을 만든다.
 * keyof와 함께 쓰면 기존 타입의 키와 값 타입을 보존하면서 modifier를 더하거나 뺄 수 있다.
 */

export {}

type Weekday = 'Mon' | 'Tue' | 'Wed' | 'Thu' | 'Fri'
type Day = Weekday | 'Sat' | 'Sun'

// 1. 유니온 키를 순회해 새 객체 타입을 만든다.
type NextDay = {
  [K in Weekday]: Day
}

let nextDay: NextDay = {
  Mon: 'Tue',
  Tue: 'Wed',
  Wed: 'Thu',
  Thu: 'Fri',
  Fri: 'Sat',
}

console.log(nextDay.Mon)
console.log(nextDay.Fri)

// let incompleteNextDay: NextDay = {
//   Mon: 'Tue',
// }
// Error: Type '{ Mon: "Tue"; }' is missing the following properties:
// Tue, Wed, Thu, Fri

// 2. keyof와 키 인덱싱을 조합하면 기존 객체 타입을 변형할 수 있다.
type Account = {
  id: number
  isEmployee: boolean
  notes: string[]
}

type OptionalAccount = {
  [K in keyof Account]?: Account[K]
}

type NullableAccount = {
  [K in keyof Account]: Account[K] | null
}

type ReadonlyAccount = {
  readonly [K in keyof Account]: Account[K]
}

let optionalAccount: OptionalAccount = {
  id: 1,
}

let nullableAccount: NullableAccount = {
  id: null,
  isEmployee: true,
  notes: null,
}

let readonlyAccount: ReadonlyAccount = {
  id: 2,
  isEmployee: false,
  notes: ['created from readonly mapped type'],
}

console.log(optionalAccount)
console.log(nullableAccount)
console.log(readonlyAccount.notes[0])

// readonlyAccount.id = 3
// Error: Cannot assign to 'id' because it is a read-only property.

// 3. -readonly와 -?로 modifier를 제거할 수 있다.
type MutableAccount = {
  -readonly [K in keyof ReadonlyAccount]: ReadonlyAccount[K]
}

type RequiredAccount = {
  [K in keyof OptionalAccount]-?: Account[K]
}

let mutableAccount: MutableAccount = {
  id: 3,
  isEmployee: true,
  notes: ['editable'],
}

mutableAccount.id = 4
mutableAccount.notes.push('changed')

console.log(mutableAccount)

let requiredAccount: RequiredAccount = {
  id: 5,
  isEmployee: false,
  notes: [],
}

console.log(requiredAccount)

// let missingRequiredAccount: RequiredAccount = {
//   id: 6,
//   isEmployee: true,
// }
// Error: Property 'notes' is missing.

// 4. 내장 매핑 타입도 같은 원리로 동작한다.
let partialAccount: Partial<Account> = {
  notes: ['only notes'],
}

let requiredFromPartial: Required<OptionalAccount> = {
  id: 7,
  isEmployee: true,
  notes: ['required now'],
}

let builtinReadonlyAccount: Readonly<Account> = {
  id: 8,
  isEmployee: false,
  notes: ['readonly account'],
}

let accountPreview: Pick<Account, 'id' | 'notes'> = {
  id: 9,
  notes: ['picked fields'],
}

let weekdaySchedule: Record<Weekday, string> = {
  Mon: 'Plan',
  Tue: 'Build',
  Wed: 'Review',
  Thu: 'Refine',
  Fri: 'Ship',
}

console.log(partialAccount)
console.log(requiredFromPartial)
console.log(builtinReadonlyAccount)
console.log(accountPreview)
console.log(weekdaySchedule.Wed)

// builtinReadonlyAccount.isEmployee = true
// Error: Cannot assign to 'isEmployee' because it is a read-only property.

// let invalidPreview: Pick<Account, 'id' | 'name'> = {
//   id: 10,
//   name: 'Ada',
// }
// Error: Type '"name"' does not satisfy the constraint 'keyof Account'.

