// # 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라
//
// ## 장점
// - 이름을 가질 수 있다.
// - 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
// - 반환 타입의 하위 타입 객체를 반환할 수 있다.
// - 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
// - 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
//
// ## 단점
// - 상속을 하려면 public이나 protected 생성자가 필요하다.
// - 정적 팩터리 메서드만 제공하는 클래스는 확장할 수 없다.
// - 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
// - API 문서에서 정적 팩터리 메서드를 잘 찾아봐야 한다.
// - 따라서 메서드 이름은 널리 알려진 규약을 따라 짓는 것이 좋다.
//   예) `from`, `valueOf`, `of`, `instance` | `getInstance`,
//       `create` | `newInstance`, `getType`, `newType`,  `type`
//       등의 이름을 사용한다.
package effectivejava.ch02.item1;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

public class Exam05 {
  public static void main(String[] args) throws Exception {
    // 장점5: 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
    // - 서비스 제공자 프레임워크를 만들 때 유용하다.
    // - 서비스 제공자 프레임워크의 예:
    //   - JDBC API
    //   - JNDI API
    //   - Java Cryptography Architecture(JCA)
    //   - Java Authentication and Authorization Service(JAAS)
    //   - Java Image I/O API
    //   - Java Sound API
    //   - Java XML API
    // - 서비스 제공자 프레임워크의 핵심 컴포넌트
    //   - 서비스 인터페이스: 구현체의 동작을 정의.
    //     예) `Connection`
    //   - 제공자 등록 API: 제공자가 구현체를 등록할 때 사용.
    //     예) `DriverManager.registerDriver()`
    //   - 서비스 접근 API: 클라이언트가 서비스의 인스턴스를 얻을 때 사용. 유연한 정적 팩토리 메서드.
    //     예) `DriverManager.getConnection()`
    //   - 서비스 제공자 인터페이스: 서비스 인터페이스의 인스턴스를 생성하는 팩토리 객체.
    //     예) `Driver`

    // 서비스 제공자 인터페이스
    Driver postgresDriver = new org.postgresql.Driver();

    // 서비스 제공자 등록
    DriverManager.registerDriver(postgresDriver);

    // 서비스 접근: 정적 팩토리 메서드를 호출하여 서비스 구현체를 얻는다.
    Connection con =
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres", "postgres", "1111");

    // 서비스 인터페이스 사용
    Statement stmt = con.createStatement();

    stmt.close();
    con.close();

    // 참고: "범용 서비스 제공자 프레임워크"
    // - Java 6부터 제공하기 시작했다.
    // - 서비스 제공자 프레임워크를 직접 만들지 않고 서비스 제공자 API를 만들고 사용할 수 있게 해준다.
    // - `java.util.ServiceLoader` 클래스
    // - 서비스 제공자 프레임워크를 쉽게 만들고 사용할 수 있게 해준다.
    // - .jar 파일의 `META-INF/services` 디렉터리에 서비스 제공자 인터페이스의 이름과 같은 파일을 만들고,
    //   그 파일에 서비스 제공자 클래스의 이름을 적어 넣으면 된다.
    // - JDBC API 및 Spring Framework도 이 범용 서비스 제공자 프레임워크를 사용하여 구현되었다.
  }
}
