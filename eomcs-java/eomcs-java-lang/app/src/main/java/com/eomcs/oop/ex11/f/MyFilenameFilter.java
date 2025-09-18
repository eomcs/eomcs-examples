// 클래스의 종류 : 패키지 멤버 클래스
package com.eomcs.oop.ex11.f;

import java.io.File;
import java.io.FilenameFilter;

// 다음 클래스와 같이 패키지에 직접 소속되는 클래스를 패키지 멤버 클래스라 부른다.
// => 일반적으로 작성하는 클래스들이 패키지 멤버 클래스이다.
// => 패키지 멤버는 여러 다른 패키지에서 사용할 수 있다.
// 
// 패키지 멤버의 접근 범위 
// => public    : 모두 접근 가능
// => (default) : 같은 패키지에 소속된 클래스 만이 접근 가능 
public class MyFilenameFilter implements FilenameFilter {
  public boolean accept(File dir, String name) {
    if (name.endsWith(".java")) 
      return true; 
    return false; 
  }
}
