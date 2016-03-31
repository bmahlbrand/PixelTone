
//var Firebase = require("firebase");
var pixelTone = angular.module('pixelTone', ['ngRoute', 'ngFileUpload']);
//Routes
pixelTone.config(['$routeProvider', '$locationProvider', function($routeProvider,$locationProvider){
	$routeProvider
	//home page
	.when('/', {
		templateUrl: 'app/home/home.html',
		controller: 'homeController',
		access: {restricted: false}
	})
	.when('/profile', {
		templateUrl: 'app/profile/profile.html',
		controller: 'profileController',
		access: {restricted: true}
	})
	// route for about
	.when('/about', {
		templateUrl: 'app/about/about.html',
		controller: 'aboutController',
		access: {restricted: false}
	})
	//route for the login page
	.when('/login', {
		templateUrl: 'app/login/login.html',
		controller: 'loginController',
		access: {restricted: false}
	})
	//route for the register page
	.when('/register', {
		templateUrl: 'app/register/register.html',
		controller: 'registerController',
		access: {restricted: false}
	})
	//route for the logout page
	.when('/logout', {
		controller: 'logoutController',
		access: {restricted: true}
	})


	.when('/upload', {
			templateUrl: 'app/upload/upload.html',
<<<<<<< HEAD
			controller: 'uploadController'
		})

        .when('/songViewer', {
			templateUrl: 'app/songViewer/songViewer.html',
			controller: 'songViewerController'
		});
=======
			controller: 'uploadController',
			access: {restricted: false}
	})
>>>>>>> origin/MusicAPI

	.otherwise({
      redirectTo: '/'
    });

  // use the HTML5 History API
  $locationProvider.html5Mode(true);
}]);

pixelTone.run(function ($rootScope, $location, $route, AuthService) {
  $rootScope.$on('$routeChangeStart',
    function (event, next, current) {
      AuthService.getUserStatus();
      if (next.access.restricted &&
          !AuthService.isLoggedIn()) {
        $location.path('/login');
        $route.reload();
      }
  });
});