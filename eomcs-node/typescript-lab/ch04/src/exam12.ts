/**
 * 제네릭 타입 별칭 (Generic Type Aliases)
 *
 * 제네릭 타입 별칭은 타입 별칭에 타입 파라미터를 선언하는 방법이다.
 * 같은 타입 구조를 여러 구체 타입에 맞게 재사용할 수 있다.
 */

// 1. 기본 제네릭 타입 별칭
type UiEventLike<T> = {
  target: T
  type: string
}

type ButtonLike = {
  kind: 'button'
  label: string
  disabled: boolean
}

type InputLike = {
  kind: 'input'
  value: string
  placeholder?: string
}

type ButtonEventLike = UiEventLike<ButtonLike>
type InputEventLike = UiEventLike<InputLike>

let saveButtonEvent: ButtonEventLike = {
  target: {
    kind: 'button',
    label: 'Save',
    disabled: false,
  },
  type: 'click',
}

let searchInputEvent: InputEventLike = {
  target: {
    kind: 'input',
    value: 'TypeScript',
    placeholder: 'Search',
  },
  type: 'input',
}

console.log(saveButtonEvent.target.label) // Save
console.log(searchInputEvent.target.value) // TypeScript

// saveButtonEvent.target.value
// Error: Property 'value' does not exist on type 'ButtonLike'.

// let invalidButtonEvent: ButtonEventLike = {
//   target: {
//     kind: 'input',
//     value: 'wrong',
//   },
//   type: 'click',
// }
// Error: Type '"input"' is not assignable to type '"button"'.

// 2. 제네릭 기본값 추가
// 타입 인수를 생략하면 Target의 기본값으로 ElementLikeForEvent를 사용한다.
type ElementLikeForEvent = {
  kind: string
  id?: string
}

type UiEventWithDefault<Target extends ElementLikeForEvent = ElementLikeForEvent> = {
  target: Target
  type: string
}

let genericUiEvent: UiEventWithDefault = {
  target: {
    kind: 'section',
    id: 'intro',
  },
  type: 'focus',
}

let buttonUiEvent: UiEventWithDefault<ButtonLike> = {
  target: {
    kind: 'button',
    label: 'Delete',
    disabled: true,
  },
  type: 'click',
}

console.log(genericUiEvent.target.kind) // section
console.log(buttonUiEvent.target.disabled) // true

// type InvalidEventTarget = UiEventWithDefault<string>
// Error: Type 'string' does not satisfy the constraint 'ElementLikeForEvent'.

// 3. 타입 합성
type TimedUiEvent<Target extends ElementLikeForEvent = ElementLikeForEvent> = {
  event: UiEventWithDefault<Target>
  from: Date
  to: Date
}

let timedButtonEvent: TimedUiEvent<ButtonLike> = {
  event: buttonUiEvent,
  from: new Date(2026, 5, 29, 10, 0, 0),
  to: new Date(2026, 5, 29, 10, 0, 1),
}

console.log(timedButtonEvent.event.target.label) // Delete
console.log(timedButtonEvent.to.getTime() - timedButtonEvent.from.getTime()) // 1000

// 4. 제네릭 타입 별칭을 함수 파라미터에 사용하기
function triggerUiEvent<Target extends ElementLikeForEvent>(
  event: UiEventWithDefault<Target>
): Target {
  console.log(event.type, event.target.kind)

  return event.target
}

let triggeredButton = triggerUiEvent(buttonUiEvent)
let triggeredGenericTarget = triggerUiEvent(genericUiEvent)

console.log(triggeredButton.label) // Delete
console.log(triggeredGenericTarget.kind) // section

// triggerUiEvent({
//   target: 'button',
//   type: 'click',
// })
// Error: Type 'string' is not assignable to type 'ElementLikeForEvent'.

// 5. 여러 타입 파라미터를 가진 타입 별칭
type ApiResponse<Data, ErrorData = { message: string }> = {
  ok: boolean
  data?: Data
  error?: ErrorData
}

type UserProfile = {
  id: string
  name: string
}

let userProfileResponse: ApiResponse<UserProfile> = {
  ok: true,
  data: {
    id: 'u-123',
    name: 'Ada',
  },
}

let failedResponse: ApiResponse<never, { code: string; message: string }> = {
  ok: false,
  error: {
    code: 'NOT_FOUND',
    message: 'User not found',
  },
}

console.log(userProfileResponse.data?.name) // Ada
console.log(failedResponse.error?.code) // NOT_FOUND
