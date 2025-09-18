package bitcamp.web.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import bitcamp.dao.BoardDao;
import bitcamp.vo.Board;

// 페이지 컨트롤러는 보통 DispatcherServlet의 IoC 컨테이너에서 관리한다.
// 주로 다음 애노테이션으로 표시한다.
@Controller
public class BoardController {
  
  @Autowired
  BoardDao boardDao; // 이 객체는 ContextLoaderListener에서 관리하는 객체이다.
  
  @RequestMapping(path = "/board/list", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String list() throws Exception {
    List<Board> boards = boardDao.findAll();
    
    StringBuffer sb = new StringBuffer();
    for (Board board : boards) {
      sb.append(board.toString() + "\n");
    }
    return sb.toString();
  }
}
