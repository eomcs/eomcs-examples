/**
 * async / await: 더 읽기 쉬운 Promise 문법
 *
 * await는 Promise의 성공 값을 기다리는 문법이고, try/catch는 catch 체인을 대체한다.
 * async 함수는 항상 Promise를 반환한다.
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

async function printUserLocation(id: number): Promise<void> {
  try {
    let user = await getUserID(id)
    let location = await getLocation(user)

    console.info('got location', location.city)
  } catch (error: unknown) {
    if (error instanceof Error) {
      console.error(error.message)
      return
    }

    console.error('unknown error', error)
  } finally {
    console.info('done getting location')
  }
}

void printUserLocation(18)
void printUserLocation(-1)

// await getUserID(18)은 getUserID(18).then(...)과 같은 역할을 한다.
// try/catch는 Promise 체인의 catch와 같은 역할을 한다.
// finally는 Promise 체인의 finally와 마찬가지로 항상 실행된다.

