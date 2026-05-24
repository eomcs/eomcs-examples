package com.eomcs.cleancode.ch03.exam09;

public class BadAndGood3 {

  static class Page {
    private String name;
    private String key;
    Page(String name, String key) { this.name = name; this.key = key; }
    String getName() { return name; }
    String getKey() { return key; }
  }

  static class Logger {
    void error(String msg) { System.out.println("[ERROR] " + msg); }
  }

  // Bad
  // - delete() 안에 try/catch가 직접 포함되어 정상 로직과 오류 처리가 한 함수에 뒤섞인다.
  // - try/catch 블록은 코드 구조를 복잡하게 만들고 함수가 두 가지 역할을 하게 된다.
  static class BadDeleteService {
    Logger logger = new Logger();

    void deletePage(Page page) throws Exception { System.out.println("페이지 삭제: " + page.getName()); }
    void deleteReference(String name) throws Exception { System.out.println("참조 삭제: " + name); }
    void deleteKey(String key) throws Exception { System.out.println("키 삭제: " + key); }

    void delete(Page page) {
      try {
        deletePage(page);
        deleteReference(page.getName());
        deleteKey(page.getKey());
      } catch (Exception e) {
        logger.error(e.getMessage()); // 정상 로직과 오류 처리가 한 함수에 혼재
      }
    }
  }

  // Good: try/catch 블록을 별도 함수로 추출한다.
  // - delete() → 오류 처리 흐름만 담당한다. try/catch가 함수 전체를 감싼다.
  // - deletePageAndAllReferences() → 실제 삭제 작업만 담당한다. 오류 처리 코드가 없다.
  // - logError() → 오류 기록만 담당한다.
  // - 각 함수가 하나의 일만 한다.
  static class GoodDeleteService {
    Logger logger = new Logger();

    void deletePage(Page page) throws Exception { System.out.println("페이지 삭제: " + page.getName()); }
    void deleteReference(String name) throws Exception { System.out.println("참조 삭제: " + name); }
    void deleteKey(String key) throws Exception { System.out.println("키 삭제: " + key); }

    void delete(Page page) {
      try {
        deletePageAndAllReferences(page);
      } catch (Exception e) {
        logError(e);
      }
    }

    private void deletePageAndAllReferences(Page page) throws Exception {
      deletePage(page);
      deleteReference(page.getName());
      deleteKey(page.getKey());
    }

    private void logError(Exception e) {
      logger.error(e.getMessage());
    }
  }
}
