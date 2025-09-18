"use strict";

// 이벤트 리스너 등록하기

class Toggle extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isToggleOn: true };
  }

  handleClick(no) {
    console.log("===>", no);
    this.setState((prevState) => ({
      isToggleOn: !prevState.isToggleOn,
    }));
  }

  render() {
    let no = 100;

    // bind()를 이용하여 이벤트 핸들러에 값을 넘길 수 있다.
    return (
      <button onClick={this.handleClick.bind(this, no)}>
        {this.state.isToggleOn ? "ON" : "OFF"}
      </button>
    );
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Toggle />);
