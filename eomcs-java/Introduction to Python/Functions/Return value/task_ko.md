## 반환값

함수는 `return` 키워드를 사용하여 호출자에게 값을 반환할 수 있습니다. 반환된 값은 변수에 대입하거나 단순히 출력할 수 있습니다. 사실, `return` 문이 없는 함수도 값을 반환합니다. 이 값은 `None`이라고 하며(빌트인 이름입니다), 보통 인터프리터에서 이 값은 출력되지 않습니다. 하지만 정말로 보고 싶다면, `print(some_func())`를 사용할 수 있습니다.

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/5900#execution-and-return?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

><i>함수 본문의 첫 번째 문장은 선택적으로 문자열 리터럴일 수 있습니다. 이 문자열 리터럴은 함수의 문서화 문자열 또는 도크스트링(docstring)이라고 합니다(도크스트링에 대한 자세한 내용은 Python 문서의 <a href="https://docs.python.org/3/tutorial/controlflow.html#tut-docstrings">문서화 문자열</a> 섹션에서 찾을 수 있습니다). 작성하는 코드에 도크스트링을 포함하는 것은 좋은 습관입니다.</i>

[Fibonacci 수열](https://en.wikipedia.org/wiki/Fibonacci_number)에서, 첫 번째와 두 번째 숫자는 `1`과 `1`입니다(때로는 `0`과 `1` 또는 `1`과 `2`로 시작하기도 합니다). 이후의 숫자는 이전 두 숫자의 합입니다.

### 과제
Fibonacci 수열의 숫자 목록을 `n`까지 반환하는 함수를 작성하세요.

<div class='hint'><code>b</code>를 1로 초기화하세요.</div>
<div class='hint'><code>b</code>를 <code>a + b</code>로 업데이트하세요.</div>
<div class='hint'><code>a</code>를 <code>tmp_var</code>로 업데이트하세요.</div>