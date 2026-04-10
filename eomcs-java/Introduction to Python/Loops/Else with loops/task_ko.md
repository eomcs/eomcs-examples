## 루프와 else

`break` 문이 가장 안쪽에 있는 `for` 또는 `while` 루프를 종료시키는 것을 보았습니다.

Python에서는 루프 문에 `else` 절을 추가할 수도 있습니다. 이것은 반복 가능한 객체가 소진될 때(`for`의 경우) 또는 조건이 `False`가 될 때(`while`의 경우) 실행되며, `break` 문으로 루프가 종료된 경우에는 실행되지 않습니다. 소수를 검색하는 루프의 예제를 확인해 보세요:

```python
for n in range(2, 10):
    for x in range(2, n):
        if n % x == 0:
            print(n, 'equals', x, '*', n//x)
            break
    else:
        # 루프가 약수를 찾지 못한 경우
        print(n, 'is a prime number')
```
```text
2 is a prime number
3 is a prime number
4 equals 2 * 2
5 is a prime number
6 equals 2 * 3
7 is a prime number
8 equals 2 * 4
9 equals 3 * 3
```
이 코드에서 `else` 절은 `if` 문이 아니라 `for` 루프에 속합니다.

기억하세요, `if` 문 뒤에 있는 `else`는 표현식이 `True`인 경우 건너뛰고 실행되지 않습니다. 하지만 루프의 경우, `else` 절은 루프 자체가 완료된 후 실행됩니다 (`break`가 루프를 중간에 종료한 경우 제외).

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6302?utm_source=jba&utm_medium=jba_courses_links)에서 확인할 수 있습니다.

### 과제
코드 편집기에서 두 번째 루프에 두 줄의 코드를 추가하여 루프가 숫자 1과 2만 출력하고 절대로 `"for loop is done"`라는 문구를 출력하지 않도록 만드세요.

<div class="hint">루프는 숫자 3에서 종료되어야 합니다.</div>