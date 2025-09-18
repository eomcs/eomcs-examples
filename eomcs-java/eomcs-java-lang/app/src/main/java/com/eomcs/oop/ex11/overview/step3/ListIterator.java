package com.eomcs.oop.ex11.overview.step3;

// MyList 컬렉션에서 데이터를 꺼내는 일을 한다.
// 
public class ListIterator implements Iterator {

  MyList list;
  int cursor;

  public ListIterator(MyList list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return cursor < list.size();
  }

  @Override
  public Object next() {
    return list.get(cursor++);
  }
}
