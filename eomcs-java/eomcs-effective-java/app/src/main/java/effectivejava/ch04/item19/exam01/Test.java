// # 아이템 19. 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라
// [상속을 고려한 설계]
// 1) 문서화
// - 메서드를 재정의하면 어떤 일이 일어나는지를 정확히 정리하여 문서로 남겨야 한다.
//   상속용 클래스는 재정의할 수 있는 메서드를 내부적으로 어떻게 이용하는지(self-use) 문서로 남겨야 한다.
// - API로 공개된 메서드에서 자신의 또 다른 메서드를 호출(self-use)할 경우,
//   어떤 순서로 호출하는지, 각각의 호출 결과가 이어지는 처리에 어떤 영향을 미치는지 문서로 남겨야 한다.
// - 즉, 재정의 가능 메서드를 호출할 수 있는 모든 상황을 문서로 남겨야 한다.
// 2) protected 메서드/필드
// - 클래스의 내부 동작 과정에 끼어들 수 있는 훅(hook)을 잘 선별하여
//   protected 메서드로 공개해야 할 수도 있다.
//   (드물게 protected 필드로 공개해야 할 수도 있다.)
//   예) java.util.AbstractList의 removeRange() 메서드
// 3) 생성자, clone(), readObject()
// - 상속용 클래스의 생성자는 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다.
//   상위 클래스가 생성자가 하위 클래스의 생성자보다 먼저 실행되기 때문에,
//   재정의한 메서드가 하위 클래스의 생성자가 초기화하는 값에 의존한다면 문제가 발생할 것이다.
// - clone()이나 readObject()도 생성자와 비슷한 효과를 내기 때문에,
//   이 메서드에서도 직.간접적으로 재정의 가능 메서드를 호출해서는 안된다.
//
// 이렇게 클래스를 상속용으로 설계하려면 엄청난 노력이 들고 그 클래스에 안기는 제약도 상당하다.
//
// [상속으로 발생하는 문제를 피하는 가장 좋은 방법]
// "상속용으로 설계하지 않은 클래스는 상속을 금지"하는 것이다.
//
// [상속이 캡슐화를 해치는 현상]
// - '좋은 API 문서란', '어떻게'가 아니라 '무엇'을 하는지를 설명해야 한다.
//   즉 클라이언트 입장에서는 내부 구현이 어떻게 되었는지 알 필요가 없다.
// - 그런데 안전하게 상속할 수 있도록 하려면 내부 구현 방식을 설명해야만 한다.
// - 이것은 상속이 캡슐화를 깨뜨린다는 것을 증명하는 대표적인 사례이다.

package effectivejava.ch04.item19.exam01;

// [주제] 상속을 고려한 설계: 효율적인 하위 클래스를 만들 수 있도록 내부 동작 일부를 protected 메서드로 공개하기

// [상속용 클래스를 만드는 제작자 입장]
// - AbstractList 클래스는 하위 클래스가 clear() 메서드를 호출했을 때,
//   removeRange()가 어떤 식으로 개입하여 작업을 수행하는지 문서로 공개하고,
//   하위 클래스를 위해 protected로 공개한다.
// - 하위 클래스 제작자가 자신이 구현하는 리스트 특성에 맞춰서
//   removeRange() 메서드를 재정의하라는 의미다.
// - 코드 예:
//    abstract class AbstractList<E> extends AbstractCollection<E> {
//
//      protected void removeRange(int fromIndex, int toIndex) {
//        ListIterator<E> it = listIterator(fromIndex);
//        for (int i = 0, n = toIndex - fromIndex; i < n; i++) {
//            it.next();
//            it.remove();
//        }
//      }
//
//      public void clear() {
//        removeRange(0, size());
//      }
//
//    }
// - removeRange(): 단순히 반복자를 통해 반복하면서 요소를 제거한다.
// - clear(): 삭제 작업에 removeRange()를 사용한다.

