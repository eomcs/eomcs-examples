package com.eomcs.cleancode.ch03.exam09;

public class BadAndGood1 {

  static class Page {
    private String name;
    private boolean exists;
    private boolean hasPermission;

    Page(String name, boolean exists, boolean hasPermission) {
      this.name = name;
      this.exists = exists;
      this.hasPermission = hasPermission;
    }

    String getName() { return name; }
    boolean exists() { return exists; }
    boolean hasPermission() { return hasPermission; }
    void delete() { this.exists = false; System.out.println("페이지 삭제됨: " + name); }
  }

  static class PageNotFoundException extends Exception {
    PageNotFoundException() { super("Page not found"); }
  }

  static class PermissionDeniedException extends Exception {
    PermissionDeniedException() { super("Permission denied"); }
  }

  enum ErrorCode { OK, NOT_FOUND, PERMISSION_DENIED }

  static class Logger {
    void info(String msg) { System.out.println("[INFO] " + msg); }
    void warn(String msg) { System.out.println("[WARN] " + msg); }
    void error(String msg) { System.out.println("[ERROR] " + msg); }
  }

  // Bad
  // - deletePage가 오류 코드를 반환하므로 호출자가 매번 반환값을 확인해야 한다.
  // - 정상 흐름(page deleted)이 오류 처리 if 문들 사이에 묻힌다.
  // - 오류 코드를 무시하면 오류가 조용히 넘어간다.
  static class BadPageService {
    ErrorCode deletePage(Page page) {
      if (!page.exists()) {
        return ErrorCode.NOT_FOUND;
      }
      if (!page.hasPermission()) {
        return ErrorCode.PERMISSION_DENIED;
      }
      page.delete();
      return ErrorCode.OK;
    }
  }

  static class BadUsage {
    void demo(BadPageService service, Page page) {
      Logger logger = new Logger();
      int result = service.deletePage(page).ordinal();

      if (result == ErrorCode.OK.ordinal()) {
        logger.info("page deleted");
      } else if (result == ErrorCode.NOT_FOUND.ordinal()) {
        logger.warn("page not found");
      } else if (result == ErrorCode.PERMISSION_DENIED.ordinal()) {
        logger.error("permission denied");
      }
    }
  }

  // Good
  // - deletePage가 예외를 던지므로 정상 흐름이 단순해진다.
  // - 오류 처리가 catch 블록으로 한 곳에 모인다.
  // - 오류를 무시하기 어렵다.
  static class GoodPageService {
    void deletePage(Page page) throws PageNotFoundException, PermissionDeniedException {
      if (!page.exists()) {
        throw new PageNotFoundException();
      }
      if (!page.hasPermission()) {
        throw new PermissionDeniedException();
      }
      page.delete();
    }
  }

  static class GoodUsage {
    void demo(GoodPageService service, Page page) {
      Logger logger = new Logger();
      try {
        service.deletePage(page);
        logger.info("page deleted");
      } catch (PageNotFoundException e) {
        logger.warn("page not found");
      } catch (PermissionDeniedException e) {
        logger.error("permission denied");
      }
    }
  }
}
