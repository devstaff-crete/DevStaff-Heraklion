'use strict';

var expect = require('chai').expect;
var calculator = require('../lib/string_calculator');

describe('StringCalculator', function() {
  [{
    input: '',
    expectedResult: 0
  }, {
    input: '1',
    expectedResult: 1
  }, {
    input: '2',
    expectedResult: 2
  }, {
    input: '1,2',
    expectedResult: 3
  }, {
    input: '1,2,3',
    expectedResult: 6
  }].forEach(function(testExample) {
    it('will return ' + testExample.expectedResult + ' on "' + testExample.input + '"', function() {
      var result = calculator.add(testExample.input);
      expect(result).to.be.eql(testExample.expectedResult);
    });
  });
});
