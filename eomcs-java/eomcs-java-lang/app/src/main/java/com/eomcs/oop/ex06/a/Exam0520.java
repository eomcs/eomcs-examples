// 다형성 - 다형적 변수의 형변환 응용 - instanceof 연산자
package com.eomcs.oop.ex06.a;

public class Exam0520 {

  // Sedan과 Truck, Bike의 모든 정보를 자세히 출력하라!
  // 단, 한 개의 메서드로 처리하라!

  public static void print(Vehicle v) {

    System.out.println("[기본정보]");
    System.out.printf("모델명: %s\n", v.model);
    System.out.printf("수용인원: %d\n", v.capacity);

    // 파라미터 v에 들어있는 주소가 Bike인지 Sedan인지 Truck인지를 구분해서 처리해야 한다.
    // 자바는 이런 경우를 대비해 인스턴스의 주소가 어떤 클래스의 주소인지
    // 판단할 수 있는 연산자를 제공한다.
    // instanceof 연산자!

    if (v instanceof Bike) {
      Bike bike = (Bike) v;
      System.out.println("[바이크 정보]");
      System.out.printf("엔진의 존재: %b\n", bike.engine);

    } else if (v instanceof Car) {
      Car car = (Car) v;
      System.out.println("[자동차 기본정보]");
      System.out.printf("cc: %d\n", car.cc);
      System.out.printf("밸브: %d\n", car.valve);

      if (v instanceof Sedan) {
        Sedan sedan = (Sedan) v;
        System.out.println("[승용차 기본정보]");
        System.out.printf("썬루프: %b\n", sedan.sunroof);
        System.out.printf("자동변속: %b\n", sedan.auto);

      } else if (v instanceof Truck) {
        Truck truck = (Truck) v;
        System.out.println("[트럭 정보]");
        System.out.printf("덤프가능: %b\n", truck.dump);
        System.out.printf("중량: %.1f\n", truck.ton);
      }
    }
    System.out.println("-------------------------");
  }

  public static void main(String[] args) {
    Bike bike = new Bike();
    bike.model = "비트오토바이2018";
    bike.capacity = 2;
    bike.engine = true;

    Sedan sedan = new Sedan();
    sedan.model = "티코";
    sedan.capacity = 5;
    sedan.cc = 800;
    sedan.valve = 16;
    sedan.auto = true;
    sedan.sunroof = true;

    Truck truck = new Truck();
    truck.model = "타이탄II";
    truck.capacity = 3;
    truck.cc = 10000;
    truck.valve = 32;
    truck.dump = true;
    truck.ton = 15;

    print(bike);

    print(sedan);

    print(truck);


  }

}




