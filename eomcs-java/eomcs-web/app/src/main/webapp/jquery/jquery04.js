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
  
  return el;
};

var $ = jQuery;