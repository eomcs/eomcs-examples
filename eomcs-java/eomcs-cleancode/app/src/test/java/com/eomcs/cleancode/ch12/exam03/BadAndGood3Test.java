package com.eomcs.cleancode.ch12.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam03.BadAndGood3.BadItem;
import com.eomcs.cleancode.ch12.exam03.BadAndGood3.DefaultPricePolicy;
import com.eomcs.cleancode.ch12.exam03.BadAndGood3.Item;
import com.eomcs.cleancode.ch12.exam03.BadAndGood3.PricePolicyFactory;
import org.junit.jupiter.api.Test;

// 예제 3: 클래스와 메서드를 최소화하라
//
// 과도한 구조(인터페이스 + 팩토리)와 단순한 구조(Item.totalPrice())는
// 동일한 결과를 반환하지만, 단순한 구조가 이해와 유지보수에 유리하다.
class BadAndGood3Test {

  @Test
  void 팩토리로_생성한_정책이_올바른_금액을_계산한다() {
    PricePolicyFactory factory = new PricePolicyFactory();
    BadItem item = new BadItem(1000, 3);

    assertEquals(3000, factory.create().calculate(item));
  }

  @Test
  void Item_totalPrice가_올바른_금액을_반환한다() {
    Item item = new Item(1000, 3);

    assertEquals(3000, item.totalPrice());
  }

  @Test
  void 두_방식이_동일한_결과를_반환한다() {
    int price = 500;
    int quantity = 4;

    int badResult = new DefaultPricePolicy().calculate(new BadItem(price, quantity));
    int goodResult = new Item(price, quantity).totalPrice();

    assertEquals(badResult, goodResult);
  }
}
