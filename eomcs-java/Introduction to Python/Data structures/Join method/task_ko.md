## join() 메서드

`.join()`은 사실 문자열 메서드이지만, 이를 다루기 위해서는 문자열, 리스트, 튜플과 같은 반복가능 객체(iterable objects)에 대한 이해가 필요하기 때문에 지금 논의하고자 합니다.  
이 [메서드](https://docs.python.org/3/library/stdtypes.html#str.join)는 반복가능 객체로부터 문자열을 생성하기 위한 유연한 방법을 제공합니다.  
`join()`은 반복가능 객체(리스트, 문자열 또는 튜플 등의 각 요소)를 특정 문자열 구분자(separator)를 사용하여 결합하고, 하나의 연결된 문자열을 반환합니다.  
만약 반복가능 객체에 문자열이 아닌 값이 포함되어 있다면 `TypeError`가 발생합니다.

`join()` 메서드의 문법은 다음과 같습니다:

```python
string.join(iterable)
```

예제:

```python
string_ = 'abcde'  # 문자열(iterable)
tuple_ = ('aa', 'bb', 'cc')  # 튜플(iterable)
list_ = ['Python', 'programming language']  # 리스트(iterable)

print(' + '.join(string_))  # ' + ' 구분자를 사용하여 결합
print(' = '.join(tuple_))  # ' = ' 구분자를 사용하여 결합

sep = ' is a '
print(sep.join(list_))  # ' is a ' 구분자를 사용하여 결합
```
```text
a + b + c + d + e
aa = bb = cc
Python is a programming language
```

더 구조적이고 상세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6972#join-a-list?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수 있습니다.

### 과제(Task)
`joined` 변수에 값을 할당하여 `print` 문이 아래 내용을 출력하도록 만드세요:
```text
I like apples and I like bananas and I like peaches and I like grapes
```

<div class="hint">예제를 자세히 살펴보고 동일한 방식으로 작성하세요!</div>
<div class="hint"><code>fruits</code>는 이 경우의 반복가능 객체이며, <code>separator</code>는 구분자 문자열입니다.</div>