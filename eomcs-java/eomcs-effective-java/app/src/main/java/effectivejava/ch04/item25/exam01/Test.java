// # 아이템 25. 톱레벨 클래스는 한 파일에 하나만 담으라
// - 소스 파일 하나에는 반드시 톱레벨 클래스(혹이 톱레벨 인터페이스)를 하나만 담자.
// - 소스 파일 하나에 톱레벨 클래스를 여러 개 선언하더라도 컴파일러가 컴파일하는 데는 아무런 문제가 없다.
// - 하지만 유지보수 측면에서 보면 좋지 않다.
// - 관련된 클래스를 묶고 싶다면 차라리 중첩 클래스를 사용하라.

package effectivejava.ch04.item25.exam01;

// [주제] 톱레벨 클래스를 사용하기
// - Item 24에서 만든 List, MyList, Iterator를 각각 별도의 파일로 분리한다.

public class Test {
  public static void main(String[] args) throws Exception {
    MyList list = new MyList();
    list.add("홍길동");
    list.add("임꺾정");
    list.add("유관순");
    list.add("안중근");
    list.add("윤봉길");

    // 익명 클래스로 만든 Iterator 인스턴스 사용
    Iterator it = list.iterator();

    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
