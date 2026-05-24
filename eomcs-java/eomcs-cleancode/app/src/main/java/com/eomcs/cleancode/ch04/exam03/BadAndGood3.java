package com.eomcs.cleancode.ch04.exam03;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BadAndGood3 {

  static class PagePath implements Comparable<Object> {
    private final List<String> names;
    PagePath(String... names) { this.names = Arrays.asList(names); }
    List<String> getNames() { return names; }

    @Override
    public int compareTo(Object o) {
      if (o instanceof PagePath other) {
        String compressed = String.join("", names);
        String compressedOther = String.join("", other.names);
        return compressed.compareTo(compressedOther);
      }
      return 1;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof PagePath other) return names.equals(other.names);
      return false;
    }

    @Override
    public int hashCode() { return Objects.hash(names); }
  }

  // Bad: 의도 없이 코드만 존재한다.
  // - return 1의 의미를 주석 없이는 알 수 없다.
  // - 타입이 다를 때 왜 현재 객체를 더 크다고 보는지 이유를 알 수 없다.
  static class BadPagePath implements Comparable<Object> {
    private final List<String> names;
    BadPagePath(String... names) { this.names = Arrays.asList(names); }

    @Override
    public int compareTo(Object o) {
      if (o instanceof PagePath other) {
        String compressed = String.join("", names);
        String compressedOther = String.join("", other.getNames());
        return compressed.compareTo(compressedOther);
      }
      return 1;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof BadPagePath other) return names.equals(other.names);
      return false;
    }

    @Override
    public int hashCode() { return Objects.hash(names); }
  }

  // Good: 의도를 설명하는 주석 (Explanation of Intent)
  // - "왜 return 1인가?"라는 질문에 답한다.
  // - 구현이 아니라 설계 의도를 드러낸다.
  // - 코드는 무엇을 하는지, 주석은 왜 그렇게 했는지를 설명한다.
  static class GoodPagePath implements Comparable<Object> {
    private final List<String> names;
    GoodPagePath(String... names) { this.names = Arrays.asList(names); }

    @Override
    public int compareTo(Object o) {
      if (o instanceof PagePath other) {
        String compressed = String.join("", names);
        String compressedOther = String.join("", other.getNames());
        return compressed.compareTo(compressedOther);
      }
      return 1; // 타입이 다를 경우 현재 객체를 더 높은 우선순위로 취급한다.
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof GoodPagePath other) return names.equals(other.names);
      return false;
    }

    @Override
    public int hashCode() { return Objects.hash(names); }
  }
}
