"use strict";

// 다른 컴포넌트 담기 - 클래스 합성
/*
튜토리얼 문서 중에서, 
"Facebook에서는 수천 개의 React 컴포넌트를 사용하지만, 
 컴포넌트를 상속 계층 구조로 작성을 권장할만한 사례를 
 아직 찾지 못했습니다.
 props와 합성은 명시적이고 안전한 방법으로 
 컴포넌트의 모양과 동작을 커스터마이징하는데 필요한 모든 
 유연성을 제공합니다. 
 컴포넌트가 원시 타입의 값, React 엘리먼트 혹은 함수 등 
 어떠한 props도 받을 수 있다는 것을 기억하세요."
*/

function FancyBorder(props) {
  return (
    <div className={"FancyBorder FancyBorder-" + props.color}>
      {props.children}
    </div>
  );
}

function Dialog(props) {
  return (
    <FancyBorder color="blue">
      <h1 className="Dialog-title">{props.title}</h1>
      <p className="Dialog-message">{props.message}</p>
      {props.children}
    </FancyBorder>
  );
}

class SignUpDialog extends React.Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.handleSignUp = this.handleSignUp.bind(this);
    this.state = { login: "" };
  }

  render() {
    return (
      <Dialog
        title="Mars Exploration Program"
        message="How should we refer to you?"
      >
        <input value={this.state.login} onChange={this.handleChange} />
        <button onClick={this.handleSignUp}>Sign Me Up!</button>
      </Dialog>
    );
  }

  handleChange(e) {
    this.setState({ login: e.target.value });
  }

  handleSignUp() {
    alert(`Welcome aboard, ${this.state.login}!`);
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<SignUpDialog />);
