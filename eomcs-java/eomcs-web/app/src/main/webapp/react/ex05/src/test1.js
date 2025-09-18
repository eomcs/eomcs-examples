"use strict";

// 이벤트 리스너 등록하기

class Toggle extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isToggleOn: true };

    // handleClick() 함수의 this가 이 객체를 가리키도록 묶어 준다.
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    // 방법1)
    // this.setState({
    //   isToggleOn: !this.state.isToggleOn,
    // });
    // 방법2) setState()에 객체를 넘기지 않고 함수를 넘긴다.
    // setState()가 함수를 파라미터로 받으면
    // 해당 함수 호출하여 그 리턴 값을 사용한다.
    // 함수를 호출할 때 현재 state 객체를 아규먼트로 넘겨준다.
    this.setState((currState) => ({
      isToggleOn: !currState.isToggleOn,
    }));
  }

  render() {
    return (
      <button onClick={this.handleClick}>
        {this.state.isToggleOn ? "ON" : "OFF"}
      </button>
    );
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Toggle />);
