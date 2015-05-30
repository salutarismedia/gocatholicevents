gceApp.controller('parishAccountController', function($scope) {

	$scope.events = [];

	function init() {
		getEvents();
	}

	function getEvents() {
		$.get("church/events").done(function($data) {
			$scope.events = $data
		}).fail(function() {
			console.log("Could not retrieve events");
		})
	}

	init();

});