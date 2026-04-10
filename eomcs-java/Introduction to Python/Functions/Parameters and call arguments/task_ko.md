## 매개변수와 호출 인자

함수 매개변수는 함수 이름 뒤의 괄호 `()` 안에 정의됩니다. 매개변수는 함수에 전달된 인자를 위한 변수 이름 역할을 합니다.

매개변수와 인자는 함수에 전달되는 정보라는 동일한 개념을 나타냅니다. 하지만 매개변수는 함수 정의 시 괄호 안에 나열된 변수이고, 인자는 함수가 호출될 때 함수로 전달되는 값입니다.

기본적으로 함수는 정확한 개수의 인자와 함께 호출되어야 합니다. 만약 함수가 2개의 인자를 기대한다면, 함수는 2개의 인자를 사용하여 호출해야 합니다:

```python
def my_function(name, surname):
    print(name + " " + surname)

my_function("Jon", "Snow")
```
결과:
```text
Jon Snow
```
그러나 호출 시 인자를 하나만 제공하면:
```python
my_function("Sam")
```
`TypeError`가 발생합니다:
```text
TypeError                                 Traceback (most recent call last)
<ipython-input-29-40eb74e4b26a> in <module>
----> 1 my_function('Jon')

TypeError: my_function() missing 1 required positional argument: 'surname'
```
더 구조적이고 자세한 정보를 원한다면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/7248?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
코드 편집기에서 전달된 매개변수의 제곱을 출력하는 함수를 정의하세요.

<div class='hint'>함수 정의 시 괄호 안에 <code>x</code> 매개변수를 추가하세요.</div>