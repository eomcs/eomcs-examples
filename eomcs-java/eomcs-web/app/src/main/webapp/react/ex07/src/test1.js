"use strict";

// 여러 개의 컴포넌트 렌더링하기

const numbers = [1, 2, 3, 4, 5];
const listItems = numbers.map((number) => <li>{number}</li>);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<ul>{listItems}</ul>);
