package com.eomcs.oop;

public class Test2 {

  public static void main(String[] args) {
    System.out.println(new Member()
        .setNo(1)
        .setName("홍길동2")
        .setEmail("hong@test.com")
        .setPassword("1111")
        .setAge(20)
        .setWork(true));
  }

  static class Member {
    int no;
    String name;
    String email;
    String password;
    int age;
    boolean work;

    @Override
    public String toString() {
      return "Member [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password
          + ", age=" + age + ", work=" + work + "]";
    }
    public int getNo() {
      return no;
    }
    public Member setNo(int no) {
      this.no = no;
      return this;
    }
    public String getName() {
      return name;
    }
    public Member setName(String name) {
      this.name = name;
      return this;
    }
    public String getEmail() {
      return email;
    }
    public Member setEmail(String email) {
      this.email = email;
      return this;
    }
    public String getPassword() {
      return password;
    }
    public Member setPassword(String password) {
      this.password = password;
      return this;
    }
    public int getAge() {
      return age;
    }
    public Member setAge(int age) {
      this.age = age;
      return this;
    }
    public boolean isWork() {
      return work;
    }
    public Member setWork(boolean work) {
      this.work = work;
      return this;
    }
  }
}
