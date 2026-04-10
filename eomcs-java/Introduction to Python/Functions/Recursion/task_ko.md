## 재귀

<b>재귀</b>(recursion)라는 단어는 라틴어 <i>recurrere</i>에서 유래되었으며, 되돌아가다, 되풀이하다, 재발하다라는 의미를 가지고 있습니다.  
프로그래밍에서 재귀는 함수가 자기 자신을 호출하는 코딩 기법을 의미합니다.

대부분의 경우 재귀가 필수적이지는 않지만, 특정 상황에서는 자기 참조적인 정의가 필요할 때가 있습니다.  
[트리 형태의 데이터 구조](https://en.wikipedia.org/wiki/Tree_(data_structure))를 탐색할 때가 좋은 예시입니다. 이러한 구조들은 [중첩된](course://Data structures/Nested Lists) 형태로 되어 있어 재귀 정의에 잘 맞습니다. 동일한 작업을 처리하기 위한 비재귀적 알고리즘은 매우 복잡할 수 있습니다.  

다음은 간단한 재귀 함수의 예입니다. 이 함수는 숫자를 인수로 받아 지정된 숫자부터 0까지의 숫자를 출력합니다.  
재귀 호출에서는 현재 `n` 값보다 1 적은 값을 인수로 전달하므로, 각 재귀 단계에서 기본 사례(0)에 더 가까워집니다.

```python
def countdown(n):
    print(n, end=' ')
    if n == 0:
        return             # 재귀 종료
    else:
        countdown(n - 1)   # 재귀 호출


countdown(4)
```
```text
4 3 2 1 0 
```

<div class="hint">이 함수는 인수의 유효성을 검사하지 않습니다. 만약 <code>n</code>이 정수가 아니거나 음수인 경우,  
기본 사례에 도달하지 않기 때문에 <a href="https://docs.python.org/3/library/exceptions.html?highlight=recursionerror#RecursionError"><code>RecursionError</code></a> 예외가 발생합니다:

```python
countdown(-10)
```
```text
RecursionError: maximum recursion depth exceeded while calling a Python object
```
Python의 재귀 한도를 확인하려면 sys 모듈의 [`getrecursionlimit()`](https://docs.python.org/3/library/sys.html#sys.getrecursionlimit) 함수를 사용하고,  
이 한도를 변경하려면 [`setrecursionlimit()`](https://docs.python.org/3/library/sys.html#sys.setrecursionlimit)을 사용할 수 있습니다:

```python
from sys import setrecursionlimit
setrecursionlimit(3000)
getrecursionlimit()
```
```text
3000
```
</div>

재귀가 모든 상황에 유용한 것은 아니라는 점을 명심하세요. 일부 문제에서는 재귀적 해결 방법이 가능하더라도  
우아하기보다는 번거로울 수 있습니다. 재귀 구현은 종종 비재귀 구현보다 더 많은 메모리를 소비하며, 경우에 따라 실행 속도가 느려질 수도 있습니다.

좀 더 체계적이고 자세한 내용을 보려면 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/7665?utm_source=jba&utm_medium=jba_courses_links)를 참고하세요.

### 작업
코드 편집기에서 양의 정수의 [팩토리얼](https://en.wikipedia.org/wiki/Factorial)을 계산하는 재귀 함수를 구현하세요.  
1과 0의 경우는 1을 반환하고, 나머지 숫자에 대해서는 이 숫자(`n`)와 이전 숫자(`n-1`)의 팩토리얼을 곱한 값을 계산합니다.

<div class="hint">재귀 함수 호출을 잊지 마세요.</div>