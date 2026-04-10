## 변수 접근

객체 내부의 변수를 접근하려면 속성 참조(attribute references)를 사용할 수 있습니다.  
속성 참조는 Python에서 모든 속성 참조에 대해 표준 문법인 `obj.name`을 사용합니다.  
유효한 속성 이름은 클래스 객체가 생성될 때 클래스 네임스페이스에 있던 모든 이름입니다.  
따라서, 클래스 정의가 다음과 같다면:

```python
class MyClass:
    year = 2021

    def say_hello(self):
        return 'hello world'
```
`MyClass.year`와 `MyClass.say_hello`는 각각 정수와 함수 객체를 반환하는 유효한 속성 참조가 됩니다.  
클래스 속성은 할당을 통해 값을 변경할 수 있으므로, 할당을 통해 `MyClass.year` 값을 변경할 수 있습니다.

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/6661#class-attribute?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
예제를 확인하고 `my_object`의 `variable1` 값을 출력하세요.  
객체 `my_object`의 `foo` 메서드를 호출하고 결과를 출력하세요.

<div class='hint'><code>object.name</code> 문법을 사용하여 <code>variable1</code>에 접근하세요.</div>