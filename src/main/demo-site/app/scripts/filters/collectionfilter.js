'use strict';

/**
 * @ngdoc filter
 * @name demoApp.filter:collectionFilter
 * @function
 * @description
 * # collectionFilter
 * Filter in the demoApp.
 */
angular.module('demoApp')
  .filter('collectionFilter', function () {
    return function (input) {

      if (!( name.indexOf( "$" ) >= 0 && name.indexOf( ".oplog.$" ) < 0 )){
        return input
      }
    };
  });
