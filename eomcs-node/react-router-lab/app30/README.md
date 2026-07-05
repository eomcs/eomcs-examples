# [데이터 모드] app30. 와일드카드 세그먼트

## Splats

"catchall" 또는 "star" 세그먼트라고도 부른다. 라우트 경로 패턴이 `/*` 로 끝나면, `/` 뒤에 오는 모든 문자(다른 `/` 문자를 포함해서)를 매칭한다.

```jsx
{
  path: "files/*";
  loader: async ({ params }) => {
    params["*"]; // will contain the remaining URL after files/
  };
}
```

`*` 를 구조 분해(destructure)해서 쓸 수도 있는데, 이때는 새로운 이름을 지어줘야 한다. 흔히 `splat` 이라는 이름을 쓴다.

```jsx
const { "*": splat } = params;
```

## 라우트 설명

위 코드는 경로 끝을 `/*` 로 선언해서, 그 뒤에 오는 나머지 경로를 통째로 받아오는 예이다.

```tsx
{
  path: "files/*";
  loader: async ({ params }) => {
    params["*"]; // will contain the remaining URL after files/
  };
}
```

- `files/*` 의 `*` 는 `/` 뒤에 오는 나머지 경로 전체를 매칭하는 와일드카드 세그먼트이다.
- 예를 들어 `/files/docs/2026/report.pdf` 로 접속하면, `files/` 뒤에 남은 `"docs/2026/report.pdf"` 전체가 매칭 대상이 된다.
- 동적 세그먼트(`:teamId`)는 `/` 를 포함하지 않는 한 구간만 매칭하지만, 와일드카드(`*`)는 `/` 를 포함한 나머지 경로 전부를 매칭한다는 점이 다르다.
- `loader` 의 `{ params }` 인자에서 와일드카드로 매칭된 값은 `"*"` 라는 이름의 키에 담기므로, `params["*"]` 처럼 대괄호 문법으로 꺼내야 한다.

```tsx
const { "*": splat } = params;
```

- 객체 구조 분해 문법으로 `"*"` 키의 값을 꺼내면서, 동시에 `splat` 이라는 사용하기 편한 이름으로 바꿔줄 수 있다.
- `"*"` 는 자바스크립트 변수 이름으로 그대로 쓸 수 없기 때문에, `{ "*": splat }` 처럼 `key: 새이름` 형태로 이름을 바꿔서 받아야 한다.
- 이 구조 분해 방식은 `loader`/`action` 의 `params` 뿐 아니라, 컴포넌트에서 `useParams()` 가 반환한 객체에도 똑같이 적용할 수 있다.

정리하면, 와일드카드 세그먼트(`/*`)는 경로의 나머지 부분 전체를 하나의 값으로 받고 싶을 때 사용하며, 그 값은 `params` 객체의 `"*"` 키를 통해 읽는다.
