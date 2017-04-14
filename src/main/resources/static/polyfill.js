/*eslint no-unused-vars: ["error", { "varsIgnorePattern": "console" }]*/
// import 'es6-promise';
var window = this;
var console = {
  error: print,
  debug: print,
  warn: print,
  log: print
};
window.setTimeout = function () {
};
window.Promise = {
  resolve: function () {
  },
  reject: function () {
  }
};