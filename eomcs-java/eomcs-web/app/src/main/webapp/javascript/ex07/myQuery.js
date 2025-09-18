function myQuery(p) {
  let el = [];
  if (p instanceof Element) {
    el.push(p);
  } else if (p.startsWith('<')) { //'<p>'
    el.push(document.createElement(p.substring(1, p.length - 1)));
  } else {
    let nodeList = document.querySelectorAll(p);
    for (let e of nodeList) {
      el.push(e);
    }
  }

  el.val = function(v) {
    if (v == undefined) {
      return el.length > 0 ? el[0].value : undefined;
    } else {
      for (let e of el) {
        e.value = v;
      }
      return this;
    }
  };

  el.on = function(eventName, listener) {
    for (let e of el) {
      e.addEventListener(eventName, listener);
    }
    return this;
  };

  el.click = function(listener) {
    if (listener) {
      el.on('click', listener);
    } else {
      for (let e of el) {
        e.dispatchEvent(new MouseEvent('click'));
      }
    }
    return this;
  };

  el.html = function(content) {
    if (content) {
      for (let e of el) {
        e.innerHTML = content;
      }
      return this;
    } else {
      return el.length > 0 ? el[0].innerHTML : undefined;
    }
  };

  el.text = function(content) {
    if (content) {
      for (let e of el) {
        e.textContent = content;
      }
      return this;
    } else {
      el.length > 0 ? el[0].textContent : undefined;
    }
  };

  el.load = function(url, p1, p2) {
    let settings = {
      url: url
    }
    let complete;
    if (p1 && 'object' == typeof p1) {
      settings.data = p1;
      if (p2 && 'function' == typeof p2) {
        complete = p2;
      }
    } else if (p1 && 'function' == typeof p1) {
      complete = p1;
    }

    settings.success = function(result) {
      for (let e of el) {
        e.innerHTML = result;
      }
      if (complete) {
        complete();
      }
    }
    myQuery.get(settings);
    return this;
  };

  el.appendTo = function(value) {
    let parents;
    if ('string' == typeof value) {
      parents = myQuery(value);
    } else {
      parents = value;
    }
    parents.append(el);
    return this;
  };

  el.append = function(childs) {
    for (let e of el) {
      for (let child of childs) {
        e.appendChild(child);
      }
    }
    return this;
  };

  el.attr = function(name, value) {
    if (arguments.length > 1) {
      for (let e of el) {
        e.setAttribute(name, value);
      }
      return this;
    } else {
      return el.length > 0 ? el[0].getAttribute(name) : undefined;
    }
  }

  return el;
}

myQuery.ajax = function(p1, p2) {
  let url, settings;
  settings = {};

  if ('string' == typeof p1) {
    url = p1;
    settings = p2;
  } else {
    settings = p1;
    url = settings.url;
  }

  if (settings.method == undefined) {
    settings.method = 'get';
  }

  if (settings.dataType == undefined) {
    settings.dataType = 'text';
  }

  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
      if (xhr.readyState == 4) {
        if (xhr.status == 200) {
          if (settings.success) {
            if (settings.dataType == 'json') {
              settings.success(JSON.parse(xhr.responseText))
            } else {
              settings.success(xhr.responseText);
            }
          }
        } else {
          if (settings.error) {
            settings.error(xhr.responseText);
          }
        }
      }
  };

  let arr = [];
  if (settings.data) {
    for (let name in settings.data) {
      arr.push(name + '=' + settings.data[name])
    }
  }
  let qs = arr.join('&');

  if (settings.method == 'get') {
    xhr.open(settings.method, url + '?' + qs, true);
    xhr.send();

  } else if (settings.method == 'post') {
    xhr.open(settings.method, url, true);
    xhr.setRequestHeader(
          "Content-Type",
          "application/x-www-form-urlencoded");
    xhr.send(qs);
  }
};

myQuery.get = function(url, p1, p2, p3) {
  if ('object' == typeof url) {
    myQuery.ajax(url);
    return;
  }

  let settings = {
    url: url,
    method: 'get',
    dataType: 'text'
  };

  if ('object' == typeof p1) {
    settings.data = p1;
    if (p2 && 'function' == typeof p2) {
      settings.success = p2;
      if (p3) {
        settings.dataType = p3;
      }
    } else if (p2 && 'string' == typeof p2) {
      settings.dataType = p2;
    }
  } else if ('function' == typeof p1) {
    settings.success = p1;
    if (p2 && 'string' == typeof p2) {
      settings.dataType = p2;
    }
  } else if ('string' == typeof p1) {
    settings.dataType = p1;
  }

  myQuery.ajax(settings);

};


myQuery.getJSON = function(url, p1, p2, p3) {
  if ('object' == typeof p1) {
    if (p2) {
      myQuery.get(url, p1, p2, 'json');
    } else {
      myQuery.get(url, p1, 'json');
    }
  } else if ('function' == typeof p1) {
    myQuery.get(url, p1, 'json');
  } else {
    myQuery.get(url, 'json');
  }
}

myQuery.post = function(url, p1, p2, p3) {
  let settings = {
    url: url,
    method: 'post',
    dataType: 'text'
  };

  if ('object' == typeof p1) {
    settings.data = p1;
    if (p2 && 'function' == typeof p2) {
      settings.success = p2;
      if (p3) {
        settings.dataType = p3;
      }
    } else if (p2 && 'string' == typeof p2) {
      settings.dataType = p2;
    }
  } else if ('function' == typeof p1) {
    settings.success = p1;
    if (p2 && 'string' == typeof p2) {
      settings.dataType = p2;
    }
  } else if ('string' == typeof p1) {
    settings.dataType = p1;
  }

  myQuery.ajax(settings);

};

let $ = myQuery;