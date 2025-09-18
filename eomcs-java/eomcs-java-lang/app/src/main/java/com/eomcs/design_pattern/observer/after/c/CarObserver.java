package com.eomcs.design_pattern.observer.after.c;

public interface CarObserver {
  // 자동차 시동을 켤 때 호출될 메서드
  // => 보통 메서드의 이름은 동사로 시작하는데,
  // 옵저버에게 통지할 때 호출하는 메서드는
  // 명사구의 상태 이름으로 정의할 수 있다.
  void carStarted();

  // 자동차 시동을 끌 때 호출될 메서드
  void carStopped();
}
