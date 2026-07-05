/**
 * 열거형 (Enums)과 const enum
 *
 * enum은 컴파일 타임에 고정된 키를 값에 매핑하는 자료구조다.
 * 기본 enum은 숫자 값 enum이며, 첫 번째 키부터 0, 1, 2... 순서로 값이 붙는다.
 */

// 1. 기본 숫자 enum
enum EnumLanguage {
  English, // 0
  Spanish, // 1
  Russian // 2
}

let enumMyLang = EnumLanguage.Russian
let enumMyLang2 = EnumLanguage['English']

console.log(enumMyLang) // 2
console.log(enumMyLang2) // 0

// enum 값은 enum 타입 변수에 할당할 수 있다.
let enumSelectedLanguage: EnumLanguage = EnumLanguage.Spanish

console.log(enumSelectedLanguage) // 1

// 존재하지 않는 키는 사용할 수 없다.
// let enumUnknownLanguage = EnumLanguage.Tagalog
// Error: Property 'Tagalog' does not exist on type 'typeof EnumLanguage'.

// 2. 숫자 enum은 런타임 객체로 존재한다.
console.log(EnumLanguage)
// {
//   '0': 'English',
//   '1': 'Spanish',
//   '2': 'Russian',
//   English: 0,
//   Spanish: 1,
//   Russian: 2
// }

// 3. 숫자 enum은 역방향 조회가 가능하다.
console.log(EnumLanguage[0]) // English
console.log(EnumLanguage[EnumLanguage.Russian]) // Russian

// 4. enum 값은 직접 지정할 수도 있다.
enum EnumHttpStatus {
  Ok = 200,
  Created = 201,
  BadRequest = 400,
  NotFound = 404
}

let enumStatus = EnumHttpStatus.NotFound

console.log(enumStatus) // 404
console.log(EnumHttpStatus[404]) // NotFound

// 5. const enum은 더 제한적으로 사용된다.
// 역방향 조회를 막아 일반 숫자 enum보다 더 안전하게 사용할 수 있다.
const enum ConstEnumLanguage {
  English,
  Spanish,
  Russian
}

let constEnumA = ConstEnumLanguage.English

console.log(constEnumA) // 0

// let constEnumB = ConstEnumLanguage.Tagalog
// Error: Property 'Tagalog' does not exist on type 'typeof ConstEnumLanguage'.

// let constEnumC = ConstEnumLanguage[0]
// Error: A const enum member can only be accessed using a string literal.
//
// const enum은 일반 enum처럼 런타임 객체로 다루기 위한 용도가 아니다.
// 따라서 숫자로 역방향 조회하는 패턴을 사용할 수 없다.

// 6. 함수 매개변수에 enum 타입을 사용할 수 있다.
function enumSpeak(language: EnumLanguage): string {
  switch (language) {
    case EnumLanguage.English:
      return 'Hello'
    case EnumLanguage.Spanish:
      return 'Hola'
    case EnumLanguage.Russian:
      return 'Privet'
  }
}

console.log(enumSpeak(EnumLanguage.English)) // Hello
console.log(enumSpeak(EnumLanguage.Spanish)) // Hola

// enumSpeak('English')
// Error: Argument of type '"English"' is not assignable to parameter of type 'EnumLanguage'.

function constEnumSpeak(language: ConstEnumLanguage): string {
  switch (language) {
    case ConstEnumLanguage.English:
      return 'Hello'
    case ConstEnumLanguage.Spanish:
      return 'Hola'
    case ConstEnumLanguage.Russian:
      return 'Privet'
  }
}

console.log(constEnumSpeak(ConstEnumLanguage.Russian)) // Privet

// 7. 숫자 enum은 임의의 숫자가 섞일 수 있어 주의가 필요하다.
enum EnumDirection {
  Up,
  Down,
  Left,
  Right
}

function enumMove(direction: EnumDirection): string {
  return `move: ${direction}`
}

console.log(enumMove(EnumDirection.Up)) // move: 0

// 숫자 enum은 숫자와 가까운 형태라, 예상하지 못한 숫자가 들어갈 위험이 있다.
// TypeScript 버전과 설정에 따라 아래와 같은 숫자 전달이 허용될 수 있다.
//
// enumMove(100)
// 위험: EnumDirection에 없는 값이지만 런타임에서는 숫자로 처리될 수 있다.

// 8. 문자열 값 const enum은 숫자 enum의 위험을 줄일 수 있다.
const enum EnumFlippable {
  Burger = 'Burger',
  Chair = 'Chair',
  Cup = 'Cup'
}

function enumFlip(flippable: EnumFlippable) {
  return `flipped ${flippable}`
}

console.log(enumFlip(EnumFlippable.Chair)) // flipped Chair
console.log(enumFlip(EnumFlippable.Burger)) // flipped Burger

// enumFlip(12)
// Error: Argument of type '12' is not assignable to parameter of type 'EnumFlippable'.
//
// enumFlip('Hat')
// Error: Argument of type '"Hat"' is not assignable to parameter of type 'EnumFlippable'.
//
// enumFlip('Chair')
// Error: 문자열 값이 같아도 enum 멤버 자체를 전달해야 한다.

// 9. 문자열 enum은 출력 값도 의미 있는 문자열이라 디버깅하기 쉽다.
let enumCup = EnumFlippable.Cup

console.log(enumCup) // Cup
