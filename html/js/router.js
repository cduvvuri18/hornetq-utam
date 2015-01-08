app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/configure', {
		controller : 'configurationController',
		templateUrl : 'configuration.html'
	}).when('/unit_testing', {
		controller : 'unitTestingController',
		templateUrl : 'unit-testing.html'
/*	}).when('/monitor', {
		controller : 'monitoringController',
		templateUrl : 'monitoring.html'
	}).when('/administer', {
		controller : 'administrationController',
		templateUrl : 'administration.html'
*/	}).when('/home', {
		controller : 'homeController',
		templateUrl : 'home.html'
	}).otherwise({
		redirectTo : '/unit_testing'
	});
} ]);