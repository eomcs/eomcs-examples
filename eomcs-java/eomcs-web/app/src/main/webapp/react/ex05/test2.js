"use strict";

// bind() 사용 전

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var obj1 = {
  title: "Hello!"
};

var obj2 = {
  title: "World!",
  getTitle: function getTitle() {
    return this.title;
  }
};

// obj2.getTitle()의 this가 가리키는 객체를 지정해 두면
// getTitle()을 어디에 붙여서 호출하든 간에 this는 처음에 지정한 객체를 가리킨다.
obj1.m = obj2.getTitle.bind(obj2);

// obj2.getTitle()의 this가 가리키는 객체를 지정해 두지 않으면
// 호출 시점에서 this가 가리키는 객체를 사용한다.
//obj1.m = obj2.getTitle;

var Box = function (_React$Component) {
  _inherits(Box, _React$Component);

  function Box() {
    _classCallCheck(this, Box);

    return _possibleConstructorReturn(this, (Box.__proto__ || Object.getPrototypeOf(Box)).apply(this, arguments));
  }

  _createClass(Box, [{
    key: "render",
    value: function render() {
      return React.createElement(
        "p",
        null,
        "title: ",
        obj1.m()
      );
    }
  }]);

  return Box;
}(React.Component);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Box, null));