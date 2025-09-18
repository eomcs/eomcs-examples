"use strict";

// && 연산자를 이용한 if를 JSX 안에 삽입하기

function Mailbox(props) {
  var unreadMessages = props.unreadMessages;
  return React.createElement(
    "div",
    null,
    React.createElement(
      "h1",
      null,
      "Hello!"
    ),
    unreadMessages.length > 0 && React.createElement(
      "h2",
      null,
      "You have ",
      unreadMessages.length,
      " unread messages."
    )
  );
}

var messages = ["React", "Re: React", "Re: Re: React"];

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Mailbox, { unreadMessages: messages }));