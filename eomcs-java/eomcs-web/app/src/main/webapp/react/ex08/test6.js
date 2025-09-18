"use strict";

// 비제어 컴포넌트
// => DOM 자체에서 폼 데이터를 다룬다.

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var NameForm = function (_React$Component) {
  _inherits(NameForm, _React$Component);

  function NameForm(props) {
    _classCallCheck(this, NameForm);

    // 리액트컴포넌트에서 값을 다루지 않는다.
    // this.state = { value: "" };
    // this.handleChange = this.handleChange.bind(this);

    // DOM의 값을 직접 다룰 때 사용할 ref를 생성한다.
    var _this = _possibleConstructorReturn(this, (NameForm.__proto__ || Object.getPrototypeOf(NameForm)).call(this, props));

    _this.input = React.createRef();

    _this.handleSubmit = _this.handleSubmit.bind(_this);
    return _this;
  }

  // 리액트에서 폼 값을 다루지 않는다.
  // handleChange(event) {
  //   this.setState({ value: event.target.value });
  // }

  _createClass(NameForm, [{
    key: "handleSubmit",
    value: function handleSubmit(event) {
      // alert("A name was submitted: " + this.state.value);

      // ref를 통해 직접 DOM의 값을 꺼낸다.
      alert("A name was submitted: " + this.input.current.value);
      event.preventDefault();
    }
  }, {
    key: "render",
    value: function render() {
      return React.createElement(
        "form",
        { onSubmit: this.handleSubmit },
        React.createElement(
          "label",
          null,
          "Name:",
          React.createElement("input", {
            type: "text"
            // 리액트에서 값을 다루지 않는다.
            //value={this.state.value}
            //onChange={this.handleChange}

            // DOM의 값을 직접 다룰 수 있도록 ref와 연결한다.
            , ref: this.input
          })
        ),
        React.createElement("input", { type: "submit", value: "Submit" })
      );
    }
  }]);

  return NameForm;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(NameForm, null));