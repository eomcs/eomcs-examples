'use strict';

class LikeButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = { liked: false };
  }

  render() {
    if (this.state.liked) {
      return 'You liked comment number ' + this.props.commentid;
    }

    return (
      <button onClick={() => this.setState({ liked: true })}>
        Like
      </button>
    );
  }
} 

document.querySelectorAll('.like_button_container')
  .forEach(container => {
    const commentid = parseInt(container.dataset.commentid, 10);
    const root = ReactDOM.createRoot(container);
    root.render(
      React.createElement(LikeButton, {commentid: commentid})
    );
  }); 