package com.eomcs.design_pattern.observer.after.b;

public class SafeBeltCarObserver implements CarObserver {

  @Override
  public void carStarted() {
    // 자동차의 시동을 걸 때 호출되는 메서드이다.
    // - 시동 걸 때 뭔가 작업하고 싶다면 이 메서드에 그 코드를 작성하면 된다.
    System.out.println("안전벨트 착용 여부 검사");
  }

  @Override
  public void carStopped() {
    // 자동차의 시동을 끌 때 호출되는 메서드이다.
    // - 시동 끌 때 뭔가 작업하고 싶다면 이 메서드에 그 코드를 작성하면 된다.
  }

}
