(function(){

	var pixelTone = angular.module('pixelTone', ['ngRoute']);

	//Routes
	pixelTone.config(['$routeProvider', '$locationProvider', function($routeProvider,$locationProvider){
		$routeProvider

		//home page
		.when('/', {
			templateUrl: 'views/home/home.html',
			controller: 'homeController'
		})

		.when('/home', {
			templateUrl: 'views/home/home.html',
			controller: 'homeController'
		})

		// route for about
		.when('/about', {
			templateUrl: 'views/about/about.html',
			controller: 'aboutController'
		})

		//route for the login page
		.when('/login', {
			templateUrl: 'views/login/login.html',
			controller: 'loginController'
		});

		//.otherwise({redirectTo: '/'});

	  // use the HTML5 History API
	  $locationProvider.html5Mode(true);
	}]);

})();
