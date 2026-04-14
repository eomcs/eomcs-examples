package com.eomcs.quickstart.oop.exam14;

import java.util.function.Predicate;

public class App4 {

  enum MemberType {
    TEACHER,
    STUDENT,
    MANAGER
  }

  static class Member {
    String name;
    MemberType type;

    public Member(String name, MemberType type) {
      this.name = name;
      this.type = type;
    }
  }

  static void printMembers(Member[] members, Predicate<Member> filter) {
    for (Member member : members) {
      if (filter.test(member)) {
        System.out.println(member.name);
      }
    }
  }

  public static void main(String[] args) {

    Member[] members = {
      new Member("홍길동", MemberType.TEACHER),
      new Member("임꺽정", MemberType.STUDENT),
      new Member("유관순", MemberType.MANAGER),
      new Member("김철수", MemberType.STUDENT),
      new Member("이영희", MemberType.TEACHER),
      new Member("박민수", MemberType.MANAGER),
      new Member("최지우", MemberType.STUDENT),
      new Member("정우성", MemberType.TEACHER),
      new Member("한지민", MemberType.MANAGER),
      new Member("강동원", MemberType.STUDENT),
      new Member("송혜교", MemberType.TEACHER),
      new Member("이병헌", MemberType.MANAGER)
    };

    System.out.println("학생 목록:");
    printMembers(members, member -> member.type == MemberType.STUDENT);

    System.out.println("\n교사 목록:");
    printMembers(members, member -> member.type == MemberType.TEACHER);

    System.out.println("\n관리자 목록:");
    printMembers(members, member -> member.type == MemberType.MANAGER);
  }
}
