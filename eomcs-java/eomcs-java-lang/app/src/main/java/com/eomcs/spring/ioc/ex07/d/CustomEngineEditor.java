// 프로퍼티 에디터 만들기
package com.eomcs.spring.ioc.ex07.d;

import java.beans.PropertyEditorSupport;
import com.eomcs.spring.ioc.ex07.Engine;

public class CustomEngineEditor extends PropertyEditorSupport {
  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    String[] values = text.split(","); // 예) "비트자동차,16,4" ==> {"비트자동차", "16", "4"}

    Engine engine = new Engine();
    engine.setMaker(values[0]);
    engine.setValve(Integer.parseInt(values[1]));
    engine.setCylinder(Integer.parseInt(values[2]));

    this.setValue(engine);
  }
}


