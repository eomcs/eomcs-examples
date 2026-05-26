package com.eomcs.cleancode.ch07.exam05;

// 예제 4: 외부 라이브러리를 감싸는 래퍼 클래스 - ACMEPort → LocalPort
public class BadAndGood4 {

  private BadAndGood4() {}

  // 외부 라이브러리가 던지는 예외들 (제어할 수 없는 서드파티 예외)
  static class DeviceResponseException extends Exception {
    DeviceResponseException(String msg) { super(msg); }
  }

  static class ATM1212UnlockedException extends Exception {
    ATM1212UnlockedException(String msg) { super(msg); }
  }

  static class GMXError extends Exception {
    GMXError(String msg) { super(msg); }
  }

  // 외부 라이브러리 클래스 (제어할 수 없는 서드파티 코드)
  static class ACMEPort {
    private int portNumber;
    ACMEPort(int portNumber) { this.portNumber = portNumber; }
    int getPortNumber() { return portNumber; }

    void open() throws DeviceResponseException, ATM1212UnlockedException, GMXError {
      System.out.println("ACMEPort.open() portNumber=" + portNumber);
    }
  }

  static class Logger {
    void log(String message, Throwable e) {
      System.out.println("[LOG] " + message + " | " + e.getMessage());
    }
  }

  // Bad: 외부 라이브러리의 예외를 직접 처리한다.
  // - 외부 라이브러리가 던지는 예외를 모두 잡아내야 한다.
  // - 외부 라이브러리와의 결합도가 높다.
  // - 라이브러리를 교체하면 이 catch 블록 전체를 수정해야 한다.
  // - 모든 catch 블록에서 reportPortError()를 반복 호출한다.
  static class BadPortClient {
    void run(Logger logger) {
      ACMEPort port = new ACMEPort(12);

      try {
        port.open();
      } catch (DeviceResponseException e) {
        reportPortError(e);
        logger.log("Device response exception", e);
      } catch (ATM1212UnlockedException e) {
        reportPortError(e);
        logger.log("Unlock exception", e);
      } catch (GMXError e) {
        reportPortError(e);
        logger.log("Device response exception", e);
      }
    }

    private void reportPortError(Exception e) {
      System.out.println("포트 오류 보고: " + e.getMessage());
    }
  }

  // -----------------------------------------------------------------------

  // Good: 외부 라이브러리를 감싸는 래퍼 클래스를 정의한다.
  // - 래퍼 안에서 외부 예외를 하나의 도메인 예외로 변환한다.
  // - 호출자는 외부 라이브러리 예외를 전혀 알 필요가 없다.
  static class PortDeviceFailure extends RuntimeException {
    PortDeviceFailure(String message, Throwable cause) { super(message, cause); }
  }

  // Good: ACMEPort를 감싸는 LocalPort 래퍼 클래스
  // - 외부 예외를 PortDeviceFailure 하나로 통합한다.
  // - 나중에 ACMEPort를 다른 라이브러리로 교체해도 이 클래스만 수정하면 된다.
  static class LocalPort {
    private final ACMEPort port;

    LocalPort(int portNumber) {
      this.port = new ACMEPort(portNumber);
    }

    public void open() {
      try {
        port.open();
      } catch (DeviceResponseException | ATM1212UnlockedException | GMXError e) {
        throw new PortDeviceFailure(
            "포트 열기 실패. portNumber=" + port.getPortNumber(),
            e
        );
      }
    }
  }

  // Good: 호출자는 PortDeviceFailure 하나만 처리하면 된다.
  static class GoodPortClient {
    void run(Logger logger) {
      LocalPort port = new LocalPort(12);

      try {
        port.open();
      } catch (PortDeviceFailure e) {
        reportError(e);
        logger.log(e.getMessage(), e);
      }
    }

    private void reportError(Exception e) {
      System.out.println("포트 오류 보고: " + e.getMessage());
    }
  }
}
