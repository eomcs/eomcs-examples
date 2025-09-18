// Object 클래스 - hashCode()에 대하여
package com.eomcs.basic.ex01;

public class Exam0140 {

  static class My {
    String name;
    int age;
  }

  public static void main(String[] args) {
    My obj1 = new My();
    obj1.name = "홍길동";
    obj1.age = 20;

    My obj2 = new My();
    obj2.name = "홍길동";
    obj2.age = 20;

    // Object에서 상속 받은 hashCode()는 인스턴스마다 고유의 4바이트 정수 값을 리턴한다.
    // => 이 값은 toString()의 출력 값으로 사용된다.
    System.out.println(Integer.toHexString(obj1.hashCode()));
    System.out.println(Integer.toHexString(obj2.hashCode()));

    System.out.println(obj1);
    System.out.println(obj2);

    // hash value?
    // - 데이터를 다른 데이터와 구분하기 위해 사용하는 특별한 정수 값이다.
    // - 특정 수학 공식(MD4, MD5, SHA, PGP, CRC 등)에 따라 값을 계산한다.
    // - 데이터가 같은지 비교할 때 사용한다.
    // - 즉 모든 데이터를 바이트 단위로 일일이 비교하는 대신에 미리 생성된 정수 값을 비교함으로써 
    //   빠르게 두 값이 같은지 알아낼 수 있다.
    // - 매우 낮은 확률이지만 데이터가 다르더라도 같은 해시 값이 나올 수 있다.
    //   당연하다. 큰 데이터를 특별한 계산 공식을 통해 4바이트 정수 값으로 표현한다는 것은
    //   언제든 다른 데이터에 대해 같은 값이 나올 가능성을 내포하는 것이다.
    //   다만 확률이 얼마나 낮은가의 문제다.
    //   다른 데이터에 대해 같은 해시 값이 나올 확률이 낮을 수록 안심하고 사용할 수 있다.
    // - 현실과 비교하면 주민번호와 같다.
    //   본인 여부를 확인할 때 주민 번호로 확인하면 매우 빠르게 결과를 알 수 있다.
    //   원래는 본인인지 알아내려면 DNA 검사를 해야 한다.
    //   그러면 너무 시간이 오래 걸린다. 
    //   그런데 지문이나 주민번호를 사용하면 빠르게 본인 여부를 알 수 있다.
    // - 이런 이유로 hash 값을 "디지털 지문"이라 부른다.
    // - 목적?
    //   => 데이터가 같은지 빠르게 비교하기 위함
    // - 응용
    //   => 본인 여부를 확인하는 인증서.
    //   => 파일의 위변조를 검사하는 용도. 
    //      예1) git 에서 커밋할 때 고유번호를 붙이는데 바로 해시 값이다.
    //      예2) 파일 다운로드 사이트에서 제공하는 해시 값.
    //      예3) 파일 공유사이트에서 파일을 구분할 때 해시 값 사용. 
    //          사용자가 파일 이름을 변경하더라도 데이터만 바꾸지 않는다면 
    //          파일의 해시 값은 같다. 
    // - 해시 알고리즘
    //   => SHA, MD, PGP 등 
  }

}







