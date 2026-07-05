/**
 * 네임스페이스: 충돌 (Collisions)
 *
 * 같은 namespace가 병합되더라도 같은 이름의 구현을 두 번 내보낼 수는 없다.
 * 단, 구현 없는 ambient overload 선언은 병합될 수 있다.
 */

export {}

namespace Network {
  export function request<T>(url: string): T
  export function request<T>(url: string, priority: number): T
  export function request<T>(url: string, algorithm: 'SHA1' | 'SHA256'): T
  export function request<T>(
    url: string,
    option?: number | 'SHA1' | 'SHA256'
  ): T {
    console.info('request', url, option ?? 'normal')

    return { ok: true } as T
  }
}

type Response = {
  ok: boolean
}

let normal = Network.request<Response>('https://example.com')
let urgent = Network.request<Response>('https://example.com', 1)
let signed = Network.request<Response>('https://example.com', 'SHA256')

console.info(normal.ok, urgent.ok, signed.ok)

// namespace Network {
//   export function request<T>(url: string): T {
//     return {} as T
//   }
// }
// namespace Network {
//   export function request<T>(url: string): T {
//     return {} as T
//   }
// }
// Error: Duplicate function implementation.
//
// 같은 이름의 함수 구현은 충돌한다.

