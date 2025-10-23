package effectivejava.ch11.item79.exam04;

public interface SetObserver<E> {
  // ObserverableSet에 요소가 추가되었을 때 호출됨
  void added(ObservableSet<E> set, E element);
}
