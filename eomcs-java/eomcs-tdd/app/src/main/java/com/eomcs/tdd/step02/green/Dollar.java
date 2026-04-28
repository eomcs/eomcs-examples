package com.eomcs.tdd.step02.green;

// Green 전략 - Fake It 방식으로 접근할 때의 예시
//

// ----------------------------------------------------------
// ### Green 1단계: 하드 코딩으로 첫 assertEquals 통과
// class Dollar {

//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   Dollar times(int multiplier) {
//     return new Dollar(10); // 일단 상수를 반환해서 테스트를 통과시킨다.
//   }
// }

// ----------------------------------------------------------
// ### Green 2단계: 상수를 변수로 치환(부분 일반화)
// class Dollar {

//   int amount;

//   Dollar(int amount) {
//     this.amount = amount;
//   }

//   Dollar times(int multiplier) {
//     return new Dollar(amount * 2);
//   }
// }

// ----------------------------------------------------------
// ### Green 3단계: 상수를 변수로 치환(완전 일반화)
class Dollar {

  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}

// Green 단계에서 작성한 코드에서 중복이 보이지 않으므로 Refactor 단계를 생략한다.
