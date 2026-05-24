package com.eomcs.cleancode.ch04.exam03;

public class BadAndGood6 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: 임시 구현이지만 나중에 고쳐야 한다는 표시가 없다.
  // - calculateDiscount()가 항상 10을 반환하는 임시 코드임을 알 수 없다.
  // - 정책 검토 후 실제 로직으로 교체해야 한다는 사실이 코드에 드러나지 않는다.
  // - 코드를 처음 본 사람은 이 값이 의도된 것인지, 임시인지 구분할 수 없다.
  static class BadDiscountService {
    int calculateDiscount(User user) {
      return 10;
    }
  }

  // Good: TODO 주석 (TODO Comments)
  // - 지금 당장 완성할 수 없는 이유와 나중에 해야 할 일을 명확히 기록한다.
  // - 나쁜 코드를 그대로 두는 핑계가 아니라, 추후 수정을 위한 이정표다.
  // - 주기적으로 TODO를 검토하고 완료되면 제거해야 한다.
  static class GoodDiscountService {
    // TODO: 정책 검토 완료 후 사용자 등급·구매 이력 기반 할인율 계산 로직으로 교체할 것.
    int calculateDiscount(User user) {
      return 10;
    }

    // TODO-MdM: makeVersion()은 체크아웃 모델 도입 시 제거 예정.
    protected String makeVersion() {
      return null;
    }
  }
}
