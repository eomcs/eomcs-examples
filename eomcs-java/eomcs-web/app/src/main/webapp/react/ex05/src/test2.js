"use strict";

// bind() 사용 전

var obj1 = {
  title: "Hello!",
};

var obj2 = {
  title: "World!",
  getTitle: function () {
    return this.title;
  },
};

// obj2.getTitle()의 this가 가리키는 객체를 지정해 두면
// getTitle()을 어디에 붙여서 호출하든 간에 this는 처음에 지정한 객체를 가리킨다.
obj1.m = obj2.getTitle.bind(obj2);

// obj2.getTitle()의 this가 가리키는 객체를 지정해 두지 않으면
// 호출 시점에서 this가 가리키는 객체를 사용한다.
//obj1.m = obj2.getTitle;

class Box extends React.Component {
  render() {
    return <p>title: {obj1.m()}</p>;
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Box />);
