package com.eomcs.cleancode.ch10.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam03.BadAndGood2.BadPortfolio;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - Portfolio
//
// 문제점:
// - BadPortfolio 내부에서 BadTokyoStockExchange를 직접 생성한다.
// - 테스트에서 가짜 거래소를 주입할 방법이 없다.
// - 실제 외부 API 의존 코드와 강하게 결합되어 있다.
// - 거래소 구현이 바뀌면 BadPortfolio도 수정해야 한다.
class PortfolioBadTest {

  @Test
  void 포트폴리오_가치를_계산한다() {
    // TokyoStockExchange 구체 클래스가 고정 가격(1000)을 반환한다고 가정
    BadPortfolio portfolio = new BadPortfolio();

    int value = portfolio.valueOf("Samsung", 10);

    assertEquals(10_000, value);
  }
}
