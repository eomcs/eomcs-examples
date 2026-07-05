/**
 * async / await: Promise 체이닝 방식
 *
 * async/await가 나오기 전에는 Promise의 then/catch/finally를 체이닝해서
 * 비동기 작업의 순서와 오류 처리를 표현했다.
 */

export {}

type User = {
  id: number
  name: string
  locationId: number
}

type Location = {
  id: number
  city: string
}

function getUserID(id: number): Promise<User> {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (id <= 0) {
        reject(new Error('invalid user id'))
        return
      }

      resolve({
        id,
        name: 'Ada',
        locationId: 100,
      })
    }, 0)
  })
}

function getLocation(user: User): Promise<Location> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        id: user.locationId,
        city: 'London',
      })
    }, 0)
  })
}

function printUserLocation(id: number) {
  getUserID(id)
    .then((user) => getLocation(user))
    .then((location) => console.info('got location', location.city))
    .catch((error: unknown) => {
      if (error instanceof Error) {
        console.error(error.message)
        return
      }

      console.error('unknown error', error)
    })
    .finally(() => console.info('done getting location'))
}

printUserLocation(18)
printUserLocation(-1)

// then은 앞 Promise의 성공 값을 다음 비동기 작업으로 넘긴다.
// catch는 중간에 reject된 Promise를 처리한다.
// finally는 성공/실패 여부와 관계없이 마지막에 실행된다.

