// 기존 클래스에 코드를 추가한다.
package com.eomcs.oop.ex05.b;

public class Car {

  public String model;
  public String maker;
  public int capacity;

  // 필드를 추가한다.
  public boolean sunroof;
  public boolean auto;

  public Car() {}

  // 다른 예제에서 다음 생성자를 사용하기 때문에
  // 이 생성자에 파라미터를 덧붙일 수는 없다.
  public Car(String model, String maker, int capacity) {
    this.model = model;
    this.maker = maker;
    this.capacity = capacity;
  }

  // 새로 생성자를 추가해야 한다.
  public Car(String model, String maker, int capacity,
      boolean sunroof, boolean auto) {
    // 이 클래스의 다른 생성자를 먼저 호출할 수 있다.
    // => 이때 this()를 사용한다.
    this(model, maker, capacity);

    this.sunroof = sunroof;
    this.auto = auto;
  }

  public void run() {
    System.out.println("달린다!");
  }
}


