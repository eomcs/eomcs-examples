package effectivejava.ch04.item25.exam01;

public class MyList {
  private Object[] items;
  private int size = 0;

  public MyList() {
    this.items = new Object[10];
  }

  public void add(Object obj) {
    if (size == items.length) throw new IndexOutOfBoundsException("MyList 용량 초과");
    items[size++] = obj;
  }

  public Object get(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("잘못된 인덱스: " + index);
    return items[index];
  }

  public int size() {
    return size;
  }

  public Iterator iterator() {
    return new Iterator() {
      private int cursor = 0;

      public boolean hasNext() {
        return cursor < MyList.this.size();
      }

      public Object next() {
        return MyList.this.get(cursor++);
      }
    };
  }
}
