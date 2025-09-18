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
//12. ajax() 코드 정리
//13. getJSON()
//14. val(), html()
//15. submit(), ajax() 변경
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
  if (this.el.length == 0) {
    return;
  }

  if (arguments.length > 0) {
    // 파라미터 값이 있으면 setter로 동작한다.
    for (let e of this.el) {
      e.innerHTML = content;
    }
    return this;

  } else {
    // 파라미터 값이 없으면 getter로 동작한다.
    return this.el[0].innerHTML;
  }
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

// jQuery 함수는 값을 꺼내는 함수(getter) 따로 넣는 함수(setter) 따로 있지 않다.
// 한 함수에서 값을 넣고 꺼내는 일을 한다.
// 파라미터 값이 있으면 값을 넣는 일을 하고,
// 파라미터 값이 없으면 꺼내는 일을 한다.
ElementBox.prototype.val = function(value) {
  if (this.el.length == 0) {
    return;
  }

  if (arguments.length > 0) {
    // 값을 설정할 때는 모든 태그에 대해 수행한다.
    for (let e of this.el) {
      e.value = value;
    }
    return this;

  } else {
    // 값을 꺼낼 때는 맨 처음 태그 값만 꺼낸다.
    return this.el[0].value;
  }
};

ElementBox.prototype.submit = function(handler) {
  return this.on('submit', handler);
};

jQuery.ajax = function(settings) {
  if (settings.method == undefined) settings.method = "GET";
  if (settings.async == undefined) settings.async = true;

  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState == 4) {
      if (xhr.status == 200) {
        if (settings.success == undefined) {
          return;
        }
        let result;
        if (settings.dataType == "json") {
          // json string ---> javascript object (deserialize)
          result = JSON.parse(xhr.responseText);
        } else {
          result = xhr.responseText;
        }
        settings.success(result);

      } else {
        if (settings.error == undefined) {
          return;
        }
        settings.error();
      }
    }
  };

  xhr.open(settings.method, settings.url, settings.async);

  if (settings.method == "POST") {
    xhr.setRequestHeader(
      "Content-Type", 
      "application/x-www-form-urlencoded");

    let payload = "";
    if (settings.data != undefined && settings.data != null) {
      for (let key in settings.data) {
        if (payload.length > 0) {
          payload += "&";
        }
        payload += key + "=" + window.encodeURIComponent(settings.data[key]);
      }
    }
    xhr.send(payload);

  } else {
    xhr.send();
  }
};

jQuery.getJSON = function(url, success) {
  jQuery.ajax({
    url: url,
    dataType: "json",
    success: success
  });
};

var $ = jQuery;