/**
 * 완전성 검사 (Totality / Exhaustiveness Checking)
 *
 * 모든 케이스를 처리했는지 확인하면, 유니온 타입이 늘어났을 때 빠진 분기를 발견할 수 있다.
 */

export {}

type Weekday = 'Mon' | 'Tue' | 'Wed' | 'Thu' | 'Fri'
type Day = Weekday | 'Sat' | 'Sun'

function getNextDay(weekday: Weekday): Day {
  switch (weekday) {
    case 'Mon':
      return 'Tue'
    case 'Tue':
      return 'Wed'
    case 'Wed':
      return 'Thu'
    case 'Thu':
      return 'Fri'
    case 'Fri':
      return 'Sat'
  }
}

console.log(getNextDay('Mon'))
console.log(getNextDay('Fri'))

// function getNextDayIncomplete(weekday: Weekday): Day {
//   switch (weekday) {
//     case 'Mon':
//       return 'Tue'
//   }
// }
// Error: Function lacks ending return statement and return type does not include 'undefined'.
//
// noImplicitReturns가 켜져 있으면 반환하지 않는 코드 경로도 잡을 수 있다.

// 1. never를 사용하면 switch에서 모든 케이스를 처리했는지 더 명확히 검사할 수 있다.
function assertNever(value: never): never {
  throw new Error(`Unexpected value: ${value}`)
}

type LoadingState =
  | { status: 'loading' }
  | { status: 'success'; data: string }
  | { status: 'error'; error: Error }

function renderState(state: LoadingState): string {
  switch (state.status) {
    case 'loading':
      return 'Loading...'
    case 'success':
      return state.data.toUpperCase()
    case 'error':
      return state.error.message
    default:
      return assertNever(state)
  }
}

console.log(renderState({ status: 'loading' }))
console.log(renderState({ status: 'success', data: 'done' }))
console.log(renderState({ status: 'error', error: new Error('failed') }))

// 2. 새로운 케이스를 추가하고 처리하지 않으면 assertNever에서 오류가 난다.
type ExtendedLoadingState =
  | LoadingState
  | { status: 'empty' }

// function renderExtendedState(state: ExtendedLoadingState): string {
//   switch (state.status) {
//     case 'loading':
//       return 'Loading...'
//     case 'success':
//       return state.data.toUpperCase()
//     case 'error':
//       return state.error.message
//     default:
//       return assertNever(state)
//   }
// }
// Error: Argument of type '{ status: "empty"; }' is not assignable to parameter of type 'never'.

function renderExtendedState(state: ExtendedLoadingState): string {
  switch (state.status) {
    case 'loading':
      return 'Loading...'
    case 'success':
      return state.data.toUpperCase()
    case 'error':
      return state.error.message
    case 'empty':
      return 'No data'
    default:
      return assertNever(state)
  }
}

console.log(renderExtendedState({ status: 'empty' }))

