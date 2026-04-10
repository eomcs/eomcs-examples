## 형식화된 문자열 리터럴

형식화된 문자열 리터럴 또는 f-문자열은 'f' 또는 'F'로 시작하는 문자열 리터럴입니다. 이러한 문자열은 중괄호 `{}`로 구분된 표현식을 포함할 수 있습니다.

중괄호 외부의 문자열 부분은 그대로 처리됩니다. 이스케이프 시퀀스는 일반 문자열 리터럴에서처럼 디코딩됩니다. 대체 표현식은 줄 바꿈(예: 삼중 따옴표 문자열에서)을 포함할 수 있지만, 주석은 포함할 수 없습니다. 각 표현식은 형식화된 문자열 리터럴이 나타나는 컨텍스트에서 왼쪽에서 오른쪽 순서대로 평가됩니다.

다음은 몇 가지 예입니다:
```python
name = "Fred"
f"He said his name is {name}."
```
```text
'He said his name is Fred.'
```

f-문자열에서 더 흥미로운 일을 할 수도 있습니다. 예를 들어:
```python
f"{name.lower()} is funny."
```

```text
'fred is funny.'
```
형식화된 문자열 리터럴에 대한 자세한 정보는 <a href="https://docs.python.org/3/reference/lexical_analysis.html#formatted-string-literals">Python 문서</a>를 참조하세요.

더 구조적이고 자세한 정보를 원한다면 [Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/6037?utm_source=jba&utm_medium=jba_courses_links)도 참고할 수 있습니다.

### 과제
f-문자열을 직접 만들어 보세요. 또한 코드를 실행하여 출력 결과를 확인해 보세요.

<div class="hint"><code>name</code> 변수에 할당된 값은 문자열이어야 하므로 따옴표 안에 입력해야 합니다. 
예: <code>'Max'</code>.</div>