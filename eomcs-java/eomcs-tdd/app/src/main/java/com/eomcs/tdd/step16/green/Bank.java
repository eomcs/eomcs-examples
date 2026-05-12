package com.eomcs.tdd.step16.green;

class Bank {
  Money reduce(Expression source, String to) {
    return source.reduce(to);
  }

  void addRate(String from, String to, int rate) {}
}
