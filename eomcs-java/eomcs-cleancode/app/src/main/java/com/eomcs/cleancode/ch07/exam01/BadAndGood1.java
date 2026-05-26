package com.eomcs.cleancode.ch07.exam01;

// 예제 1: 오류 코드보다 예외를 사용하라 - deletePage
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Page {
    private boolean deletable;

    Page(boolean deletable) { this.deletable = deletable; }

    boolean delete() { return deletable; }
  }

  // Bad: 오류 코드를 반환한다.
  // - 호출자가 반환 코드를 즉시 검사해야 하므로 정상 흐름과 오류 흐름이 뒤섞인다.
  // - if/else 분기가 계속 늘어난다.
  // - 결과를 체크하지 않으면 오류가 무시된다.
  static class BadPageService {
    static final int E_OK           = 0;
    static final int E_NOT_FOUND    = 1;
    static final int E_DELETE_FAILED = 2;

    public int deletePage(Page page) {
      if (page == null) {
        return E_NOT_FOUND;
      }
      if (!page.delete()) {
        return E_DELETE_FAILED;
      }
      return E_OK;
    }
  }

  static class BadPageClient {
    void run(Page page) {
      BadPageService pageService = new BadPageService();

      int result = pageService.deletePage(page);

      if (result == BadPageService.E_OK) {
        System.out.println("삭제 성공");
      } else if (result == BadPageService.E_NOT_FOUND) {
        System.out.println("페이지 없음");
      } else if (result == BadPageService.E_DELETE_FAILED) {
        System.out.println("삭제 실패");
      }
    }
  }

  // -----------------------------------------------------------------------

  static class PageNotFoundException extends RuntimeException {
    PageNotFoundException() { super("페이지를 찾을 수 없습니다."); }
  }

  static class PageDeleteFailedException extends RuntimeException {
    PageDeleteFailedException() { super("페이지 삭제에 실패했습니다."); }
  }

  // Good: 예외를 던진다.
  // - 정상 흐름이 먼저 읽힌다.
  // - 오류 처리 로직이 catch 블록으로 분리된다.
  // - 예외 타입으로 실패 원인이 명확하게 드러난다.
  static class GoodPageService {
    public void deletePage(Page page) {
      if (page == null) {
        throw new PageNotFoundException();
      }
      if (!page.delete()) {
        throw new PageDeleteFailedException();
      }
    }
  }

  static class GoodPageClient {
    void run(Page page) {
      GoodPageService pageService = new GoodPageService();

      try {
        pageService.deletePage(page);
        System.out.println("삭제 성공");
      } catch (PageNotFoundException e) {
        System.out.println("페이지 없음");
      } catch (PageDeleteFailedException e) {
        System.out.println("삭제 실패");
      }
    }
  }
}
