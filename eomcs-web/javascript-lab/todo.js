"use strict"

console.log("할 일 목록 시작!");

let workingLength = 15;
let completedLength = 60;

const workingLengthTag = document.getElementById('working-length');
const completedLengthTag = document.getElementById('completed-length');
const workingListTag = document.getElementById('working-list');
const completedListTag = document.getElementById('completed-list');
const addBtn = document.getElementById('add-btn');
const todoTitleTag = document.getElementById('todo-title');

workingLengthTag.innerHTML = workingLength;
completedLengthTag.innerHTML = completedLength;

function createTodoArticle(todoItem) {
    return `<article>
        <input id="${todoItem.workId}" type="checkbox">
        <label for="${todoItem.workId}">${todoItem.title}</label>
        </article>`;
}

workingListTag.addEventListener('change', (e) => {
    const inputTag = e.target;
    const articleTag = e.target.parentElement;

    if (inputTag.checked) {
        completedListTag.appendChild(articleTag);
        articleTag.classList.add('completed-todo');
    }
});

completedListTag.addEventListener('change', (e) => {
    const inputTag = e.target;
    const articleTag = e.target.parentElement;

    if (!inputTag.checked) {
        workingListTag.appendChild(articleTag);
        articleTag.classList.remove('completed-todo');
    }
});

addBtn.addEventListener('click', () => {
    const todo = {
        workId: crypto.randomUUID(),
        title: todoTitleTag.value
    };
    const todoArticleTag = createTodoArticle(todo);
    workingListTag.innerHTML = workingListTag.innerHTML + todoArticleTag;
});

