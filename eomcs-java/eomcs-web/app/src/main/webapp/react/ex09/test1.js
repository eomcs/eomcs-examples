"use strict";

// State 끌어 올리기 - 여러 컴포넌트에서 데이터를 공유하는 방법
// 1단계

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function BoilingVerdict(props) {
  if (props.celsius >= 100) {
    return React.createElement(
      "p",
      null,
      "The water would boil."
    );
  }
  return React.createElement(
    "p",
    null,
    "The water would not boil."
  );
}

var Calculator = function (_React$Component) {
  _inherits(Calculator, _React$Component);

  function Calculator(props) {
    _classCallCheck(this, Calculator);

    var _this = _possibleConstructorReturn(this, (Calculator.__proto__ || Object.getPrototypeOf(Calculator)).call(this, props));

    _this.state = { temperature: "" };
    _this.handleChange = _this.handleChange.bind(_this);
    return _this;
  }

  _createClass(Calculator, [{
    key: "handleChange",
    value: function handleChange(event) {
      this.setState({ temperature: event.target.value });
    }
  }, {
    key: "render",
    value: function render() {
      var temperature = this.state.temperature;
      return React.createElement(
        "fieldset",
        null,
        React.createElement(
          "legend",
          null,
          "\uC12D\uC528 \uC628\uB3C4:"
        ),
        React.createElement("input", { type: "number", value: temperature, onChange: this.handleChange }),
        React.createElement(BoilingVerdict, { celsius: parseFloat(temperature) })
      );
    }
  }]);

  return Calculator;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Calculator, null));