## 기본 매개변수

함수를 정의할 때 가변적인 수의 인자를 사용할 수도 있습니다. 이에는 세 가지 형태가 있으며, 이를 조합하여 사용할 수 있습니다. 가장 유용한 형태는 하나 이상의 인자에 기본값을 지정하는 것입니다. 이렇게 하면 정의된 인자보다 적은 수의 인자로도 함수를 호출할 수 있습니다. 예를 들어, 코드 에디터에 있는 첫 번째 함수를 살펴보세요. 이 함수는 다음과 같은 방식으로 호출할 수 있습니다:

- 필수 인자 `a`만 제공하기: `multiply_by(3)`

- 선택적 인자 중 하나를 제공하기: `multiply_by(3, 47)` 또는 `multiply_by(3, c=47)`

- 모든 인자를 제공하기: `multiply_by(3, 47, 0)`

함수를 호출할 때 어떤 인자를 제공할지 지정할 수 있습니다. 세 번째 예에서 `c=47`을 사용한 방식과 같습니다. 이를 지정하지 않으면 인자의 값은 순서에 따라 할당됩니다.  
함수 호출 및 정의에서 `=` 기호 주변에 공백을 넣지 마세요.

<a href="https://docs.python.org/3/tutorial/controlflow.html#default-argument-values">Python 공식 문서</a>의 이 섹션을 읽어보며 이 주제를 더 탐구해 보세요.

좀 더 구조적이고 자세한 정보를 원한다면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/10295?utm_source=jba&utm_medium=jba_courses_links)도 참조해 보세요.

### 과제
`hello()` 함수에 매개변수를 추가하고 `name` 매개변수에 기본값을 설정하세요.  

<div class='hint'><code>name</code> 매개변수에 임의의 기본값을 지정하세요.</div>