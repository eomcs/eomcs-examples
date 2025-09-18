function jQuery(p) {
  var el;
  if (p.charAt(0) == '<') { // 예) <table>
    const template = document.createElement('template');
    template.innerHTML = p;
    el = template.content.children; 
  } else {
    el = document.querySelectorAll(p);
  }
  
  el.html = function(htmlString) {
    for (var e of el) {
      e.innerHTML = htmlString;
    }
    return el;
  };
  
  el.appendTo = function(selector) {
    var parents = document.querySelectorAll(selector);
    for (var parent of parents) {
      for (var child of el) {
        parent.appendChild(child);
      }
    }
    return el;
  };
  
  el.on = function(eventName, handler) {
    for (var e of el) {
      e.addEventListener(eventName, handler);
    }
    return el;
  };
  
  el.click = function(handler) {
    return el.on('click', handler);
  };
  
  el.change = function(handler) {
    return el.on('change', handler);
  };
  
  return el;
}

jQuery.ajax = function(url, settings) {
  if (!settings) {
    settings = {};
  }
  
  if (!settings.dataType) {
    settings.dataType = "text";
  }
  
  if (!settings.method) {
    settings.method = "GET";
  }
  
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState == 4) {
        if (xhr.status == 200) {
          if (settings.success) {
            if (settings.dataType == "text") {
              settings.success(xhr.responseText);
            } else if (settings.dataType == "json") {
              settings.success(JSON.parse(xhr.responseText));
            }
          }
        } else {
          if (settings.error) {
            settings.error();
          }
        }
    }
  };
  xhr.open(settings.method, url, true);
  
  if (settings.method == "POST") {
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  }
  
  xhr.send();
};

var $ = jQuery;