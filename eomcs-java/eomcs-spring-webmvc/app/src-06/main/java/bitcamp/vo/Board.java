package bitcamp.vo;

public class Board {
  private int no;
  private String contents;
  
  public Board() {
  }
  
  public Board(int no, String contents) {
    this.no = no;
    this.contents = contents;
  }
  
  @Override
  public String toString() {
    return "Board [no=" + no + ", contents=" + contents + "]";
  }
  
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  
  
}
