package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood13 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: HTML 주석 (HTML Comments)
  // - <p>, <br/> 같은 HTML 태그를 Javadoc에 직접 삽입한다.
  // - IDE에서 읽기 어렵고 소스 코드에서는 더욱 지저분하다.
  // - 웹 문서 생성 도구가 있다면, HTML 마크업은 도구의 역할이지 프로그래머의 책임이 아니다.
  static class BadUserRepository {
    /**
     * <p>This method saves a user.</p>
     * <br/>
     */
    public void save(User user) {
      System.out.println("저장: " + user.getName());
    }
  }

  // Good: Javadoc에는 순수 텍스트로 간결하게 작성한다.
  // - HTML 태그 없이도 충분히 읽기 쉬운 문서를 작성할 수 있다.
  // - 꼭 필요한 내용(파라미터, 반환값, 예외)만 명시한다.
  static class GoodUserRepository {
    /**
     * Saves a user to the repository.
     */
    public void save(User user) {
      System.out.println("저장: " + user.getName());
    }
  }
}
