package bitcamp;

import javax.servlet.ServletContext;
import com.eomcs.web.MyWebInitializer;

public class MyWebInitializerImpl3 implements MyWebInitializer {
  @Override
  public void start(ServletContext ctx) {
    System.out.println("MyWebInitializerImpl3.. ok!");
  }
}
