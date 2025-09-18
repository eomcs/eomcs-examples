package com.eomcs.lang.ex99;

//# 콘솔로 출력하기 II - 특정 형식을 갖춘 문자열 안에 값을 삽입하여 출력하기
//
public class Exam0120 {
  public static void main(String[] args) {

    //형식을 지정하지 않으면 print()와 같다. 
    System.out.printf("Hello!\n");

    // %s : 지정한 자리에 문자열을 삽입한다.
    //      삽입할 값은 오른쪽에 설정한다.
    System.out.printf("이름: %s\n", "홍길동");
    System.out.printf("안녕하세요! %s입니다.\n", "임꺽정");

    // %d : 정수 값을 10진수 문자열로 만들어 삽입한다.
    // %x : 정수 값을 16진수 문자열로 만들어 삽입한다.
    // %c : 정수 값을 문자로 만들어 삽입한다.
    // %b : true/false 값을 문자열로 만들어 삽입한다.
    System.out.printf("%d %x %c %b\n", 65, 65, 65, false);

    // 한 개의 값을 여러 곳에 삽입할 수 있다.
    // %[n$]s : n은 문자열에 삽입될 값의 순서이다. 순서는 1부터 증가한다.
    System.out.printf("%d %1$x %1$c\n", 65);
    System.out.printf("%3$d %1$x %2$c\n", 65, 66, 67); // 3$(67), 1$(65), 2$(66)

    // 값을 삽입할 때 사용할 공간을 지정할 수 있다.
    // 문자열을 삽입할 때: 
    //   %[-][사용할공간너비]s : -는 왼쪽 정렬이다. 안 붙이면 기본 오른쪽 정렬이다.
    System.out.printf("'%-10s' '%10s'\n", "홍길동", "임꺽정");
    System.out.printf("'%-10d' '%10d'\n", 12345, 12345);

    // 정수를 삽입할 때:
    //   %[0][사용할공간너비]d : 앞의 빈자리는 0으로 채운다.
    //   %[+][0][사용할공간너비]d : +는 숫자 앞에 부호를 붙인다.
    System.out.printf("'%010d' '%07d'\n", 12345, 12345);
    System.out.printf("'%+010d' '%+07d'\n", 12345, -12345);
  }
}
















