// 썸네일 이미지 만들기
package com.eomcs.web.ex04;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/ex04/s8")
public class Servlet08 extends GenericServlet {

  private static final long serialVersionUID = 1L;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    this.uploadDir = this.getServletContext().getRealPath("/upload");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // 테스트
    // - http://localhost:8080/java-web/ex04/test08.html 실행
    //

    req.setCharacterEncoding("UTF-8");

    HttpServletRequest httpReq = (HttpServletRequest) req;

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("<html>");
    out.println("<head><title>servlet04</title></head>");
    out.println("<body><h1>파일 업로드 결과</h1>");

    // 일반 폼 데이터를 원래 하던 방식대로 값을 꺼낸다.
    out.printf("이름=%s<br>\n", httpReq.getParameter("name"));
    out.printf("나이=%s<br>\n", httpReq.getParameter("age"));

    // 파일 데이터는 getPart()를 이용한다.
    Part photoPart = httpReq.getPart("photo");
    if (photoPart.getSize() == 0) {
      out.println("</body></html>");
      return;
    }

    // 파일을 선택해서 업로드 했다면,
    String filename = UUID.randomUUID().toString();
    photoPart.write(this.uploadDir + "/" + filename);

    // 원본 사진을 가지고 특정 크기의 썸네일 이미지를 만들기
    // 1) 썸네일 이미지를 생성해주는 자바 라이브러리 추가
    // => mvnrepository.com에서 thumbnailator 라이브러리 검색
    // => build.gradle 에 추가
    // => '$ gradle eclipse' 실행
    // => eclise IDE에서 프로젝트 리프래시

    // 2) 썸네일 이미지 만들기
    // => 원본 이미지 파일이 저장된 경로를 알려주고
    // 어떤 썸네일 이미지를 만들어야 하는지 설정한다.
    Builder<File> thumbnailBuilder = Thumbnails.of(this.uploadDir + "/" + filename);
    thumbnailBuilder.size(20, 20);
    thumbnailBuilder.outputFormat("jpg");
    thumbnailBuilder.toFiles(Rename.PREFIX_DOT_THUMBNAIL);

    Builder<File> thumbnailBuilder2 = Thumbnails.of(this.uploadDir + "/" + filename);
    thumbnailBuilder2.size(20, 20);
    thumbnailBuilder2.crop(Positions.CENTER);
    thumbnailBuilder2.outputFormat("jpg");
    thumbnailBuilder2.toFiles(new Rename() {
      @Override
      public String apply(String name, ThumbnailParameter param) {
        return name + "_20x20";
      }
    });

    Thumbnails.of(this.uploadDir + "/" + filename)
    .size(80, 80)
    .outputFormat("jpg")
    .crop(Positions.CENTER)
    //.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
    .toFiles(new Rename() {
      @Override
      public String apply(String name, ThumbnailParameter param) {
        return name + "_80x80";
      }
    });

    Thumbnails.of(this.uploadDir + "/" + filename)
    .size(160, 160)
    .outputFormat("jpg")
    .crop(Positions.CENTER)
    //.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
    .toFiles(new Rename() {
      @Override
      public String apply(String name, ThumbnailParameter param) {
        return name + "_160x160";
      }
    });

    out.printf("사진=%s<br>\n", filename);
    out.printf("<img src='../upload/thumbnail.%s.jpg'><br>\n", filename);
    out.printf("<img src='../upload/%s_20x20.jpg'><br>\n", filename);
    out.printf("<img src='../upload/%s_80x80.jpg'><br>\n", filename);
    out.printf("<img src='../upload/%s' height='80'><br>\n", filename);
    out.printf("<img src='../upload/%s_160x160.jpg'><br>\n", filename);
    out.printf("<img src='../upload/%s'><br>\n", filename);
    out.println("</body></html>");
  }
}

