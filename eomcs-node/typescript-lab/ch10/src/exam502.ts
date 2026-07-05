/**
 * 네임스페이스: 중첩 네임스페이스
 *
 * namespace 안에 namespace를 다시 export해서 계층을 만들 수 있다.
 */

export {}

type Dog = {
  name: string
}

type Connection = {
  port: number
  close(): void
}

namespace Network {
  export namespace HTTP {
    export function get<T>(url: string): Promise<T> {
      console.info('HTTP GET', url)

      return Promise.resolve([{ name: 'Tori' }] as T)
    }
  }

  export namespace TCP {
    export function listenOn(port: number): Connection {
      console.info('TCP listen', port)

      return {
        port,
        close() {
          console.info('TCP close', port)
        },
      }
    }
  }

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

let connection = Network.TCP.listenOn(8080)
connection.close()

void Network.UDP.send('udp://url.com/cats', Buffer.alloc(3))

