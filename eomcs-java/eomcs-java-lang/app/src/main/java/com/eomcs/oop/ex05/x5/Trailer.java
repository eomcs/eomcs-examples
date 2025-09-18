package com.eomcs.oop.ex05.x5;

public class Trailer extends Option {

  public Trailer(Car car) {
    super(car);
  }

  CampingTrailer trailer;

  public void setTrailer(CampingTrailer trailer) {
    this.trailer = trailer;
  }

  @Override
  public void start() {
    car.start();
  }

  @Override
  public void stop() {
    car.stop();
  }

  @Override
  public void run() {
    car.run(); // 옵션 자체는 실제 자동차가 아니다. 그래서 run()이 호출되면 실제 자동차 객체에게 위임한다.
    System.out.println("덜덜덜 트레일러가 따라간다.");
  }
}
