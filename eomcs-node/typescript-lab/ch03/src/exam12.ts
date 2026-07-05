/**
 * 배열 (Arrays)
 *
 * TypeScript는 배열 리터럴의 요소를 보고 배열 타입을 추론한다.
 * 배열은 가능하면 같은 타입의 요소로 구성하는 것이 좋다.
 */

// 1. 숫자만 담긴 배열은 number[]로 추론한다.
let arrayA = [1, 2, 3]

arrayA.push(4)

console.log(arrayA) // [1, 2, 3, 4]

// arrayA.push('5')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 2. 문자열만 담긴 배열은 string[]으로 추론한다.
let arrayB = ['a', 'b']

arrayB.push('c')

console.log(arrayB) // ['a', 'b', 'c']

// arrayB.push(1)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 3. 배열 타입을 직접 명시할 수 있다.
let arrayC: string[] = ['a']

arrayC.push('b')

console.log(arrayC) // ['a', 'b']

// arrayC.push(false)
// Error: Argument of type 'boolean' is not assignable to parameter of type 'string'.

// 4. T[]와 Array<T>는 같은 의미다.
let arrayD: number[] = [1, 2, 3]
let arrayE: Array<number> = [4, 5, 6]

console.log(arrayD.concat(arrayE)) // [1, 2, 3, 4, 5, 6]

// 5. 여러 타입이 섞이면 유니온 배열로 추론한다.
let arrayF = [1, 'a']

arrayF.push('b')
arrayF.push(2)

console.log(arrayF) // [1, 'a', 'b', 2]

// arrayF.push(true)
// Error: Argument of type 'boolean' is not assignable to parameter of type 'string | number'.

// arrayF의 타입은 (string | number)[] 이다.
arrayF.forEach((item) => {
  if (typeof item === 'number') {
    console.log(item.toFixed(2))
  } else {
    console.log(item.toUpperCase())
  }
})

// 6. 빈 배열은 요소를 추가하면서 타입이 확장된다.
let arrayG = []

arrayG.push(1)
arrayG.push('red')

console.log(arrayG) // [1, 'red']

// 여기까지 arrayG는 (string | number)[]처럼 사용할 수 있다.
arrayG.forEach((item) => {
  if (typeof item === 'number') {
    console.log(item + 10)
  } else {
    console.log(item.toUpperCase())
  }
})

// 7. 배열은 같은 타입의 요소로 구성하는 편이 좋다.
let arrayScores: number[] = [90, 85, 100]
let arrayAverage =
  arrayScores.reduce((sum, score) => sum + score, 0) / arrayScores.length

console.log(arrayAverage) // 91.66666666666667

// 8. 객체 배열도 타입을 명시해서 사용할 수 있다.
type ArrayUser = {
  name: string
  age: number
}

let arrayUsers: ArrayUser[] = [
  { name: 'Ada', age: 36 },
  { name: 'Grace', age: 85 }
]

arrayUsers.push({ name: 'Margaret', age: 87 })

console.log(arrayUsers.map((user) => user.name)) // ['Ada', 'Grace', 'Margaret']

// arrayUsers.push({ name: 'Alan' })
// Error: Property 'age' is missing.
