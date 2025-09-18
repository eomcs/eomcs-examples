package com.eomcs.algorithm.data_structure.array2.step2;

import com.eomcs.algorithm.data_structure.array2.Score;

public class Test {
  public static void main(String[] args) {
    ArrayList<Score> list = new ArrayList<Score>();
    list.add(new Score("홍길동", 100, 100, 100));
    list.add(new Score("임꺽정", 90, 90, 90));
    list.add(new Score("유관순", 80, 80, 80));
    list.add(new Score("안중근", 70, 70, 70));
    list.add(new Score("윤봉길", 80, 90, 100));

    for (int i = 0; i < list.size(); i++) {
      // 불편1 해결!
      // => 목록을 생성할 때 목록에서 다룰 항목의 타입을 Score로 선언한다.
      // => 그러면 get() 메서드의 리턴 타입은 Score 가 된다.
      // => 따라서 따로 형변환 할 필요가 없다.
      //
      Score s = list.get(i);
      System.out.printf("%s: %d, %f\n", s.name, s.sum, s.aver);
    }
  }
}
