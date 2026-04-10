## \_\_str__ vs \_\_repr__ 메서드

Python에서 `str()`과 `repr()` 메서드는 객체의 문자열 표현을 위해 사용되지만, 몇 가지 차이점이 있습니다.  
예를 들어:
```python
s = 'Hello World'
print (str(s))
print(repr(s))
```
```text
Hello World
'Hello World'
```
`repr()` 함수로 문자열을 출력하면 따옴표가 포함되어 출력되는 것을 볼 수 있습니다.  
`str()`은 사용자에게 출력값을 생성할 때 사용되며, `repr()`은 일반적으로 디버깅과 개발 과정에서 사용됩니다.  
`repr()`은 모호하지 않아야 하고, `str()`은 읽기 쉽게 만들어야 합니다.  

`__init__` 메서드처럼, `__repr__`과 `__str__` 메서드는 Python에서 예약어입니다.  
`print()` 문과 내장 함수 `str()`은 객체 클래스에 정의된 `__str__` 메서드를 사용해 문자열 표현을 표시하며,  
내장 함수 `repr()`은 객체 클래스에 정의된 `__repr__` 메서드를 사용합니다.  

따라서 우리가 직접 정의하는 클래스는 디버깅을 위해 자세한 정보가 필요하다면 `__repr__` 메서드가 있어야 합니다.  
그리고 사용자에게 제공할 문자열 표현이 유용하다고 생각되면 `__str__` 메서드를 만들어야 합니다.  
코드 편집기에서 클래스 `Complex`의 또 다른 구현을 참고하세요. 코드를 실행하여 두 `print` 문이 출력하는 내용을 확인하세요.  

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/7139#str__-vs-__repr?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 작업
클래스 `Cat`에 대해 `__str__`과 `__repr__` 메서드를 구현하세요.  
`__str__` 메서드는 다음과 같은 문자열을 반환해야 합니다:  
`"My siamese cat's name is Lucy"`  
`__repr__` 메서드는 다음과 같은 문자열을 반환해야 합니다:  
`"Cat, breed: siamese, name: Lucy"`  
[f-strings](course://Strings/F-strings)를 사용하세요.

<div class="hint"><code>self.attribute</code> 문법을 사용하는 것을 잊지 마세요.</div>
<div class="hint">작은따옴표를 출력하려면 문자 이스케이프를 사용하는 것을 잊지 마세요.</div>