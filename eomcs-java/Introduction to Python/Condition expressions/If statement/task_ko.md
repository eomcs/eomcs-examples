## if문

Python의 복합 문장은 다른 문장(또는 문장 그룹)을 포함하며, 어떤 방식으로든 그 문장들의 실행에 영향을 미칩니다.

아마 가장 널리 알려진 문장 유형은 `if` 문일 것입니다. `if` 키워드는 조건문을 형성하는 데 사용되며, 표현식이 `True`인지 확인한 후 특정 코드를 실행합니다. Python은 들여쓰기를 사용하여 코드 블록을 정의합니다:

```python
if value > 1000: 
    print("It's a large number!")  # 들여쓰기된 블록
    a += 1                         # 들여쓰기된 블록
    
print("Outside the block!")        
```

코드 블록은 들여쓰기로 시작하고, 들여쓰기가 없는 첫 번째 줄에서 끝납니다. 들여쓰기의 양은 블록 전체에서 일관되어야 합니다. 일반적으로 들여쓰기에는 네 개의 공백 또는 하나의 탭이 사용됩니다. 잘못된 들여쓰기는 `IndentationError`를 초래할 수 있습니다.

단일 문장만 실행해야 하는 경우, `if` 문과 같은 줄에 작성할 수 있습니다.

```python
if a > b: print("a is greater than b")
```

더 구조적이고 자세한 정보는 [Hyperskill 지식 페이지](https://hyperskill.org/learn/step/5953?utm_source=jba&utm_medium=jba_courses_links)를 참조할 수 있습니다.

### 작업
만약 `tasks` 리스트가 비어 있지 않다면 `"Not an empty list!"`를 출력하세요.  
리스트를 비운 후, 다시 확인(다른 조건이 필요할 수 있습니다!)하여 비어있다면 `'Now empty!'`를 출력하세요.

<div class='hint'><code>len()</code> 함수를 사용하여 <code>tasks</code>가 비어 있는지 확인하세요.</div>