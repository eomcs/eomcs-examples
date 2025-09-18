// JSON 라이브러리 준비 - Gson 라이브러러 가져오기
package com.eomcs.openapi.json.gson;

import com.google.gson.Gson;

public class Exam0100 {
  public static void main(String[] args) {
    // Gson 라이브러리를 프로젝트에 추가한다.
    // 1) `build.gradle` 빌드 스크립트 파일 변경
    //   - Gson 라이브러리 정보를 dependecies {} 블록에 추가한다.
    //     - https://search.maven.org/ 또는 https://central.sonatype.com/ 사이트에 방문한다.
    //     - `gson` 검색어로 라이브러리를 찾는다.
    //     - `com.google.code.gson` 라이브러리를 선택한다.
    //     - 검색 결과에서 최신 버전을 선택한다.
    //     - Gradle Groovy DSL 코드를 복사하여 빌드 스크립트에 붙여 넣는다.
    // 2) `$ gradle eclipse` 를 실행하여 라이브러리를 다운로드하여 프로젝트에 등록한다.
    //   - 명령을 실행한 후 eclipse IDE 에서 해당 프로젝트를 refresh 해야 한다.
    //   - 'Referenced Libraries' 노드에서 gson 라이브러리 파일이 추가된 것을 확인한다.
    Gson gson = new Gson();
  }
}
