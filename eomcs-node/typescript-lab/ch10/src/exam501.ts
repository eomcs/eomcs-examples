/**
 * 네임스페이스: 기본 사용
 *
 * namespace는 TypeScript만의 코드 캡슐화 방법이다.
 * namespace 내부에서는 export한 선언만 바깥에서 접근할 수 있다.
 */

export {}

type GitRepo = {
  name: string
  stars: number
}

namespace Network {
  let defaultHeaders = {
    Accept: 'application/json',
  }

  export function get<T>(url: string): Promise<T> {
    console.info('GET', url, defaultHeaders.Accept)

    return Promise.resolve({
      name: 'typescript',
      stars: 100_000,
    } as T)
  }
}

namespace App {
  export async function main() {
    let repo = await Network.get<GitRepo>(
      'https://api.github.com/repos/Microsoft/typescript'
    )

    console.info(repo.name, repo.stars)
  }
}

void App.main()

// Network.defaultHeaders
// Error: Property 'defaultHeaders' does not exist on type 'typeof Network'.
//
// defaultHeaders는 export하지 않았으므로 Network namespace 밖에서 접근할 수 없다.

