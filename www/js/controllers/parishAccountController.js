gceApp.controller('parishAccountController', function($scope, $http) {

	$scope.events = [];
	$scope.churchId;

	$scope.init = function(churchId) {
		$scope.churchId = churchId;
		$scope.getEvents();
	}

	$scope.getEvents = function() {
		$http.get("events/json/" + $scope.churchId).success(function($data) {
			$scope.events = $data
		});
	}

});