# Chapter 1. Introduction (소개)

> "Programming TypeScript" by Boris Cherny (O'Reilly)

---

## 1. 왜 TypeScript인가?

TypeScript는 다음 세대의 웹 앱, 모바일 앱, NodeJS 프로젝트, IoT 디바이스를 위한 언어다. TypeScript가 제공하는 핵심 가치는 다음과 같다:

- **안전성**: 일반적인 실수를 사전에 감지
- **문서화**: 코드 자체가 개발자에게 문서가 됨
- **리팩터링 용이성**: 타입 정보를 기반으로 안전한 코드 변경 가능
- **불필요한 단위 테스트 감소**: 타입 시스템이 일부 테스트를 대체

---

## 2. 타입 안전성 (Type Safety)

> **TYPE SAFETY**: 타입을 사용하여 프로그램이 잘못된 일을 하지 못하도록 방지하는 것.

**잘못된 코드의 예시:**

- 숫자와 배열을 곱하는 행위
- 객체 목록이 필요한 함수에 문자열 목록을 전달
- 존재하지 않는 메서드 호출
- 이동된 모듈 임포트

---

## 3. JavaScript의 문제점

JavaScript는 잘못된 코드도 예외를 던지지 않고 "최선을 다해" 실행한다:

```javascript
3 + []          // "3" (문자열로 평가)

let obj = {}
obj.foo         // undefined (예외 없음)

function a(b) {
  return b / 2
}
a("z")          // NaN (예외 없음)
```

이 "관대함"이 오히려 문제다. 버그를 **코드를 작성하는 시점**이 아니라 **실제 실행 시점**에야 발견하게 된다. 최악의 경우 사용자가 먼저 버그를 발견하게 된다.

---

## 4. TypeScript의 해결책

TypeScript는 **에디터에서 코드를 입력하는 순간** 오류를 알려준다:

```typescript
3 + []
// Error TS2365: Operator '+' cannot be applied to types '3' and 'never[]'.

let obj = {}
obj.foo
// Error TS2339: Property 'foo' does not exist on type '{}'.

function a(b: number) {
  return b / 2
}
a("z")
// Error TS2345: Argument of type '"z"' is not assignable to parameter of type 'number'.
```

---

## 5. TypeScript가 바꾸는 코딩 방식

타입 시스템을 활용하면 개발 습관 자체가 달라진다:

- **값(value) 레벨이 아닌 타입(type) 레벨에서 먼저 프로그램을 설계**하게 된다.
- 엣지 케이스를 나중이 아닌 **설계 단계에서 미리 고민**하게 된다.
- 결과적으로 더 **단순하고, 빠르고, 이해하기 쉬운** 코드를 작성하게 된다.

**“엣지 케이스”** 란 보통 정상 흐름 밖의 애매한 경우들이다.
- 값이 없을 때
- null 또는 undefined일 때
- 배열이 비어 있을 때
- 실패할 수 있는 요청일 때
- 권한이 없는 사용자일 때
- 특정 상태에서는 호출되면 안 되는 함수일 때
---

## 핵심 요약

| 구분 | JavaScript | TypeScript |
|------|-----------|------------|
| 오류 감지 시점 | 런타임 (실행 시) | 컴파일 타임 (작성 시) |
| 잘못된 연산 | 조용히 처리 (`NaN`, `undefined` 등) | 즉시 오류 메시지 |
| 타입 명시 | 없음 | 명시적 타입 어노테이션 |
| 안전성 | 낮음 | 높음 |
