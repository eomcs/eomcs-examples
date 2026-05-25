# Chapter 5. Formatting (코드 형식)

이 장은 코드 형식을 맞추는 이유와 방법에 대해 설명한다.

## 형식을 맞추는 목적

> **코드의 형식(formatting)은 단순한 스타일 문제가 아니라, “의도를 전달하기 위한 도구”다**

- 코드는 컴파일러가 아니라 **사람이 읽는다**
- 형식은 코드의 의미를 전달하는 중요한 수단이다
- **형식 = 커뮤니케이션**

> **형식은 나중에 정리할 게 아니라, "처음부터 중요하다"**

- 코드의 첫 인상이 이해도를 결정한다
- 협업에서 읽기 속도에 직접 영향
- 유지보수 비용에 큰 차이 발생

> **형식은 “시간이 지나도 남는다”**

- 코드는 계속 바뀌지만, 형식은 그 코드의 이해 방식을 결정한다
- 잘 정리된 코드는 이후 수정되어도 구조는 유지됨
- 나쁜 형식의 코드는 수정될수록 더 읽기 어려워짐

> **형식은 "의도를 표현한다"**

- 형식 + 함수 이름 = 설계 표현

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 코드  | 좋은 코드 |
| ---- | ------ | ----- |
| 가독성  | 낮음     | 높음    |
| 구조   | 보이지 않음 | 명확    |
| 유지보수 | 어려움    | 쉬움    |
| 의도   | 숨겨짐    | 드러남   |


## 예제

- [적절한 행 길이를 유지하라 (Vertical Formatting)](exam01/README.md)
    - 신문 기사처럼 작성하라 (The Newspaper Metaphor)
    - 개념은 빈 행으로 분리하라 (Vertical Openness Between Concepts)
    - 세로 밀집도 (Vertical Density)
    - 수직 거리 (Vertical Distance)
    - 세로 순서 (Vertical Ordering)
- [가로 형식 맞추기 (Horizontal Formatting)](exam02/README.md)
    - 가로 공백과 밀집도 (Horizontal Openness and Density)
    - 가로 정렬 (Horizontal Alignment)
    - 들여쓰기 (Indentation)
    - 가짜 범위 (Dummy Scopes)
- [팀 규칙 (Team Rules)](exam03/README.md)