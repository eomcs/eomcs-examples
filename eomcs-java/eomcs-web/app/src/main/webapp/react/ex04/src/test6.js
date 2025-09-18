"use strict";

// 6단계: 클래스 컴포넌트 사용 + State + Lifecycle(타이머설정/해제)
const root = ReactDOM.createRoot(document.getElementById("root"));

class Clock extends React.Component {
  constructor(props) {
    super(props);

    // 보통 생성자에서 객체의 상태를 초기화시킨다.
    this.state = { date: new Date() };
  }

  // DOM 에 렌더링 될 때 호출됨
  componentDidMount() {
    this.timerID = setInterval(() => this.tick(), 1000);
  }

  // 렌더링된 DOM이 삭제될 때 호출됨
  componentWillUnmount() {
    clearInterval(this.timerID);
  }

  tick() {
    console.log("tick!!!");
    // 직접 state의 프로퍼티 값을 변경하면 다시 렌더링하지 않는다.
    this.state.date = new Date();

    // 반드시 setState()를 호출하여 state의 프로퍼티를 변경해야만 다시 렌더링 한다.
    // setState()를 UI 컴포넌트 상태를 변경하면 render() 메서드가 다시 호출된다.
    // => 컴포넌트 상태를 변경하면 UI에 반영하기 위해 render()가 자동 호출된다.
    // this.setState({
    //   date: new Date(),
    // });
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
