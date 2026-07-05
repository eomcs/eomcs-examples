/**
 * this 타입 지정 (Typing this)
 *
 * JavaScript의 this는 함수를 어떻게 호출했는지에 따라 값이 달라진다.
 * TypeScript에서는 함수의 첫 번째 파라미터 자리에 this 타입을 선언할 수 있다.
 */

// 1. this 타입 선언하기
// this: Date는 실제 인수가 아니다. 호출할 때 전달하는 값이 아니라 호출 문맥을 검사한다.
function formatDate(this: Date) {
  return `${this.getFullYear()}-${this.getMonth() + 1}-${this.getDate()}`
}

let today = new Date(2026, 5, 29)

console.log(formatDate.call(today)) // 2026-6-29

let boundFormatDate = formatDate.bind(today)

console.log(boundFormatDate()) // 2026-6-29

// formatDate()
// Error: The 'this' context of type 'void' is not assignable to method's 'this' of type 'Date'.

// formatDate(today)
// Error: Expected 0 arguments, but got 1.

// formatDate.call('2026-06-29')
// Error: Argument of type 'string' is not assignable to parameter of type 'Date'.

// 2. this 타입과 일반 파라미터 함께 사용하기
type AccountBalance = {
  owner: string
  amount: number
}

function deposit(this: AccountBalance, amount: number) {
  this.amount += amount
  return `${this.owner}: ${this.amount}`
}

let savingsAccount: AccountBalance = {
  owner: 'Ada',
  amount: 100,
}

console.log(deposit.call(savingsAccount, 50)) // Ada: 150

// deposit(savingsAccount, 50)
// Error: Expected 1 arguments, but got 2.

// deposit(50)
// Error: The 'this' context of type 'void' is not assignable to method's 'this' of type 'AccountBalance'.

// deposit.call(savingsAccount, '50')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// 3. 객체 메서드에서 this 사용하기
let wallet = {
  owner: 'Grace',
  amount: 200,
  withdraw(this: AccountBalance, amount: number) {
    this.amount -= amount
    return `${this.owner}: ${this.amount}`
  },
}

console.log(wallet.withdraw(30)) // Grace: 170

let withdrawFromWallet = wallet.withdraw

// withdrawFromWallet(20)
// Error: The 'this' context of type 'void' is not assignable to method's 'this' of type 'AccountBalance'.

console.log(withdrawFromWallet.call(wallet, 20)) // Grace: 150

// 4. 화살표 함수는 자체 this를 갖지 않는다.
// 따라서 함수 파라미터 자리에 this 타입을 선언할 수 없다.
//
// let invalidThis = (this: Date) => this.getFullYear()
// Error: An arrow function cannot have a 'this' parameter.

// 5. this 타입은 호출자에게 보이지 않는 가짜 파라미터다.
// 아래 함수는 this와 amount를 선언했지만, bind로 this를 고정하면 amount만 받는 함수가 된다.
let depositToSavings = deposit.bind(savingsAccount)

console.log(depositToSavings(25)) // Ada: 175

// depositToSavings(savingsAccount, 25)
// Error: Expected 1 arguments, but got 2.
