(function(){

	var pixelTone = angular.module('pixelTone', ['ngRoute', 'ngFileUpload']);

	//Routes
	pixelTone.config(function($routeProvider){
		$routeProvider

		//home page
		.when('/', {
			templateUrl: 'app/home/home.html',
			controller: 'homeController'
		})

		.when('/home', {
			templateUrl: 'app/home/home.html',
			controller: 'homeController'
		})

		// route for about
		.when('/about', {
			templateUrl: 'app/about/about.html',
			controller: 'aboutController'
		})

		//route for the login page
		.when('/login', {
			templateUrl: 'app/login/login.html',
			controller: 'loginController'
		})
        
        .when('/signup', {
			templateUrl: 'app/login/login.html',
			controller: 'loginController'
		})
        
        .when('/upload', {
			templateUrl: 'app/upload/upload.html',
			controller: 'uploadController'
		});
	});


})();