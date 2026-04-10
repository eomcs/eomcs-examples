## 중첩 리스트 컴프리헨션

중첩 리스트 컴프리헨션은 다른 리스트 컴프리헨션 내부에 중첩된 리스트 컴프리헨션입니다. 이는 [중첩 루프](course://Loops/Nested for Loop)와 매우 유사합니다. 다음은 중첩 루프를 사용하여 [중첩 리스트](course://Data structures/Nested Lists)를 생성하는 프로그램입니다:

```python
matrix = []

for i in range(3):

    # 리스트 안에 빈 하위 리스트를 추가합니다.
    matrix.append([])

    for j in range(0, 10, 2):
        matrix[i].append(j)

print(matrix)
```
출력:
```text
[[0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8]]
```

같은 작업을 중첩 리스트 컴프리헨션을 사용하여 한 줄로 해결할 수 있습니다:

```python
matrix = [[j for j in range(0, 10, 2)] for i in range(3)]
print(matrix)
```
출력:
```text
[[0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8]]
```

더 구조화된 상세 정보를 원하면 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6938#nested-list-comprehension?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제

$10×10$ `matrix`를 생성하세요. 각 행(하위 리스트)은 **문자** 0–9를 `string`에서 가져오도록 합니다. 리스트 컴프리헨션을 사용하여 한 줄로 코드를 작성해 보세요. 

<div class="hint">

과제 설명의 예제를 참조하세요. `range` 대신 `string`을 iterable로 사용하면 됩니다. 

</div>