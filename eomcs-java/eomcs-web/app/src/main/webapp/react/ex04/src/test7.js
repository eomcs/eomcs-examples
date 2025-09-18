"use strict";

// 7단계: 클래스 컴포넌트 사용 + State + Lifecycle(타이머설정/해제) + 자식 컴포넌트
const root = ReactDOM.createRoot(document.getElementById("root"));

// 데이터는 "하향식(top-down)" 또는 "단방향식"으로 흐른다.
function FormattedDate(props) {
  return <h2>It is {props.date.toLocaleTimeString()}.</h2>;
}

class Clock extends React.Component {
  constructor(props) {
    super(props);
    this.state = { date: new Date() };
  }

  componentDidMount() {
    this.timerID = setInterval(() => this.tick(), 1000);
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

root.render(<Clock />);
