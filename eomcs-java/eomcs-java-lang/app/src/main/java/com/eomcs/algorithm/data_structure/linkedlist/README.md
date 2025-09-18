# LinkedList 만들기

1) LinkedList 클래스 정의
2) 값을 담을 노드 클래스를 설계한다.
3) 첫 번째 노드와 마지막 노드의 주소를 담을 필드를 추가한다.
   목록 크기를 저장할 필드를 추가한다.
4) 목록에 값을 추가하는 add() 메서드를 정의한다.
5) 목록에서 값을 조회하는 get() 메서드를 정의한다.
6) 목록에서 특정 인덱스 위치에 값을 삽입하는 add(int, Object) 메서드를 정의한다.
   - Node의 생성자를 추가한다.
7) 목록에서 특정 인덱스에 값을 제거하는 remove(int) 메서드를 정의한다.
8) 목록에서 특정 인덱스의 값을 바꾸는 set(int, Object) 메서드를 정의한다.
9) 목록의 데이터를 새 배열에 담아 리턴하는 toArray() 메서드를 정의한다.
10) 인스턴스 필드에 대해 캡슐화를 적용한다.
   - 목록 크기를 리턴하는 size()를 추가로 정의한다.

테스트2: MyLinkedListTest2
11) 제네릭을 적용한다.
 
테스트3: MyLinkedListTest3
12) 파라미터로 받은 배열에 값을 채워주는 toArray(E[]) 메서드를 추가한다.

테스트4: MyLinkedListTest4
13) Object.clone()을 오버라이딩: shallow copy

테스트5: MyLinkedListTest5
14) Object.clone()을 오버라이딩: deep copy