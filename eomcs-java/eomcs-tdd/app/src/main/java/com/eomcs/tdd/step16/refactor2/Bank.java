package com.eomcs.tdd.step16.refactor2;

import java.util.Hashtable;

class Bank {

  // 환율 정보를 저장하는 테이블
  private Hashtable<Pair, Integer> rates = new Hashtable<>();

  Money reduce(Expression source, String to) {
    return source.reduce(this, to);
  }

  // 하드 코딩된 환율 정보를 해시테이블에서 조회하도록 변경한다.
  int rate(String from, String to) {
    // 동일 통화에 대한 환율 정보는 항상 1로 반환한다.
    if (from.equals(to)) {
      return 1;
    }
    Integer rate = rates.get(new Pair(from, to));
    return rate.intValue();
  }

  // 해시테이블에 환율 정보를 저장하도록 addRate() 메서드를 완성한다.
  void addRate(String from, String to, int rate) {
    rates.put(new Pair(from, to), rate);
  }

  // 통화쌍 정보를 저장하기 위한 내부 클래스
  private class Pair {
    private String from;
    private String to;

    public Pair(String from, String to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public boolean equals(Object object) {
      Pair pair = (Pair) object;
      return from.equals(pair.from) && to.equals(pair.to);
    }

    @Override
    public int hashCode() {
      // 간단한 해시코드 구현!
      // - 완벽한 로직을 고민하느라 멈춰 있는 대신, 당장 직면한 테스트를 가장 빠르게 통과시켜 초록 막대를 볼 수 있도록
      //   최적화를 뒤로 미룬다.
      // - 나중에 다루어야 할 통화의 종류가 많아지고 실제 사용 데이터(real usage data)를 얻게 되면
      //   그때 가서 제대로 된 해시 로직을 구현한다.
      return 0;
    }
  }
}
