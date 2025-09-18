class Exam01 {

  public static void main(String[] args) {
    System.out.println("Hello!");

    // 설계도
    class Student {
      String name;
      int age;
      boolean working;
    }

    // 설계도에 따라 객체 생성(메모리 준비)
    Student obj = new Student();

    // 객체 프로퍼티에 값 저장
    obj.name = "홍길동";
    obj.age = 20;
    obj.working = true;
    // obj.tel = "02-1111-2222"; // 설계도에 없는 프로퍼티는 사용할 수 없다.

    // 객체 프로퍼티에 저장된 값 꺼내기
    System.out.println(obj.name);
    System.out.println(obj.age);
    System.out.println(obj.working);

    java.util.HashMap obj2 = new java.util.HashMap();
    obj2.put("name", "홍길동");
    obj2.put("age", 20);
    obj2.put("working", true);

    System.out.println(obj2.get("name"));
    System.out.println(obj2.get("age"));
    System.out.println(obj2.get("working"));
  }
}