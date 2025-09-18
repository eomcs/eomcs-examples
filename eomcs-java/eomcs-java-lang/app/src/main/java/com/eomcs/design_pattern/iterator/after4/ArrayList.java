package com.eomcs.design_pattern.iterator.after4;

public class ArrayList<E> {

  static final int DEFAULT_SIZE = 5;

  Object[] arr;
  int size;

  public ArrayList() {
    this(0);
  }

  public ArrayList(int capacity) {
    if (capacity > DEFAULT_SIZE)
      arr = new Object[capacity];
    else 
      arr = new Object[DEFAULT_SIZE];
  }

  public Object[] toArray() {
    Object[] list = new Object[this.size];
    for (int i = 0; i < this.size; i++) {
      list[i] = this.arr[i];
    }
    return list;
  }

  public void add(E value) {
    if (this.size == arr.length) 
      increase();

    arr[this.size++] = value;
  }

  public int insert(int index, E value) {
    if (index < 0 || index >= size)
      return -1;

    if (this.size == arr.length) 
      increase();

    for (int i = size - 1; i >= index; i--)
      this.arr[i + 1] = this.arr[i];

    this.arr[index] = value;
    size++;

    return 0;
  }

  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= size)
      return null;

    return (E) this.arr[index];
  }

  @SuppressWarnings("unchecked")
  public E set(int index, E value) {
    if (index < 0 || index >= size)
      return null;

    E old = (E) this.arr[index];
    this.arr[index] = value;
    return old;
  }

  @SuppressWarnings("unchecked")
  public E remove(int index) {
    if (index < 0 || index >= size)
      return null;

    E old = (E) this.arr[index];

    for (int i = index; i < size - 1; i++) 
      this.arr[i] = this.arr[i+1];

    size--;

    return old;
  }

  public int size() {
    return this.size;
  }

  private void increase() {
    int originSize = arr.length;
    int newSize = originSize + (originSize >> 1);
    Object[] temp = new Object[newSize];
    for (int i = 0; i < this.arr.length; i++) {
      temp[i] = this.arr[i];
    }
    arr = temp;
  }

  // Iterator 구현체를 제공한다.
  public Iterator<E> iterator() {

    // local class(로컬 클래스)
    // => 바깥 클래스의 인스턴스를 사용하면서 특정 메서드 안에서만 사용할 클래스라면 
    //    로컬 클래스로 정의하라!
    // => 물론 논스태틱 중첩 클래스처럼 바깥 클래스의 인스턴스 주소를 다루는 필드와 
    //    생성자 파라미터가 자동으로 추가된다.
    class ArrayListIterator<T> implements Iterator<T> {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < ArrayList.this.size();
      }

      @SuppressWarnings("unchecked")
      @Override
      public T next() {
        return (T) ArrayList.this.get(index++);
      }
    }

    return new ArrayListIterator<E>();
  }
}

















