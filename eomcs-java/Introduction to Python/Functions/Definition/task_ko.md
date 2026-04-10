## 정의

함수는 코드를 유용한 블록으로 나누고, 가독성을 높이며, 재사용할 수 있도록 해주는 편리한 방법입니다.  
`def` 키워드는 함수 정의를 나타냅니다.  
이 키워드는 함수 이름과 괄호로 묶인 **형식 매개변수** 목록(비어있을 수 있음)이 따라와야 합니다.  
함수의 본문을 구성하는 명령문은 다음 줄에서 시작하며 들여쓰기로 구분되어야 합니다.

<details>
형식 매개변수는 괄호로 묶여 있으며, 함수에 의해 정의된 변수로서 함수가 호출될 때 값을 받습니다.  
목록은 메서드에 필요한 모든 값의 변수 이름으로 구성됩니다.  
각 형식 매개변수는 쉼표로 구분됩니다.  
메서드가 입력 값을 받지 않을 경우, 메서드 이름 뒤에 비어 있는 괄호 세트를 가져야 합니다. 예: <code>addition()</code>.
</details>

함수는 호출되었을 때만 실행됩니다. 함수를 호출하려면 함수 이름 뒤에 괄호를 사용하세요:

```python
def my_function():  # 함수 정의
  print("Hello from a function")

my_function()  # 함수 호출
```

함수 정의에 대한 자세한 내용은 Python 문서의 <a href="https://docs.python.org/3/tutorial/controlflow.html#defining-functions">이 섹션</a>에서 읽어보세요.

보다 구조적이고 자세한 정보를 원한다면, [Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/5900?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
 - 반복문 안에서 함수 `my_function`을 호출하여 함수가 5번 호출되도록 하세요.
 - 파일의 중복된 `print` 문을 대체할 수 있는 함수를 정의하세요.

<div class='hint'><code>()</code>를 사용하여 <code>my_function</code> 함수를 호출하세요.</div>
<div class='hint'><code>def</code> 키워드를 사용하여 <code>fun</code> 함수를 정의하세요.</div>