'use strict';

module.exports = {
  add: function(value) {
    value = value || '0';
    return value.split(',').reduce(function(sum, n) {
      return sum + parseInt(n, 10);
    }, 0);
  }
};
