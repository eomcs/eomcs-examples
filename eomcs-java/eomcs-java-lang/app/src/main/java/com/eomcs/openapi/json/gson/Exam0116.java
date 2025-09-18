// 메서드 chaining call - after
package com.eomcs.openapi.json.gson;

public class Exam0116 {

  public static void main(String[] args) {

    class Member {
      int no;
      String name;
      String email;
      String password;
      boolean working;

      public int getNo() {return no;}
      public Member setNo(int no) {this.no = no; return this;}
      public String getName() {return name;}
      public Member setName(String name) {this.name = name; return this;}
      public String getEmail() {return email;}
      public Member setEmail(String email) {this.email = email; return this;}
      public String getPassword() {return password;}
      public Member setPassword(String password) {this.password = password; return this;}
      public boolean isWorking() {return working;}
      public Member setWorking(boolean working) {this.working = working; return this;}
    }

    Member m = new Member()
        .setNo(100)
        .setName("홍길동")
        .setEmail("hong@test.com")
        .setPassword("1111")
        .setWorking(true);

  }
}





