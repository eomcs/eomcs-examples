// iterator 디자인 패턴 : 데이터 목록에서 값을 꺼내는 것을 별도의 객체로 분리하는 설계 방식 
package com.eomcs.design_pattern.iterator.after;

// Iterator 디자인 패턴
// => 데이터 목록을 관리하는 방식에 상관없이 일관된 방식으로 데이터를 꺼낼 수 있게 해주는 설계 기법
// => 즉 데이터 목록을 관리하는 객체를 직접 사용하여 값을 꺼내는 것이 아니라,
//    값을 꺼내는 주는 별도의 객체의 도움을 받아 값을 꺼낸다.
// => 값을 꺼내주는 객체를 "Iterator"라 부른다.
//    값을 꺼내주는 객체의 사용법을 통일하기 위하여 인터페이스로 사용 규칙을 정의한다.
// => 각각의 데이터 목록 관리 객체는 Iterator 규칙에 따라 값을 꺼내는 객체를 리턴한다.
public class Test01 {

  public static void main(String[] args) {
    ArrayList<String> list1 = new ArrayList<>();
    list1.add("aaa");
    list1.add("bbb");
    list1.add("ccc");
    list1.add("ddd");
    
    LinkedList<String> list2 = new LinkedList<>();
    list2.add("aaa2");
    list2.add("bbb2");
    list2.add("ccc2");
    list2.add("ddd2");
    
    Stack<String> list3 = new Stack<>();
    list3.push("aaa3");
    list3.push("bbb3");
    list3.push("ccc3");
    list3.push("ddd3");
    
    Queue<String> list4 = new Queue<>();
    list4.offer("aaa4");
    list4.offer("bbb4");
    list4.offer("ccc4");
    list4.offer("ddd4");
    
    // ArrayList에서 제공하는 메서드를 사용하여 직접 꺼내지 말고 
    // 값을 꺼내주는 객체의 도움을 받으라!
    // 이렇게 하는 이유? 
    // => 데이터 목록 객체의 종류(ArrayList, LinkedList, Stack, Queue)에 상관없이 
    //    일관된 방법으로 값을 꺼내기 위함이다.
    Iterator<String> iterator = list1.iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next() + ",");
    }
    System.out.println();
    
    // LinkedList도 ArrayList에서 값을 꺼내는 방식과 동일하게 값을 꺼낼 수 있다.
    // 이런 이유로 Iterator를 사용하는 것이다.
    iterator = list2.iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next() + ",");
    }
    System.out.println();
    
    // Stack이 데이터를 어떻게 관리하는가에 상관없이,
    // ArrayList에서 값을 꺼내는 방식과 동일하게 값을 꺼낼 수 있다.
    // 이런 이유로 Iterator를 사용하는 것이다.
    iterator = list3.iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next() + ",");
    }
    System.out.println();
    
    // Queue가 데이터를 어떻게 관리하는가에 상관없이,
    // ArrayList에서 값을 꺼내는 방식과 동일하게 값을 꺼낼 수 있다.
    // 이런 이유로 Iterator를 사용하는 것이다.
    iterator = list4.iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next() + ",");
    }
    System.out.println();
  }

}









