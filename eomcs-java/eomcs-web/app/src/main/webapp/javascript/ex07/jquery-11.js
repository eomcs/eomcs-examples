//1. 태그 찾기
//2. 태그 만들기
//3. append() 
//4. 리팩토링
//5. html()
//6. on()
//7. appendTo()
//8. Method Chaining
//9. click()
//10. 리팩토링
//11. ajax()
function jQuery(selector) {
  return new ElementBox(selector);
}

function ElementBox(selector) {
  this.el = [];

  if (selector.startsWith("<")) {
    this.el[0] = document.createElement(selector.substring(1, selector.length - 1));

  } else {
    let nodeList = document.querySelectorAll(selector);
    for (let e of nodeList) {
      this.el.push(e);
    }
  }
}

ElementBox.prototype.append = function(childBox) {
  for (let parent of this.el) {
    for (let child of childBox.el) {
      parent.appendChild(child.cloneNode(true));
    }
  }
  for (let child of childBox.el) {
    if (child.parentElement != null || child.parentElement != undefined) {
      child.parentElement.removeChild(child);
    }
  }
  return this;
};

ElementBox.prototype.appendTo = function(parentBox) {
  for (let parentTag of parentBox.el) {
    for (let child of this.el) {
      parentTag.appendChild(child.cloneNode(true));
    }
  }
  for (let child of this.el) {
    if (child.parentElement != null || child.parentElement != undefined) {
      child.parentElement.removeChild(child);
    }
  }
  return this;
};

ElementBox.prototype.html = function(content) {
  for (let e of this.el) {
    e.innerHTML = content;
  }
  return this;
};

ElementBox.prototype.on = function(eventName, listener) {
  for (let e of this.el) {
    e.addEventListener(eventName, listener);
  }
  return this;
};

ElementBox.prototype.click = function(handler) {
  return this.on('click', handler);
};


jQuery.ajax = function(settings) {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        let result;
        if (settings.dataType == "json") {
          // json string ---> javascript object (deserialize)
          result = JSON.parse(xhr.responseText);
        } else {
          result = xhr.responseText;
        }
        settings.success(result);

      } else {
        settings.error();
      }
    }
  };
  xhr.open(settings.method, settings.url, settings.async);
  xhr.send();
};

var $ = jQuery;