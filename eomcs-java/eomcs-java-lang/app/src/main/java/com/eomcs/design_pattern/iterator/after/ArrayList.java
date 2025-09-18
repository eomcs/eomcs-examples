package com.eomcs.design_pattern.iterator.after;

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
  
  // 자신이 보유한 데이터를 대신 꺼내주는 일을 하는 객체를 리턴한다.
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      // 이 클래스는 ArrayList에서 값을 꺼내주는 일을 전문적으로 한다.
      // => 이런 일을 하는 객체를 "Iterator"라 부른다.
      //
      int index = 0;
      
      @Override
      public boolean hasNext() {
        return index < size();
      }

      @Override
      public E next() {
        return (E) get(index++);
      }
    };
  }
  
  
}

















