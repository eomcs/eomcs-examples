package com.eomcs.quickstart.oop.exam14;

import java.util.function.Consumer;

public class App2 {

  static class User {
    String name;
    int age;

    public User(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  static <T> void processUsers(T[] arr, Consumer<T> consumer) {
    for (T obj : arr) {
      consumer.accept(obj);
    }
  }

  public static void main(String[] args) {
    User[] users = {new User("홍길동", 20), new User("임꺽정", 30), new User("유관순", 17)};
    processUsers(users, user -> System.out.println(user.name));
  }
}
