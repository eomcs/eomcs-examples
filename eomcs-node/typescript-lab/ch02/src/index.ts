console.log('Hello TypeScript!')

// 타입 추론 예시 (ch02 - 2장)
let a = 1 + 2        // number
let b = a + 3        // number
let c = {
  apple: a,          // { apple: number, banana: number }
  banana: b
}
let d = c.apple * 4  // number
let e = "aaa" + 10   // string

// e = 20 // Error: Type 'number' is not assignable to type 'string'.