//1. 태그 찾기
//2. 태그 만들기
//3. append() 
function jQuery(selector) {
  if (selector.startsWith("<")) {
    let e = document.createElement(selector.substring(1, selector.length - 1));
    e.append = function(child) {
      e.appendChild(child);
    };
    return e;
  } else {
    let el = document.querySelectorAll(selector);
    for (let e of el) {
      e.append = function(child) {
        e.appendChild(child);
      };
    }
    return el;
  }
}

var $ = jQuery;