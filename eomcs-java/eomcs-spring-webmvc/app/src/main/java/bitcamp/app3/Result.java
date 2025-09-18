package bitcamp.app3;

public class Result {
  String status;
  Object data;

  public Result() {
  }

  public Result(String status, Object data) {
    this.status = status;
    this.data = data;
  }

  @Override
  public String toString() {
    return "Result{" +
        "status='" + status + '\'' +
        ", data=" + data +
        '}';
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
