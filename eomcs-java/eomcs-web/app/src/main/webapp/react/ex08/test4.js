"use strict";

// 다중 입력 제어

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Reservation = function (_React$Component) {
  _inherits(Reservation, _React$Component);

  function Reservation(props) {
    _classCallCheck(this, Reservation);

    var _this = _possibleConstructorReturn(this, (Reservation.__proto__ || Object.getPrototypeOf(Reservation)).call(this, props));

    _this.state = {
      isGoing: true,
      numberOfGuests: 2
    };
    _this.handleInputChange = _this.handleInputChange.bind(_this);
    return _this;
  }

  _createClass(Reservation, [{
    key: "handleInputChange",
    value: function handleInputChange(event) {
      var target = event.target;
      var value = target.type === "checkbox" ? target.checked : target.value;
      var name = target.name;

      // ES6의 computed property name 문법
      //this.setState({ [name]: value });

      // ES5의 문법
      var partialState = {};
      partialState[name] = value;
      this.setState(partialState);

      // setState()는 기존 state에 바뀐 부분만 병합한다.
      // 따라서 바뀐 부분만 설정하면 된다.
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
          "Is going:",
          React.createElement("input", {
            name: "isGoing",
            type: "checkbox",
            checked: this.state.isGoing,
            onChange: this.handleInputChange
          })
        ),
        React.createElement("br", null),
        React.createElement(
          "label",
          null,
          "Number of guests:",
          React.createElement("input", {
            name: "numberOfGuests",
            type: "number",
            value: this.state.numberOfGuests,
            onChange: this.handleInputChange
          })
        )
      );
    }
  }]);

  return Reservation;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Reservation, null));