package com.eomcs.lang.ex05;

//# 비트 연산자 : 응용 V - 사용자 권한 관리에 적용
//
public class Exam0480 {
  public static void main(String[] args) {
    // 1) 사용자 권한을 값으로 정의
    final int LOGOUT = 0x01;    // 00000001   - 로그아웃
    final int GENERAL = 0x02;   // 00000010   - 일반 로그인
    final int ADMIN = 0x04;     // 00000100   - 관리자 로그인

    // 2) 메뉴의 접근 범위 설정
    int menu1 = LOGOUT; // 로그아웃 상태에서만 접근 가능한 메뉴
    int menu2 = GENERAL; // 일반으로 로그인 된 사용자만 접근 가능한 메뉴
    int menu3 = ADMIN; // 관리자로 로그인 된 사용자만 접근 가능한 메뉴
    int menu4 = LOGOUT | GENERAL | ADMIN; // 로그아웃 되었든, 일반으로 로그인 되었든, 관리자로 로그인 되었든 모두 접근 가능한 메뉴

    // 3) 접근 테스트
    // => menu1이 로그아웃 상태에서 접근 가능한 것인지 검사한다.
    System.out.println((menu1 & LOGOUT) > 0);
    // 계산 원리
    //    menu1:    00000001
    //    LOGOUT:   00000001
    //            &-----------
    //              00000001   <---- 0 보다 큰 값이 나온다.
    // 해설: 
    // 어떤 값에 대해 LOGOUT 값을 & 한다는 것은
    // LOGOUT 비트(오른쪽에서 첫 번째 비트)가 1인지 검사한다는 뜻이다. 

    System.out.println((menu1 & GENERAL) > 0);
    // 계산 원리
    //    menu1:    00000001
    //    GENERAL:  00000010
    //            &-----------
    //              00000000   <---- 0 
    // 해설: 
    // 어떤 값에 대해 GENERAL 값을 & 한다는 것은
    // GENERAL 비트(오른쪽에서 두 번째 비트)가 1인지 검사한다는 뜻이다.

    System.out.println((menu1 & ADMIN) > 0);
    // 계산 원리
    //    menu1:    00000001
    //    ADMIN:    00000100
    //            &-----------
    //              00000000   <---- 0 
    // 해설: 
    // 어떤 값에 대해 ADMIN 값을 & 했을 때 true 가 나온다면,
    // 그 값을 2진수로 보았을 때 ADMIN 비트가 1이라는 의미다. 
    //   
  }
}
