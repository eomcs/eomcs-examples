package com.eomcs.quickstart.collection.exam05;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

// Properties:
// - java.util 패키지에 소속되어 있다.
// - Hashtable<Object, Object>을 상속한다.
// - 키와 값이 모두 String인 설정(프로퍼티) 데이터를 관리하는 데 특화된 클래스이다.
// - .properties 파일로 저장·로드할 수 있다.
// - Java의 시스템 프로퍼티(System.getProperties())도 Properties로 관리된다.
//
// 상속 구조:
//   Hashtable<Object,Object> → Properties
//
// 주요 메서드:
//   String getProperty(String key)           키에 대응하는 값 반환. 없으면 null
//   String getProperty(String key, String d) 키가 없으면 기본값(d) 반환
//   Object setProperty(String key, String v) 키-값 쌍 설정. 이전 값 반환
//   void store(OutputStream, String comment) .properties 형식으로 저장
//   void load(InputStream out)               .properties 파일 로드
//   void storeToXML(OutputStream, String)    XML 형식으로 저장
//   void loadFromXML(InputStream)            XML 파일 로드
//   Set<String> stringPropertyNames()        모든 키를 Set<String>으로 반환
//

public class App3 {

  public static void main(String[] args) throws IOException {

    // 1. setProperty() / getProperty() - 기본 사용
    System.out.println("[Properties 기본 사용]");
    Properties props = new Properties();
    props.setProperty("db.host", "localhost");
    props.setProperty("db.port", "3306");
    props.setProperty("db.name", "mydb");
    props.setProperty("db.user", "root");
    props.setProperty("db.password", "1234");

    System.out.println("db.host: " + props.getProperty("db.host"));
    System.out.println("db.port: " + props.getProperty("db.port"));

    // 2. getProperty(key, defaultValue) - 키가 없으면 기본값 반환
    System.out.println("\n[getProperty - 기본값]");
    System.out.println("db.timeout: " + props.getProperty("db.timeout", "30")); // 없음 → 30

    // 3. stringPropertyNames() - 모든 키 순회
    System.out.println("\n[stringPropertyNames() - 키 순회]");
    for (String key : props.stringPropertyNames()) {
      System.out.println("  " + key + " = " + props.getProperty(key));
    }

    // 4. store() - .properties 파일로 저장
    System.out.println("\n[store() - .properties 파일로 저장]");
    String filePath = System.getProperty("java.io.tmpdir") + "/db.properties";
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
      props.store(fos, "Database Configuration"); // 두 번째 인수는 주석
    }
    System.out.println("저장 완료: " + filePath);

    // 5. load() - .properties 파일에서 로드
    System.out.println("\n[load() - .properties 파일에서 로드]");
    Properties loaded = new Properties();
    try (FileInputStream fis = new FileInputStream(filePath)) {
      loaded.load(fis);
    }
    System.out.println("로드된 db.host: " + loaded.getProperty("db.host"));
    System.out.println("로드된 db.port: " + loaded.getProperty("db.port"));
    System.out.println("로드된 항목 수: " + loaded.size());

    // 6. 기본값(default) Properties 상속
    System.out.println("\n[기본값 Properties 상속]");
    Properties defaults = new Properties();
    defaults.setProperty("timeout", "30");
    defaults.setProperty("encoding", "UTF-8");

    Properties config = new Properties(defaults); // defaults를 기본값으로 사용
    config.setProperty("timeout", "60");          // defaults의 값을 재정의

    System.out.println("timeout (재정의):  " + config.getProperty("timeout"));  // 60
    System.out.println("encoding (상속):   " + config.getProperty("encoding")); // UTF-8

    // 7. 시스템 프로퍼티 활용
    System.out.println("\n[시스템 프로퍼티]");
    Properties sysProps = System.getProperties();
    System.out.println("os.name:    " + sysProps.getProperty("os.name"));
    System.out.println("java.version: " + sysProps.getProperty("java.version"));
    System.out.println("user.home:  " + sysProps.getProperty("user.home"));
  }
}
