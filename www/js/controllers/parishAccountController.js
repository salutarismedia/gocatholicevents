gceApp.controller('parishAccountController', function($scope) {

	$scope.events = [];
	$scope.churchId;

	$scope.init = function(churchId) {
		$scope.churchId = churchId;
		$scope.getEvents();
	}

	$scope.getEvents = function() {
		$.get("events/json/" + $scope.churchId).done(function($data) {
			$scope.events = $data
		}).fail(function() {
			console.log("Could not retrieve events");
		})
	}

});