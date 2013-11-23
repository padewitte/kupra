var mrcApp = angular.module('MrcApp', []);
mrcApp.controller('MrcCtrl', function MrcCtrl($scope, $http) {
	//Run a test
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
				testInProgress.result = {method : config.method, url: '', code: 'Response code : ' + status, content : data, color : 'success', contentColor : 'warning' };
				if(headers().resulttotalsize){
					testInProgress.result.headers = [ "resulttotalsize : "+headers().resulttotalsize,"resultpagesize : "+headers().resultpagesize];
				}
				if(headers().recordsaffected){
					testInProgress.result.headers = ["RecordsAffected : "+headers().recordsaffected];
				}
				
			}).error(function(data, status, headers, config) {
				testInProgress.state = 'KO';
				testInProgress.result =null;
				testInProgress.result = {method : config.method, url: '', code: 'Response code : ' + status, content : data, color : 'danger', contentColor : 'warning' };
			});
	};
	
	var test_postFirstData = {
			name : 'Delete all items in collection',
			request : {method : 'DELETE', url : '/mrc/test/1'},
			state : 'NOTRUN',
			//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
			result : {},
			run : runFunction
	};
	var test_postSecondData = {
		name : 'Insert again a new document in MongoDB database',
		request : {method : 'POST', url : '/mrc/test',content : "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}", headers : []},
		state : 'NOTRUN',
		//result : {method : '', url: '', code: '', headers : '', content : '', color : 'success' },
		result : {},
		run : runFunction
	};
	
	var test_showTest = {
			name : 'Show the content of test collection',
			request : {method : 'GET', url : '/mrc/test'},
			state : 'NOTRUN',
			run : runFunction
	};
	
	var test_showTest_byId = {
			name : 'Show the document with id 2 content of test collection',
			request : {method : 'GET', url : '/mrc/test/2'},
			state : 'NOTRUN',
			run : runFunction
	};
	
	var test_count = {
			name : 'Count items in test collection',
			request : {method : 'GET', url : '/mrc/test', headers : ['count' : true]},
			state : 'NOTRUN',
			run : runFunction
	};
	
	var test_Query = {
			name : 'Filter items with name equals to Sylvain CHAVANEL',
			request : {method : 'GET', url : '/mrc/test', headers : ['query' : {'name' : 'Sylvain CHAVANEL'}]},
			state : 'NOTRUN',
			run : runFunction
	}
	
	var test_UpdateById = {
			name : 'Perform an update replacing object with id 1',
			request : {method : 'PUT', content : '{"_id" : "1", "name" : "Thibaut PINOT"}' , url : '/mrc/test/1'},
			state : 'NOTRUN',
			run : runFunction
	};
	
	var test_DeleteAllItems = {
			name : 'DELETE all items in test collection',
			request : {method : 'DELETE', url : '/mrc/test/1',headers : ['query' : {'name' : {$exists : true }}]},
			state : 'NOTRUN',
			run : runFunction
			
	}
	
	$scope.testsToRun = [test_postFirstData,test_postSecondData,test_showTest, test_showTest_byId, test_count, test_Query, test_UpdateById, test_DeleteAllItems];
});
