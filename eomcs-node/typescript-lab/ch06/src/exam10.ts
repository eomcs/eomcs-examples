/**
 * 타입 연산자: 키 인덱싱 연산자 (Keying-in Operator)
 *
 * 객체 타입에 ['key']를 사용하면 중첩 타입에서 특정 프로퍼티 타입을 추출할 수 있다.
 * 배열 타입의 요소 타입은 [number]로 추출하고, 튜플 타입의 요소 타입은 [0], [1]처럼 위치로 추출한다.
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

type User = APIResponse['user']
type FriendList = APIResponse['user']['friendList']
type Friend = FriendList['friends'][number]

let response: APIResponse = {
  user: {
    userId: 'user-1',
    friendList: {
      count: 2,
      friends: [
        { firstName: 'Ada', lastName: 'Lovelace' },
        { firstName: 'Grace', lastName: 'Hopper' },
      ],
    },
  },
}

let user: User = response.user
let friendList: FriendList = response.user.friendList
let firstFriend: Friend = friendList.friends[0]

console.log(user.userId)
console.log(friendList.count)
console.log(`${firstFriend.firstName} ${firstFriend.lastName}`)

function printFriend(friend: Friend) {
  console.log(`${friend.lastName}, ${friend.firstName}`)
}

printFriend({
  firstName: 'Barbara',
  lastName: 'Liskov',
})

// printFriend({
//   firstName: 'Alan',
// })
// Error: Property 'lastName' is missing in type '{ firstName: string; }'.
//
// Friend는 APIResponse의 중첩 타입에서 추출했기 때문에
// 원본 응답 타입과 같은 형태를 유지한다.

// 1. 배열의 요소 타입은 [number]로 추출한다.
type FriendArray = APIResponse['user']['friendList']['friends']
type FriendFromArray = FriendArray[number]

let anotherFriend: FriendFromArray = {
  firstName: 'Donald',
  lastName: 'Knuth',
}

console.log(`${anotherFriend.firstName} ${anotherFriend.lastName}`)

// 2. 튜플의 요소 타입은 숫자 리터럴 인덱스로 추출한다.
type Coordinate = [number, number, 'px' | '%']

type X = Coordinate[0]
type Y = Coordinate[1]
type CoordinateUnit = Coordinate[2]
type CoordinateElement = Coordinate[number]

let x: X = 10
let y: Y = 20
let unit: CoordinateUnit = 'px'

console.log(x, y, unit)

let coordinateElement: CoordinateElement = '%'

console.log(coordinateElement)

// unit = 'em'
// Error: Type '"em"' is not assignable to type '"px" | "%"'.

// 3. 존재하지 않는 키로는 타입을 추출할 수 없다.
// type Missing = APIResponse['account']
// Error: Property 'account' does not exist on type 'APIResponse'.

