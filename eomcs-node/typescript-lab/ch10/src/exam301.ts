/**
 * CommonJS / AMD 코드 사용
 *
 * TypeScript는 CommonJS/AMD 스타일 모듈도 ES2015 import 문법으로 가져올 수 있다.
 * Node 내장 모듈은 전통적으로 CommonJS 모듈이므로 좋은 예제가 된다.
 */

import { readFileSync } from 'node:fs'
import * as fs from 'node:fs'
import * as path from 'node:path'

// 1. CommonJS 모듈도 ES2015 named import 스타일로 가져올 수 있다.
let currentFile = readFileSync(__filename, 'utf8')

console.info('current file length:', currentFile.length)

// 2. CommonJS 기본 내보내기는 기본 설정에서 와일드카드 import로 가져오는 방식이 안전하다.
let packageJsonPath = path.join(process.cwd(), 'package.json')
let packageJson = fs.readFileSync(packageJsonPath, 'utf8')

console.info('package.json includes typescript:', packageJson.includes('typescript'))

// 3. esModuleInterop: true 설정을 켜면 CommonJS 모듈을 default import처럼 가져올 수 있다.
// 현재 프로젝트 설정에는 esModuleInterop이 없으므로 아래 코드는 주석으로 둔다.
//
// import fs from 'node:fs'
// fs.readFileSync(packageJsonPath, 'utf8')
//
// tsconfig.json:
// {
//   "compilerOptions": {
//     "esModuleInterop": true
//   }
// }

