/**
 * 모듈 모드 vs 스크립트 모드 (Module Mode vs Script Mode)
 *
 * TypeScript는 파일에 import나 export가 있으면 모듈 모드로 파싱한다.
 * 모듈 모드에서는 top-level 선언이 파일 내부 스코프에 머무른다.
 *
 * 현재 프로젝트는 module: "nodenext" 설정 때문에 moduleDetection: "force"가 적용되어
 * 모든 파일이 모듈처럼 처리된다.
 */

export {}

let moduleScopedName = 'module scoped value'

function printModuleScopedName() {
  console.info(moduleScopedName)
}

printModuleScopedName()

// 모듈 모드에서는 top-level 변수가 전역 객체에 자동으로 붙지 않는다.
console.info('globalThis.moduleScopedName:', (globalThis as any).moduleScopedName)

// 전역에 값을 두고 싶다면 의도를 명확히 드러내야 한다.
globalThis.explicitGlobalName = 'explicit global value'

console.info('globalThis.explicitGlobalName:', globalThis.explicitGlobalName)

declare global {
  var explicitGlobalName: string
}

// 스크립트 모드 예시:
//
// 아래처럼 import/export가 전혀 없는 파일은 일반적으로 스크립트 모드로 처리된다.
// 스크립트 모드에서는 top-level 선언이 전역 스코프에 놓이므로,
// 다른 스크립트 파일의 같은 이름 선언과 충돌할 수 있다.
//
// let sharedName = 'script scoped?'
//
// 또 UMD 라이브러리처럼 전역 변수로 노출된 값을 직접 사용할 수도 있다.
// 하지만 실제 애플리케이션 코드에서는 의존 관계가 불명확해지므로 권장하지 않는다.

// 모듈 모드 예시:
//
// export {}
// let sharedName = 'file scoped'
//
// export 또는 import가 하나라도 있으면 sharedName은 파일 내부에만 존재한다.
// 그래서 다른 모듈 파일에서 같은 이름을 선언해도 충돌하지 않는다.

