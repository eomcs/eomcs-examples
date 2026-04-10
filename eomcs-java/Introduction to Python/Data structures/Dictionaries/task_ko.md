## 딕셔너리

딕셔너리는 리스트와 비슷하지만, 값을 접근할 때 인덱스 대신 키를 사용하여 값을 조회한다는 점이 다릅니다. 키는 변경할 수 없는(immutable) 타입이어야 합니다. 문자열과 숫자는 항상 키로 사용할 수 있으며, 튜플은 변경 불가능한 객체만 포함한다면 키로 사용할 수 있습니다. 리스트는 키로 사용할 수 없습니다.

딕셔너리를 `<code>key: value</code>` 쌍의 집합으로 생각해 보세요. 단, 한 딕셔너리 내에서 키는 고유해야 합니다. 딕셔너리는 중괄호로 묶여 있으며, 예를 들어 `dct = {'key1': "value1", 'key2': "value2"}`와 같습니다. 중괄호 쌍 `{}`는 빈 딕셔너리를 생성합니다.

딕셔너리는 `dict` 생성자를 사용하여 생성할 수도 있습니다:
```python
a = dict(one=1, two=2, three=3)
b = {'one': 1, 'two': 2, 'three': 3}
c = dict([('two', 2), ('one', 1), ('three', 3)])
print(a == b == c)
```
```text
True
```

딕셔너리의 값을 접근할 때는 리스트에서 값을 접근하는 방법과 유사하지만, 인덱스 대신 키를 사용합니다. 이 데이터 구조에 대한 자세한 정보는 <a href="https://docs.python.org/3/tutorial/datastructures.html#dictionaries">여기</a>에서 확인할 수 있습니다.

더 구조적이고 상세한 정보를 원한다면 [Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6481?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수도 있습니다.

### 작업
자렛(Jared)의 번호 `570`을 전화번호부에 추가하세요.
제러드(Gerard)의 번호를 전화번호부에서 삭제하세요.
`phone_book`에서 제인(Jane)의 전화번호를 출력하세요.

<div class='hint'>딕셔너리 인덱싱을 사용하세요. 예: <code>dct[key]</code></div>