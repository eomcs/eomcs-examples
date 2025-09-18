// 메서드 chaining call - before
package com.eomcs.openapi.json.gson;

public class Exam0117 {

  public static void main(String[] args) {

    class Member {
      int no;
      String name;
      String email;
      String password;
      boolean working;

      public int getNo() {return no;}
      public void setNo(int no) {this.no = no;}
      public String getName() {return name;}
      public void setName(String name) {this.name = name;}
      public String getEmail() {return email;}
      public void setEmail(String email) {this.email = email;}
      public String getPassword() {return password;}
      public void setPassword(String password) {this.password = password;}
      public boolean isWorking() {return working;}
      public void setWorking(boolean working) {this.working = working;}

      public Member no(int no) {this.no = no; return this;}
      public Member name(String name) {this.name = name; return this;}
      public Member email(String email) {this.email = email; return this;}
      public Member password(String password) {this.password = password; return this;}
      public Member working(boolean working) {this.working = working; return this;}
    }

    Member m = new Member()
        .no(100)
        .name("홍길동")
        .email("hong@test.com")
        .password("1111")
        .working(true);

  }
}





