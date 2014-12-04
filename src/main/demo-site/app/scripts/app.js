'use strict';

/**
 * @ngdoc overview
 * @name demoApp
 * @description
 * # demoApp
 *
 * Main module of the application.
 */
angular
  .module('demoApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.select'
  ])
  .config(function ($routeProvider) {

    $routeProvider
      .when('/', {
        templateUrl: 'views/dbs.html',
        controller: 'DbsCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/cluster', {
        templateUrl: 'views/cluster.html',
        controller: 'ClusterCtrl'
      })
      .when('/dbs', {
        templateUrl: 'views/dbs.html',
        controller: 'DbsCtrl'
      })
      .when('/collections', {
        templateUrl: 'views/collections.html',
        controller: 'CollectionsCtrl'
      })
      .when('/graph', {
        templateUrl: 'views/graph.html',
        controller: 'GraphCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  }).run(function($rootScope) {
  //DEV
  //$rootScope.kupraUrl = 'http://localhost:8080/kupra/rest/';
  $rootScope.kupraUrl = '/kupra/rest/';
});