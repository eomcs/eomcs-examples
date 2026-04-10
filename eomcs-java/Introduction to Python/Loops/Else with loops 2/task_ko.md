## 루프와 else 2

이런 유형의 `else`는 루프 내부에 `if` 조건이 있고, 그 조건이 루프 변수에 따라 달라지는 경우에만 유용합니다. 이전 과제에서의 예제를 다시 살펴봅시다:

```python
for n in range(2, 10):
    for x in range(2, n):
        if n % x == 0:
            print(n, 'equals', x, '*', n//x)
            break
    else:
        # 루프가 약수를 찾지 못하고 끝났을 경우
        print(n, 'is a prime number')
```

이 `else` 문은 오직 `n`이 소수인 경우에만 실행됩니다. 즉, 내부 `for` 루프의 어느 반복에서도 `if` 문이 실행되지 않았을 때 실행됩니다.

### 과제
[함수](course://Loops/Else with loops 2) `contains_even_number()` 안에 `for` 루프를 작성하여 리스트 `lst`를 반복하고 짝수 요소를 발견하면,
`f"List {lst} contains an even number."`를 출력하고 루프를 종료하세요. 만약 해당 요소를 발견하지 못하면,
`f"List {lst} does not contain an even number."`를 출력하세요.

<div class="hint">

주어진 숫자가 짝수인지 테스트하려면 `if i % 2 == 0:`를 사용할 수 있습니다.
</div>

<div class="hint">

짝수를 찾았을 경우 루프를 종료하려면 `break` 문을 사용하세요.
</div>

<div class="hint">

`f"List {lst} does not contain an even number."`를 출력하려면 `else` 절을 사용하세요.
</div>