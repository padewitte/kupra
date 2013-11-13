var mrcApp = angular.module('MrcApp', []);
mrcApp.controller('MrcCtrl', function MrcCtrl($scope, $http) {

	$scope.step1_postData = "MyData";

	var runFunction = function() {
		this.state = 'RUNNING';
		if ($http) {
			$http({
				method : 'GET',
				url : 'test.json'
			}).success(function(data, status, headers, config) {
				this.state = 'OK';
				this.result = JSON.stringify(data);
			}).error(function(data, status, headers, config) {
				this.state = 'KO';
				this.result = JSON.stringify(data);
			});
		} else {
			alert('SHIT');
		}
	}

	$scope.test1_postData = {
		name : 'Insert a new document in MongoDB database',
		request : 'POST ...',
		state : 'NOTRUN',
		result : 'POST ... result',
		run : runFunction
	};
	$scope.test2_postData = {
		name : 'Insert again a new document in MongoDB database',
		request : 'POST ...',
		state : 'NOTRUN',
		result : '',
		run : runFunction
	};

	$scope.igor = {
		name : 'Igor',
		address : '123 Somewhere'
	};

}).directive('myDirective', function() {
	return {
		restrict : 'EA',
		scope : {
			testname : '=testname'

		},
		templateUrl : 'my-directive.html'
	};
});