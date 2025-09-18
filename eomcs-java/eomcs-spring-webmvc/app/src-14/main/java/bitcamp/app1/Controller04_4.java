// 요청 핸들러의 아규먼트 - 프로퍼티 에디터 사용하기
package bitcamp.app1;

import java.beans.PropertyEditorSupport;
import java.io.PrintWriter;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c04_4")
public class Controller04_4 {

  // 클라이언트가 보낸 요청 파라미터 값(String 타입)을
  // request handler의 아규먼트 타입(String, int, boolean 등)의 값으로 바꿀 때
  // primitive type에 대해서만 자동으로 변환해 준다.
  // 그 외의 타입에 대해서는 프로퍼티 에디터(타입 변환기)가 없으면 예외를 발생시킨다.

  // 테스트:
  // http://.../c04_4/h1?model=sonata&capacity=5&auto=true&createdDate=2019-4-19
  @GetMapping("h1")
  @ResponseBody
  public void handler1(
      PrintWriter out,
      String model,
      @RequestParam(defaultValue = "5") int capacity, // String ===> int : Integer.parseInt(String)
      boolean auto, // String ===> boolean : Boolean.parseBoolean(String)
      Date createdDate // 프로퍼티 에디터를 설정하지 않으면 변환 오류 발생
      ) {

    out.printf("model=%s\n", model);
    out.printf("capacity=%s\n", capacity);
    out.printf("auto=%s\n", auto);
    out.printf("createdDate=%s\n", createdDate);
  }

  // 테스트:
  // http://.../c04_4/h2?car=sonata,5,true,2019-4-19
  @GetMapping("h2")
  @ResponseBody
  public void handler2(PrintWriter out,
      // 콤마(,)로 구분된 문자열을 Car 객체로 변환하기?
      // => String ===> Car 프로퍼티 에디터를 등록하면 된다.
      @RequestParam("car") Car car) {

    out.println(car);
  }

  // 테스트:
  // http://.../c04_4/h3?engine=bitengine,3500,16
  @GetMapping("h3")
  @ResponseBody
  public void handler3(PrintWriter out,
      // 콤마(,)로 구분된 문자열을 Engine 객체로 변환하기?
      // => String ===> Engine 프로퍼티 에디터를 등록하면 된다.
      @RequestParam("engine") Engine engine) {

    out.println(engine);
  }



  // 이 페이지 컨트롤러에서 사용할 프로퍼티 에디터 설정하는 방법
  // => 프론트 컨트롤러는 request handler를 호출하기 전에
  //    그 메서드가 원하는 아규먼트 값을 준비해야 한다.
  //    각 아규먼트 값을 준비할 때
  //    @InitBinder가 표시된 메서드(request handler를 실행할 때 사용할 도구를 준비하는 메서드)
  //    를 호출하여 프로퍼티 에디터(변환기)를 준비시킨다.
  //    그리고 이 준비된 값 변환기(프로퍼티 에디터)를 이용하여 파라미터 값을
  //    request handler의 아규먼트가 원하는 타입의 값을 바꾼다.
  //    request handler의 아규먼트 개수 만큼 이 메서드를 호출한다.
  // => 따라서 프로퍼티 에디터를 적용하기에
  //    @InitBinder가 표시된 메서드가 적절한 지점이다.
  //    즉 이 메서드에 프로퍼티 에디터를 등록하는 코드를 둔다.
  //

  @InitBinder
  // => 메서드 이름은 마음대로.
  // => 작업하는데 필요한 값이 있다면 파라미터로 선언하라.
  public void initBinder(WebDataBinder binder) {
    System.out.println("Controller04_4.initBinder()...");
    // 프로퍼티 에디터를 등록하려면 그 일을 수행할 객체(WebDataBinder)가 필요하다.
    // request handler 처럼 아규먼트를 선언하여
    // 프론트 컨트롤러에게 달라고 요청하라.

    //String ===> java.util.Date 프로퍼티 에디터 준비
    DatePropertyEditor propEditor = new DatePropertyEditor();

    // WebDataBinder에 프로퍼티 에디터 등록하기
    binder.registerCustomEditor(
        java.util.Date.class, // String을 Date 타입으로 바꾸는 에디터임을 지정한다.
        propEditor // 바꿔주는 일을 하는 프로퍼티 에디터를 등록한다.
        );


    // WebDataBinder에 프로퍼티 에디터 등록하기
    binder.registerCustomEditor(
        Car.class, // String을 Car 타입으로 바꾸는 에디터임을 지정한다.
        new CarPropertyEditor() // 바꿔주는 일을 하는 프로퍼티 에디터를 등록한다.
        );

    // WebDataBinder에 프로퍼티 에디터 등록하기
    binder.registerCustomEditor(Engine.class, // String을 Engine 타입으로 바꾸는 에디터임을 지정한다.
        new EnginePropertyEditor() // 바꿔주는 일을 하는 프로퍼티 에디터를 등록한다.
        );
  }

  // PropertyEditor 만들기
  // => 문자열을 특정 타입의 프로퍼터의 값으로 변환시킬 때 사용하는 에디터이다.
  // => java.beans.PropertyEditor 인터페이스를 구현해야 한다.
  // => PropertyEditor를 직접 구현하면 너무 많은 메서드를 오버라이딩 해야 하기 때문에
  //    자바에서는 도우미 클래스인 PropertyEditorSupport 클래스를 제공한다.
  //    이 클래스는 PropertyEditor를 미리 구현하였다.
  //    따라서 이 클래스를 상속 받은 것 더 낫다.
  class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      System.out.println("DatePropertyEditor.setAsText()");
      // 프로퍼티 에디터를 사용하는 측(예: 프론트 컨트롤러)에서
      // 문자열을 Date 객체로 바꾸기 위해 이 메서드를 호출할 것이다.
      // 그러면 이 메서드에서 문자열을 프로퍼티가 원하는 타입으로 변환한 후 저장하면 된다.
      try {

        // 1) String ==> java.util.Date
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // Date date = format.parse(text); // String ===> java.util.Date
        // setValue(date); // 내부에 저장

        // 2) String ==> java.sql.Date
        setValue(java.sql.Date.valueOf(text));

      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    }

    @Override
    public Object getValue() {
      System.out.println("DatePropertyEditor.getValue()");
      // 이 메서드는 프로퍼티 에디터를 사용하는 측(예: 프론트 컨트롤러)에서
      // 변환된 값을 꺼낼 때 호출된다.
      // 이 메서드를 오버라이딩 하는 이유는 이 메서드가 호출된 것을
      // 확인하기 위함이다. 원래는 오버라이딩 해야 할 이유가 없다.
      return super.getValue();
    }
  }

  // String ===> Car 프로퍼티 에디터 만들기
  class CarPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      String[] values = text.split(",");

      Car car = new Car();
      car.setModel(values[0]);
      car.setCapacity(Integer.parseInt(values[1]));
      car.setAuto(Boolean.parseBoolean(values[2]));
      car.setCreatedDate(java.sql.Date.valueOf(values[3]));

      setValue(car);
    }
  }

  class EnginePropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      String[] values = text.split(",");

      Engine engine = new Engine();
      engine.setModel(values[0]);
      engine.setCc(Integer.parseInt(values[1]));
      engine.setValve(Integer.parseInt(values[2]));

      setValue(engine);
    }
  }
}


