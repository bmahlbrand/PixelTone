(function(){

	var pixelTone = angular.module('pixelTone', ['ngRoute']);

	//Routes
	pixelTone.config(function($routeProvider){
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
	});


})();
