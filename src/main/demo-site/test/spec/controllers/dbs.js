'use strict';

describe('Controller: DbsCtrl', function () {

  // load the controller's module
  beforeEach(module('demoApp'));

  var DbsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DbsCtrl = $controller('DbsCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
