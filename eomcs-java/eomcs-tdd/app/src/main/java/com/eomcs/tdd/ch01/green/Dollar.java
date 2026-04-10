package com.eomcs.tdd.ch01.green;

// Green 단계

// ----------------------------------------------------------
// Green 1: Dollar 클래스 생성 → 컴파일 오류 해결
// class Dollar {}

// ----------------------------------------------------------
// Green 2: 생성자 추가 → 컴파일 오류 해결
// class Dollar {
//   Dollar(int amount) {}
// }

// ----------------------------------------------------------
// Green 3: times() 메서드 추가 → 컴파일 오류 해결
// class Dollar {
//   Dollar(int amount) {}

//   void times(int multiplier) {}
// }

// ----------------------------------------------------------
// Green 4: amount 필드 추가 → 컴파일 오류 해결
// class Dollar {
//   int amount;

//   Dollar(int amount) {}

//   void times(int multiplier) {}
// }

// ----------------------------------------------------------
// Green 5: amount 필드 값 하드 코딩 → 통과(Green)
// class Dollar {
//   int amount = 10;

//   Dollar(int amount) {}

//   void times(int multiplier) {}
// }
