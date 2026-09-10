"use strict"

console.log("할 일 목록 시작!");

let workingLength = 15;
let completedLength = 60;

const workingLengthTag = document.getElementById('working-length');
const completedLengthTag = document.getElementById('completed-length');
const workingListTag = document.getElementById('working-list');
const completedListTag = document.getElementById('completed-list');

workingLengthTag.innerHTML = workingLength;
completedLengthTag.innerHTML = completedLength;


const todoItems = [
    {
        workId: 100, 
        title: "객체 만들기"
    },
    {
        workId: 101, 
        title: "배열 만들기"
    }
];

for (const todoItem of todoItems) {
    const todoItemTag = createTodoArticle(todoItem);
    console.log(todoItemTag);
    workingListTag.innerHTML = workingListTag.innerHTML + todoItemTag;
}

function createTodoArticle(todoItem) {
    const todoItemTag = `<article>
        <input id="work${todoItem.workId}" type="checkbox">
        <label for="work${todoItem.workId}">${todoItem.title}</label>
        </article>`;
    return todoItemTag;
}

/*
<h2>진행 중(<span id="working-length">5</span>)</h2>
<article>
    <input id="work100" type="checkbox">
    <label for="work100">객체 만들기</label>
    </article>
<article>
    <input id="work101" type="checkbox">
    <label for="work101">배열 만들기</label>
    </article>
*/