## 패키지

패키지는 “도트 모듈 이름”을 사용하여 Python의 모듈 [네임스페이스](https://docs.python.org/3/tutorial/classes.html#python-scopes-and-namespaces)를 구조화하는 방법입니다. 예를 들어, 모듈 이름 `A.B`는 패키지 `A` 안에 있는 하위 모듈 `B`를 지정합니다. 모듈을 사용하여 다른 모듈의 작성자가 서로의 전역 변수 이름을 걱정하지 않아도 되는 것처럼, 도트 모듈 이름을 사용하면 [NumPy](https://numpy.org/)나 [Pillow](https://pypi.org/project/Pillow/)와 같은 다중 모듈 패키지 작성자가 서로의 모듈 이름을 걱정하지 않아도 됩니다. 

<div class="hint"><code>__init__.py</code> 파일은 Python이 해당 파일을 포함하는 디렉터리를 패키지로 처리하도록 요구합니다. 이는 <code>string</code>과 같은 일반적인 이름을 가진 디렉터리가 나중에 모듈 검색 경로에서 발생하는 유효한 모듈을 의도치 않게 숨기는 것을 방지합니다. 가장 간단한 경우, <code>__init__.py</code>는 빈 파일일 수 있습니다.</div>

`functions` 및 `classes` 패키지를 확인해보세요. 패키지 사용자는 패키지의 개별 모듈을 예를 들어 다음과 같이 가져올 수 있습니다:

```python
import functions.greeting.hello
```

이렇게 하면 `functions.greeting.hello` 하위 모듈이 로드됩니다. 이 경우 전체 이름으로 참조해야 합니다:

```python
functions.greeting.hello.hello('Susan')
```
하위 모듈을 가져오는 또 다른 방법은 다음과 같습니다:

```python
from functions.greeting import hello
```

이 방법도 `hello` 하위 모듈을 로드하며, 해당 모듈을 패키지 접두사 없이 사용할 수 있도록 만듭니다. 예를 들어, 다음과 같이 사용할 수 있습니다:

```python
hello.hello('Susan')
```

패키지에 대해 더 자세히 알고 싶다면, Python 문서의 <a href="https://docs.python.org/3/tutorial/modules.html#packages">이 페이지</a>를 읽어보세요.

더 구조화되고 상세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6384?utm_source=jba&utm_medium=jba_courses_links)에서 확인할 수도 있습니다.

### 작업
`classes` 및 `functions` 디렉터리와 해당 하위 디렉터리의 파일 구조를 살펴보세요.

코드 편집기에서 `official` 모듈을 올바르게 가져와 마지막 `print` 문이 작동하도록 하세요.

두 번째 `print` 문에서는 함수 호출을 추가하여, `'Alex'`에게 작별 인사를 출력하도록 하세요.

<div class="hint"><code>package.subpackage.module</code>과 같은 구문을 사용하여 모듈에 접근하세요.</div>
<div class="hint"><code>import module as something</code>과 같은 구문을 사용하세요.</div>
<div class="hint">임포트를 확인하세요: 두 번째 작업에 적합한 함수가 있을 수 있습니다.
주의하세요: 해당 모듈은 이미 특정 이름으로 가져와져 있습니다.</div>