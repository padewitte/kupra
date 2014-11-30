'use strict';

/**
 * @ngdoc directive
 * @name demoApp.directive:kupraDb
 * @description
 * # kupraDb
 */
angular.module('demoApp')
  .directive('kupraDb', function($http) {
                          return {
                            restrict: 'E',
                            templateUrl: 'scripts/directives/kupradb.html',
                            link: function(scope, element, attrs) {
                              $http.get('http://localhost:8080/kupra/rest/db/col/?colstats')
                                .success(function(data){
                                    scope.message = data,
                                    console.log(data)
                                });
                            }
                          };
                        });
