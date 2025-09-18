function jQuery(selector) {
  if (selector.startsWith('<')) {
    var tagName = selector.substring(1, selector.length - 1);
    var el = [document.createElement(tagName)];
  } else {
    var el = document.querySelectorAll(selector);
  }
  
  el.html = function(value) {
    if (arguments.length == 0) {
      return this[0].innerHTML;
    }
    for (var e of el) {
      e.innerHTML = value;
    }
    return this;
  };
  
  el.css = function(name, value) {
    if (arguments.length == 0) {
      return;
    }
    
    if (arguments.length == 1) {
      return this[0].style[name];
    }
    
    for (var e of el) {
      e.style[name] = value;
    }
    return this;
  };
  
  el.on = function(event, listener) {
    if (arguments.length < 2) {
      return;
    }
    
    for (var e of el) {
      e.addEventListener(event, listener);
    }
    return this;
  };
  
  el.click = function(listener) {
    if (arguments.length < 1) {
      return;
    }
    
    this.on('click', listener);
    return this;
  }
  
  el.append = function(childs) {
    if (arguments.length < 1) {
      return;
    }
    
    for (var e of el) {
      for (var child of childs) {
        e.appendChild(child);
      }
    }
    return this;
  }
  
  return el;
};

var $ = jQuery;