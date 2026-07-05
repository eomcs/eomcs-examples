/**
 * 선언 병합 (Declaration Merging): 병합 가능 여부
 *
 * TypeScript는 같은 이름의 선언을 상황에 따라 병합한다.
 * 타입 공간과 값 공간이 분리되어 있기 때문에 가능한 조합이 있고,
 * 같은 공간에서 충돌하는 조합은 허용되지 않는다.
 */

export {}

// 1. 값 + 타입 별칭: 컴패니언 객체 패턴
type Currency = {
  unit: 'EUR' | 'GBP' | 'JPY' | 'USD'
  value: number
}

let Currency: {
  DEFAULT: Currency['unit']
  from(value: number, unit?: Currency['unit']): Currency
} = {
  DEFAULT: 'USD' as Currency['unit'],

  from(value: number, unit: Currency['unit'] = 'USD'): Currency {
    return {
      unit,
      value,
    }
  },
}

let amountDue: Currency = Currency.from(100, 'JPY')

console.info(amountDue)

// Currency는 타입 위치에서는 type Currency를 뜻하고,
// 값 위치에서는 let Currency 객체를 뜻한다.

// 2. enum + namespace: enum에 정적 메서드 추가
enum Direction {
  Up = 'UP',
  Down = 'DOWN',
}

namespace Direction {
  export function opposite(direction: Direction): Direction {
    return direction === Direction.Up ? Direction.Down : Direction.Up
  }
}

console.info(Direction.opposite(Direction.Up))

// 3. interface + namespace: 인터페이스와 관련 유틸리티를 같은 이름으로 묶기
interface User {
  id: number
  name: string
}

namespace User {
  export function create(id: number, name: string): User {
    return {
      id,
      name,
    }
  }

  export function displayName(user: User) {
    return `${user.id}: ${user.name}`
  }
}

let user = User.create(1, 'Ada')

console.info(User.displayName(user))

// 4. namespace + namespace: 같은 이름의 namespace는 병합된다.
namespace Network {
  export namespace HTTP {
    export function get(url: string) {
      return `GET ${url}`
    }
  }
}

namespace Network {
  export namespace UDP {
    export function send(url: string) {
      return `SEND ${url}`
    }
  }
}

console.info(Network.HTTP.get('https://example.com'))
console.info(Network.UDP.send('udp://example.com'))

// 5. 같은 공간에서 충돌하는 선언은 허용되지 않는다.
// let duplicated = 1
// let duplicated = 2
// Error: Cannot redeclare block-scoped variable 'duplicated'.

// type ID = string
// type ID = number
// Error: Duplicate identifier 'ID'.

// namespace API {
//   export function request(url: string) {
//     return url
//   }
// }
// namespace API {
//   export function request(url: string) {
//     return url
//   }
// }
// Error: Duplicate function implementation.

// 6. 함수 오버로드 선언은 같은 이름을 여러 번 선언할 수 있는 예외다.
namespace API {
  export function request(url: string): string
  export function request(url: string, priority: number): string
  export function request(url: string, priority?: number) {
    return priority === undefined ? url : `${url}?priority=${priority}`
  }
}

console.info(API.request('https://example.com'))
console.info(API.request('https://example.com', 1))

// 7. 모듈 + 모듈 병합은 서드파티 모듈 선언 확장에 사용한다.
// 예를 들어 Express의 Request 인터페이스에 user 프로퍼티를 추가하는 식이다.
// 이 패턴은 실제 외부 모듈 타입을 확장할 때 declare module 'module-name' 형태로 사용한다.
