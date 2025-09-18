package com.eomcs.lang.ex07;

//# 메서드 : JVM 메모리
//
public class Exam0410 {

  static void swap(int a, int b) {
    int temp = a;
    a = b;
    b = temp;
    System.out.printf("swap(): a=%d, b=%d\n", a, b);
  }

  public static void main(String[] args) {
    int a = 100;
    int b = 200;
    swap(a, b);
    System.out.printf("main(): a=%d, b=%d\n", a, b);
  }
}
// 실행 순서와 메모리
// 1) java -classpath bin com.eomcs.lang.ex07.Exam0410
//    => JVM은 클래스 정보를 Method Area 영역에 로드한다.
// 2) main() 호출
//    => JVM Stack 영역에 main() 메서드가 사용할 로컬 변수를 준비한다.
// 3) swap() 호출
//    => JVM Stack 영역에 swap() 메서드가 사용할 로컬 변수를 준비한다.
// 4) swap() 실행 완료
//    => JVM Stack 영역에 있던 swap()이 사용한 메모리를 제거한다.
// 5) main() 실행 완료
//    => JVM Stack 영역에 있던 main()이 사용한 메모리를 제거한다.
// 6) JVM 실행 종료
//    => OS가 JVM에게 사용하라고 빌려줬던 모든 메모리를 회수한다.


// JVM이 메모리를 다루는 방법
// - 크게 다음 세가지 영역으로 나눠 관리한다.
//
// 1) Method Area
// - 클래스 명령 코드를 둔다.
// - static 변수를 둔다.
//
// 2) Heap
// - new 명령으로 만든 메모리(인스턴스=객체)를 둔다.
// - Garbage Collector(GC)가 관리하는 영역이다.
//
// 3) JVM Stack
// - 스레드 별로 JVM Stack 메모리를 따로 관리한다.
// - 메서드의 로컬 변수는 둔다.
// - 각 메서드마다 프레임 단위로 관리한다.
// - 메서드 호출이 끝나면 그 메서드가 사용한 프레임 메모리가 제거된다.
// - 이렇게 메서드가 호출될 때 로컬 변수가 준비되고 
//   맨마지막에 호출한 메서드가 먼저 삭제된다고 해서
//   "스택(stack)" 메모리라 부른다.
//   스택? 접시 쌓는 것을 생각하라!
// - 스택 방식을 "Last In First Out(LIFO;후입선출, FILO;선입후출)"라 부른다. 
//
// JVM이 종료하면 JVM이 사용했던 모든 메모리를 OS가 회수한다.
