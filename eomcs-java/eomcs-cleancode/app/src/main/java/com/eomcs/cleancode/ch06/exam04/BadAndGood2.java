package com.eomcs.cleancode.ch06.exam04;

// 예제 2: Active Record
// - DTO의 특수한 형태다.
// - public 필드 또는 bean-style getter/setter를 가진 자료 구조
// - save, find 같은 탐색/저장 메서드를 추가로 가진다.
// - 보통 데이터베이스 테이블을 직접 반영한다.
public class BadAndGood2 {

  private BadAndGood2() {}

  // Active Record: DB 테이블 행(Row)과 1:1로 대응하는 자료 구조다.
  // - 데이터 보관
  // - DB 저장 (save)
  // - DB 조회 (findById)
  // 이 세 가지 역할만 담당한다. 비즈니스 규칙은 여기에 넣지 않는다.
  static class UserRecord {
    private Long id;
    private String email;
    private String name;

    UserRecord(Long id, String email, String name) {
      this.id = id;
      this.email = email;
      this.name = name;
    }

    public Long getId()     { return id; }
    public String getEmail() { return email; }
    public String getName()  { return name; }

    public void setEmail(String email) { this.email = email; }
    public void setName(String name)   { this.name = name; }

    // DB 저장: INSERT 또는 UPDATE
    public void save() {
      System.out.println("INSERT OR UPDATE users SET email='" + email + "' WHERE id=" + id);
    }

    // DB 조회: SELECT * FROM users WHERE id = ?
    public static UserRecord findById(Long id) {
      System.out.println("SELECT * FROM users WHERE id = " + id);
      return new UserRecord(id, "user@example.com", "홍길동");
    }
  }

  // 사용 예
  static class UserClient {
    void demo() {
      // 조회
      UserRecord user = UserRecord.findById(1L);
      System.out.println(user.getName());

      // 수정 후 저장
      user.setEmail("new@example.com");
      user.save();
    }
  }
}
