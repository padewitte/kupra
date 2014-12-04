'use strict';

describe('Service: ksGetCollections', function () {

  // load the service's module
  beforeEach(module('demoApp'));

  // instantiate service
  var ksGetCollections;
  beforeEach(inject(function (_ksGetCollections_) {
    ksGetCollections = _ksGetCollections_;
  }));

  it('should do something', function () {
    expect(!!ksGetCollections).toBe(true);
  });

});
