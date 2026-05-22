package com.eomcs.cleancode.ch02.exam12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BadAndGood1 {

  static class User {
    String name;
  }

  // Bad
  // - add라는 동일한 단어를 서로 다른 의미로 사용한다.
  //   - BadUserList.add(): 컬렉션에 요소 추가
  //   - BadMathUtils.add(): 숫자 덧셈
  // - BadSetUtils.add()는 "하나 추가"처럼 읽히지만 실제로는 두 집합의 합집합(union)이다.
  // - 단어와 실제 동작이 불일치해 API 사용자가 오해하게 된다.
  static class BadUserList {
    List<User> users = new ArrayList<>();
    void add(User user) {
      users.add(user);
    }
  }

  static class BadMathUtils {
    int add(int a, int b) {
      return a + b;
    }
  }

  static class BadSetUtils {
    Set<Integer> add(Set<Integer> set1, Set<Integer> set2) {
      Set<Integer> result = new HashSet<>(set1);
      result.addAll(set2);
      return result;
    }
  }

  // Good
  // - UserList.add()는 컬렉션에 추가하는 표준 의미이므로 그대로 유지한다.
  // - MathUtils에서 숫자 합계는 의미가 다르므로 sum으로 구분한다.
  // - SetUtils에서 두 집합의 합집합은 수학적 의미에 맞게 union으로 표현한다.
  static class GoodUserList {
    List<User> users = new ArrayList<>();
    void add(User user) {
      users.add(user);
    }
  }

  static class GoodMathUtils {
    int sum(int a, int b) {
      return a + b;
    }
  }

  static class GoodSetUtils {
    Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
      Set<Integer> result = new HashSet<>(set1);
      result.addAll(set2);
      return result;
    }
  }
}
