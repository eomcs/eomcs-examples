"use strict";

// 다른 컴포넌트 담기 - 화면 레이아웃에 활용

function Contacts() {
  return React.createElement("div", { className: "Contacts" });
}

function Chat() {
  return React.createElement("div", { className: "Chat" });
}

function SplitPane(props) {
  return React.createElement(
    "div",
    { className: "SplitPane" },
    React.createElement(
      "div",
      { className: "SplitPane-left" },
      props.left
    ),
    React.createElement(
      "div",
      { className: "SplitPane-right" },
      props.right
    )
  );
}

function App() {
  return React.createElement(SplitPane, { left: React.createElement(Contacts, null), right: React.createElement(Chat, null) });
}

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(App, null));