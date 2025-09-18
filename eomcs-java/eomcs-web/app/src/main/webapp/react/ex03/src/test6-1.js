"use strict";

// 컴포넌트 쪼개기 전
// => 여러 개의 element가 중첩 구조로 되어 있으면 재사용이 어렵다.
function formatDate(date) {
  return date.toLocaleDateString();
}

function Comment(props) {
  return (
    <div className='Comment'>
      <div className='UserInfo'>
        <img className='Avatar' src={props.author.avatarUrl} alt={props.author.name} />
        <div className='UserInfo-name'>{props.author.name}</div>
      </div>
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
