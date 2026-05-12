package com.eomcs.tdd.step01.red;

// Red 상태

// ----------------------------------------------------------
// Red 1: Dollar 클래스 생성 → 컴파일 오류 해결
// class Dollar {}

// ----------------------------------------------------------
// Red 2: 생성자 추가 → 컴파일 오류 해결
// class Dollar {
//   Dollar(int amount) {}
// }

// ----------------------------------------------------------
// Red 3: times() 메서드 추가 → 컴파일 오류 해결
// class Dollar {
//   Dollar(int amount) {}

//   void times(int multiplier) {}
// }

// ----------------------------------------------------------
// Red 4: amount 필드 추가 → 컴파일 오류 해결
class Dollar {
  int amount;

  Dollar(int amount) {}

  void times(int multiplier) {}
}
