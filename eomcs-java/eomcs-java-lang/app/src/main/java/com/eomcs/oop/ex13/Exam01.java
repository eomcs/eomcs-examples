//Internationalization(i18n) 구현
package com.eomcs.oop.ex13;

import java.io.FileReader;
import java.util.Properties;

public class Exam01 {
  static String filename = "label";
  static String userCountry = "US";
  static String userLanguage = "en";
  static Properties label;

  static void init() throws Exception {
    if (System.getProperty("user.country") != null)
      userCountry = System.getProperty("user.country");

    if (System.getProperty("user.language") != null)
      userLanguage = System.getProperty("user.language");

    System.out.printf("지역: %s\n", userCountry);
    System.out.printf("언어: %s\n", userLanguage);

    //    Properties props = System.getProperties();
    //    props.forEach((k, v) -> System.out.printf("%s = %s\n", k, v));

    label = new Properties();
    label.load(new FileReader(
        String.format("%s-%s_%s.properties", 
            filename, userLanguage, userCountry)));

  }

  public static void main(String[] args) throws Exception {
    init();
    // 다음은 화면에 텍스트를 출력할 때 직접 입력한 값을 출력하는 것이 아니라
    // 프로퍼티 파일에서 읽은 값을 출력한다. 
    // 이렇게 한 이유는 다양한 언어로 출력하기 위함이다.
    // 즉 다국어를 제공할 수 있도록 프로그래밍 하는 것을  
    // "국제화(i18n;Internationalization)"라 한다.
    // 
    System.out.println(label.get("welcome"));
    System.out.println(label.get("bye"));

    // 지역화(l10n;Localization)
    // => 국제화를 지원하는 프로그램의 경우 메뉴명, 라벨명, 버튼명 등 
    //    화면에서 출력하는 텍스트를 별도의 프로퍼티 파일에 저장한다.
    // => 이 프로퍼티 파일에 저장된 데이터를 각각의 언어로 바꾸는 것을 지역화라 부른다.
  }

}





