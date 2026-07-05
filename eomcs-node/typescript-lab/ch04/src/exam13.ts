/**
 * 제한된 다형성 (Bounded Polymorphism)
 *
 * 제한된 다형성은 제네릭 타입 파라미터에 extends로 상한을 두는 방법이다.
 * T extends U는 T가 반드시 U이거나 U의 서브타입이어야 한다는 뜻이다.
 */

// 1. extends로 상한 설정하기
type ValueNode = {
  value: string
}

type ValueLeafNode = ValueNode & {
  isLeaf: true
}

type ValueInnerNode = ValueNode & {
  children: [ValueNode] | [ValueNode, ValueNode]
}

function mapValueNode<T extends ValueNode>(
  node: T,
  f: (value: string) => string
): T {
  return {
    ...node,
    value: f(node.value),
  }
}

let rootNode: ValueNode = {
  value: 'root',
}

let leafNode: ValueLeafNode = {
  value: 'leaf',
  isLeaf: true,
}

let innerNode: ValueInnerNode = {
  value: 'inner',
  children: [{ value: 'left' }, { value: 'right' }],
}

let mappedRootNode = mapValueNode(rootNode, value => value.toUpperCase())
let mappedLeafNode = mapValueNode(leafNode, value => value.toUpperCase())
let mappedInnerNode = mapValueNode(innerNode, value => value.toUpperCase())

console.log(mappedRootNode.value) // ROOT
console.log(mappedLeafNode.isLeaf) // true
console.log(mappedInnerNode.children.length) // 2

// mapValueNode({ label: 'leaf' }, label => label.toUpperCase())
// Error: Object literal may only specify known properties, and 'label' does not exist in type 'ValueNode'.

// mapValueNode({ value: 123 }, value => value.toUpperCase())
// Error: Type 'number' is not assignable to type 'string'.

// 제한하지 않은 제네릭에서는 node.value에 접근할 수 없다.
//
// function invalidMapNode<T>(node: T) {
//   return node.value
// }
// Error: Property 'value' does not exist on type 'T'.

// 2. 구체 타입 보존하기
// 반환 타입이 T이므로 LeafNode의 isLeaf 같은 세부 타입 정보가 유지된다.
let definitelyLeaf: true = mappedLeafNode.isLeaf

console.log(definitelyLeaf) // true

// let plainNode: ValueNode = mappedLeafNode
// ValueLeafNode는 ValueNode에 할당 가능하다.

// let leafOnlyNode: ValueLeafNode = mappedRootNode
// Error: Property 'isLeaf' is missing in type 'ValueNode' but required in type '{ isLeaf: true; }'.

// 3. 여러 조건을 동시에 제한하기
type HasSides = {
  numberOfSides: number
}

type SidesHaveLength = {
  sideLength: number
}

function logShapePerimeter<Shape extends HasSides & SidesHaveLength>(
  shape: Shape
): Shape {
  console.log(shape.numberOfSides * shape.sideLength)

  return shape
}

let squareShape = {
  numberOfSides: 4,
  sideLength: 5,
  color: 'blue',
}

let returnedSquareShape = logShapePerimeter(squareShape)

console.log(returnedSquareShape.color) // blue

// logShapePerimeter({ numberOfSides: 3 })
// Error: Property 'sideLength' is missing.

// logShapePerimeter({ sideLength: 5 })
// Error: Property 'numberOfSides' is missing.

// 4. 가변 인수 arity 모델링
// T extends unknown[]는 함수의 인수 목록을 튜플 타입으로 보존한다.
function callWithArgs<T extends unknown[], R>(
  f: (...args: T) => R,
  ...args: T
): R {
  return f(...args)
}

function fillWithText(length: number, value: string): string[] {
  return Array.from({ length }, () => value)
}

let filledTexts = callWithArgs(fillWithText, 3, 'a')

console.log(filledTexts) // ['a', 'a', 'a']

// callWithArgs(fillWithText, 3)
// Error: Expected 3 arguments, but got 2.

// callWithArgs(fillWithText, 3, 'a', 'z')
// Error: Expected 3 arguments, but got 4.

// callWithArgs(fillWithText, '3', 'a')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 5. 반환 타입도 함께 보존하기
function describeUserForBounded(name: string, age: number) {
  return `${name} is ${age}`
}

let describedUserForBounded = callWithArgs(describeUserForBounded, 'Ada', 36)

console.log(describedUserForBounded.toUpperCase()) // ADA IS 36

// describedUserForBounded.toFixed()
// Error: Property 'toFixed' does not exist on type 'string'.
