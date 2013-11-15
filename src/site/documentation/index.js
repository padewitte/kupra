var mrcApp = angular.module('MrcApp', []);
mrcApp.controller('MrcCtrl', function MrcCtrl($scope, $http) {

	$scope.step1_postData = "MyData";

	var runFunction = function() {
		var testInProgress = this;
		testInProgress.state = 'RUNNING';
		
		$http({
				method : 'GET',
				url : '/mrc/test'
			}).success(function(data, status, headers, config) {
				testInProgress.state = 'OK';
				testInProgress.result = {method : config.method, url: '', code: status, content : data, color : 'success' };
				if(headers().resulttotalsize){
					testInProgress.result.headers = ["resulttotalsize : "+headers().resulttotalsize,"resultpagesize : "+headers().resultpagesize];
				}
			}).error(function(data, status, headers, config) {
				testInProgress.state = 'KO';
				testInProgress.result = JSON.stringify(data);
			});
	};
	
	$scope.test1_postData = {
		name : 'Insert a new document in MongoDB database',
		request : 'POST ...',
		state : 'NOTRUN',
		result : 'POST ... result',
		run : runFunction,
		classRes : "success"
	};
	$scope.test2_postData = {
		name : 'Insert again a new document in MongoDB database',
		request : {method : 'POST ', url : ''},
		state : 'NOTRUN',
		result : {},
		//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
		run : runFunction
	};
});

mrcApp.directive('myDirective', function() {
	
	return {
		restrict : 'EA',
		scope : {
			testname : '=testname'

		},
		templateUrl : 'my-directive.html'
	};
	

});