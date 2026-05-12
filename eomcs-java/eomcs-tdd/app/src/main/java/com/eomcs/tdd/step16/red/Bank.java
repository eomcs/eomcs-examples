package com.eomcs.tdd.step16.red;

class Bank {
  Money reduce(Expression source, String to) {
    return source.reduce(to);
  }

  void addRate(String from, String to, int rate) {
    // 환율을 저장하는 로직은 아직 구현하지 않았다.
    // 단지 컴파일 오류를 방지하기 위해 메서드 시그니처만 추가한다.
  }
}
