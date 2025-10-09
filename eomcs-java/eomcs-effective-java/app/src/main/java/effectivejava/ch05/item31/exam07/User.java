package effectivejava.ch05.item31.exam07;

public class User implements Comparable<User> {
  protected String name;

  public User(String name) {
    this.name = name;
  }

  @Override
  public int compareTo(User o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + '}';
  }
}
