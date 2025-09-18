//# 캡슐화 문법 사용 후 - 캡슐화와 게터/세터
package com.eomcs.oop.ex08.a;

class Score4 {

  private String name;
  // name은 직접 접근해도 되는데, 프로그래밍의 일관성을 위해
  // 다른 필드처럼 직접 접근을 막고 getter/setter를 통해 값을 다루도록 한다.
  // 이렇게 하면 나중에 name에 대해
  // 값의 유효성을 검사하는 코드를 즉시 삽입할 수 있어 유지보수에도 도움이 된다.
  //
  // 그래서 실무에서는 그냥 모든 필드를 private이나 protected로 접근을 제한한 다음에
  // setter/getter를 두는 방식으로 프로그래밍을 한다.
  // setter/getter가 필요없는 필드라도 그냥 관성적으로 그렇게 한다.
  // 고민하지 말고 여러분도 그냥 이렇게 하라!
  // setter에서 유효성을 검사하지 않더라도 그냥 만들라!
  //
  // - 즉 필드는 캡슐화 문법을 통해 외부의 접근 제한하고,
  //   세터/게터 메서드를 통해 값을 설정/조회하게 만든다.
  // - name, kor, eng, math 필드 같은 경우
  //   값을 설정하고 조회도 해야 하기 때문에 게터/세터가 모두 있다.
  // - sum, aver 필드 같은 경우
  //   값을 조회만 해야 하기 때문에 게터만 있다.
  // - 이렇게 필드에 대해서 항상 게터/세터를 모두 만드는 것이 아니다.
  //
  // 용어 주의!
  // - name, kor, eng, math, sum, aver 는 "필드(field)" 라 부른다.
  // - getXxx()/setXxx()는 "프로퍼티(property)"라 부른다.
  // - 필드를 프로퍼티라 부르는 것이 아니다!
  //   게터/세터를 프로퍼티라 부르는 것이다.

  // 점수를 변경할 때 계산을 다시 해야 하고, 유효하지 않은 값을 넣지 못하도록 막아야 한다.
  // 따라서 직접 접근하는 것을 막는다.
  private int kor;
  private int eng;
  private int math;

  // 계산 결과를 조작하지 못하도록 접근을 제한하자!
  private int sum;
  private float aver;

  // 대신 결과 값을 꺼낼 수 있는 메서드(getter)를 제공해야 한다.

  public void setName(String name) {
    // 이렇게 유효성을 검사하지 않더라도 setter를 그냥 만들라!
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getKor() {
    return this.kor;
  }
  public void setKor(int kor) {
    if (kor >= 0 && kor <= 100) { // 유효한 점수인 경우에만 저장한다.
      this.kor = kor;
      this.compute(); // 유효한 값이라면 다시 합계와 평균을 계산한다.
    }
  }

  public int getEng() {
    return this.eng;
  }
  public void setEng(int eng) {
    if (eng >= 0 && eng <= 100) { // 유효한 점수인 경우에만 저장한다.
      this.eng = eng;
      this.compute(); // 유효한 값이라면 다시 합계와 평균을 계산한다.
    }
  }

  public int getMath() {
    return this.math;
  }
  public void setMath(int math) {
    if (math >= 0 && math <= 100) {// 유효한 점수인 경우에만 저장한다.
      this.math = math;
      this.compute(); // 유효한 값이라면 다시 합계와 평균을 계산한다.
    }
  }

  public int getSum() {
    return this.sum;
  }

  public float getAver() {
    return this.aver;
  }

  // 점수를 변경할 때 마다 호출되기 때문에 임의로 호출할 필요가 없다.
  // 따라서 비공개로 만든다.
  // 초보 개발자의 많은 착각!
  // - 필드는 무조건 private,
  //   메서드는 무조건 public 으로 해야 한다고 생각한다.
  // - 착각이다.
  //   필드든 메서드든 공개할 것은 공개하고 공개하지 말아야 하는 것은 공개하지 말라.
  // - 기본이 비공개이고, 공개할 것만 공개하라!
  //   이렇게 하는 것이 클래스가 잘못 사용되는 상황을 방지할 수 있다.
  //
  private void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}

public class Exam0410 {
  public static void main(String[] args) {

    Score4 s1 = new Score4();

    s1.setName("홍길동");
    s1.setKor(100);
    s1.setEng(90);
    s1.setMath(80);

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.getName(),
        s1.getKor(), s1.getEng(), s1.getMath(),
        s1.getSum(), s1.getAver());
  }
}

// 정리!
// # 객체지향 프로그래밍(Object-Oriented Programming; OOP)의 특징
// 1) 추상화(abstraction)
//   - 프로그램에서 다루는 데이터나 코드를 클래스로 정의하는 행위.
//   - 클래스 멤버(스태틱 멤버) : 스태틱 필드, 스태틱 블록, 스태틱 메서드
//   - 인스턴스 멤버 : 인스턴스 필드, 인스턴스 블록, 인스턴스 메서드, 생성자
//
// 2) 상속(inheritance)
//   - 기능 확장을 위한 문법
//
// 3) 캡슐화(encapsulation)
//   - 외부의 접근을 제어하는 문법
//
// 4) 다형성(polymorphism)
//   - 하나의 코드가 여러 용도로 쓰이게 하는 것.
//   - 오버로딩(overloading)
//     - 메서드 시그너처가 다르더라도 같은 기능을 하는 메서드에 대해 같은 이름을 갖게하여
//       일관성 있는 프로그래밍을 하게 도와주는 문법.
//   - 오버라이딩(overriding)
//     - 상속 받은 메서드를 자신의 역할에 맞게 재정의 하는 것.
//   - 다형적 변수(polymorphic variable)
//     - 하나의 변수가 여러 타입을 가리킬 수 있고, 다양한 타입으로 다뤄질 수 있게 도와주는 문법.
//
// # 캡슐화
// - 클래스 멤버나 인스턴스 멤버의 접근을 제한하는 문법이다.
// - 이유? 잘못된 사용으로 결과가 왜곡되는 것을 막기 위함이다.
// - 정의된 대로 역할을 수행하게 도와준다.
// - 문법: 클래스 멤버나 인스턴스 멤버 선언할 때 접근 제한자(modifier)를 붙인다.
// - 접근 제한자
//   - private : 클래스에 소속된 같은 멤버만 접근 가능
//   - (default) : 같은 패키지에 소속된 멤버만 접근 가능
//   - protected : 같은 패키지에 소속되거나 자손 클래스의 멤버만 접근 가능
//   - public : 모두 접근 가능
//
// # getter/setter
// - 캡슐화와 더불어 사용되는 기술이다.
// - 필드에 대해 외부의 직접적인 접근을 막는 대신 메서드를 통해 값을 변경, 조회하도록 유도한다.
// - 메서드에서 값의 유효 범위를 검사하여 변경을 허락할 수 있다.
// - getter/setter 를 다른 말로 "프로퍼티(property)"라 부른다.
//   - getter만 있는 경우: read only 프로퍼티
//   - setter만 있는 경우: write only 프로퍼티
//   - getter/setter 모두 있는 경우: read/write 프로퍼티
//









