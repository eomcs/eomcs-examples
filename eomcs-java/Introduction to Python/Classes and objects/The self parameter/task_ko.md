## self 매개변수

이제 이전 작업에서 본 `self` 매개변수에 대해 설명할 시간입니다.  
클래스 메서드에 전달되는 첫 번째 인수는 `self`입니다. 이것은 단지 관례일 뿐이며,  
`self`라는 이름은 Python에서 특별한 의미를 가지지 않습니다. 그러나 이 관례를 따르는 것이 권장됩니다.  
그렇지 않으면 다른 Python 프로그래머가 코드를 읽기 어려울 수 있습니다.

Python은 `self` 매개변수를 사용하여 생성되거나 수정되는 객체를 참조합니다.  
메서드는 `self` 인수의 메서드 속성을 사용하여 다른 메서드를 호출할 수 있습니다:

```python
class Bag:
    def __init__(self):
        self.data = []

    def add(self, x):
        self.data.append(x)

    def addtwice(self, x):
        self.add(x)  # 다른 메서드에서 메서드 `add`를 호출
        self.add(x)
```
  
더 체계적이고 자세한 정보를 원한다면 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6669#self?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 작업
코드 편집기에서 `Calculator` 클래스의 `add` 메서드를 구현하세요. 이 메서드는 
`current` 필드에 `amount`를 더해야 합니다. 또한 `get_current` 메서드를 완성하세요.  
코드가 어떻게 작동하는지 확인하기 위해 실행해보세요.

<div class='hint'><code>self.current</code> 변수에 <code>amount</code>를 더하세요.</div>
<div class="hint"><code>+=</code> 연산자를 사용하세요.</div>