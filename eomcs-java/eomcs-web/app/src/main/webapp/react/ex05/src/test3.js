"use strict";

// 이벤트 리스너 등록하기

class Toggle extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isToggleOn: true };
  }

  // 인스턴스 필드에 arrow function을 할당하면
  // this를 명시적으로 bind 할 필요가 없다.
  handleClick = () => {
    // arrow function에는 빌트인 변수 this, arguments, super 가 없다.
    // 따라서 클로저 입장에서 바깥 변수 this를 따로 복제해 둔다.
    // 이것을 보통 '바인드(bind) 한다'라고 표현한다.
    this.setState((prevState) => ({
      isToggleOn: !prevState.isToggleOn,
    }));
  };

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
