'use strict';

describe('Controller: CollectionsCtrl', function () {

  // load the controller's module
  beforeEach(module('demoApp'));

  var CollectionsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CollectionsCtrl = $controller('CollectionsCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
