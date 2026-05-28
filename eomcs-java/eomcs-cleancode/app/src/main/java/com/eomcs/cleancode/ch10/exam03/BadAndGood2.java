package com.eomcs.cleancode.ch10.exam03;

// 예제 2: 변경으로부터 격리하라 - Portfolio / StockExchange
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: Portfolio가 TokyoStockExchange라는 구체 클래스에 직접 의존한다
  //   - 외부 API가 바뀌면 Portfolio도 영향을 받는다
  //   - 테스트하기 어렵다
  //   - 테스트 중에도 실제 증권 거래소 API를 호출할 위험이 있다
  static class BadTokyoStockExchange {

    public int currentPrice(String stockName) {
      // 실제 외부 API 호출
      return 1000;
    }
  }

  static class BadPortfolio {

    private final BadTokyoStockExchange exchange;

    BadPortfolio() {
      this.exchange = new BadTokyoStockExchange(); // 구체 클래스를 직접 생성
    }

    public int valueOf(String stockName, int shares) {
      int price = exchange.currentPrice(stockName);
      return price * shares;
    }
  }

  // Good: 인터페이스를 사이에 두어 변경 영향을 격리한다
  //   - Portfolio는 실제 거래소 구현을 모른다
  //   - 테스트에서는 가짜 거래소를 넣을 수 있다
  //   - 외부 API 변경이 Portfolio로 전파되지 않는다
  //   - 변경 가능성이 큰 부분이 StockExchange 구현체 안으로 격리된다
  interface StockExchange {
    int currentPrice(String stockName);
  }

  static class TokyoStockExchange implements StockExchange {

    @Override
    public int currentPrice(String stockName) {
      // 실제 외부 API 호출
      return 1000;
    }
  }

  static class Portfolio {

    private final StockExchange exchange;

    Portfolio(StockExchange exchange) { // 인터페이스로 의존성 주입
      this.exchange = exchange;
    }

    public int valueOf(String stockName, int shares) {
      int price = exchange.currentPrice(stockName);
      return price * shares;
    }
  }

  // 테스트용 구현: 고정 가격을 반환하는 가짜 거래소
  static class FixedStockExchange implements StockExchange {

    private final int fixedPrice;

    FixedStockExchange(int fixedPrice) {
      this.fixedPrice = fixedPrice;
    }

    @Override
    public int currentPrice(String stockName) {
      return fixedPrice;
    }
  }
}
