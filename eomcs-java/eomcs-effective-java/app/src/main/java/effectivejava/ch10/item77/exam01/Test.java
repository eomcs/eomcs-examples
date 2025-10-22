// # 아이템 77. 예외를 무시하지 말라
// - API 설계자가 메서드 선언에 예외를 명시한 까닭은,
//   그 메서드를 사용할 때 적절한 조치를 취해달라고 말하는 것이다.
// - catch 블록을 비워두면 예외가 존재할 이유가 없어진다.
// - 예외를 무시해야 할 때도 있다.
//   예) FileInputStream을 닫을 때.
//      - 파일 상태를 변경하지 않았으니 복구할 것이 없다.
//      - 필요한 정보는 다 읽었다는 뜻이니 남은 작업을 중단할 이유도 없다.
// - 예외를 무시하기로 했다면,
//   catch 블록 안에 그렇게 결정한 이유를 주석으로 남기고 예외 변수의 이름도 ignored로 바꿔놓자.
//
package effectivejava.ch10.item77.exam01;

// [주제] 예외 무시하기 - ignored(변수명) + 무시한 이유(주석)

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

  public static void main(String[] args) {
    File f = new File("words.txt");
    FileInputStream in = null;
    try {
      in = new FileInputStream(f);
      // 파일 읽기 작업 수행
    } catch (FileNotFoundException e) {
      System.out.println("파일을 찾을 수 없습니다: " + f.getPath());
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException ignored) { // 예외 변수 이름도 ignored로 변경
          // 닫기 작업 중 예외가 발생했지만 다음 작업을 하는 데 지장이 없기 때문에 무시함.(무시한 이유)
        }
      }
    }
  }
}
