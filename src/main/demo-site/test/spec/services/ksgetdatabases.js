'use strict';

describe('Service: ksGetDatabases', function () {

  // load the service's module
  beforeEach(module('demoApp'));

  // instantiate service
  var ksGetDatabases;
  beforeEach(inject(function (_ksGetDatabases_) {
    ksGetDatabases = _ksGetDatabases_;
  }));

  it('should do something', function () {
    expect(!!ksGetDatabases).toBe(true);
  });

});
