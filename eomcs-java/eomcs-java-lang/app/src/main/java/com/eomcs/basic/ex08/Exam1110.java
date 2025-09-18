// java.util.Map - compute() 사용법
package com.eomcs.basic.ex08;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Exam1110 {
  public static void main(String[] args) {
    String str = "An object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.";

    Map<Character, Integer> result = new HashMap<>();

    for (char ch : str.toCharArray()) {
      result.compute(ch, (k, v) -> (v == null) ? 1 : ++v);
    }

    for (Entry<Character, Integer> entry : result.entrySet()) {
      System.out.printf("%c: %d\n", entry.getKey(), entry.getValue());
    }
  }

}
