package com.eomcs.cleancode.ch10.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam03.BadAndGood2.FixedStockExchange;
import com.eomcs.cleancode.ch10.exam03.BadAndGood2.Portfolio;
import com.eomcs.cleancode.ch10.exam03.BadAndGood2.StockExchange;
import org.junit.jupiter.api.Test;

// 예제 2: 변경으로부터 격리 - Portfolio / StockExchange
//
// Bad: Portfolio가 TokyoStockExchange에 직접 의존 — 테스트에 실제 API가 호출된다.
// Good: StockExchange 인터페이스를 주입받으므로 테스트에서 가짜 거래소를 넣을 수 있다.
//       외부 API 변경이 Portfolio로 전파되지 않는다.
class PortfolioGoodTest {

  @Test
  void 포트폴리오_가치를_계산한다() {
    StockExchange exchange = new FixedStockExchange(1000); // 가짜 거래소 주입
    Portfolio portfolio = new Portfolio(exchange);

    int value = portfolio.valueOf("Samsung", 10);

    assertEquals(10_000, value);
  }

  @Test
  void 주가가_다를_때_가치가_달라진다() {
    StockExchange exchange = new FixedStockExchange(500);
    Portfolio portfolio = new Portfolio(exchange);

    int value = portfolio.valueOf("Samsung", 4);

    assertEquals(2_000, value);
  }
}
