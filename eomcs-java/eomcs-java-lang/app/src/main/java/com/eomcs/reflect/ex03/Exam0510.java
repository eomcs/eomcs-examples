// 필드 정보 추출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Exam0510 {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    Class<?> clazz = Car.class;

    Field[] fields = clazz.getDeclaredFields();
    for (Field f : fields) {
      System.out.printf("%s:%s\n", f.getName(), f.getType().getName());
    }


    Constructor<Car> defaultConst = (Constructor<Car>) clazz.getConstructor();
    Car car = defaultConst.newInstance();

    // 1) private 필드는 일반적인 방식으로 접근할 수 없다.
    //    car.maker = "비트자동차"; // 컴파일 오류!

    // 2) 다음과 같이 Reflection API를 사용하면 private 필드에 접근할 수 있다.
    Field makerField = clazz.getDeclaredField("maker");

    // private modifier로 선언된 필드라 하더라도
    // 다음 메서드를 통해 접근 가능하도록 만들 수 있다.
    makerField.setAccessible(true);

    // private 필드에 값 넣기
    // 가능해?
    makerField.set(car, "비트자동차");

    System.out.println(car);
  }

}


