// 캡슐화(encapsulation) - 셋터와 겟터
package com.eomcs.oop.ex08.b;

// 실무에서는 셋터에서 유효 값을 검증하는 코드를 잘 넣지 않는다.
// 따로 인스턴스 변수의 값을 검증하는 메서드를 추가하여 처리한다.
// 그래서 실무에서 셋터 메서드는 인스턴스 변수에 그냥 값을 넣는 경우가 많다.
// 즉 인스턴스 변수에 직접 값을 넣는 것과 동일하게 동작한다.
// 이런 상황 때문에 셋터, 겟터의 무용론을 주장하는 개발자들이 있다.
// 그들은 그냥 인스턴스 변수의 접근 범위를 public으로 공개하여 사용할 것을 주장한다.
// 그러나 대부분의 개발자들은 셋터의 무용함을 떠나,
// 메서드를 통해 변수의 값을 설정하는 방법을 선호한다.
// 혹 나중에 메서드에 기타 코드를 추가할 경우를 대비하기 위함이다.
// 변수를 직접 사용하면 변수를 제어하는 코드를 삽입하기 어렵기 때문이다.
//
class Customer4 {

  // 외부에서 인스턴스 변수에 직접 접근하지 못하도록 막는다!
  private String name;
  private int age;
  private int weight;
  private int height;

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
    this.age = age;
  }
  public int getWeight() {
    return weight;
  }
  public void setWeight(int weight) {
    this.weight = weight;
  }
  public int getHeight() {
    return height;
  }
  public void setHeight(int height) {
    this.height = height;
  }
}

public class Exam0140 {

  public static void main(String[] args) {
    // 환자 데이터를 등록해보자!
    Customer4 c1 = new Customer4();

    // 실무에서 만드는 셋터는 보통 파라미터 값을 검증하지 않기 때문에
    // 그냥 입력된 값 그대로 인스턴스 변수에 저장한다.
    // 그래서 값을 꺼내 출력해보면 입력된 값 그대로 출력될 것이다.
    c1.setName("홍길동");
    c1.setAge(300);
    c1.setWeight(-100);
    c1.setHeight(-50);

    System.out.printf("%s, %d, %d, %d\n",
        c1.getName(), c1.getAge(), c1.getWeight(), c1.getHeight());
  }
}







