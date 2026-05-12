package com.eomcs.tdd.step16.refactor1;

class Bank {
  Money reduce(Expression source, String to) {
    // 계산식을 수행할 때 Bank를 통해 환율 계산을 할 수 있도록
    // Bank 객체 자신을 reduce() 메서드에 전달한다.
    return source.reduce(this, to);
  }

  // 환율 정보를 리턴하는 메서드를 추가한다.
  int rate(String from, String to) {
    // 일단 테스트 통과를 위해 하드 코딩으로 환율 계산을 구현한다.
    if (from.equals("CHF") && to.equals("USD")) {
      return 2;
    }
    return 1;
  }

  void addRate(String from, String to, int rate) {}
}
