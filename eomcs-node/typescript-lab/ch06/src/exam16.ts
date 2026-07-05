/**
 * 사용자 정의 타입 가드 (User-Defined Type Guards)
 *
 * 함수로 분리한 타입 검사는 boolean만 반환하면 호출한 곳의 타입을 정제하지 못한다.
 * 반환 타입에 `value is Type`을 쓰면 TypeScript가 그 함수를 타입 가드로 사용한다.
 */

export {}

// 1. boolean을 반환하는 함수는 타입 정제 정보를 전달하지 못한다.
function isStringBoolean(value: unknown): boolean {
  return typeof value === 'string'
}

function parseInputWithoutGuard(input: string | number) {
  if (isStringBoolean(input)) {
    console.log(input)

    // input.toUpperCase()
    // Error: Property 'toUpperCase' does not exist on type 'string | number'.
    //
    // isStringBoolean의 구현은 string을 검사하지만,
    // 반환 타입이 boolean이라 TypeScript는 input이 string임을 알 수 없다.
    return
  }

  console.log(input)
}

parseInputWithoutGuard('hello')
parseInputWithoutGuard(42)

// 2. `value is string`을 반환하면 호출한 곳에서 타입이 정제된다.
function isString(value: unknown): value is string {
  return typeof value === 'string'
}

function parseInput(input: string | number) {
  if (isString(input)) {
    console.log(input.toUpperCase()) // input은 string
    return
  }

  console.log(input.toFixed(2)) // input은 number
}

parseInput('typescript')
parseInput(3.14159)

// 3. 배열 필터링에서도 사용자 정의 타입 가드가 유용하다.
let mixedValues: (string | number | null)[] = ['Ada', 1, null, 'Grace', 2]

function isNonNullableString(value: string | number | null): value is string {
  return typeof value === 'string'
}

let names = mixedValues.filter(isNonNullableString) // string[]

console.log(names.map((name) => name.toUpperCase()))

// 4. 복잡한 객체 타입에도 타입 가드를 적용할 수 있다.
type LegacyDialog = {
  kind: 'legacy'
  title: string
  buttons: string[]
}

type Dialog = {
  kind: 'modern'
  title: string
  actions: { label: string; primary?: boolean }[]
}

function isLegacyDialog(dialog: LegacyDialog | Dialog): dialog is LegacyDialog {
  return dialog.kind === 'legacy'
}

function renderDialog(dialog: LegacyDialog | Dialog) {
  if (isLegacyDialog(dialog)) {
    console.log(`${dialog.title}: ${dialog.buttons.join(', ')}`)
    return
  }

  let primaryAction = dialog.actions.find((action) => action.primary)

  console.log(`${dialog.title}: ${primaryAction?.label ?? 'no primary action'}`)
}

renderDialog({
  kind: 'legacy',
  title: 'Confirm',
  buttons: ['OK', 'Cancel'],
})

renderDialog({
  kind: 'modern',
  title: 'Save changes',
  actions: [
    { label: 'Cancel' },
    { label: 'Save', primary: true },
  ],
})

// 5. 타입 가드의 반환 타입은 약속이다. 구현이 틀리면 런타임 오류가 날 수 있다.
function isStringUnsafely(value: unknown): value is string {
  return typeof value === 'number'
}

try {
  function printUpperCase(value: string | number) {
    if (isStringUnsafely(value)) {
      console.log(value.toUpperCase())
    }
  }

  printUpperCase(100)
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}
