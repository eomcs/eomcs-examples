## 모듈 가져오기

프로그램이 길어질수록 유지보수를 쉽게 하기 위해 여러 파일로 나누고 싶을 수 있습니다. 또한, 이미 작성한 편리한 함수를 여러 프로그램에서 정의를 복사하지 않고도 사용하고 싶을 수 있습니다.

Python의 모듈은 Python 정의와 문장을 포함하는 `.py` 확장자를 가진 Python 파일입니다.  
모듈은 `import` 키워드와 파일 이름에서 `.py` 확장자를 제외한 이름을 사용하여 다른 모듈에서 가져올 수 있습니다.  

예를 들어, 여러 함수(`func1`, `func2` 등)가 포함된 `my_funcs.py`라는 스크립트를 작성했다고 가정해봅시다.  
이제 같은 디렉토리에 있는 다른 스크립트에서 이를 사용하고 싶다면, `import my_funcs`를 사용할 수 있습니다.  
그러나 이는 `my_funcs`에 정의된 함수의 이름을 직접 가져오는 것이 아니라, 모듈 이름을 사용하여 함수에 접근할 수 있도록 합니다.  
예를 들어:
```python
my_funcs.func1()
```

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6019#module-loading?utm_source=jba&utm_medium=jba_courses_links)를 참고할 수 있습니다.

### 과제
코드 편집기에서 이미 `my_funcs` 모듈이 가져와져 있습니다.  
이 모듈의 함수 `hello_world`를 `"John"`이라는 인수를 전달하여 호출하세요.

<div class='hint'>다음과 같은 구문을 사용하여 모듈에서 함수를 호출하세요: <code>module.function()</code>.</div>
<div class="hint">함수에 인수를 제공하는 것을 잊지 마세요.</div>