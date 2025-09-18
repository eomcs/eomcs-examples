// 요청 핸들러의 아규먼트 - multipart/form-data 형식의 파라미터 값 받기
package bitcamp.app1;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/c04_8")
public class Controller04_8 {

  // ServletContext는 메서드의 아규먼트로 받을 수 없다.
  // 의존 객체로 주입 받아야 한다.
  @Autowired
  ServletContext sc;

  // 클라이언트가 멀티파트 형식으로 전송한 데이터를 꺼내기
  // => Servlet API에서 제공하는 Part를 사용하거나
  //    또는 Spring에서 제공하는 MultipartFile 타입의 아규먼트를 선언하면 된다.
  //
  // 주의!
  // => DispatcherServlet을 web.xml을 통해 배치했다면,
  //    <multipart-config/> 태그를 추가해야 한다.
  // => WebApplicationInitializer를 통해 DispatcherServlet을 배치했다면,
  //    App1WebApplicationInitializer 클래스를 참고하라!
  //

  // 테스트:
  // http://.../html/app1/c04_8.html
  @PostMapping(value = "h1", produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String handler1(
      String name,
      int age,
      Part photo // Servlet API의 객체
      ) throws Exception {

    String filename = null;
    if (photo.getSize() > 0) {
      filename = UUID.randomUUID().toString();
      String path = sc.getRealPath("/html/app1/" + filename);
      photo.write(path);
    }

    return "<html><head><title>c04_8/h1</title></head><body>" + "<h1>업로드 결과</h1>" + "<p>이름:" + name
        + "</p>" + "<p>나이:" + age + "</p>" +
        // 현재 URL이 다음과 같기 때문에 업로드 이미지의 URL을 이 경로를 기준으로 계산해야 한다.
        // http://localhost:8080/java-spring-webmvc/app1/c04_8/h1
        //
        (filename != null ? "<p><img src='../../html/app1/" + filename + "'></p>" : "")
        + "</body></html>";
  }

  // MultipartFile로 멀티파트 데이터를 받으려면,
  // Spring WebMVC 설정에서 MultipartResolver 객체를 등록해야 한다.
  //
  // 테스트:
  // http://.../html/app1/c04_8.html
  @PostMapping(value = "h2", produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String handler2(//
      String name, //
      @RequestParam(defaultValue = "0") int age, //
      MultipartFile photo // Spring API의 객체
      ) throws Exception {

    String filename = null;
    if (!photo.isEmpty()) {
      filename = UUID.randomUUID().toString();
      String path = sc.getRealPath("/html/app1/" + filename);
      photo.transferTo(new File(path));
    }

    return "<html><head><title>c04_8/h2</title></head><body>" + "<h1>업로드 결과</h1>" + "<p>이름:" + name
        + "</p>" + "<p>나이:" + age + "</p>" +
        // 현재 URL이 다음과 같기 때문에 업로드 이미지의 URL을 이 경로를 기준으로 계산해야 한다.
        // http://localhost:8080/java-spring-webmvc/app1/c04_8/h2
        //
        (filename != null ? "<p><img src='../../html/app1/" + filename + "'></p>" : "")
        + "</body></html>";
  }

  // 테스트:
  // http://.../html/app1/c04_8.html
  @PostMapping(value = "h3", produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String handler3(//
      String name, //
      int age, //
      // 같은 이름으로 전송된 여러 개의 파일은 배열로 받으면 된다.
      MultipartFile[] photo //
      ) throws Exception {

    StringWriter out0 = new StringWriter();
    PrintWriter out = new PrintWriter(out0);
    out.println("<html><head><title>c04_8/h3</title></head><body>");
    out.println("<h1>업로드 결과</h1>");
    out.printf("<p>이름:%s</p>\n", name);
    out.printf("<p>나이:%s</p>\n", age);

    for (MultipartFile f : photo) {
      if (!f.isEmpty()) {
        String filename = UUID.randomUUID().toString();
        String path = sc.getRealPath("/html/app1/" + filename);
        f.transferTo(new File(path));
        out.printf("<p><img src='../../html/app1/%s'></p>\n", filename);
      }
    }
    out.println("</body></html>");

    return out0.toString();
  }

}


