package com.eomcs.cleancode.ch08.exam02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// 학습 테스트 예제 1: java.util.logging 사용법을 운영 코드가 아닌 테스트로 먼저 탐색한다.
// - 라이브러리 동작 방식을 작은 단위로 검증한다.
// - 알게 된 사용법이 테스트 코드로 기록된다.
// - 라이브러리 업그레이드 시 이 테스트를 다시 실행해 기대 동작을 확인할 수 있다.
class LoggingLearningTest {

  private Logger logger;

  @BeforeEach
  void setUp() {
    logger = Logger.getLogger("testLogger");
    logger.setUseParentHandlers(false);

    for (Handler handler : logger.getHandlers()) {
      logger.removeHandler(handler);
    }
  }

  @Test
  void ConsoleHandler로_로그를_출력한다() {
    ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new SimpleFormatter());
    logger.addHandler(handler);

    logger.info("hello");
    // 콘솔에 로그가 출력되면 성공
  }

  @Test
  void 핸들러가_없으면_아무것도_출력되지_않는다() {
    logger.info("silent log");
    // 핸들러가 없으면 아무 것도 출력되지 않는다
  }

  @Test
  void 핸들러를_여러_개_추가하면_각각_출력된다() {
    ConsoleHandler handler1 = new ConsoleHandler();
    ConsoleHandler handler2 = new ConsoleHandler();
    logger.addHandler(handler1);
    logger.addHandler(handler2);

    logger.warning("두 핸들러 모두 출력");
    // 같은 로그가 두 번 출력된다
  }
}
