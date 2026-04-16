package com.eomcs.quickstart.collection.exam06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

// Comparable<T>:
// - java.lang 패키지에 소속되어 있다.
// - 클래스의 자연 순서(natural ordering)를 정의한다.
// - compareTo(T o) 메서드 하나만 선언되어 있다.
// - 클래스 자체에 구현하므로 "내부 비교자"라고도 부른다.
//
// int compareTo(T o) 반환값 규칙:
//   음수  : this < o  (this가 앞으로)
//   0     : this == o (동등)
//   양수  : this > o  (this가 뒤로)
//
// Comparable을 구현한 주요 표준 클래스:
//   Integer, Long, Double 등 래퍼 클래스
//   String  : 사전순(lexicographic) 비교
//   LocalDate, LocalDateTime 등 날짜/시간 클래스
//
// Comparable 활용:
//   Collections.sort(list)         : List 정렬
//   Arrays.sort(array)             : 배열 정렬
//   new TreeSet<>() / new TreeMap<>() : 자동 정렬 컬렉션
//

public class App {

  // 학생 클래스 - 점수 기준 자연 순서 정의
  static class Student implements Comparable<Student> {
    String name;
    int score;

    Student(String name, int score) {
      this.name = name;
      this.score = score;
    }

    @Override
    public int compareTo(Student other) {
      // 점수 오름차순 (낮은 점수 → 앞)
      return this.score - other.score;
      // 내림차순이면: return other.score - this.score;
    }

    @Override
    public String toString() {
      return name + "(" + score + ")";
    }
  }

  public static void main(String[] args) {

    // 1. 표준 클래스의 자연 순서 - Integer, String
    System.out.println("[표준 클래스의 자연 순서]");
    List<Integer> numbers = new ArrayList<>();
    numbers.add(30);
    numbers.add(10);
    numbers.add(50);
    numbers.add(20);
    numbers.add(40);
    Collections.sort(numbers); // Comparable(Integer) 사용
    System.out.println("정수 오름차순: " + numbers);

    List<String> words = new ArrayList<>();
    words.add("banana");
    words.add("apple");
    words.add("cherry");
    words.add("mango");
    Collections.sort(words); // Comparable(String) 사용 - 사전순
    System.out.println("문자열 사전순: " + words);

    // 2. compareTo() 반환값 직접 확인
    System.out.println("\n[compareTo() 반환값]");
    Integer a = 10;
    Integer b = 20;
    System.out.println("10.compareTo(20): " + a.compareTo(b)); // 음수 (10 < 20)
    System.out.println("20.compareTo(10): " + b.compareTo(a)); // 양수 (20 > 10)
    System.out.println("10.compareTo(10): " + a.compareTo(a)); // 0   (동등)

    System.out.println("\"apple\".compareTo(\"banana\"): " + "apple".compareTo("banana")); // 음수
    System.out.println("\"banana\".compareTo(\"apple\"): " + "banana".compareTo("apple")); // 양수

    // 3. 커스텀 클래스에 Comparable 구현
    System.out.println("\n[커스텀 Student 클래스 - 점수 오름차순]");
    List<Student> students = new ArrayList<>();
    students.add(new Student("홍길동", 85));
    students.add(new Student("임꺽정", 92));
    students.add(new Student("유관순", 78));
    students.add(new Student("이순신", 95));
    students.add(new Student("장보고", 88));

    System.out.println("정렬 전: " + students);
    Collections.sort(students); // Student.compareTo() 사용
    System.out.println("정렬 후: " + students);

    // 4. TreeSet - 자동 정렬 (Comparable 필수)
    System.out.println("\n[TreeSet - 자동 정렬]");
    TreeSet<Student> treeSet = new TreeSet<>();
    treeSet.add(new Student("홍길동", 85));
    treeSet.add(new Student("임꺽정", 92));
    treeSet.add(new Student("유관순", 78));
    treeSet.add(new Student("이순신", 95));
    treeSet.add(new Student("장보고", 88));
    System.out.println("TreeSet (점수 오름차순): " + treeSet);

    // TreeSet에서 최저점/최고점
    System.out.println("최저점: " + treeSet.first());
    System.out.println("최고점: " + treeSet.last());

    // 5. Collections.min() / max() - Comparable 활용
    System.out.println("\n[Collections.min() / max()]");
    List<Student> list2 = new ArrayList<>();
    list2.add(new Student("홍길동", 85));
    list2.add(new Student("임꺽정", 92));
    list2.add(new Student("유관순", 78));
    System.out.println("최솟값(최저점): " + Collections.min(list2));
    System.out.println("최댓값(최고점): " + Collections.max(list2));

    // 6. Comparable을 구현하지 않은 클래스를 TreeSet에 추가하면 ClassCastException
    System.out.println("\n[Comparable 미구현 → ClassCastException]");
    try {
      // Object는 Comparable을 구현하지 않음
      TreeSet<Object> badSet = new TreeSet<>();
      badSet.add(new Object());
      badSet.add(new Object()); // 두 번째 추가 시 비교 시도 → 예외
    } catch (ClassCastException e) {
      System.out.println("ClassCastException: Comparable 미구현 객체는 TreeSet에 저장 불가");
    }
  }
}
