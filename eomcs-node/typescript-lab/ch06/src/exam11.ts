/**
 * 타입 연산자: keyof 연산자
 *
 * keyof는 객체 타입의 모든 키를 string 리터럴 유니온으로 반환한다.
 * keyof와 키 인덱싱 연산자를 조합하면 타입 안전한 getter를 만들 수 있다.
 */

export {}

type APIResponse = {
  user: {
    userId: string
    friendList: {
      count: number
      friends: { firstName: string; lastName: string }[]
    }
  }
}

type ResponseKeys = keyof APIResponse // 'user'
type UserKeys = keyof APIResponse['user'] // 'userId' | 'friendList'
type FriendListKeys = keyof APIResponse['user']['friendList'] // 'count' | 'friends'

let responseKey: ResponseKeys = 'user'
let userKey: UserKeys = 'friendList'
let friendListKey: FriendListKeys = 'friends'

console.log(responseKey, userKey, friendListKey)

// responseKey = 'account'
// Error: Type '"account"' is not assignable to type '"user"'.

// userKey = 'name'
// Error: Type '"name"' is not assignable to type '"userId" | "friendList"'.

function get<O extends object, K extends keyof O>(object: O, key: K): O[K] {
  return object[key]
}

type ActivityLog = {
  lastEvent: Date
  events: { id: string; timestamp: Date; type: 'Read' | 'Write' }[]
}

let activityLog: ActivityLog = {
  lastEvent: new Date('2026-06-30T12:00:00.000Z'),
  events: [
    {
      id: 'event-1',
      timestamp: new Date('2026-06-30T11:50:00.000Z'),
      type: 'Read',
    },
    {
      id: 'event-2',
      timestamp: new Date('2026-06-30T11:55:00.000Z'),
      type: 'Write',
    },
  ],
}

let lastEvent = get(activityLog, 'lastEvent') // Date
let events = get(activityLog, 'events') // { id: string; timestamp: Date; type: 'Read' | 'Write' }[]

console.log(lastEvent.toISOString())
console.log(events[0].type)

// get(activityLog, 'missing')
// Error: Argument of type '"missing"' is not assignable to parameter of type 'keyof ActivityLog'.

// 1. 반환 타입은 key에 따라 정확히 달라진다.
function printActivityValue<K extends keyof ActivityLog>(key: K) {
  let value = get(activityLog, key)

  console.log(value)

  return value
}

let eventDate = printActivityValue('lastEvent')
let eventList = printActivityValue('events')

console.log(eventDate.getFullYear())
console.log(eventList.length)

// 2. keyof는 선택적 프로퍼티도 키 유니온에 포함한다.
type Options = {
  baseURL: string
  cacheSize?: number
  tier?: 'prod' | 'dev'
}

type OptionKeys = keyof Options // 'baseURL' | 'cacheSize' | 'tier'

let optionKey: OptionKeys = 'cacheSize'

console.log(optionKey)

