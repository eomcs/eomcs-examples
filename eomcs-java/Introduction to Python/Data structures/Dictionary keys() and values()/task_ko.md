## 딕셔너리 keys() 및 values()

딕셔너리에는 `keys()` , `values()`, `items()`과 같은 유용한 메서드들이 많이 있습니다.  
`keys()` 메서드는 딕셔너리의 키를 삽입된 순서대로 표시하는 뷰 객체를 반환합니다.  
`values()`는 딕셔너리의 값을 새로운 뷰로 반환합니다. `items()` 메서드가 호출되면,  
`(key, value)` 쌍의 튜플 형태로 리스트에 딕셔너리의 항목을 새로운 뷰로 반환합니다.  

`dict.keys()`, `dict.values()` 및 `dict.items()`가 반환하는 객체는  
딕셔너리의 항목에 대한 동적 뷰를 제공합니다. 이는  
딕셔너리가 변경되었을 때, 뷰가 해당 변경 사항을 반영한다는 것을 의미합니다.

`dict_name` 뒤에 점(`.`)을 붙이고 &shortcut:CodeCompletion;을 사용하여  
나머지도 탐색할 수 있습니다.

딕셔너리가 지원하는 연산에 대해 더 알아보려면 <a href="https://docs.python.org/3/library/stdtypes.html#typesmapping">여기</a>를 참고하세요.

더 구조적이고 자세한 정보를 원하면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/11096?utm_source=jba&utm_medium=jba_courses_links)를 참고하세요.

### 작업
`phone_book`의 모든 값을 출력하세요.  

<div class='hint'><code>values()</code> 메서드를 사용하세요.</div>