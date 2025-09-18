"use strict";

// 비제어 컴포넌트 - 파일 입력 태그
// => <input type="file" />은 항상 비제어 컴포넌트다.
// => 프로그래밍으로 값을 다룰 수 없다.
//

class FileInput extends React.Component {
  constructor(props) {
    super(props);
    this.fileInput = React.createRef();
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    alert(`Selected file:  ${this.fileInput.current.files[0].name}`);
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Upload file:
          <input type="file" ref={this.fileInput} />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<FileInput />);
