'use strict';

describe('Filter: collectionFilter', function () {

  // load the filter's module
  beforeEach(module('demoApp'));

  // initialize a new instance of the filter before each test
  var collectionFilter;
  beforeEach(inject(function ($filter) {
    collectionFilter = $filter('collectionFilter');
  }));

  it('should return the input prefixed with "collectionFilter filter:"', function () {
    var text = 'angularjs';
    expect(collectionFilter(text)).toBe('collectionFilter filter: ' + text);
  });

});
