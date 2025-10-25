package effectivejava.ch12.item86.exam02;

import lombok.Data;

@Data
public class Person implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private final String name;
  private final int age;
  //  private final String email; // 새 필드 추가
  //
  //  @Serial
  //  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
  //    in.defaultReadObject(); // 스트림에 있는 값들로 필드 설정 (새 필드는 기본값으로 설정됨)
  //    if (email == null) {
  //      throw new InvalidObjectException("email 값이 누락되었다.");
  //    }
  //  }
}
