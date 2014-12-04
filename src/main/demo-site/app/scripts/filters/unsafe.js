'use strict';

/**
 * @ngdoc filter
 * @name demoApp.filter:unsafe
 * @function
 * @description
 * # unsafe
 * Filter in the demoApp.
 */
angular.module('demoApp')
  .filter('unsafe', function () {
    return function (input) {
        return convertToBytes(input);
    };
  });

function convertToBytes(input){
   if((input / 1024) < 1){
           return ""+input + " Bytes";
         }
         input = input / 1024
         if((input / 1024) < 1){
           return ""+Math.round(input) + " KB";
         }
         input = input / 1024
         if((input / 1024) < 1){
           return ""+Math.round(input) + " MB";
         }
         input = input / 1024
         if((input / 1024) < 1){
           return ""+Math.round(input) + " GB";
         }
         input = input / 1024
         if((input / 1024) < 1){
           return ""+Math.round(input) + " TB";
         }

         input = input / 1024
                  if((input / 1024) < 1){
                    return ""+Math.round(input) + " PB";
                  }

         return ""+input;
}