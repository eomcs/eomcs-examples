## 문자 이스케이프

백슬래시(backslash)는 작은따옴표나 큰따옴표 같은 특수 기호를 이스케이프하는 데 사용됩니다.  
예를 들어, `"It\'s me"` 또는 `"She said \"Hello\""`와 같이 사용할 수 있습니다.  
문자열의 일부로 <code>\\</code> 문자를 입력하려면 역시 이를 이스케이프해야 합니다.  
예를 들어, 아래는 단일 백슬래시를 출력하는 방법입니다:

```python
print('\\')
```

특수 기호 `'\n'`은 문자열에서 줄바꿈을 추가하는 데 사용되고, `'\t'`는 탭 간격을 의미합니다.

따옴표는 특별한 의미를 가지므로, 백슬래시로 이스케이프할 수 있습니다.  
문자열 안에 따옴표를 출력해야 한다면 다른 종류의 따옴표를 사용하는 것이 좋습니다:  
큰따옴표로 묶인 문자열 안에는 작은따옴표를 이스케이프 없이 사용할 수 있고, 그 반대도 적용됩니다.  
참고로 좋아하는 따옴표 유형을 선택하여 일관되게 사용하는 것이 좋습니다.

이스케이프에 대해 더 알고 싶다면,  
<a href="https://docs.python.org/3/reference/lexical_analysis.html#string-and-bytes-literals">Python 문서의 이 섹션</a>을 참조하세요.

보다 구조적이고 상세한 정보를 원한다면,  
[이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/7130?utm_source=jba&utm_medium=jba_courses_links)를 확인할 수도 있습니다.

### 과제
다음 텍스트를 하나의 문자열로 출력하세요:  
```text
The name of this ice cream is "Sweet'n'Tasty"  
```

<div class='hint'>이스케이프 문자를 사용하여 따옴표를 처리하세요.</div>