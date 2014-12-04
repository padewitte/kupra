'use strict';

describe('Service: ksGetColStats', function () {

  // load the service's module
  beforeEach(module('demoApp'));

  // instantiate service
  var ksGetColStats;
  beforeEach(inject(function (_ksGetColStats_) {
    ksGetColStats = _ksGetColStats_;
  }));

  it('should do something', function () {
    expect(!!ksGetColStats).toBe(true);
  });

});
