package bitcamp.app3;

public class Member {
  int no;
  String email;
  String password;

  public Member() {
  }

  public Member(int no, String email, String password) {
    this.no = no;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "Member{" +
        "no=" + no +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
