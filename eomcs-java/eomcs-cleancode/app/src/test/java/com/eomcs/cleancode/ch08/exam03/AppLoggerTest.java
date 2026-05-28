package com.eomcs.cleancode.ch08.exam03;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.Test;

// 학습 테스트 예제 4: 학습 결과를 AppLogger 래퍼 클래스로 캡슐화한다.
// - log4j 학습 테스트로 얻은 사용법 지식을 래퍼 클래스 안에 가둔다.
// - 운영 코드(OrderService)는 log4j API를 전혀 알 필요가 없다.
// - log4j를 다른 로깅 도구로 바꾸더라도 AppLogger만 수정하면 된다.
class AppLoggerTest {

  // 학습 테스트로 확정한 log4j 설정을 캡슐화한 래퍼 클래스
  static class AppLogger {
    private final Logger logger;

    AppLogger(String name) {
      LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
      Configuration config = ctx.getConfiguration();

      // Log4j2 기본 설정의 루트 레벨은 ERROR 다.
      // INFO 로그는 레벨에서 걸러져 출력되지 않습니다.
      // 루트 레벨을 INFO로 낮춰서 INFO 로그가 출력되도록 한다.
      config.getRootLogger().setLevel(Level.INFO);

      PatternLayout layout = PatternLayout.newBuilder().setPattern("%p %t %m%n").build();
      ConsoleAppender appender =
          ConsoleAppender.newBuilder()
              .setName("Console-" + name)
              .setLayout(layout)
              .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
              .build();
      appender.start();

      config.addAppender(appender);
      config.getLoggerConfig(name).addAppender(appender, null, null);
      ctx.updateLoggers();

      this.logger = LogManager.getLogger(name);
    }

    void info(String message) {
      logger.info(message);
    }
  }

  // AppLogger를 주입받는 서비스 코드 - log4j를 전혀 모른다
  static class OrderService {
    private final AppLogger logger;

    OrderService(AppLogger logger) {
      this.logger = logger;
    }

    void createOrder(String orderId) {
      logger.info("주문 생성 시작. orderId=" + orderId);
      logger.info("주문 생성 완료. orderId=" + orderId);
    }
  }

  @Test
  void AppLogger는_log4j_설정_없이_바로_사용할_수_있다() {
    AppLogger logger = new AppLogger("AppLoggerTest");

    logger.info("AppLogger 동작 확인");
    // 예외 없이 실행되면 성공
  }

  @Test
  void OrderService는_log4j를_몰라도_로그를_출력할_수_있다() {
    AppLogger logger = new AppLogger("OrderService");
    OrderService orderService = new OrderService(logger);

    orderService.createOrder("ORDER-001");
    // 로그가 정상 출력되면 성공
  }

  @Test
  void 이름이_다른_AppLogger는_독립적으로_동작한다() {
    AppLogger orderLogger = new AppLogger("OrderService2");
    AppLogger paymentLogger = new AppLogger("PaymentService");

    orderLogger.info("OrderService 로그");
    paymentLogger.info("PaymentService 로그");
    // 각각 독립적으로 로그가 출력되면 성공
  }
}
