var mrcApp = angular.module('MrcApp', []);
mrcApp.controller('MrcCtrl', function MrcCtrl($scope, $http) {


	var runFunction = function() {
		var testInProgress = this;
		testInProgress.state = 'RUNNING';

		$http({
				method :  testInProgress.request.method,
				url : testInProgress.request.url,
				data : testInProgress.request.content
			}).success(function(data, status, headers, config) {
				testInProgress.state = 'OK';
				testInProgress.result =null;
				testInProgress.result = {method : config.method, url: '', code: 'Response code : ' + status, content : data, color : 'success' };
				if(headers().resulttotalsize){
					testInProgress.result.headers = [ "resulttotalsize : "+headers().resulttotalsize,"resultpagesize : "+headers().resultpagesize];
				}
				if(headers().recordsaffected){
					testInProgress.result.headers = ["RecordsAffected : "+headers().recordsaffected];
				}
				
			}).error(function(data, status, headers, config) {
				testInProgress.state = 'KO';
				testInProgress.result =null;
				testInProgress.result = {method : config.method, url: '', code: 'Response code : ' + status, content : data, color : 'danger' };
			});
	};
	
	var test1_postData = {
			name : 'Delete all items in collection',
			request : {method : 'DELETE', url : '/mrc/test/1'},
			state : 'NOTRUN',
			//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
			result : {},
			run : runFunction
	};
	var test2_postData = {
		name : 'Insert again a new document in MongoDB database',
		request : {method : 'POST', url : '/mrc/test',content : "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}", headers : []},
		state : 'NOTRUN',
		//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
		result : {},
		run : runFunction
	};
	var countItems = {
			name : 'count items in colection test',
			request : {method : 'GET', url : '/mrc/test', headers : [ {"count" : true}]},
			state : 'NOTRUN',
			//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
			result : {},
			run : runFunction
		};
	$scope.testsToRun = [test1_postData,test2_postData,countItems];
});
