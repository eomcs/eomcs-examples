## 리스트 작업

문자열과는 다르게, 리스트는 변경 가능한(mutable) 타입입니다. 즉, `lst[index] = new_item`를 사용하여 리스트의 내용을 변경하는 것이 가능합니다.

```python
cubes = [1, 8, 27, 65, 125]  # 여기엔 뭔가 문제가 있습니다
4 ** 3  # 4의 세제곱은 64이지, 65가 아닙니다!
```
```text
64
```
```python
cubes[3] = 64  # 잘못된 값을 교체합니다
cubes
```
```text
[1, 8, 27, 64, 125]
```

`append()` 메서드나 리스트 연결(concatenation)을 사용하여 리스트 끝에 새 항목을 추가할 수 있습니다.

```python
squares = [1, 4, 9, 16, 25]
squares.append(6**2)
squares
```
```text
[1, 4, 9, 16, 25, 36]
```

다른 유용한 리스트 메서드에 대해 알아보려면 <a href="https://docs.python.org/3/tutorial/datastructures.html#more-on-lists">이 페이지</a>를 확인하세요.

더 구조화되고 자세한 정보를 원한다면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/6031?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수도 있습니다.

### 작업
`animals` 리스트에서 `"dino"`를 `"dinosaur"`로 교체하세요.  
<div class='hint'>리스트 인덱싱 작업과 값 할당을 사용하세요.</div>