'use strict';

/**
 * @ngdoc service
 * @name demoApp.ksGetCollections
 * @description
 * # ksGetCollections
 * Service in the demoApp.
 */
angular.module('demoApp')
  .service('ksGetCollections', function ksGetCollections($http,$q, $log, $rootScope) {
 return {
                                                            /**
                                                            * List all collections of a databse
                                                            */
                                                            getCollections: function(dbName){
                                                              var deferred =$q.defer()
                                                              $log.log(dbName);
                                                              $http.get($rootScope.kupraUrl+dbName+'/system.namespaces/')
                                                              .success(function(data){
                                                                $log.log("Collections Service");
                                                                $log.log(data);
                                                                var colsData = [];
                                                                for (var i =0 ; i < data.length; i++ ){
                                                                  var name = data[i].name.substring(dbName.length+1);

                                                                  if ( name.indexOf( "$" ) >= 0 && name.indexOf( ".oplog.$" ) < 0 )
                                                                    continue;
                                                                    data[i].name = name
                                                                    colsData.push(data[i]);
                                                                  }

                                                                  deferred.resolve(colsData);
                                                                });
                                                                return deferred.promise;
                                                              }
                                                            }
                                                          });
