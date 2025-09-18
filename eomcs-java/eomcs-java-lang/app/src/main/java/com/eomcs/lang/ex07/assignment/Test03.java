package com.eomcs.lang.ex07.assignment;

public class Test03 {
  public static void main(String[] args) {
    int[] values = {34, 4, -3, 78, 12, 22, 45, 0, -22};

    // 배열의 들어 있는 값의 순서를 꺼꾸로 바꿔라. 
    reverse(values); 
    
    printValues(values);
    // 출력결과:
    // -22, 0, 45, 22, 12, 78, -3, 4, 34
  }
  
  static void reverse(int[] values) {
    int count = values.length >> 1;
    int temp = 0;
    int endPos = values.length - 1;
    for (int i = 0; i < count; i++) {
      temp = values[i];
      values[i] = values[endPos - i];
      values[endPos - i] = temp;
    }
  }
  
  static void printValues(int[] values) {
    for (int i = 0; i < values.length; i++) {
      System.out.printf("%3d ", values[i]);
    }
    System.out.println();
  }
  
}





