## 리스트 소개

Python에는 데이터를 함께 묶는 데 사용되는 여러 복합 데이터 유형이 있습니다.  
그중 가장 다재다능한 것은 `리스트`로, 쉼표로 구분된 값(항목)들을 사각 괄호로 묶어 작성할 수 있습니다. 예: `lst = [item1, item2]`.  
리스트에는 서로 다른 유형의 항목이 포함될 수 있지만, 일반적으로 리스트의 모든 항목은 동일한 유형입니다. 문자열과 마찬가지로, 리스트는 인덱싱과 슬라이싱이 가능합니다([Lesson 3](course://Strings/String slicing) 참조).  
모든 슬라이스 연산은 요청된 요소를 포함하는 새로운 리스트를 반환합니다.

리스트는 다음과 같은 연결(concatenation) 연산도 지원합니다:

```python
squares = [1, 4, 9, 16, 25]
squares + [36, 49, 64, 81, 100]
[1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
```

리스트에 대해 더 자세히 알아보려면 <a href="https://docs.python.org/3.9/tutorial/introduction.html#lists">이 페이지</a>를 참조할 수 있습니다.

더 구조적이고 세부적인 정보를 얻으려면 [이 Hyperskill 지식 기반 페이지](https://hyperskill.org/learn/step/5979?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
리스트 슬라이싱을 사용하여 `[4, 9, 16]`을 출력하세요.

<div class='hint'>리스트 슬라이싱 구문은 문자열에 사용하는 방식과 동일하게 생겼습니다: <code>lst[index1:index2]</code>.  
<code>index2</code>에 해당하는 요소는 포함되지 않는다는 것을 잊지 마세요!</div>