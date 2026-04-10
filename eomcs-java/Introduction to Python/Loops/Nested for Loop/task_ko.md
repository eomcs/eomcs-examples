## 중첩 반복문

중첩 반복문은 한 반복문 안에 또 다른 반복문이 있는 형태입니다.  
외부 반복문의 한 번의 반복마다 내부 반복문이 실행됩니다.

```python
adjectives = ["black", "stylish", "expensive"]
clothes = ["jacket", "shirt", "boots"]

for x in adjectives:
  for y in clothes:
    print(x, y)
```
출력:
```text
black jacket
black shirt
black boots
stylish jacket
stylish shirt
stylish boots
expensive jacket
expensive shirt
expensive boots
```
<details>

어떤 유형의 반복문도 다른 반복문 안에 중첩될 수 있다는 점에 유의하세요.  
예를 들어, [`while` 반복문](course://Loops/While loop) (자세한 내용은 이후 참조)을  
`for` 반복문 안에 중첩하거나 그 반대로도 가능합니다.
</details>

더 체계적이고 자세한 정보는 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/6065#nested-loop?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
3x3 크기의 틱택토 보드가 주어졌을 때, 모든 위치를 출력하는 과제를 수행하세요.  
각 변의 좌표는 리스트 `coordinates`에 저장되어 있습니다. 출력은 다음과 같아야 합니다:
```text
1 x 1
1 x 2
1 x 3
2 x 1
...
```
그리고 이와 같이 모든 좌표의 조합을 출력합니다.

<div class="hint">

중첩된 `for` 반복문에서 동일한 리스트를 다른 변수 이름  
(`coordinate2`)을 사용하여 다시 반복(iterate)하세요.
</div>