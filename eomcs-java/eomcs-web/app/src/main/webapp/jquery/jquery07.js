function jQuery(selector) {
  var el = document.querySelectorAll(selector);
  
  el.html = function(value) {
    if (arguments.length == 0) {
      return this[0].innerHTML;
    }
    for (var e of el) {
      e.innerHTML = value;
    }
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
  };
  
  el.on = function(event, listener) {
    if (arguments.length < 2) {
      return;
    }
    
    for (var e of el) {
      e.addEventListener(event, listener);
    }
  };
  
  el.click = function(listener) {
    if (arguments.length < 1) {
      return;
    }
    
    this.on('click', listener);
  }
  
  return el;
};

var $ = jQuery;