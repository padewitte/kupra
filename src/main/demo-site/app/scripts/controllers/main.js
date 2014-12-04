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

     $scope.selectedDb = {};
       $scope.dbs = [
         {name: 'test', size: '100 ko'},
         {name: 'mrc', size: '200 ko'},
         {name: 'foo', size: '300 Mo'}
       ];
  });
