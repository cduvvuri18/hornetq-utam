app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/unit_testing', {
		controller : 'unitTestingController',
		templateUrl : 'unit-testing.html'
	}).otherwise({
		redirectTo : '/unit_testing'
	});
} ]);