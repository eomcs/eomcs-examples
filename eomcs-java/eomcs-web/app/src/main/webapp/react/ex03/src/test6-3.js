"use strict";

// 컴포넌트 쪼갠 후 - 2단계
// => element를 여러 개의 컴포넌트로 분해해 놓으면 재사용 하기 쉽다.
function formatDate(date) {
  return date.toLocaleDateString();
}

function Avatar(props) {
  return <img className='Avatar' src={props.user.avatarUrl} alt={props.user.name} />;
}

function UserInfo(props) {
  return (
    <div className='UserInfo'>
      <Avatar user={props.user} />
      <div className='UserInfo-name'>{props.user.name}</div>
    </div>
  );
}

function Comment(props) {
  return (
    <div className='Comment'>
      <UserInfo user={props.author} />
      <div className='Comment-text'>{props.text}</div>
      <div className='Comment-date'>{formatDate(props.date)}</div>
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  Comment({
    author: {
      avatarUrl:
        "https://scs-phinf.pstatic.net/MjAyMjA0MTdfMjQ0/MDAxNjUwMTUzNjExMDM5.-wJzs-n6lS91xFZCKJeXmxJLhKDURQse_E4xFWcmdOUg.wL2EySuB2zDveuQT1iMYNul82zFEIhw17d1A3k1xZeAg.PNG/image%7Cpremium%7Cchannel%7Ceomcs%7C2022%7C04%7C17%7C1650153611005.png?type=nfs200_200",
      name: "엄진영",
    },
    text: "코딩스쿨",
    date: new Date(),
  })
);
