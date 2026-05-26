package com.eomcs.cleancode.ch07.exam04;

import java.io.IOException;

// 예제 3: 예외에 의미를 제공하라 - loadProduct (원인 예외 보존)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Logger {
    void error(String message, Throwable e) {
      System.out.println("[ERROR] " + message + " | cause: " + e.getClass().getSimpleName());
    }
  }

  static class Product {
    private String code;
    private String name;
    Product(String code, String name) { this.code = code; this.name = name; }
    String getCode() { return code; }
    String getName() { return name; }
  }

  interface ExternalProductApi {
    Product fetch(String productCode) throws IOException;
  }

  // Bad: 모호한 메시지로 감싸고 원인 예외를 버린다.
  // - 외부 API 호출 중 어떤 상품에서 실패했는지 알기 어렵다.
  // - 원래 발생한 IOException의 정보가 사라진다.
  // - "API error"는 너무 일반적이어서 추적 불가능하다.
  static class BadProductService {
    private ExternalProductApi externalProductApi;
    BadProductService(ExternalProductApi api) { this.externalProductApi = api; }

    @SuppressWarnings("java:S112") // 의도적인 Bad 예제 — 모호한 RuntimeException이 핵심 문제점
    public Product loadProduct(String productCode) {
      try {
        return externalProductApi.fetch(productCode);
      } catch (IOException e) {
        throw new RuntimeException("API error"); // 원인 예외가 사라진다
      }
    }
  }

  static class BadProductClient {
    void run(BadProductService productService, Logger logger) {
      try {
        Product product = productService.loadProduct("P-100");
        System.out.println(product.getName());
      } catch (RuntimeException e) {
        logger.error(e.getMessage(), e); // 로그: "API error" — 무엇이, 왜 실패했는지 알 수 없다
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 실패한 작업, 대상, 원인 예외를 모두 담는다.
  static class ProductLoadException extends RuntimeException {
    ProductLoadException(String message, Throwable cause) { super(message, cause); }
  }

  // Good: productCode를 메시지에 담고, IOException을 cause로 보존한다.
  // - 실패한 작업이 "상품 정보 조회"임을 알 수 있다.
  // - 실패한 대상 productCode=P-100이 로그에 남는다.
  // - 원인 예외 IOException을 cause로 보존해 근본 원인도 추적 가능하다.
  static class GoodProductService {
    private ExternalProductApi externalProductApi;
    GoodProductService(ExternalProductApi api) { this.externalProductApi = api; }

    public Product loadProduct(String productCode) {
      try {
        return externalProductApi.fetch(productCode);
      } catch (IOException e) {
        throw new ProductLoadException(
            "상품 정보 조회 실패. productCode=" + productCode,
            e // 원인 예외를 함께 전달한다
        );
      }
    }
  }

  static class GoodProductClient {
    void run(GoodProductService productService, Logger logger) {
      try {
        Product product = productService.loadProduct("P-100");
        System.out.println(product.getName());
      } catch (ProductLoadException e) {
        logger.error(e.getMessage(), e); // 로그: "상품 정보 조회 실패. productCode=P-100"
      }
    }
  }
}
