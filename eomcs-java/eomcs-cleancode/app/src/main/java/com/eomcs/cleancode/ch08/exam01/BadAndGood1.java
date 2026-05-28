package com.eomcs.cleancode.ch08.exam01;

import java.util.HashMap;
import java.util.Map;

// 예제 1: 외부 코드 사용하기 - Map을 그대로 반환하지 말고 래퍼 클래스로 감싸라
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Sensor {
    private String id;
    Sensor(String id) { this.id = id; }
    String getId() { return id; }
  }

  // Bad: Map을 그대로 반환한다.
  // - Map의 clear(), put(), remove() 등 모든 기능이 외부에 노출된다.
  // - 호출자가 센서 저장 방식이 Map이라는 사실을 알아야 한다.
  // - 외부 코드가 내부 상태를 임의로 변경할 수 있다.
  // - 저장 방식을 DB나 캐시로 바꾸면 호출 코드도 함께 바꿔야 한다.
  static class BadSensorService {
    private Map<String, Sensor> sensors = new HashMap<>();

    public Map<String, Sensor> getSensors() {
      return sensors; // 내부 구현이 그대로 노출된다
    }
  }

  static class BadSensorClient {
    void run(BadSensorService sensorService) {
      Map<String, Sensor> sensors = sensorService.getSensors();

      Sensor sensor = sensors.get("SENSOR-001");
      System.out.println(sensor.getId());
      sensors.clear();                          // 외부에서 전체 센서 데이터 삭제 가능
      sensors.put("TEMP", new Sensor("TEMP")); // 외부에서 임의로 추가 가능
    }
  }

  // -----------------------------------------------------------------------

  // Good: Map을 클래스 안에 숨기고 의도 있는 메서드만 노출한다.
  // - Map이 내부 구현 세부사항으로 숨겨진다.
  // - 호출자는 getById()처럼 의미 있는 메서드만 사용한다.
  // - 외부에서 내부 컬렉션을 함부로 변경할 수 없다.
  // - 저장 방식이 바뀌어도 호출 코드는 거의 영향을 받지 않는다.
  static class Sensors {
    private final Map<String, Sensor> sensors = new HashMap<>();

    public Sensor getById(String id) {
      return sensors.get(id);
    }

    public void register(Sensor sensor) {
      sensors.put(sensor.getId(), sensor);
    }
  }

  static class GoodSensorClient {
    void run(Sensors sensors) {
      Sensor sensor = sensors.getById("SENSOR-001"); // 의도 있는 메서드만 사용한다
      System.out.println(sensor.getId());
    }
  }
}
