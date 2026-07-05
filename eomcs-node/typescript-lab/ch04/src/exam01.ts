/**
 * 함수 선언과 호출 (Declaring and Invoking Functions)
 *
 * TypeScript에서는 매개변수 타입을 명시해서 함수가 받을 수 있는 값을 제한한다.
 * 반환 타입은 대부분 함수 본문을 보고 TypeScript가 추론할 수 있다.
 */

// 1. Named function (기명 함수)
function greet(name: string) {
  return 'hello ' + name
}

console.log(greet('Ada')) // hello Ada

// greet(100)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 2. Function expression (함수 표현식)
let greet2 = function(name: string) {
  return 'hello ' + name
}

console.log(greet2('Grace')) // hello Grace

// 3. Arrow function expression (화살표 함수)
let greet3 = (name: string) => {
  return 'hello ' + name
}

console.log(greet3('Linus')) // hello Linus

// 4. Shorthand arrow function (단축 화살표 함수)
let greet4 = (name: string) => 'hello ' + name

console.log(greet4('Matz')) // hello Matz

// 5. Function constructor
// Function 생성자는 매개변수와 반환값 타입을 확인하지 못하므로 타입 안전하지 않다.
let greet5 = new Function('name', 'return "hello " + name')

console.log(greet5('Brendan')) // hello Brendan
console.log(greet5(100)) // hello 100

// 매개변수 타입은 반드시 명시하고, 반환 타입은 선택적으로 명시한다.
function add(a: number, b: number): number {
  return a + b
}

console.log(add(1, 2)) // 3

// add(1)
// Error: Expected 2 arguments, but got 1.

// add(1, 'a')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 반환 타입을 생략해도 TypeScript는 number로 추론한다.
function multiply(a: number, b: number) {
  return a * b
}

let product = multiply(3, 4)

console.log(product) // 12

// let message: string = multiply(3, 4)
// Error: Type 'number' is not assignable to type 'string'.

// parameter(매개변수): 함수 선언에 정의된 변수
// argument(인수): 함수 호출 시 전달하는 값
function introduce(name: string, age: number) {
  return `${name} is ${age} years old.`
}

let introduction = introduce('TypeScript', 12)

console.log(introduction) // TypeScript is 12 years old.
