/**
 * 판별 유니온 타입 (Discriminated Union Types)
 *
 * 유니온의 각 케이스에 같은 위치의 리터럴 태그 필드를 두면,
 * TypeScript가 태그 값을 기준으로 나머지 필드까지 정확하게 정제한다.
 */

export {}

// 1. 태그가 없으면 value와 target 사이의 관계를 정제하기 어렵다.
type UntaggedTextEvent = {
  value: string
  target: HTMLInputElement
}

type UntaggedMouseEvent = {
  value: [number, number]
  target: HTMLElement
}

type UntaggedUserEvent = UntaggedTextEvent | UntaggedMouseEvent

function handleUntagged(event: UntaggedUserEvent) {
  if (typeof event.value === 'string') {
    console.log(event.value.toUpperCase())

    // event.target.value
    // Error: Property 'value' does not exist on type 'HTMLInputElement | HTMLElement'.
    //
    // value를 검사해도 target까지 HTMLInputElement로 정제되지는 않는다.
    return
  }

  console.log(event.value[0], event.value[1])
}

// 2. 리터럴 태그가 있으면 한 번의 검사로 전체 케이스가 정제된다.
type UserTextEvent = {
  type: 'TextEvent'
  value: string
  target: HTMLInputElement
}

type UserMouseEvent = {
  type: 'MouseEvent'
  value: [number, number]
  target: HTMLElement
}

type UserEvent = UserTextEvent | UserMouseEvent

function handle(event: UserEvent) {
  if (event.type === 'TextEvent') {
    console.log(event.value.toUpperCase())
    console.log(event.target.value)
    return
  }

  console.log(event.value[0], event.value[1])
  console.log(event.target.tagName)
}

let input = {
  value: 'hello',
  tagName: 'INPUT',
} as HTMLInputElement

let button = {
  tagName: 'BUTTON',
} as HTMLElement

handleUntagged({
  value: 'untagged text',
  target: input,
})

handle({
  type: 'TextEvent',
  value: 'tagged text',
  target: input,
})

handle({
  type: 'MouseEvent',
  value: [20, 30],
  target: button,
})

// 3. Redux/useReducer 스타일의 액션도 판별 유니온으로 표현할 수 있다.
type CounterAction =
  | { type: 'increment'; amount: number }
  | { type: 'decrement'; amount: number }
  | { type: 'reset' }

function reduceCounter(count: number, action: CounterAction) {
  switch (action.type) {
    case 'increment':
      return count + action.amount
    case 'decrement':
      return count - action.amount
    case 'reset':
      return 0
  }
}

console.log(reduceCounter(10, { type: 'increment', amount: 5 }))
console.log(reduceCounter(10, { type: 'decrement', amount: 3 }))
console.log(reduceCounter(10, { type: 'reset' }))

