"use strict";

// 이벤트 리스너 등록하기

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Toggle = function (_React$Component) {
  _inherits(Toggle, _React$Component);

  function Toggle(props) {
    _classCallCheck(this, Toggle);

    var _this = _possibleConstructorReturn(this, (Toggle.__proto__ || Object.getPrototypeOf(Toggle)).call(this, props));

    _this.handleClick = function () {
      // arrow function에는 빌트인 변수 this, arguments, super 가 없다.
      // 따라서 클로저 바깥 변수 this를 따로 복제해 둔다.
      // 이것을 보통 '바인드(bind) 한다'라고 표현한다.
      _this.setState(function (prevState) {
        return {
          isToggleOn: !prevState.isToggleOn
        };
      });
    };

    _this.state = { isToggleOn: true };
    return _this;
  }

  // 인스턴스 필드에 arrow function을 할당하면
  // this를 명시적으로 bind 할 필요가 없다.


  _createClass(Toggle, [{
    key: "render",
    value: function render() {
      return React.createElement(
        "button",
        { onClick: this.handleClick },
        this.state.isToggleOn ? "ON" : "OFF"
      );
    }
  }]);

  return Toggle;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Toggle, null));