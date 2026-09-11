"use strict"

console.log("할 일 목록 시작!");

const workingLengthTag = document.getElementById('working-length');
const completedLengthTag = document.getElementById('completed-length');
const workingListTag = document.getElementById('working-list');
const completedListTag = document.getElementById('completed-list');
const addBtn = document.getElementById('add-btn');
const todoTitleTag = document.getElementById('todo-title');
const todoFormTag = document.getElementById('todo-form');
const workingTab = document.getElementById('working-tab');
const completedTab = document.getElementById('completed-tab');

function createTodoArticle(todoItem) {
    return `<article>
        <input id="${todoItem.workId}" type="checkbox">
        <label for="${todoItem.workId}">${todoItem.title}</label>
        <button type='button'>삭제</button>
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

workingListTag.addEventListener('click', (e) => {
    if (e.target.tagName !== "BUTTON")
        return; 
    
    const btn = e.target;
    const article = btn.parentElement;
    workingListTag.removeChild(article);
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
    document.getElementById('working-length').innerHTML = workingListTag.querySelectorAll('article').length;
    todoFormTag.reset();    
});

completedTab.addEventListener('click', () => {
    completedListTag.style['display'] = 'block';
    workingListTag.style['display'] = 'none';
});

workingTab.addEventListener('click', () => {
    completedListTag.style['display'] = 'none';
    workingListTag.style['display'] = 'block';
});