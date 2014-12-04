'use strict';

describe('Service: kupraServices', function () {

  // load the service's module
  beforeEach(module('demoApp'));

  // instantiate service
  var kupraServices;
  beforeEach(inject(function (_kupraServices_) {
    kupraServices = _kupraServices_;
  }));

  it('should do something', function () {
    expect(!!kupraServices).toBe(true);
  });

});
