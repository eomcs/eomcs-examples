## 튜플

튜플은 또 다른 표준 시퀀스 데이터 타입을 나타냅니다.  
튜플은 리스트와 거의 동일하지만, 튜플과 리스트의 가장 큰 차이점은 튜플이 **불변**(immutable)이라는 점입니다. 즉, 튜플에서는 요소를 추가, 교체, 삭제할 수 없습니다.  
튜플은 쉼표로 구분된 요소들이 괄호 안에 들어가는 형태로 생성됩니다. 예를 들어: 

```python
(a, b, c)
```

특별한 경우는 0개 또는 1개의 항목을 포함하는 튜플을 생성할 때입니다.  
빈 튜플은 빈 괄호 쌍으로 생성되며, 1개의 항목을 포함하는 튜플은 값 뒤에 쉼표를 붙여 생성됩니다. 예를 들어:

```python
empty = ()
singleton = 'hello',    # <-- 뒤에 붙은 쉼표를 주의하세요
len(empty)
```
```text
0
```
```python
len(singleton)
```
```text
1
```
```python
singleton
```
```text
('hello',)
```

문장 `t = 12345, 54321, 'hello!'`은 튜플 패킹(tuple packing)의 예시입니다.  
값 `12345`, `54321`, 그리고 `hello!`가 하나의 튜플로 묶인 것입니다.  

튜플에서도 몇 가지 리스트 메서드를 사용할 수 있습니다.  
튜플에 대한 자세한 내용은 <a href="https://docs.python.org/3/tutorial/datastructures.html#tuples-and-sequences">여기</a>에서 확인할 수 있습니다.

좀 더 구조화되고 상세한 정보를 보려면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/7462?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수도 있습니다.

### 작업
튜플 `alphabet`의 길이를 출력하세요. 그런 다음 `'fun_tuple'`이라는 단일 요소를 가진 튜플을 생성하세요.  
코드를 실행하면 출력 결과를 확인할 수 있습니다.  

<div class='hint'><code>len()</code> 함수를 사용하세요.</div>

<div class='hint'>단일 요소를 가진 튜플에서는 뒤에 쉼표를 붙이는 것을 잊지 마세요.</div>