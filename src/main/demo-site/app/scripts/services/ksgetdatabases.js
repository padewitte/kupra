'use strict';

/**
 * @ngdoc service
 * @name demoApp.ksGetDatabases
 * @description
 * # ksGetDatabases
 * Service in the demoApp.
 */
angular.module('demoApp')
  .service('ksGetDatabases', function ksGetDatabases($http,$q, $log, $rootScope) {
                                                      return {
                                                            /**
                                                            * List all database
                                                            */
                                                            getDatabases: function(){
                                                              var deferred =$q.defer();

                                                              $http.get($rootScope.kupraUrl+'/admin/?cmd={"listDatabases":1}').success(function(data){
                                                                deferred.resolve(data.databases);
                                                              });
                                                              return deferred.promise;
                                                            }



                                                        }
                                                      });
