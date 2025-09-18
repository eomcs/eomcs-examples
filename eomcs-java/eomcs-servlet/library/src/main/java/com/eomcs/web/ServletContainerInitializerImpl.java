package com.eomcs.web;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

// 서블릿 컨테이너가 시작될 때 보고를 받고 싶으면,
// => SerlvetContainerIntializer 규칙에 따라 작성해야 한다.
// => 이 클래스의 전체 이름(패키지명을 포함한 클래스명)을 다음 경로의 파일에 등록해야 한다.
//       /META-INF/services/javax.servlet.ServletContainerInitializer 파일
//
// 서블릿 컨테이너가 시작할 때 실행 과정
// 1) 모든 *.jar 파일에서 META-INF/services/javax.servlet.ServletContainerInitializer 파일을 있는지 찾는다.
// 2) javax.servlet.ServletContainerInitializer 파일 안에 등록된 클래스의 인스턴스를 생성한다.
// 3) ServletContainerInitializer 구현 객체에 대해 onStartup() 메서드를 호출한다.
//    - onStartup() 메서드를 호출할 때 파라미터 값 중에서 types 목록 값은,
//      ServletContainerInitializer 구현 클래스의 @HandlesTypes 에 선언된
//      그 클래스를 상속 받거나 구현한 모든 클래스들의 목록이다.
//
// 4) 웹 애플리케이션을 시작한다.
//    - ServletContextListener의 contextInitialized() 메서드를 호출한다.
//
@HandlesTypes(MyWebInitializer.class)
public class ServletContainerInitializerImpl implements ServletContainerInitializer{

  @Override
  public void onStartup(Set<Class<?>> types, ServletContext ctx) throws ServletException {
    System.out.println("ServletContainerInitializerImpl.onStartup()...호출됨!");
    if (types == null)
      return;

    // 이 라이브러리 외부에 구현된 MyWebInitializer 구현체를 찾아 객체를 생성한다.
    for (Class<?> clazz : types) {
      try {
        System.out.println(clazz.getName() + ".start() 호출함!");
        MyWebInitializer obj =
            (MyWebInitializer) clazz.getConstructor().newInstance();
        obj.start(ctx);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}






