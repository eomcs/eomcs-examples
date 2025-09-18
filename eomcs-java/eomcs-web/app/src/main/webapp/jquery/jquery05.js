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
    if (arguments.length == 1) {
      return this[0].style[name];
    }
    for (var e of el) {
      e.style[name] = value;
    }
  };
  
  return el;
};

var $ = jQuery;