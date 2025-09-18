// 기존 jQuery 에 기능 추가
ElementBox.prototype.button = function() {
  this.el.forEach((e) => {
    e.classList.add("btn")
  });
};