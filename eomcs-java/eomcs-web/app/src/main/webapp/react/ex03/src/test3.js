"use strict";

// 클래스 컴포넌트 만들기
// => 컴포넌트의 이름은 항상 대문자로 시작한다.
class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}

// Welcome 클래스의 설계도에 따라 React element 객체를 생성한다.
const element = <Welcome name="임꺽정" />;

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
