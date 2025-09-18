// 캡슐화(encapsulation) : 적용 후
package com.eomcs.oop.ex08.c;

// 필드에 직접 접근하는 것을 막는다.
//
class Patient2 {
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
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    if (height > 0 && height < 300)
      this.height = height;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    if (weight > 0 && weight < 500)
      this.weight = weight;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    if (gender > 0 && gender < 3)
      this.gender = gender;
  }

  @Override
  public String toString() {
    return String.format("name=%s, age=%d, height=%d, weight=%d, gender=%d",
        this.name, this.age, this.height, this.weight, this.gender);
  }
}


public class Exam0120 {

  public static void main(String[] args) {


    Patient2 p = new Patient2();
    p.setName("김영희");
    p.setAge(20);
    p.setWeight(60);
    p.setHeight(157);
    p.setGender(Patient.WOMAN);

    System.out.println(p);

    Patient2 p2 = new Patient2();
    p2.setName("이철희");
    p2.setAge(300); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값은 무시된다.
    p2.setWeight(-50); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값은 무시된다.
    p2.setHeight(400); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값은 무시된다.
    p2.setGender(3); // 캡슐화를 무너뜨릴 수 있는 유효하지 않은 값은 무시된다.

    System.out.println(p2);

  }

}





