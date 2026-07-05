/**
 * 탈출구 (Escape Hatches)
 *
 * 타입 단언, 논널 단언, 확정 할당 단언은 타입 체커에게
 * "이 값은 내가 책임진다"고 알려주는 문법이다.
 * 편리하지만 런타임 안전성을 보장하지는 않는다.
 */

export {}

// 1. 타입 단언 (Type Assertions)
function getUserInput(): string | number {
  return '42'
}

function formatInput(input: string) {
  return input.trim().toUpperCase()
}

let input = getUserInput()

console.log(formatInput(input as string))
console.log(formatInput(<string>input))

// formatInput(input)
// Error: Argument of type 'string | number' is not assignable to parameter of type 'string'.

function addToList(list: string[], item: string) {
  list.push(item)
}

let list = ['safe']

// 관계없는 타입 단언은 바로 할 수 없지만 any를 경유하면 가능하다.
addToList(list, 100 as any)

console.log(list)

try {
  console.log(list[1].toUpperCase())
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 2. 논널(Non-null) 단언
type Dialog = {
  id?: string
}

type ElementLike = {
  id: string
  parentNode: ParentNodeLike | null
}

type ParentNodeLike = {
  removeChild(element: ElementLike): void
}

let elementsById: Record<string, ElementLike> = {}

function getElementById(id: string): ElementLike | null {
  return elementsById[id] ?? null
}

function removeFromDOM(dialog: Dialog, element: ElementLike) {
  element.parentNode!.removeChild(element)
  delete dialog.id
}

function closeDialog(dialog: Dialog) {
  if (!dialog.id) {
    return
  }

  setTimeout(() => {
    removeFromDOM(dialog, getElementById(dialog.id!)!)
  })
}

let parentNode: ParentNodeLike = {
  removeChild(element) {
    console.log(`remove ${element.id}`)
    delete elementsById[element.id]
  },
}

let visibleDialog: Dialog = {
  id: 'dialog-1',
}

elementsById['dialog-1'] = {
  id: 'dialog-1',
  parentNode,
}

closeDialog(visibleDialog)

try {
  removeFromDOM(
    { id: 'detached-dialog' },
    {
      id: 'detached-dialog',
      parentNode: null,
    }
  )
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

// 논널 단언이 많아진다면 상태를 유니온 타입으로 나누는 편이 더 안전하다.
type VisibleDialog = {
  id: string
}

type DestroyedDialog = {
  status: 'destroyed'
}

type SafeDialog = VisibleDialog | DestroyedDialog

function closeSafeDialog(dialog: SafeDialog) {
  if ('id' in dialog) {
    console.log(`close ${dialog.id}`)
    return
  }

  console.log(dialog.status)
}

closeSafeDialog({ id: 'dialog-2' })
closeSafeDialog({ status: 'destroyed' })

// 3. 확정 할당 단언 (Definite Assignment Assertions)
let userId!: string

function fetchUser() {
  userId = 'user-1'
}

fetchUser()

console.log(userId.toUpperCase())

let unsafeUserId!: string

try {
  console.log(unsafeUserId.toUpperCase())
} catch (error) {
  console.log('Runtime Error:', (error as Error).message)
}

