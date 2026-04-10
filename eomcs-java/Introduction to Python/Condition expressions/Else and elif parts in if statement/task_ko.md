## else와 elif 문

`elif`와 `else` 문은 `if` 문을 보완합니다.  
`elif` 부분은 0개 이상 포함될 수 있으며, `else` 부분은 선택 사항입니다.  
`elif` 키워드는 'else if'의 줄임말이며, 과도한 들여쓰기를 방지하는 데 유용합니다.

<div class="hint">`if … elif … elif …` 구조는 Java와 같은 다른 언어에서 사용하는 <code>switch</code> 또는 <code>case</code> 문을 대신할 수 있습니다.</div>

조건 실행 시, 표현식을 하나씩 평가하여 하나가 `True`로 판명되면 해당 블록이 실행되며,  
`if` 문 다른 부분은 평가되지 않습니다.  
모든 표현식이 false인 경우, `else` 절이 있다면 해당 블록이 실행됩니다.

```python
a = 200
b = 33
if b > a:
  print("b가 a보다 큽니다")
elif a == b:
  print("a와 b는 같습니다")
else:
  print("a가 b보다 큽니다")
```
```text
a가 b보다 큽니다
```

<details>

단순한 if-else 문은 이전 과제에서 보여드린 것처럼 한 줄의 코드로도 작성할 수 있습니다. 예를 들어,  
```python
if a > b:
    a += 1
else: 
    a -= 1
```
는 다음과 같이 작성할 수 있습니다.  
```python
a += 1 if a > b else a -= 1
```
</details>
  
더 체계적이고 자세한 정보를 보려면 [여기](https://hyperskill.org/learn/step/5932?utm_source=jba&utm_medium=jba_courses_links)와  
[여기](https://hyperskill.org/learn/step/5926?utm_source=jba&utm_medium=jba_courses_links) Hyperskill 지식 베이스 페이지를 참조하세요.

### 작업  
`name`이 `"John"`과 같으면 `True`를 출력하고, 그렇지 않으면 `False`를 출력하세요.  

<div class='hint'><code>if</code> 키워드와 <code>==</code> 연산자를 사용하세요.</div>  
<div class='hint'><code>else</code> 키워드를 사용하세요.</div>