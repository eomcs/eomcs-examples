package com.eomcs.cleancode.ch02.exam03;

// 의미를 구분할 수 없는 클래스 이름
public class BadAndGood2 {

  // Bad: 이름이 달라도 의미 구분이 불가능하다.
  class Product {}

  class ProductData {} // Data가 뭘 의미하나?

  class ProductInfo {} // Info는 Data와 어떻게 다른가?

  // Good: 역할을 드러내는 이름을 사용한다.
  class Order {} // 도메인 객체

  class OrderRepository {} // 영속성 담당

  class OrderDto {} // 전송용 객체
}
