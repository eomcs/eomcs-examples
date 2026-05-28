package com.eomcs.cleancode.ch12.exam04;

// 예제 1: 절차 중복을 메서드로 추출하라 - ImageEditor
public class BadAndGood1 {

  private BadAndGood1() {}

  static class RenderedOp {
    void dispose() {}
  }

  static class ImageUtilities {
    static RenderedOp getScaledImage(RenderedOp image, float fx, float fy) {
      return new RenderedOp();
    }
    static RenderedOp getRotatedImage(RenderedOp image, int degrees) {
      return new RenderedOp();
    }
  }

  // Bad: 이미지 교체 절차 3줄이 scale()과 rotate() 양쪽에 반복된다
  //   - 이미지 교체 방식이 바뀌면 두 곳을 모두 수정해야 한다
  //   - 한 곳을 빠뜨리면 동작이 달라진다
  static class BadImageEditor {

    private RenderedOp image;

    BadImageEditor(RenderedOp image) {
      this.image = image;
    }

    public void scale(float factor) {
      RenderedOp newImage =
          ImageUtilities.getScaledImage(image, factor, factor);

      image.dispose();
      System.gc();
      image = newImage;
    }

    public void rotate(int degrees) {
      RenderedOp newImage =
          ImageUtilities.getRotatedImage(image, degrees);

      image.dispose();
      System.gc();
      image = newImage;
    }
  }

  // Good: 이미지 교체 절차가 replaceImage()로 모였다
  //   - 이미지 교체 방식이 바뀌면 한 곳만 수정하면 된다
  //   - scale()과 rotate()는 자신의 핵심 의도만 표현한다
  static class ImageEditor {

    private RenderedOp image;

    ImageEditor(RenderedOp image) {
      this.image = image;
    }

    public void scale(float factor) {
      replaceImage(ImageUtilities.getScaledImage(image, factor, factor));
    }

    public void rotate(int degrees) {
      replaceImage(ImageUtilities.getRotatedImage(image, degrees));
    }

    private void replaceImage(RenderedOp newImage) {
      image.dispose();
      System.gc();
      image = newImage;
    }
  }
}
