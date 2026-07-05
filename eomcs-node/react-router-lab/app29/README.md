# [데이터 모드] app29. 선택적 세그먼트

## Optional Segments

세그먼트 끝에 `?` 를 붙이면 그 경로 세그먼트를 선택적(optional)으로 만들 수 있다.

```jsx
{
  path: ":lang?/categories";
}
```

정적(static) 세그먼트도 선택적으로 만들 수 있다.

```jsx
{
  path: "users/:userId/edit?";
}
```

## 라우트 설명

위 코드는 경로 세그먼트 끝에 `?` 를 붙여서, 있어도 되고 없어도 매칭되는 URL을 만드는 예이다.

```tsx
{
  path: ":lang?/categories";
}
```

- `:lang?` 은 동적 세그먼트 `:lang` 뒤에 `?` 를 붙인 선택적 동적 세그먼트이다.
- `/categories` 로 접속해도 매칭되고(이때 `params.lang` 은 `undefined`), `/ko/categories` 로 접속해도 매칭된다(`params.lang` 은 `"ko"`).
- 즉, 하나의 라우트 선언으로 언어 코드가 있는 URL과 없는 URL을 모두 처리할 수 있다.

```tsx
{
  path: "users/:userId/edit?";
}
```

- 이번에는 동적 세그먼트가 아니라 정적 세그먼트인 `edit` 뒤에 `?` 를 붙였다.
- `/users/100` 으로 접속해도 매칭되고, `/users/100/edit` 으로 접속해도 매칭된다.
- 이런 방식으로 "조회 화면"과 "수정 화면"을 URL 구조는 그대로 두고 하나의 라우트로 함께 처리할 수 있다. (실제로는 `loader`/`Component` 안에서 `edit` 세그먼트의 유무에 따라 다른 화면을 보여주도록 분기 처리하게 된다.)

정리하면, 세그먼트 뒤의 `?` 는 그 세그먼트가 URL에 있어도 되고 없어도 되게 만든다. 동적 세그먼트(`:lang?`)뿐 아니라 정적 세그먼트(`edit?`)에도 똑같이 적용할 수 있다.
