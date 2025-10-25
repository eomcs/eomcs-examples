package effectivejava.ch12.item86.exam01;

import lombok.Data;

@Data
public class Person implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private final String name;
  private final int age;
  //  private final short age; // 타입을 int에서 short로 변경
  //  private final String email; // 새 필드 추가
}
