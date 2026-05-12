package com.eomcs.tdd.step16.refactor1;

interface Expression {
  // 계산식을 수행할 때 환율 정보를 얻을 수 있도록
  // Bank 파라미터를 추가한다.
  Money reduce(Bank bank, String to);
}
