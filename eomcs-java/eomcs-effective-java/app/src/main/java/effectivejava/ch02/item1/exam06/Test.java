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
package effectivejava.ch02.item1.exam06;

// 단점1: public이나 protected 생성자가 없기 때문에 상속 불가하다.
// 예)java.util.Collections, java.util.Arrays, java.util.Objects 등
// - final 클래스이거나, private 생성자만 있는 클래스다.
//
// class MyCollections extends java.util.Collections {}
// class MyArrays extends java.util.Arrays {}
// class MyObjects extends java.util.Objects {}

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Test {
  public static void main(String[] args) throws Exception {
    // 단점2: 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
    // - API 문서를 잘 써놓고 메서드 이름도 널리 알려진 규약을 따라 짓는 식으로 문제를 줄여야 한다.
    //   예) `from`, `valueOf`, `of`, `instance` | `getInstance`,
    //       `create` | `newInstance`, `getType`, `newType`,  `type`
    //       등의 이름을 사용한다.

    // from():
    Date d = Date.from(java.time.Instant.now());

    // of():
    enum Rank {
      ACE,
      TWO,
      THREE,
      FOUR,
      FIVE,
      SIX,
      SEVEN,
      EIGHT,
      NINE,
      TEN,
      JACK,
      QUEEN,
      KING
    }
    Set<Rank> faceCards = EnumSet.of(Rank.JACK, Rank.QUEEN, Rank.KING);

    // valueOf():
    BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);

    // instance() | getInstance(): 같은 인스턴스임을 보장하지는 않는다.
    StackWalker luke = StackWalker.getInstance();

    // create() | newInstance(): 매번 새로운 인스턴스 반환한다.
    Object newArray = Array.newInstance(Integer.class, 10);

    // getType(): 다른 클래스의 인스턴스를 반환한다.
    FileStore fs = Files.getFileStore(Path.of("./test.txt"));

    // newType(): 다른 클래스의 인스턴스를 반환한다.
    BufferedReader br = Files.newBufferedReader(Path.of("./test.txt"));

    // type(): getType()과 newType()의 간결한 버전
    Vector<String> vector = new Vector<>();
    vector.add("apple");
    vector.add("banana");
    vector.add("cherry");
    List<String> strings = Collections.list(vector.elements());
  }
}
