package com.eomcs.spring.ioc.ex08.c3;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

// @Autowired 애노테이션을 처리해주는 역할을 한다.
// 스프링 IoC 컨테이너가 객체를 생성한 후 보고를 하면
// 이 클래스는 생성된 객체에서 @Autowired가 붙은 세터를 찾는다.
// 있다면 세터를 호출하여 의존 객체를 주입한다.
// 의존 객체가 없다면 그 의존 객체가 생성될 때까지
// 별도로 담아 둔다.
// 의존 객체가 생성되는 순간 즉시 별도로 담아 둔 그 객체에 대해 셋터를 호출할 것이다.
//
public class MyAutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

  // 생성된 모든 객체를 기록한다.
  HashMap<Class<?>, List<Object>> beans = new HashMap<>();

  // 파라미터 값이 준비되지 않아서 호출이 연기된 @Autowired 메서드를 기록한 맵
  HashMap<Class<?>, List<AutowiredMethod>> autowiredMethods = new HashMap<>();

  private void addBean(Class<?> type, Object bean) {
    List<Object> objList = beans.get(type); // 해당 타입의 객체 목록을 꺼낸다.
    if (objList == null) { // 해당 타입의 객체 목록이 없다면,
      objList = new ArrayList<>(); // 새로 객체를 저장할 목록을 준비한다.
      beans.put(type, objList); // 해당 타입의 객체를 저장할 목록을 맵에 추가한다.
    }
    objList.add(bean); // 해당 타입의 객체 목록에 새 객체를 추가한다.
  }

  private Object getBean(Class<?> type) {
    List<Object> objList = beans.get(type); // 해당 타입의 객체 목록을 꺼낸다.
    if (objList == null) { // 해당 타입의 객체 목록이 없다면,
      return null;
    }
    return objList.get(0); // 객체 목록에서 첫 번째 객체를 꺼낸다.
  }

  // 객체에 대해 모든 초기화가 끝난 후에 @Autowired 애노테이션을 처리하자!
  // 따라서 다음 메서드만 오버라이딩 한다.
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.out.println("MyAutowiredAnnotationBeanPostProcessor.postProcessAfterInitialization()");

    // 새로 생성된 객체를 기록한다.
    addBean(bean.getClass(), bean);

    // 해당 빈에서 @Autowired가 붙은 메서드를 찾아 호출하거나 호출을 연기시킨다.
    callAutowiredMethod(bean);

    // 해당 빈을 필요로하는 셋터 메서드를 찾아 호출한다.
    injectDependency(bean);

    return bean;
  }

  private void addAutowiredMethod(Class<?> paramType, AutowiredMethod autowiredMethod) {

    // 해당 의존 객체에 대해 나중에 호출하기 위해 모아둔 메서드 목록을 꺼낸다.
    List<AutowiredMethod> methods = autowiredMethods.get(paramType);

    if (methods == null) { // 의존 객체를 주입할 메서드 목록이 없다면,
      methods = new ArrayList<>(); // 메서드 목록을 새로 준비한다.
      autowiredMethods.put(paramType, methods); // 그리고 맵에 등록한다.
    }

    // 목록에 셋터 메소드 정보(셋터메서드 + 객체)를 추가한다.
    methods.add(autowiredMethod);
  }


  // 객체의 @Autowired 가 붙은 메서드를 모두 찾아 호출한다.
  private void callAutowiredMethod(Object bean) {
    // 이 객체의 모든 public 메서드를 꺼낸다.
    Method[] methods = bean.getClass().getMethods();

    // public 메서드 중에서 @Autowired로 표시된 메서드를 찾는다.
    for (Method m : methods) {

      // 메서드에서 @Autowired 애노테이션이 있는지 확인한다.
      Autowired anno = m.getAnnotation(Autowired.class);

      if (anno == null) {
        continue;
      }

      // @Autowired 가 붙은 메서드를 찾았으면 호출하여 의존 객체를 주입한다.

      // => 먼저 어떤 타입의 의존 객체인지 알아낸다. 메서드의 파라미터 타입을 알아낸다.
      Class<?> paramType = m.getParameters()[0].getType();

      // => 파라미터 타입의 객체를 보관소에서 꺼낸다.
      Object dependency = this.getBean(paramType);

      if (dependency != null) { // 해당 의존 객체가 있다면
        // 셋터를 호출한다.
        try {
          m.invoke(bean, dependency);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else { // 셋터가 원하는 파라미터 값이 없다면,
        // 일단 그 값이 나타날 때까지 호출을 연기하자!
        // 즉 호출될 메서드 목록에 추가한다.
        addAutowiredMethod(paramType, new AutowiredMethod(bean, m));
      }
    }
  }

  private void injectDependency(Object dependency) {
    // 이 메서드에서 파라미터로 받은 객체를 원하는 셋터가 있는지 확인한다.
    // => 즉 이 타입의 객체를 받기 위해 호출이 연기된 메서드가 있는지 확인한다.
    List<AutowiredMethod> setters = autowiredMethods.get(dependency.getClass());

    if (setters == null) {
      return;
    }

    // 이 객체를 간절히 원하는 셋터 메서드가 있다면 호출하여 주입한다.
    while (setters.size() > 0) {
      AutowiredMethod setter = setters.remove(0);
      try {
        setter.method.invoke(setter.object, dependency);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  class AutowiredMethod {
    Object object; // 메서드를 호출할 때 사용할 인스턴스
    Method method; // @Autowired가 붙은 메서드

    public AutowiredMethod(Object object, Method method) {
      this.object = object;
      this.method = method;
    }
  }
}


