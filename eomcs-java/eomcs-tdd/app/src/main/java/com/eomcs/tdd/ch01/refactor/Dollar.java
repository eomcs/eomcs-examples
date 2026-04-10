package com.eomcs.tdd.ch01.refactor;

// Refactor 단계
//
// 하드코딩된 10 → amount * multiplier 로 일반화한다.

// ----------------------------------------------------------
// Refactor 1 - 생성자의 파라미터 값을 필드에 대입
// class Dollar {
//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   void times(int multiplier) {}
// }

// ----------------------------------------------------------
// Refactor 2 - times() 메서드에서 계산
// class Dollar {
//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   void times(int multiplier) {
//     amount = amount * 2;
//   }
// }

// ----------------------------------------------------------
// Refactor 3 - times()의 파라미터 값 활용하여 계산을 일반화
// class Dollar {
//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   void times(int multiplier) {
//     amount = amount * multiplier;
//   }
// }

// ----------------------------------------------------------
// Refactor 4 - 자바 복합 대입 연산자 *= 활용
// class Dollar {

//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   void times(int multiplier) {
//     amount *= multiplier;
//   }
// }
