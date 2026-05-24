package com.eomcs.cleancode.ch03.exam09;

public class BadAndGood2 {

  static class Page {
    private String name;
    private String key;
    Page(String name, String key) { this.name = name; this.key = key; }
    String getName() { return name; }
    String getKey() { return key; }
  }

  enum ErrorCode { OK, ERROR }

  static class Logger {
    void info(String msg) { System.out.println("[INFO] " + msg); }
    void error(String msg) { System.out.println("[ERROR] " + msg); }
  }

  // Bad
  // - 오류 코드 방식에서는 각 호출마다 반환값을 if로 검사해야 한다.
  // - if 블록이 깊게 중첩되어 정상 흐름을 파악하기 어렵다.
  // - 오류 처리 코드가 정상 로직보다 많아진다.
  static class BadDeleteService {
    ErrorCode deletePage(Page page) { System.out.println("페이지 삭제: " + page.getName()); return ErrorCode.OK; }
    ErrorCode deleteReference(String name) { System.out.println("참조 삭제: " + name); return ErrorCode.OK; }
    ErrorCode deleteKey(String key) { System.out.println("키 삭제: " + key); return ErrorCode.OK; }

    void delete(Page page) {
      Logger logger = new Logger();
      if (deletePage(page) == ErrorCode.OK) {
        if (deleteReference(page.getName()) == ErrorCode.OK) {
          if (deleteKey(page.getKey()) == ErrorCode.OK) {
            logger.info("page deleted");
          } else {
            logger.error("config delete failed");
          }
        } else {
          logger.error("registry delete failed");
        }
      } else {
        logger.error("page delete failed");
      }
    }
  }

  // Good
  // - 예외 방식에서는 정상 흐름이 위에서 아래로 자연스럽게 읽힌다.
  // - 오류 처리가 catch 블록 하나로 통합되어 중첩이 없다.
  // - 정상 로직과 오류 로직이 명확히 분리된다.
  static class GoodDeleteService {
    void deletePage(Page page) throws Exception { System.out.println("페이지 삭제: " + page.getName()); }
    void deleteReference(String name) throws Exception { System.out.println("참조 삭제: " + name); }
    void deleteKey(String key) throws Exception { System.out.println("키 삭제: " + key); }

    void delete(Page page) {
      Logger logger = new Logger();
      try {
        deletePage(page);
        deleteReference(page.getName());
        deleteKey(page.getKey());
        logger.info("page deleted");
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
    }
  }
}
