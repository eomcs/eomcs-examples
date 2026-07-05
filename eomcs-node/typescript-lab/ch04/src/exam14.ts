/**
 * 제네릭 기본값 (Generic Type Defaults)
 *
 * 제네릭 타입 파라미터에도 기본값을 지정할 수 있다.
 * 기본값이 있는 타입 파라미터는 기본값이 없는 타입 파라미터 뒤에 와야 한다.
 */

// 1. 제네릭 기본값이 있는 타입 별칭
type DefaultElementTarget = {
  kind: string
  id?: string
}

type DefaultButtonTarget = DefaultElementTarget & {
  kind: 'button'
  label: string
}

type AppEvent<
  Type extends string,
  Target extends DefaultElementTarget = DefaultElementTarget
> = {
  target: Target
  type: Type
}

// Type만 명시하면 Target은 기본값인 DefaultElementTarget이 된다.
let focusEvent: AppEvent<'focus'> = {
  target: {
    kind: 'section',
    id: 'intro',
  },
  type: 'focus',
}

// Type과 Target을 모두 명시하면 더 구체적인 target 타입을 사용할 수 있다.
let submitEvent: AppEvent<'submit', DefaultButtonTarget> = {
  target: {
    kind: 'button',
    id: 'save-button',
    label: 'Save',
  },
  type: 'submit',
}

console.log(focusEvent.target.kind) // section
console.log(submitEvent.target.label) // Save

// focusEvent.target.label
// Error: Property 'label' does not exist on type 'DefaultElementTarget'.

// submitEvent.type = 'focus'
// Error: Type '"focus"' is not assignable to type '"submit"'.

// 2. 기본값이 있는 타입 파라미터는 뒤에 와야 한다.
//
// type InvalidGenericDefault<
//   Target extends DefaultElementTarget = DefaultElementTarget,
//   Type extends string
// > = {
//   target: Target
//   type: Type
// }
// Error: Required type parameters may not follow optional type parameters.

// 3. 여러 타입 파라미터에 기본값 지정하기
type Result<
  Data,
  ErrorData = Error,
  Meta extends Record<string, unknown> = Record<string, never>
> = {
  data?: Data
  error?: ErrorData
  meta: Meta
}

let successfulResult: Result<string> = {
  data: 'ok',
  meta: {},
}

let failedResult: Result<never, { code: string; message: string }> = {
  error: {
    code: 'INVALID_INPUT',
    message: 'Name is required',
  },
  meta: {},
}

let pagedResult: Result<string[], Error, { page: number; total: number }> = {
  data: ['a', 'b'],
  meta: {
    page: 1,
    total: 2,
  },
}

console.log(successfulResult.data) // ok
console.log(failedResult.error?.code) // INVALID_INPUT
console.log(pagedResult.meta.total) // 2

// successfulResult.meta.page
// Error: Property 'page' does not exist on type 'Record<string, never>'.

// 4. 함수의 제네릭 타입 파라미터에도 기본값을 둘 수 있다.
function createList<Item = string>(...items: Item[]): Item[] {
  return items
}

let defaultStringList = createList('a', 'b', 'c')
let explicitNumberList = createList<number>(1, 2, 3)

console.log(defaultStringList) // ['a', 'b', 'c']
console.log(explicitNumberList) // [1, 2, 3]

// createList<number>(1, '2', 3)
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 5. 기본값은 추론할 정보가 부족할 때 사용된다.
function createEmptyList<Item = string>(): Item[] {
  return []
}

let emptyStringList = createEmptyList()
let emptyNumberList = createEmptyList<number>()

emptyStringList.push('TypeScript')
emptyNumberList.push(100)

console.log(emptyStringList) // ['TypeScript']
console.log(emptyNumberList) // [100]

// emptyStringList.push(100)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.
