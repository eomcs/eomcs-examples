// 메서드 chaining call - before
package com.eomcs.openapi.json.gson;

public class Exam0115 {

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
    }

    // 필드에 값을 직접 주입하는 방식
    Member m1 = new Member();
    m1.no = 100;
    m1.name = "홍길동";
    m1.email = "hong@test.com";
    m1.password = "1111";
    m1.working = true;


    // 셋터를 이용하여 값을 주입하는 방식
    Member m2 = new Member();
    m2.setNo(100);
    m2.setName("홍길동");
    m2.setEmail("hong@test.com");
    m2.setPassword("1111");
    m2.setWorking(true);

  }
}





