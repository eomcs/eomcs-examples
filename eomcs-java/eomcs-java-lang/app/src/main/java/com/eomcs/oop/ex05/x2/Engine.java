package com.eomcs.oop.ex05.x2;

public class Engine {
  int cc;
  int valve;

  public void run() {
    // 하이브리드 전기차 구동 기능 추가
    if (kwh > 0) {
      System.out.println("전기로 간다!");
      return;
    }

    // 트레일러 장착 기능이 추가되었다면 다음과 같이 run() 메서드를 변경해야 한다.
    if (trailer == null) {
      System.out.println("씽씽 달린다!");
    } else {
      System.out.println("느릿 느릿 조심히 움직인다!");
    }
  }

  // 자동차 기능 추가
  public void start() {
    System.out.println("시동 건다!");
  }

  public void stop() {
    System.out.println("시동 끈다!");
  }

  // 트럭 기능 추가
  public void dump() {
    System.out.println("짐 내린다!");
  }

  // 트레일러 붙이는 기능 추가
  CampingTrailer trailer;
  public void setTrailer(CampingTrailer trailer) {
    this.trailer = trailer;
  }

  // 하이브리드 자동차 기능 추가
  int kwh;
  public void chargeBattery(int kwh) {
    this.kwh = kwh;
  }

}







