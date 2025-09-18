// 디자인 패턴과 추상 클래스 - 템플릿 메서드 패턴
package com.eomcs.oop.ex10.c;

// 수퍼 클래스에서 추상적으로 표현하고, 서브 클래스에서 그 구체적인 내용을 결정하는 설계 방식
// 즉 수퍼 클래스에서 전체적인 논리 흐름(예: build() 메서드)을 정의하고,
// 서브 클래스에서 각 흐름에 따라 구체적인 동작(예: startEffect(), endEffect())을 정의한다. 
public abstract class Building {

  // 다음과 같이 일반 메서드에서 
  // 전체적인 논리 흐름(명령어 처리의 흐름)을 정의한다.
  // 서브 클래스들은 이 메서드를 그대로 상속 받기 때문에 재정의하지 않는 이상에는
  // 모든 서브 클래스들이 건물을 지을 때 동일한 방식으로 작업할 것이다.
  public void build() {
    System.out.println("건축하겠습니다!");
    startEffect();

    System.out.println("건축이 완료되었습니다!");
    endEffect();
  }

  // 템플릿 메서드
  // => 아직 구체적으로 어떤 명령을 실행할 지 정의되지 않은 상태
  //    즉 틀만 갖춘 상태
  // => 추상 메서드를 사용하여 템플릿 메서드를 선언한다.
  public abstract void startEffect();
  public abstract void endEffect();
}





