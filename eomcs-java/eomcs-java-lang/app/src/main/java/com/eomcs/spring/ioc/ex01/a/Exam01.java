// 스프링 IoC 컨테이너 사용
package com.eomcs.spring.ioc.ex01.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {
  public static void main(String[] args) {
    // IoC(Inversion Of Control) 컨테이너
    // => bean container 라고도 부른다.
    // => 인스턴스의 생성과 관리를 담당한다.
    // => 각 객체가 의존하는 객체(dependency)를 자동으로 주입해 준다.
    //    "의존 객체 주입(dependency injection; DI)"이라 부른다.
    //    그래서 "DI 컨테이너"라고도 부른다.
    //
    // Spring IoC 컨테이너
    // => spring.io 사이트에서 제공하는 프레임워크이다.
    // => 프로젝트에 Spring IoC 컨테이너 포함하기
    //    mvnrepository.com 또는 search.maven.org에서
    //    spring-context 로 라이브러리를 검색한다.
    // => build.gradle 에 의존 라이브러리 정보를 추가한다.
    // => '$ gradle eclipse'를 실행하여 라이브러리 파일을 다운로드 받고
    //    이클립스 설정 파일을 갱신한다.
    // => 이클립스에서 프로젝트 정보를 갱신한다.
    //
    // ApplicationContext 인터페이스
    // => 스프링 IoC 컨테이너의 사용 규칙을 정의한 인터페이스이다.
    // => 모든 스프링 IoC 컨테이너는 이 규칙에 따라 정의되어 있다.
    //
    // ApplicationContext 구현체(implements, 인터페이스를 구현한 클래스 또는 그 클래스의 인스턴스)의 종류
    // 1) XML 파일에서 설정 정보를 읽어들이는 IoC 컨테이너
    // - ClassPathXmlApplicationContext : 설정 파일을 자바 CLASSPATH 경로에서 찾는다.
    // - FileSystemXmlApplicationContext : 설정 파일을 OS 경로에서 찾는다.
    // 2) 자바 클래스 파일의 애노테이션에서 설정 정보를 읽어 들이는 IoC 컨테이너
    // - AnnotationConfigApplicationContext : 설정 정보를 자바 클래스에서 찾는다.
    //

    // 1) 자바 CLASSPATH 에서 설정 파일을 찾는 IoC 컨테이너
    // => 자바 CLASSPATH?
    //    1) JVM을 실행할 때 -classpath 옵션으로 지정한 경로
    //       예) $java -classpath c:\a\b;d:\xxx\yyy\zzz;c:\bb\x.jar Hello
    //    2) 만약 -classpath 경로가 지정되어 있지 않으면,
    //       OS에서 CLASSPATH 환경 변수로 등록한 경로
    //
    // => 파일 경로이기 때문에 패키지와 패키지 사이에는 . 대신에 /를 사용해야 한다.
    //
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(
        "com/eomcs/spring/ioc/ex01/a/application-context.xml");

    System.out.println("실행 완료!");
  }
}



