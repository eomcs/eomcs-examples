# 실습에 필요한 테이블 생성

## 게시판 테이블 생성

```
create table x_board (
  board_id int not null primary key auto_increment,
  title varchar(255) not null,
  contents text null,
  created_date datetime null default now(),
  view_count int null default 0
);
```

## 게시판 예제 데이터 입력

```
insert into x_board(board_id, title, contents)
values(1, '제목1', '내용');

insert into x_board(board_id, title, contents)
values(2, '제목2', '내용');

insert into x_board(board_id, title, contents)
values(3, '제목3', '내용');

insert into x_board(board_id, title, contents)
values(4, '제목4', '내용');

insert into x_board(board_id, title, contents)
values(5, '제목5', '내용');

insert into x_board(board_id, title, contents)
values(6, '제목6', '내용');
```







