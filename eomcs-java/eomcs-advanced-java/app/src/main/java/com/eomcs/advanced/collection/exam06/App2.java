package com.eomcs.advanced.collection.exam06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

// Comparator<T>:
// - java.util 패키지에 소속되어 있다.
// - 클래스 외부에서 비교 기준을 별도로 정의한다.
// - compare(T o1, T o2) 메서드를 구현한다.
// - "외부 비교자"라고도 부른다.
// - 람다 표현식으로 간결하게 작성할 수 있다.
//
// int compare(T o1, T o2) 반환값 규칙:
//   음수  : o1 < o2  (o1이 앞으로)
//   0     : o1 == o2 (동등)
//   양수  : o1 > o2  (o1이 뒤로)
//
// Comparator 팩토리 메서드 (Java 8+):
//   Comparator.comparing(keyExtractor)       : 특정 필드 기준 오름차순
//   Comparator.comparingInt(keyExtractor)    : int 필드 기준 오름차순 (박싱 없음)
//   comparator.reversed()                    : 역순(내림차순)으로 전환
//   comparator.thenComparing(keyExtractor)   : 동일한 경우 추가 기준 적용
//   Comparator.naturalOrder()                : 자연 순서(Comparable)
//   Comparator.reverseOrder()                : 자연 순서의 역순
//
// Comparable vs Comparator:
//   Comparable : 클래스 자체에 하나의 자연 순서 내장. compareTo() 구현
//   Comparator : 클래스 외부에서 다양한 정렬 기준 정의. compare() 구현
//

public class App2 {

  static class Student {
    String name;
    int score;
    String grade; // "A", "B", "C"

    Student(String name, int score, String grade) {
      this.name = name;
      this.score = score;
      this.grade = grade;
    }

    @Override
    public String toString() {
      return name + "(" + score + "/" + grade + ")";
    }
  }

  public static void main(String[] args) {

    List<Student> students = new ArrayList<>();
    students.add(new Student("홍길동", 86, "B"));
    students.add(new Student("임꺽정", 92, "A"));
    students.add(new Student("유관순", 78, "C"));
    students.add(new Student("이순신", 95, "A"));
    students.add(new Student("장보고", 85, "B"));
    students.add(new Student("강감찬", 91, "A"));

    // 1. 익명 클래스로 Comparator 구현
    System.out.println("[익명 클래스 Comparator - 점수 오름차순]");
    Comparator<Student> byScore =
        new Comparator<Student>() {
          @Override
          public int compare(Student o1, Student o2) {
            return o1.score - o2.score;
          }
        };
    List<Student> list = new ArrayList<>(students);
    Collections.sort(list, byScore);
    System.out.println(list);

    // 2. 람다로 Comparator 구현
    System.out.println("\n[람다 Comparator - 이름 사전순]");
    Comparator<Student> byName = (o1, o2) -> o1.name.compareTo(o2.name);
    list = new ArrayList<>(students);
    list.sort(byName); // List.sort() 메서드 사용
    System.out.println(list);

    // 3. Comparator.comparing() - 메서드 참조
    System.out.println("\n[Comparator.comparing() - 점수 오름차순]");
    list = new ArrayList<>(students);
    list.sort(Comparator.comparingInt(s -> s.score));
    System.out.println(list);

    // 4. reversed() - 내림차순 전환
    System.out.println("\n[reversed() - 점수 내림차순]");
    list = new ArrayList<>(students);
    list.sort(Comparator.comparingInt((Student s) -> s.score).reversed());
    System.out.println(list);

    // 5. thenComparing() - 복합 정렬 기준
    System.out.println("\n[thenComparing() - 점수 내림차순, 동점 시 이름 사전순]");
    list = new ArrayList<>(students);
    list.sort(
        Comparator.comparingInt((Student s) -> s.score).reversed().thenComparing(s -> s.name));
    System.out.println(list);

    // 6. Comparator.reverseOrder() - 자연 순서의 역순
    System.out.println("\n[reverseOrder() - 정수 내림차순]");
    List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
    numbers.sort(Comparator.reverseOrder());
    System.out.println(numbers);

    // 7. TreeSet에 Comparator 전달 - Comparable 없이도 정렬 가능
    System.out.println("\n[TreeSet + Comparator - 점수 내림차순]");
    TreeSet<Student> treeSet =
        new TreeSet<>(
            Comparator.comparingInt((Student s) -> s.score).reversed().thenComparing(s -> s.name));
    treeSet.addAll(students);
    System.out.println(treeSet);

    // 8. TreeMap에 Comparator 전달 - 키 내림차순 정렬
    System.out.println("\n[TreeMap + Comparator - 키 내림차순]");
    TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
    treeMap.put("banana", 500);
    treeMap.put("apple", 1000);
    treeMap.put("mango", 700);
    treeMap.put("cherry", 300);
    System.out.println(treeMap); // 알파벳 내림차순

    // 9. Comparator.comparing() - String 필드, 메서드 참조
    System.out.println("\n[Comparator.comparing() - 등급 오름차순, 동일 등급 내 점수 내림차순]");
    list = new ArrayList<>(students);
    list.sort(
        Comparator.comparing((Student s) -> s.grade)
            .thenComparing(Comparator.comparingInt((Student s) -> s.score).reversed()));
    System.out.println(list);
  }
}
