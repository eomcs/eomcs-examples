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

    return React.createElement(
      'button',
      { onClick: () => this.setState({ liked: true }) },
      'Like'
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