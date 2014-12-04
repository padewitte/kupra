'use strict';

describe('Controller: ClusterCtrl', function () {

  // load the controller's module
  beforeEach(module('demoApp'));

  var ClusterCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ClusterCtrl = $controller('ClusterCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
