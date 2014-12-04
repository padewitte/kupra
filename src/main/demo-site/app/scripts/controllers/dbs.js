'use strict';

/**
 * @ngdoc function
 * @name demoApp.controller:DbsCtrl
 * @description
 * # DbsCtrl
 * Controller of the demoApp
 */
angular.module('demoApp')
  .controller('DbsCtrl', function ($scope, ksGetColStats) {
    $scope.dbCtrl = {};

    $scope.charts = [];


    $scope.$watch('dbCtrl.selected.name', function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    var stats = ksGetColStats.getColStats(newValue);
                    stats.then(function(rawStats){
                        stats = rawStats;
                        console.log(stats);

                        if( $scope.charts.length == 0){
                            $scope.charts[0] = Morris.Donut({
                              element: 'count-chart',
                              data: stats[0]
                            });

                            $scope.charts[1] = Morris.Donut({
                                element: 'size-chart',
                                data: stats[1],
                                formatter : function (y, data) { return convertToBytes(y) }
                            });
                        }else{
                            $scope.charts[0].setData(stats[0]);
                            $scope.charts[1].setData(stats[1]);
                        }


                    })

                }
               })
  });
