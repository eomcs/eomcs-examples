"use strict";

// 8단계: 클래스 컴포넌트 사용 + State + Lifecycle(타이머설정/해제) + 자식 컴포넌트 + 여러 개
const root = ReactDOM.createRoot(document.getElementById("root"));

function FormattedDate(props) {
  return <h2>It is {props.date.toLocaleTimeString()}.</h2>;
}

class Clock extends React.Component {
  constructor(props) {
    super(props);
    this.state = { date: new Date() };
  }

  componentDidMount() {
    this.timerID = setInterval(
      () => this.tick(),
      parseInt(Math.random() * 3000)
    );
  }

  componentWillUnmount() {
    clearInterval(this.timerID);
  }

  tick() {
    this.setState({
      date: new Date(),
    });
  }

  render() {
    return (
      <div>
        <h1>Hello, world!</h1>
        <FormattedDate date={this.state.date} />
      </div>
    );
  }
}

// 모든 컴포넌트가 독립적이다!
function App() {
  return (
    <div>
      <Clock />
      <Clock />
      <Clock />
    </div>
  );
}
root.render(App());
