## 내장 모듈

Python은 [표준 모듈 라이브러리](https://docs.python.org/3/library/)를 제공합니다.

일부 모듈은 인터프리터에 내장되어 있으며, 이러한 모듈은 언어의 핵심은 아니지만 효율성을 위해 또는 운영 체제의 기본 기능(예: 시스템 호출)에 대한 접근을 제공하기 위해 내장되어 있습니다.  
주목할 만한 모듈로는 `sys`가 있으며, 이는 모든 Python 인터프리터에 내장되어 있습니다. 만약 인터프리터가 대화형 모드에 있을 경우, `sys.ps1`과 `sys.ps2` 변수는 기본 프롬프트와 보조 프롬프트로 사용되는 문자열을 정의합니다:

```text
>>> import sys
>>> sys.ps1
'>>> '
>>> sys.ps2
'... '
```

`sys.path` 변수는 문자열의 리스트로, 모듈을 검색할 때 인터프리터가 참조하는 경로를 결정합니다: 작업의 코드를 실행했을 때 이 변수가 출력하는 내용을 확인해 보세요.

모듈의 메서드를 탐색하기 위해 점(.)을 입력한 후 &shortcut:CodeCompletion; 단축키를 사용할 수 있습니다. 표준 모듈에 대한 자세한 내용은 <a href="https://docs.python.org/3/tutorial/modules.html#standard-modules">여기</a>에서 읽을 수 있습니다.

더 체계적이고 상세한 정보는 [Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/6019#built-in-modules?utm_source=jba&utm_medium=jba_courses_links)에서도 참고할 수 있습니다.  

### 과제
내장 모듈 `datetime`을 가져와 현재 날짜와 시간을 출력하세요.  

<div class='hint'><code>datetime.datetime.today()</code> 함수를 사용하세요.</div>