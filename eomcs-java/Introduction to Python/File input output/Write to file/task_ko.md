## 파일에 쓰기

이미 언급했듯이, `open()` 함수의 두 번째 인자로 `'w'`를 사용하면 파일은 쓰기 전용으로 열립니다. 새로운 빈 파일이 생성됩니다. 동일한 이름의 파일이 이미 존재하는 경우, 해당 파일은 삭제됩니다. 기존 파일에 내용을 추가하고 싶다면 `'a'` (append, 추가) 모디파이어를 사용해야 합니다.  

파일 객체의 또 다른 메소드인 `f.write(string)`은 <i>문자열</i>의 내용을 파일에 기록하며, 기록된 문자 수를 반환합니다.

```python
f.write('This is a test\n')
```
```text
15
```
텍스트 모드에서 다른 유형의 객체는 먼저 문자열로 변환해야 합니다:
```python
value = ('the answer', 42)
s = str(value)  # 튜플을 문자열로 변환
f.write(s)
```
```python
18
```
지정된 텍스트가 파일 어디에 삽입될지는 파일 모드 (`'a'` vs `'w'`)에 따라 다릅니다.

`'a'`: 텍스트가 파일 끝에 삽입됩니다.

`'w'`: 파일이 비워지고 텍스트가 파일 시작 부분에 삽입됩니다.

라인 브레이크(줄 바꿈)와 같은 기호를 문자열에 포함하려면 (이를 통해 새 줄에서 시작하게 만듭니다), 이를 `+`를 사용하여 추가하세요:
```python
f.write('\n' + 'string,' + ' ' + 'another string')
```
이 코드는 새 줄을 추가한 뒤 `'string, another string'`을 기록합니다.

더 구조적이고 자세한 정보를 원한다면 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/8334?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수 있습니다.

### 작업
코드 편집기에서 `output.txt` 파일 끝에 `zoo` 리스트의 모든 요소를 `' and '`로 구분하여 **한 줄 추가** 하세요.  
리스트 요소를 필요한 문자열로 결합하기 위해 <code>' and '.join(lst)</code> 구문을 사용하세요. 그 후, `number`를 같은 출력 파일에 또 다른 줄로 추가하세요.

<div class='hint'><code>'a'</code> 모디파이어를 사용하여 파일 끝에 줄을 추가하세요.</div>
<div class='hint'><code>write()</code> 메소드를 사용하세요.</div>
<div class='hint'><code>number</code>를 쓰기 전에 문자열로 변환하세요.</div>
<div class="hint">각 문자열의 시작에 <code>\n</code>을 추가하여 새 줄에 기록되도록 하세요.</div>