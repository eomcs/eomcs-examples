package com.eomcs.design_pattern.iterator.after2;

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
    return new ArrayListIterator<E>(this);
  }

  // static nested class(스태틱 중첩 클래스)
  static class ArrayListIterator<E> implements Iterator<E> {

    ArrayList<E> list;
    int index = 0;

    public ArrayListIterator(ArrayList<E> list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return index < list.size();
    }

    @Override
    public E next() {
      return list.get(index++);
    }
  }
}

















