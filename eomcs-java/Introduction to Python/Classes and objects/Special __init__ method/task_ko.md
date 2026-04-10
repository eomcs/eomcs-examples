## 특수 &#95;&#95;init&#95;&#95; 메서드

클래스 객체를 "호출"하여 인스턴스를 생성하는 작업은 비어 있는 객체를 만듭니다. 하지만 특정 초기 상태로 사용자 정의된 객체를 생성하는 것이 유용합니다. 따라서 클래스는 `__init__()`라는 특수 메서드를 정의할 수 있습니다. 예를 들어:

```python
class MyClass:
    def __init__(self):
        self.data = []
```
`__init__`은 파이썬의 예약 메서드 중 하나입니다. 정의되어 있다면, 클래스 인스턴스가 생성될 때 `__init__()` 메서드는 자동으로 호출되며, 객체와 객체의 속성을 초기화합니다. 해당 메서드는 항상 최소한 하나의 인수인 `self`를 받아야 합니다. 따라서 이 예제에서는 새로 초기화된 인스턴스를 다음과 같이 얻을 수 있습니다:

```python
x = MyClass()
```
`__init__()` 메서드는 더 큰 유연성을 위해 인수를 받을 수 있습니다. 이 경우, 클래스 생성 연산자에 전달되는 인수는 `__init__()`로 전달됩니다. 예를 들어:
```python
class Complex:
    def __init__(self, real_part, imag_part):
        self.r = real_part
        self.i = imag_part
        self.num = complex(self.r, self.i)

x = Complex(3.0, -4.5)  # 복소수 생성
x.num
```
```text
(3-4.5j)
```

더 구성적이고 자세한 정보를 원하신다면, [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/6669#def-__init?utm_source=jba&utm_medium=jba_courses_links)를 참고하세요.

### 과제
코드 편집기에서 `Car` 클래스의 `__init__()` 메서드에 매개변수를 추가하여 지정된 색상과 브랜드로 객체를 생성할 수 있도록 하세요.

<div class='hint'>세 개의 매개변수 &mdash; <code>self</code>, <code>color</code>, <code>brand</code>를 추가하세요.</div>