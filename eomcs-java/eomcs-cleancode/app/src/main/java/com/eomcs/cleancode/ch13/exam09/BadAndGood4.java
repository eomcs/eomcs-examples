package com.eomcs.cleancode.ch13.exam09;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 예제 4: 스레드 코드를 조정 가능하게 만들어라
// 스레드 수, 큐 크기, 반복 횟수 등을 쉽게 바꿀 수 있어야 한다
public class BadAndGood4 {

  private BadAndGood4() {}

  static class Image {

    private final String name;

    Image(String name) {
      this.name = name;
    }

    String name() {
      return name;
    }
  }

  // Bad: 스레드 수가 코드에 박혀 있다
  //   - 환경에 따라 조정하기 어렵다
  //   - 테스트에서 부하를 바꾸기 어렵다
  //   - 로컬/스테이징/운영에서 같은 값을 쓸 수밖에 없다
  static class BadImageResizeService {

    private final ExecutorService executor =
        Executors.newFixedThreadPool(4); // 스레드 수 하드코딩

    public void resizeAll(List<Image> images) {
      for (Image image : images) {
        executor.submit(() -> resize(image));
      }
    }

    private void resize(Image image) {
      System.out.println("resize: " + image.name());
    }
  }

  // Good: 스레드 수를 생성자로 주입받아 환경별로 조정 가능하다
  //   - 로컬에서는 2개, 테스트에서는 32개, 운영에서는 설정값 기반으로 조정 가능하다
  //   - 부하 테스트에서 다양한 스레드 수를 시도할 수 있다
  static class ImageResizeService {

    private final ExecutorService executor;

    ImageResizeService(int threadCount) {
      this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void resizeAll(List<Image> images) {
      for (Image image : images) {
        executor.submit(() -> resize(image));
      }
    }

    private void resize(Image image) {
      System.out.println("resize: " + image.name());
    }
  }
}
