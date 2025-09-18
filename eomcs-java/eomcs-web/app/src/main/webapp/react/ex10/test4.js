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

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function FancyBorder(props) {
  return React.createElement(
    "div",
    { className: "FancyBorder FancyBorder-" + props.color },
    props.children
  );
}

function Dialog(props) {
  return React.createElement(
    FancyBorder,
    { color: "blue" },
    React.createElement(
      "h1",
      { className: "Dialog-title" },
      props.title
    ),
    React.createElement(
      "p",
      { className: "Dialog-message" },
      props.message
    ),
    props.children
  );
}

var SignUpDialog = function (_React$Component) {
  _inherits(SignUpDialog, _React$Component);

  function SignUpDialog(props) {
    _classCallCheck(this, SignUpDialog);

    var _this = _possibleConstructorReturn(this, (SignUpDialog.__proto__ || Object.getPrototypeOf(SignUpDialog)).call(this, props));

    _this.handleChange = _this.handleChange.bind(_this);
    _this.handleSignUp = _this.handleSignUp.bind(_this);
    _this.state = { login: "" };
    return _this;
  }

  _createClass(SignUpDialog, [{
    key: "render",
    value: function render() {
      return React.createElement(
        Dialog,
        {
          title: "Mars Exploration Program",
          message: "How should we refer to you?"
        },
        React.createElement("input", { value: this.state.login, onChange: this.handleChange }),
        React.createElement(
          "button",
          { onClick: this.handleSignUp },
          "Sign Me Up!"
        )
      );
    }
  }, {
    key: "handleChange",
    value: function handleChange(e) {
      this.setState({ login: e.target.value });
    }
  }, {
    key: "handleSignUp",
    value: function handleSignUp() {
      alert("Welcome aboard, " + this.state.login + "!");
    }
  }]);

  return SignUpDialog;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(SignUpDialog, null));