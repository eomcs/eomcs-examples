# 의도를 분명히 밝혀라(Use Intention-Revealing Names)

> **이름만 보고도 코드의 의도를 이해할 수 있어야 한다**

- 변수, 함수, 클래스 이름은 "설명서" 역할을 해야 한다
- 주석 없이도 이해 가능한 코드가 목표

## ❌ 나쁜 예 (의도가 드러나지 않음)

```java
public List<int[]> getThem() {
    List<int[]> list1 = new ArrayList<int[]>();

    for (int[] x : theList) {
        if (x[0] == 4) {
            list1.add(x);
        }
    }
    return list1;
}
```

이 코드에서 우리는 다음을 전혀 알 수 없다:

- theList는 무엇인가?
- x는 무엇인가?
- x[0]은 무엇을 의미하는가?
- 숫자 4는 무엇인가?
- getThem()은 무엇을 반환하는가?

👉 즉, 모든 것을 추측해야 한다

## ✅ 개선된 예 (의도가 드러남)

```java
public List<int[]> getFlaggedCells() {
    List<int[]> flaggedCells = new ArrayList<>();

    for (int[] cell : gameBoard) {
        if (cell[STATUS_VALUE] == FLAGGED) {
            flaggedCells.add(cell);
        }
    }
    return flaggedCells;
}
```

개선 포인트:

1. 함수 이름 개선
    - getThem() → getFlaggedCells()
    - 👉 무엇을 반환하는지 명확
2. 변수 이름 개선
    - theList → gameBoard
    - list1 → flaggedCells
    - x → cell
    - 👉 데이터의 의미가 드러남    
3. 매직 넘버 제거
    - 4 → FLAGGED
    - x[0] → STATUS_VALUE
    - 👉 숫자의 의미를 명확히 표현

## ✅ 더 나은 개선 (객체 사용)

```java
public List<Cell> getFlaggedCells() {
    List<Cell> flaggedCells = new ArrayList<>();

    for (Cell cell : gameBoard) {
        if (cell.isFlagged()) {
            flaggedCells.add(cell);
        }
    }
    return flaggedCells;
}
```

추가 개선 포인트:

1. 배열 → 객체
    - int[] 대신 Cell 클래스 사용
2. 의미를 메서드로 표현
    - cell[STATUS_VALUE] == FLAGGED
    - → cell.isFlagged()
    - 👉 코드가 자연어처럼 읽힘

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드     | 좋은 코드              |
| ------ | --------- | ------------------ |
| 함수 이름  | getThem   | getFlaggedCells    |
| 변수 이름  | x, list1  | cell, flaggedCells |
| 데이터 구조 | int[]     | Cell 객체            |
| 조건 표현  | x[0] == 4 | cell.isFlagged()   |
| 이해 난이도 | 높음        | 매우 낮음              |

## 핵심 원칙

- 이름만 보고 역할을 알 수 있어야 한다
- 추측을 유도하지 말 것
- 매직 넘버를 제거할 것
- 의미를 코드로 표현할 것