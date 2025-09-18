package bitcamp.app1;

import java.beans.PropertyEditorSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

// @ControllerAdvice
// => 이름에 이미 역할에 대한 정보가 담겨있다.
// => 페이지 컨트롤러를 실행할 때 충고하는 역할을 수행한다.
//    즉 프론트 컨트롤러가 페이지 컨트롤러의 request handler를 호출하기 전에
//    이 애노테이션이 붙은 클래스를 참고하여 적절한 메서드를 호출한다.
//
@ControllerAdvice
public class GlobalControllerAdvice {

  // 이 클래스에 프로퍼티 에디터를 등록하는 @InitBinder 메서드를 정의한다.
  @InitBinder
  public void initBinder(WebDataBinder binder) {

    DatePropertyEditor propEditor = new DatePropertyEditor();
    binder.registerCustomEditor(java.util.Date.class, propEditor);

    binder.registerCustomEditor(Car.class, new CarPropertyEditor());

    binder.registerCustomEditor(Engine.class, new EnginePropertyEditor());
  }

  class DatePropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      try {
        // String ==> java.util.Date
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // Date date = format.parse(text);
        // setValue(date);

        // String ==> java.sql.Date
        setValue(java.sql.Date.valueOf(text));

      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    }
  }

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
      engine.setValve(Integer.parseInt(values[1]));

      setValue(engine);
    }
  }
}


