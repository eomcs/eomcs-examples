## 키워드 인자

함수를 호출할 때 `kwarg=value` 형태의 키워드 인자를 사용할 수 있다는 것을 이미 암시했습니다. 예를 들어, 우리가 당신을 위해 정의한 함수 `cat()`은 하나의 필수 인자(`food`)와 세 개의 선택적 인자(`state`, `action`, `breed`)를 받습니다.  
다음 방법들로 호출할 수 있습니다 (직접 시도해 보세요):

```python
cat('chicken')                     # 1 위치 인자
cat(food='chicken')                # 1 키워드 인자
cat(food='fish', action='bite')    # 2 키워드 인자
cat(action='bite', food='fish')    # 2 키워드 인자
cat('beef', 'happy', 'hiss')       # 3 위치 인자
cat('a hug', state='purrring')     # 1 위치, 1 키워드 인자
```
함수 호출에서, 키워드 인자는 반드시 위치 인자 뒤에 와야 합니다. 전달된 모든 키워드 인자는 함수가 받아들이는 인자 중 하나와 일치해야 합니다 (예: `book`은 `cat` 함수에 유효한 인자가 아님), 그리고 그 순서는 중요하지 않습니다. 여기에는 선택적이지 않은 인자도 포함됩니다 (예: `cat(food='fish')`도 유효합니다). 어떤 인자도 한 번 이상 값을 받을 수 없습니다.  
다음의 호출들은 모두 유효하지 않습니다:

```python
cat()                              # 필수 인자가 누락됨
cat(food='fish', 'dead')           # 키워드 인자 뒤에 위치 인자가 나옴
cat('veggies', food='nothing')     # 동일한 인자에 대한 값 중복
cat(actor='Johnny Depp')           # 알 수 없는 키워드 인자
```

### 작업
에디터에서 함수를 적절한 인자와 함께 호출하여 다음을 출력하도록 완성하세요:
```text
-- This cat wouldn't growl if you gave it soup
-- Lovely fur, the Sphinx
-- It's still hungry!
```

<div class="hint">키워드 인자를 사용할 때는 <code>state='asleep'</code>와 같은 문법을 사용하세요.</div>
<div class="hint">필수 인자인 <code>food</code>는 첫 번째 위치에 있어야 하지만, 키워드 인자로 전달하는 경우는 예외입니다.</div>