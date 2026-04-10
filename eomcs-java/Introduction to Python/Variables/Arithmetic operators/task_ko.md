## 산술 연산자

다른 프로그래밍 언어에서와 마찬가지로, 덧셈( `+` ), 뺄셈( `-` ), 곱셈( `*` ), 나눗셈( `/` ) 연산자는 숫자와 함께 사용할 수 있습니다. 추가적으로, Python에서는 거듭제곱( `**` ), 나머지( `%` ), 그리고 버림 나눗셈( `//` ) 연산자를 제공합니다.

- `*` (곱셈) 연산자는 인수의 곱을 반환합니다. 인수는 숫자일 수도 있고, 하나의 인수가 정수이며 다른 하나는 시퀀스여야 합니다.

- `/` (나눗셈) 및 `//` (버림 나눗셈) 연산자는 인수의 몫을 반환합니다. 정수의 나눗셈은 부동소수점(float)를 반환하며, 정수의 버림 나눗셈은 정수를 반환합니다.

- `%` (나머지) 연산자는 첫 번째 인수를 두 번째 인수로 나눈 나머지를 반환합니다.

- `+` (덧셈) 연산자는 인수의 합을 반환합니다. 인수는 모두 숫자이거나 동일한 유형의 시퀀스여야 합니다.

- `-` (뺄셈) 연산자는 인수의 차를 반환합니다.

예제:
```python
a = 16
b = 3
result = a // b  # result는 5가 됩니다
result = a % b   # result는 1이 됩니다
result = a ** 2  # result는 256이 됩니다 (16의 제곱)
```

이진 산술 연산은 일반적인 우선순위를 따릅니다. 이러한 연산 중 일부는 특정 비숫자 유형에도 적용된다는 점을 참고하세요.

이 주제에 대해 더 자세히 알아보려면 <a href="https://docs.python.org/3/reference/expressions.html#binary-arithmetic-operations">여기</a>를 참조하세요.

더 구조적이고 자세한 정보를 원한다면, [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/5865?utm_source=jba&utm_medium=jba_courses_links)를 참고할 수도 있습니다.

### 작업
 - `init_number`에 저장된 값을 `2`로 나누세요.
 - 그러한 나눗셈의 나머지를 계산하세요.
 - `division_result`를 `2`로 곱하세요.
 - `division_remainder`를 `multiplication_result`에 더하세요.
 - `multiplication_result`에서 `init_number`를 빼세요.
 - `init_number`를 `2`로 버림 나눗셈 하세요.
 - `multiplication_result`를 3의 거듭제곱으로 만드세요.

<div class='hint'>먼저 <code>/</code> 연산자를 사용하세요.</div>
<div class='hint'>그다음 <code>%</code> 연산자를 사용하세요.</div>

<div class='hint'>그다음 <code>*</code> 연산자를 사용하세요.</div>

<div class='hint'>그다음 <code>+</code> 연산자를 사용하세요.</div>

<div class='hint'>그다음 <code>-</code> 연산자를 사용하세요.</div>

<div class='hint'>그다음 <code>//</code> 연산자를 사용하세요.</div>

<div class='hint'>그다음 <code>**</code> 연산자를 사용하세요.</div>