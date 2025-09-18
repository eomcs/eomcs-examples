"use strict";

// 비제어 컴포넌트
// => DOM 자체에서 폼 데이터를 다룬다.

class NameForm extends React.Component {
  constructor(props) {
    super(props);

    // 리액트컴포넌트에서 값을 다루지 않는다.
    // this.state = { value: "" };
    // this.handleChange = this.handleChange.bind(this);

    // DOM의 값을 직접 다룰 때 사용할 ref를 생성한다.
    this.input = React.createRef();

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // 리액트에서 폼 값을 다루지 않는다.
  // handleChange(event) {
  //   this.setState({ value: event.target.value });
  // }

  handleSubmit(event) {
    // alert("A name was submitted: " + this.state.value);

    // ref를 통해 직접 DOM의 값을 꺼낸다.
    alert("A name was submitted: " + this.input.current.value);
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Name:
          <input
            type="text"
            // 리액트에서 값을 다루지 않는다.
            //value={this.state.value}
            //onChange={this.handleChange}

            // DOM의 값을 직접 다룰 수 있도록 ref와 연결한다.
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
