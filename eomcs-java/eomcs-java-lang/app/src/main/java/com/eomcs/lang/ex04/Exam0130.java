package com.eomcs.lang.ex04;

//# 변수에 값 할당
//- 변수가 가리키는 메모리에 값을 저장하는 것
//- 문법
//    변수명 = 변수 또는 리터럴;
//- 용어 
//    = : 할당 연산자(assignment operator)
//    l-value : = 왼쪽에 있는 변수를 가리킨다. l-value는 리터럴이 될 수 없다.
//    r-value : = 오른쪽에 있는 변수나 리터럴을 가리킨다.
//- 의미
//    = 오른쪽에 지정된 값을 왼쪽 변수가 가리키는 메모리에 저장.
//

public class Exam0130 {
  public static void main(String[] args) {

    int age;

    //- 문법
    //    변수명 = 값;
    age = 20; 

    //표현
    //- age 변수가 가리키는 메모리에 20을 저장하라!
    //- age라는 이름의 메모리에 20을 저장하라!
    //- age 변수에 20을 저장하라!
    //- age에 20을 저장하라! (실무에서는 주로 이 표현을 사용한다)
  }
}
