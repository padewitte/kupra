'use strict';

/**
 * @ngdoc service
 * @name demoApp.ksGetColStats
 * @description
 * # ksGetColStats
 * Service in the demoApp.
 */
angular.module('demoApp')
  .service('ksGetColStats', function ksGetColStats($http,$q, $log, $rootScope, ksGetCollections) {
    return {
        getColStats : function(db){
            var deferred = $q.defer();

            ksGetCollections.getCollections(db).then(function(collections) {

                var ret = []; ret.push([]); ret.push([])
                var arr = [];

                for (var i = 0; i < collections.length; ++i) {
                    console.log(db +" = "+collections[i].name)
                    arr.push($http.get($rootScope.kupraUrl+db+"/"+collections[i].name+'/?Colstats')
                        .success(function(data){
                            console.log(data);
                            ret[0].push({label : data.ns, value : data.count})
                            ret[1].push({label : data.ns, value : data.size})
                            }))
                }

                $q.all(arr).then(function (retDatas) {
                     console.log("Fin");
                     console.log(ret);
                     deferred.resolve(ret);
                });
            })
            return deferred.promise

        }


    }
  });
