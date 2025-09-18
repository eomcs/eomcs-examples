"use strict";

// 5단계: 클래스 컴포넌트 사용 + State + Lifecycle
const root = ReactDOM.createRoot(document.getElementById("root"));

class Clock extends React.Component {
  constructor(props) {
    super(props);
    this.state = { date: new Date() };
  }

  // DOM 에 렌더링 될 때 호출됨
  componentDidMount() {
    console.log("ok");
  }

  // 렌더링된 DOM이 삭제될 때 호출됨
  componentWillUnmount() {
    console.log("no");
  }

  render() {
    return (
      <div>
        <h1>Hello, world!</h1>
        <h2>It is {this.state.date.toLocaleTimeString()}.</h2>
      </div>
    );
  }
}

root.render(<Clock />);
