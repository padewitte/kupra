'use strict';

/**
* @ngdoc directive
* @name demoApp.directive:kupraSelect
* @description
* # kupraSelect
*/
angular.module('demoApp')
.directive('kupraSelect', function ($http, ksGetDatabases, ksGetCollections) {
  return {
    templateUrl: 'views/directives/kupraselect.html',
    
    scope: {
      db: '=',
      col: '=?'
    },
    restrict: 'E',
    link: function postLink(scope, element, attrs) {
      
      ksGetDatabases.getDatabases().then(function(databases) {
        scope.dbs = databases;});
        
      if(scope.col){
          scope.$watch('db', function(newValue, oldValue) {
            if (newValue !== oldValue) {
              ksGetCollections.getCollections(scope.db.selected.name).then(function(collection) {
                scope.cols = collection;});
              }}, true);
            }
          }
        };
      });
      