## 파일 읽기

파일의 내용을 읽으려면 `f.read(size)`를 호출할 수 있습니다. 이 메서드는 일정량의 데이터를 읽어 문자열로 반환합니다. `size`를 생략하거나 음수로 지정하면 파일의 전체 내용을 읽어 반환합니다.

```python
with open('somefile.txt') as f:
    print(f.read())
```
```text
여기에 파일에 있는 모든 내용이 있습니다.\n
```
<i>**참고**: 파일 크기가 컴퓨터의 메모리보다 두 배 클 경우 문제가 발생할 수 있습니다.</i>

`f.readline()`은 파일에서 한 줄을 읽습니다. 문자열 끝에 줄바꿈 문자(`\n`)가 남아 있으며, 파일의 마지막 줄이 줄바꿈으로 끝나지 않는 경우에만 생략됩니다. `f.readline()`이 빈 문자열을 반환하면 파일 끝에 도달한 것입니다. 빈 줄은 `\n`으로 표현되며 이는 단일 줄바꿈만 포함하는 문자열입니다.

```python
f.readline()
```
```text
'이것은 파일의 첫 번째 줄입니다.\n'
```
```python
f.readline()
```
```text
'파일의 두 번째 줄\n'
```
```python
f.readline()
```
```text
''
```

파일에서 줄을 읽으려면 파일 객체를 반복(iterate)하면 됩니다. 이는 메모리 효율적이고 빠르며, 코드가 간단해집니다:
```python
for line in f:
    print(line)
```
```text
이것은 파일의 첫 번째 줄입니다.
파일의 두 번째 줄
```

파일의 모든 줄을 리스트로 읽고 싶다면 `list(f)` 또는 `f.readlines()`를 사용할 수도 있습니다.

더 자세한 내용은 Python Tutorial의 [파일 객체 메서드](https://docs.python.org/3/tutorial/inputoutput.html#methods-of-file-objects) 섹션을 참조하세요.

더 구조적이고 자세한 정보를 원하면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/8139?utm_source=jba&utm_medium=jba_courses_links)를 참고할 수도 있습니다.

### 과제
"input.txt" 파일의 내용을 각 줄을 반복(iterate)하며 출력한 다음, "input1.txt"의 첫 번째 줄만 출력하세요.

<div class="hint">설명에서 제시된 예제처럼 파일 객체를 반복하세요.</div>
<div class='hint'><code>print</code> 함수를 사용하세요.</div>
<div class='hint'><code>readline()</code> 메서드를 사용해 한 줄만 출력하세요.</div>