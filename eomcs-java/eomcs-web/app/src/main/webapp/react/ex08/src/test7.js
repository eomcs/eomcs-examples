"use strict";

// 비제어 컴포넌트의 기본 값

class NameForm extends React.Component {
  constructor(props) {
    super(props);
    this.input = React.createRef();
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    alert("A name was submitted: " + this.input.current.value);
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Name:
          <input
            // 컴포넌트가 mount 된 다음에는
            // defaultValue 속성 값을 변경해도
            // DOM의 값이 변경되지 않는다.
            defaultValue="홍길동"
            type="text"
            ref={this.input}
          />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<NameForm />);
