## 변수 타입

Python 프로그램의 모든 데이터는 객체나 객체 간의 관계로 표현됩니다. 모든 객체는 정체성(identity), 타입(type), 값(value)을 가지고 있습니다. 객체의 정체성은 한 번 생성되면 변경되지 않으며, 메모리에서 객체의 주소로 생각할 수 있습니다.

객체의 `type`은 객체가 지원하는 연산(예: "길이가 있는가?")을 결정하며, 해당 타입의 객체가 가질 수 있는 가능한 값을 정의합니다. `type()` 함수는 객체의 타입을 반환합니다(타입 자체도 객체입니다). 객체의 정체성과 마찬가지로, 객체의 타입 역시 변경할 수 없습니다.

일부 객체의 값은 변경될 수 있습니다. 값이 변경 가능한 객체를 <i>mutable(변경 가능)</i>하다고 하고, 생성된 이후 값이 변경할 수 없는 객체는 <i>immutable(변경 불가능)</i>하다고 합니다.

Python에서 숫자에는 두 가지 주요 타입이 있습니다: 정수(integer)와 부동소수(float). 이 둘의 가장 큰 차이점은 `float`가 소수점을 포함한 숫자이고, `int`는 소수점이 없는 숫자라는 점입니다.  

이 주제에 대한 더 많은 정보는 Python 문서의 "<a href="https://docs.python.org/3/reference/datamodel.html#objects-values-and-types">객체, 값, 그리고 타입</a>" 및 "<a href="https://docs.python.org/3/reference/datamodel.html#the-standard-type-hierarchy">표준 타입 계층 구조</a>" 섹션을 참고하세요.

더 체계적이고 자세한 정보를 원한다면, [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/5852?utm_source=jba&utm_medium=jba_courses_links)도 참조할 수 있습니다.

### 과제
변수 `float_number`의 타입을 출력하세요.

<div class="hint">
2번 줄에서 <code>number</code>의 타입을 어떻게 판별했는지 확인하고, 동일한 방법으로 <code>float_number</code>의 타입을 판별하세요.</div>