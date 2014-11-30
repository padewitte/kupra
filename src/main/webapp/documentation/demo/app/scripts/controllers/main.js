'use strict';

/**
 * @ngdoc function
 * @name demoApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the demoApp
 */
angular.module('demoApp')
  .controller('MainCtrl', function ($scope, $http) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJSSSS',
      'Karma2'
    ];
    $http.get('http://localhost:8080/kupra/rest/db/col/?colstats').success(function(data){console.log(data)});



  });
