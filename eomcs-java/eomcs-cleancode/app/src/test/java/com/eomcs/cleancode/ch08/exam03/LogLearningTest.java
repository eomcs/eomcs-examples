package com.eomcs.cleancode.ch08.exam03;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// log4j 학습 테스트: 예제 1~3
// - 운영 코드가 아니라 테스트 코드에서 log4j 동작을 단계적으로 탐색한다.
// - 각 단계에서 필요한 설정을 발견하고, 알게 된 사용법을 테스트 코드로 기록한다.
class LogLearningTest {

  private Logger logger;
  private LoggerContext ctx;

  @BeforeEach
  void initialize() {
    ctx = (LoggerContext) LogManager.getContext(false);
    Configuration config = ctx.getConfiguration();

    // 각 테스트가 깨끗한 상태에서 시작하도록 루트 로거의 Appender를 모두 제거한다
    config
        .getRootLogger()
        .getAppenders()
        .keySet()
        .forEach(name -> config.getRootLogger().removeAppender(name));

    // Log4j2 기본 설정의 루트 레벨은 ERROR 다.
    // INFO 로그는 레벨에서 걸러져 출력되지 않습니다.
    // 루트 레벨을 INFO로 낮춰서 INFO 로그가 출력되도록 한다.
    config.getRootLogger().setLevel(Level.INFO);
    ctx.updateLoggers();

    logger = LogManager.getLogger("logger");
  }

  @AfterEach
  void cleanup() {
    // 테스트가 끝난 후 log4j2-test.xml 설정을 복원한다
    ctx.reconfigure();
  }

  // 예제 1 - 1단계: 가장 단순한 학습 테스트
  // Appender 설정이 없으면 아무것도 출력되지 않는다.
  // 이 실행을 통해 Appender 설정이 필요하다는 사실을 발견한다.
  @Test
  void testLogCreate() {
    logger.info("hello");
    // 아무것도 출력되지 않는다
  }

  // 예제 2 - 2단계: ConsoleAppender를 추가해 보는 학습 테스트
  // 레이아웃 없이 ConsoleAppender를 추가하면 기본 형식으로 출력된다.
  @Test
  void testLogAddAppender() {
    Configuration config = ctx.getConfiguration();

    ConsoleAppender appender =
        ConsoleAppender.newBuilder()
            .setName("Console")
            .setLayout(PatternLayout.createDefaultLayout())
            .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
            .build();
    appender.start();

    config.addAppender(appender);
    config.getRootLogger().addAppender(appender, null, null);
    ctx.updateLoggers();

    logger.info("hello");
  }

  // 예제 3 - 3단계: 출력 스트림과 레이아웃을 모두 지정하여 설정을 확정한다
  @Test
  void addAppenderWithStream() {
    Configuration config = ctx.getConfiguration();

    PatternLayout layout = PatternLayout.newBuilder().setPattern("%p %t %m%n").build();
    ConsoleAppender appender =
        ConsoleAppender.newBuilder()
            .setName("Console")
            .setLayout(layout)
            .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
            .build();
    appender.start();

    config.addAppender(appender);
    config.getRootLogger().addAppender(appender, null, null);
    ctx.updateLoggers();

    logger.info("addAppenderWithStream");
  }

  // 예제 3 - 3단계: 스트림을 지정하지 않으면 기본 스트림(SYSTEM_OUT)이 사용된다
  @Test
  void addAppenderWithoutStream() {
    Configuration config = ctx.getConfiguration();

    PatternLayout layout = PatternLayout.newBuilder().setPattern("%p %t %m%n").build();
    ConsoleAppender appender =
        ConsoleAppender.newBuilder()
            .setName("Console")
            .setLayout(layout)
            .build(); // 스트림을 지정하지 않으면 기본값(SYSTEM_ERR)이 사용된다
    appender.start();

    config.addAppender(appender);
    config.getRootLogger().addAppender(appender, null, null);
    ctx.updateLoggers();

    logger.info("addAppenderWithoutStream");
  }

  // 예제 3 - 3단계: log4j2-test.xml 의 기본 설정을 그대로 사용한다
  @Test
  void basicLogger() {
    ctx.reconfigure(); // log4j2-test.xml 설정 복원

    logger.info("basicLogger");
  }
}
