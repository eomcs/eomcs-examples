// 중첩 클래스 사용 전 : 패키지 멤버 클래스가 불편한 경우
package com.eomcs.oop.ex11.h.before;

public class Exam01 {

  public static void main(String[] args) {

    LinkedList list = new LinkedList();
    list.add(100);
    list.add(200);
    list.add(300);
    list.add(400);
    list.add(500);
    list.add(600);

    LinkedList list2 = new LinkedList();
    list2.add(10);
    list2.add(20);
    list2.add(30);
    list2.add(40);
    list2.add(50);
    list2.add(60);

    // list에 들어 있는 값을 배열로 꺼내 보자!
    Object[] arr = list.toArray(LinkedList.FORWARD);
    // 1) LinkedList에게 배열을 달라고 요청한다.
    // 2) LinkedList는 Array에게 자신의 주소를 알려주면서
    //    자신이 갖고 있는 값을 꺼내 배열을 만들어 달라고 요청한다.
    // 3) Array는 LinkedList 객체의 주소를 참고하여 해당 값을 꺼내 배열을 만들어 리턴한다.
    // 4) LinkedList는 Array로부터 받은 배열을 그대로 리턴한다.
    // 5) LinkedList 리턴한 배열을 출력한다.
    for (Object obj : arr) {
      System.out.print(obj + ",");
    }
    System.out.println();

    Object[] arr2 = list.toArray(LinkedList.REVERSE);
    for (Object obj : arr2) {
      System.out.print(obj + ",");
    }
    System.out.println();

    Object[] arr3 = list2.toArray(LinkedList.REVERSE);
    for (Object obj : arr3) {
      System.out.print(obj + ",");
    }
    System.out.println();

  }

}







