/**
 * 네임스페이스: 파일 분리 및 병합
 *
 * 같은 이름의 namespace는 인터페이스처럼 병합된다.
 * 원래는 여러 파일에 나눠 작성할 수 있지만, 여기서는 실행 가능한 예제를 위해 한 파일에서 보여준다.
 */

export {}

type Dog = {
  name: string
}

namespace Network {
  export namespace HTTP {
    export function get<T>(url: string): Promise<T> {
      console.info('HTTP GET', url)

      return Promise.resolve([{ name: 'Milo' }] as T)
    }
  }
}

namespace Network {
  export namespace UDP {
    export function send(url: string, packets: Buffer): Promise<void> {
      console.info('UDP send', url, packets.length)

      return Promise.resolve()
    }
  }
}

Network.HTTP.get<Dog[]>('http://url.com/dogs').then((dogs) => {
  console.info(dogs[0].name)
})

void Network.UDP.send('udp://url.com/cats', Buffer.alloc(123))

// 현재 프로젝트는 moduleDetection: "force"라 각 파일이 모듈로 처리된다.
// 전역 스크립트 파일 여러 개에서 namespace Network를 선언하면 전역 Network로 병합되지만,
// 모듈 파일에서는 파일 스코프 안에서만 병합된다.

