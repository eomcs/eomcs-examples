package com.eomcs.tdd.step18.red;

import java.util.Hashtable;

class Bank {

  private Hashtable<Pair, Integer> rates = new Hashtable<>();

  Money reduce(Expression source, String to) {
    return source.reduce(this, to);
  }

  int rate(String from, String to) {
    if (from.equals(to)) {
      return 1;
    }
    Integer rate = rates.get(new Pair(from, to));
    return rate.intValue();
  }

  void addRate(String from, String to, int rate) {
    rates.put(new Pair(from, to), rate);
  }

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
      return 0;
    }
  }
}
