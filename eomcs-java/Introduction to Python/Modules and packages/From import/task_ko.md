## from import

`import` 문은 특정 모듈에서 이름을 직접 가져오는 형태로 사용할 수 있습니다. 이렇게 하면 `module_name` 접두사 없이 가져온 이름을 사용할 수 있습니다. 예를 들어:

```python
from calculator import Add

calc = Add()    # 접두사 `calculator` 없이 이름 `Add`를 직접 사용
```

이 방식은 가져온 이름의 모듈을 로컬 심볼 테이블에 정의하지 않습니다 (따라서 위 예제에서는 `calculator`가 정의되지 않습니다).

모듈이 정의한 모든 이름을 가져오는 옵션도 있습니다:
```python
from calculator import *
calc = Multiply()
```
이 방식은 밑줄 `_`로 시작하는 이름을 제외한 모든 이름을 가져옵니다. 그러나 대부분의 경우 Python 프로그래머는 이 방식을 사용하지 않습니다. 이유는 모듈에서 가져온 알 수 없는 이름 집합이 해석기에 도입되어 이미 정의한 일부 이름을 숨길 가능성이 있기 때문입니다.

모듈 이름 뒤에 `as`를 사용하면, `as` 뒤에 따라오는 이름이 해당 모듈에 직접 바인딩됩니다:

```python
import my_module as mm
mm.hello_world()
```
이것은 `import my_module` 구문과 본질적으로 동일하며, 차이점은 `mm`라는 이름으로 사용할 수 있다는 점입니다. 유사한 결과를 가져오는 방식으로 `from`을 사용할 때도 활용할 수 있습니다:

```python
from calculator import Subtract as Minus
```

더 구조적이고 자세한 내용은 [이 Hyperskill 지식 페이지](https://hyperskill.org/learn/step/6019#module-loading?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### Task
`calculator`에서 `Calculator` 클래스를 가져와 이 클래스의 인스턴스를 생성하세요. 이 경우 올바르게 접근하는 방법을 기억하세요.

<div class="hint">참고: <code>Calculator</code> 클래스는 직접 가져왔으므로 접두사 없이 호출해야 합니다.</div>