// [AbstractList 클래스를 상속받아 하위 클래스를 만드는 제작자 입장]
// - AbstractList를 상속받는 제작자는 문서를 읽고,
//   clear() 메서드가 내부적으로 removeRange()를 호출하여 삭제를 수행한다는 사실을 이해한다.
// - clear() 메서드의 성능을 높이기 위하여,
//   ArrayList 자료구조(배열)에 맞게 다음과 같이 removeRange() 메서드를 재정의한다.
// - 코드 예:
//    class ArrayList<E> extends AbstractList<E> {
//
//      @Override
//      protected void removeRange(int fromIndex, int toIndex) {
//        int numMoved = size - toIndex;
//        System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
//
//        int newSize = size - (toIndex - fromIndex);
//        for (int i = newSize; i < size; i++) {
//          elementData[i] = null; // GC 돕기
//        }
//        size = newSize;
//      }
//
//    }
//
// - 원래의 removeRange() 메서드는 단순히 반복하면서 remove()를 호출한다.
// - remove()는 원소를 삭제하고 뒤에 있는 원소들을 앞으로 당기는 작업을 수행한다.
// - 이 상황에서 clear()를 호출한다면, n개의 원소를 삭제하는데 O(n^2)의 시간이 걸린다.
// - 삭제 원리:
//     n 개의 원소를 삭제:
//       1: remove(0) -> n-1개의 원소를 당김
//       2: remove(1) -> n-2개의 원소를 당김
//       ...
//       n-1: remove(n-1) -> 1개의 원소를 당김
//       n: remove(n-1) -> 0개의 원소를 당김
//
//       총 이동 회수 = (n-1) + (n-2) + ... +   1   +   0 = n(n-1)/2 = O(n^2)
//       계산 방법:
//       1) 계산하기 쉽게 이동 회수의 결과를 두 배로 만들자
//          ((n-1) + (n-2) + ... +   1   +   0) + ((n-1) + (n-2) + ... +   1   +   0)
//       2) 각 항목을 꺼꾸로 더하자
//          ((n-1) + (n-2) + ... +   1   +   0)
//        +    0       1     ...   (n-2)   (n-1)
//        --------------------------------------
//          (n-1) + (n-1) + ... + (n-1) + (n-1) = n(n-1)
//       3) 최종 결과를 2로 나눠 원래 값으로 돌려놓자
//          n(n-1)/2 = n^2 - n
//       4) 시간 복잡도로 표현하면
//          O(n^2) (상수항과 저차항은 무시)

// - 따라서 n개의 원소를 삭제하려면 O(n^2)의 시간이 걸린다.
//   만약 removeRange() 메서드를 재정의하지 않는다면,
//   clear()를 호출할 때, 제거할 원소 수의 제곱에 비례해 성능이 느려질 것이다.
// - 재정의한 removeRange() 메서드는 성능을 높이기 위해
//   삭제보다는 백열의 값을 복사하는 방식으로 처리하였다.
//   이를 통해 n개의 원소를 삭제하는데 걸리는 시간은 O(n)이 된다.
//
public class Test {
  public static void main(String[] args) throws Exception {
    // [정리]
    // - 위의 설명처럼 상속용 클래스를 설계할 때 어떤 메서드를 protected로 공개할지,
    //   결정하는 것은 매우 중요하다.
    // - 결정하는 기준은 있을까?
    //   절대적인 마법의 기준은 없다.
    //   다만 심사숙고해서 잘 예측해본 다음, 실제 하위 클래스를 만들어 시험해보는 것이 최선이다.
    //   protected 메서드도 내부 구현에 해당하므로 가능한 공개되는 수는 적어야 한다.
    //   멤버의 접근제어 기본은 private임을 잊지 말자!
    // - 상속용 클래스는 배포 전에 반드시 하위 클래스를 만들어 검증해야 한다.

  }
}
