// Data Persistence Framework 도입 - 좀 더 쉽게 Mybatis 설정 파일의 InputStream 얻는 방법
package com.eomcs.mybatis.ex01.a;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    // 1. mybatis 설정 파일을 읽을 InputStream 도구를 준비한다.
    // => 다음과 같이 mybatis 설정 파일이 있는 경로를 직접 지정하면
    //    애플리케이션의 배포 경로가 바뀔 때 마다 파일 경로를 바꾸기 위해
    //    소스를 변경하고 다시 컴파일 해야 하는 문제가 있다.
    //      InputStream mybatisConfigInputStream = new FileInputStream(
    //           "./bin/main/com/eomcs/mybatis/ex01/a/mybatis-config.xml");

    // => 이런 문제를 해결하기 위해 Mybatis는 도우미 객체를 제공한다.
    // => Resources 클래스의 메서드를 이용하면
    //    자바 클래스가 있는 패키지 폴더의 mybatis 설정 파일을 바로 지정할 수 있다.
    //
    InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex01/a/mybatis-config.xml");
    // => 파라미터에 전달할 mybatis 설정 파일의 경로를 지정할 때,
    //    자바 패키지 경로를 그대로 지정하면 된다.
    //    프로젝트의 경로를 지정할 필요가 없다.
    // => 단 파일 경로이기 때문에 폴더와 폴더 사이를 가리킬 때
    //    . 대신에 / 를 사용해야 한다.
    // => JVM은 현재 실행하는 애플리케이션의 자바 클래스 경로를 알고 있다.
    // => 주의!
    //    자바 패키지 경로에서 찾기 때문에 mybatis 설정 파일은 반드시
    //    자바 패키지 경로에 있어야 한다.
    //

    SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();

    SqlSessionFactory factory = factoryBuilder.build(mybatisConfigInputStream);

    SqlSession sqlSession = factory.openSession();

    System.out.println("mybatis 사용 준비 완료!");

    sqlSession.close();
  }

}


