// 잘못된 값을 넣었을 때 무시하지 않고 강력하게 오류를 알리기!
package com.eomcs.oop.ex08.c;

class Patient3 {
  public static final int WOMAN = 1;
  public static final int MAN = 2;

  private String name;
  private int age;
  private int height;
  private int weight;
  private int gender;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    if (age > 0 && age < 150)
      this.age = age;
    else
      throw new RuntimeException("나이가 유효하지 않습니다!");
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    if (height > 0 && height < 300)
      this.height = height;
    else
      throw new RuntimeException("키가 유효하지 않습니다!");
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    if (weight > 0 && weight < 500)
      this.weight = weight;
    else
      throw new RuntimeException("몸무게가 유효하지 않습니다!");
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    if (gender > 0 && gender < 3)
      this.gender = gender;
    else
      throw new RuntimeException("성별이 유효하지 않습니다!");
  }

  @Override
  public String toString() {
    return String.format("name=%s, age=%d, height=%d, weight=%d, gender=%d",
        this.name, this.age, this.height, this.weight, this.gender);
  }
}

public class Exam0130 {

  public static void main(String[] args) {


    Patient3 p = new Patient3();
    p.setName("김영희");
    p.setAge(20);
    p.setWeight(60);
    p.setHeight(157);
    p.setGender(Patient.WOMAN);

    System.out.println(p);

    Patient3 p2 = new Patient3();
    p2.setName("이철희");
    p2.setAge(300); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값을 넣으면 오류 발생!
    p2.setWeight(-50); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값을 넣으면 오류 발생!
    p2.setHeight(400); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값을 넣으면 오류 발생!
    p2.setGender(3); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값을 넣으면 오류 발생!

    System.out.println(p2);

  }

}





