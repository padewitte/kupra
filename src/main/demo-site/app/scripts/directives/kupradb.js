'use strict';

/**
 * @ngdoc directive
 * @name demoApp.directive:kupraDb
 * @description
 * # kupraDb
 */
angular.module('demoApp')
    .directive('kupraDb', function($http,$rootScope) {
                          return {
                            restrict: 'E',
                            scope: {
                                  db: '='
                                },
                            templateUrl: 'views/directives/kupradb.html',
                            link: function(scope, element, attrs) {
                                scope.$watch('db', function(newValue, oldValue) {
                                    if (newValue !== oldValue) {
                                        // You actions here
                                        $http.get($rootScope.kupraUrl+scope.db)
                                            .success(function(data){
                                                scope.dbData = data,
                                                console.log(data)
                                            });
                                        }
                                    }, true);
                            }
                          };
                        })
  .directive('kupraCol', function($http, $rootScope) {
                          return {
                            scope: {
                                                              db: '=',
                                                              col: '='
                                                            },
                            restrict: 'E',
                            templateUrl: 'views/directives/kupradb.html',
                            link: function(scope, element, attrs, basketCtrl) {
                                scope.$watch('col', function(newValue, oldValue) {

                                    if (newValue !== oldValue) {
                                        console.log("Find : "+newValue)
                                        $http.get($rootScope.kupraUrl+scope.db.selected.name+'/'+scope.col.selected.name+'/?Colstats')
                                            .success(function(data){
                                                scope.dbData = data,
                                                console.log(data)
                                            });
                                        }
                                    }, true);
                            }
                          };
                        });