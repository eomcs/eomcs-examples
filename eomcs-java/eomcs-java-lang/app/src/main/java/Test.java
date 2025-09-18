public class Test {


  public static void main(String[] args) {
    String str = "2000원";
    System.out.println(str.length());
    int i = Integer.parseInt(str.replace("원", ""));
    System.out.println(i + 100);
    int a = 100;
    int $ = 200;
  }

}
