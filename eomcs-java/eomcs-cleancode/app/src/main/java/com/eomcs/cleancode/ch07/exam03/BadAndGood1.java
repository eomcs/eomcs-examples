package com.eomcs.cleancode.ch07.exam03;

// 예제 1: Checked Exception 남용 - DeviceController
public class BadAndGood1 {

  private BadAndGood1() {}

  static class DeviceHandle {
    void shutdown() { System.out.println("장치 종료"); }
  }

  // Bad: Checked Exception — 모든 호출자가 강제로 처리해야 한다.
  static class DeviceHandleException extends Exception {
    DeviceHandleException(String message) { super(message); }
  }

  // Bad: throws 선언이 호출 체인 전체로 퍼진다.
  // - 실제로 복구할 수 없는 예외까지 강제 처리된다.
  // - 호출자는 printStackTrace() 같은 의미 없는 코드를 추가하게 된다.
  // - 코드가 예외 처리로 오염된다.
  static class BadDeviceController {
    DeviceHandle getHandle() { return null; }

    public void sendShutDown() throws DeviceHandleException {
      DeviceHandle handle = getHandle();

      if (handle == null) {
        throw new DeviceHandleException("장치를 찾을 수 없음");
      }

      handle.shutdown();
    }
  }

  static class BadDeviceClient {
    void run() {
      BadDeviceController controller = new BadDeviceController();

      // 의미 없는 try-catch가 강제된다
      try {
        controller.sendShutDown();
      } catch (DeviceHandleException e) {
        e.printStackTrace();
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: Unchecked Exception — 필요한 곳에서만 처리한다.
  static class DeviceNotFoundException extends RuntimeException {
    DeviceNotFoundException(String message) { super(message); }
  }

  // Good: throws 선언이 사라진다.
  // - 불필요한 try-catch가 없어진다.
  // - 정상 흐름이 명확해진다.
  // - 진짜 필요한 곳에서만 예외를 처리할 수 있다.
  static class GoodDeviceController {
    DeviceHandle getHandle() { return null; }

    public void sendShutDown() {
      DeviceHandle handle = getHandle();

      if (handle == null) {
        throw new DeviceNotFoundException("장치를 찾을 수 없음");
      }

      handle.shutdown();
    }
  }

  static class GoodDeviceClient {
    void run() {
      GoodDeviceController controller = new GoodDeviceController();

      // 강제 처리 없이 정상 흐름만 드러난다
      // 필요하다면 상위에서 한 번에 처리한다
      controller.sendShutDown();
    }
  }
}
