package com.eomcs.design_pattern.observer.after.h;

// 이전 버전에서는 인터페이스를 직접 구현했지만,
// (그래서 관심도 없는 carStopped() 메서드까지 정의했다)
// 이번 버전에서는 추상 클래스를 상속 받아 간접적으로 구현한다.
public class BrakeOilCarObserver extends AbstractCarObserver {
  @Override
  public void carStarted() {
    System.out.println("브레이크 오일 유무 검사");
  }

  // 어? carStopped() 구현하지 않았네요?
  // - 수퍼 클래스에서 구현했습니다.
  // - 물론 빈 코드를 가진 메서드이죠!
  //
}
