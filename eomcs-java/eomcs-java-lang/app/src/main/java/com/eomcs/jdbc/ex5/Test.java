package com.eomcs.jdbc.ex5;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;

public class Test {
  public static void main(String[] args) throws Exception {
    // 1) 프로그램 아규먼트
    // > java -cp bin/main step25.ex4.Test aaa bbb ccc
    // => args = {"aaa", "bbb", "ccc"}
    for (String arg : args) {
      System.out.println(arg);
    }
    System.out.println("---------------------------");

    // 2) 시스템 아규먼트
    // > java -D키=값 -D키=값 ... step25.ex4.Test
    // => JVM 프로퍼티 = 기본 프로퍼티들 + -D 옵션으로 추가한 프로퍼티
    Properties jvmProps = System.getProperties();
    Set<Object> keySet = jvmProps.keySet();
    for (Object key : keySet) {
      System.out.printf("%s=%s\n", key, jvmProps.get(key));
    }
    System.out.println("---------------------------");

    // 3) 프로퍼티 파일에서 읽어 오기 
    Properties fileProps = new Properties();
    fileProps.load(new FileInputStream("jdbc.properties"));
    keySet = fileProps.keySet();
    for (Object key : keySet) {
      System.out.printf("%s=%s\n", key, fileProps.get(key));
    }
    System.out.println("---------------------------");

  }
}







