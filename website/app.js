(function(){

	var pixelTone = angular.module('pixelTone', ['ngRoute']);

	//Routes
	pixelTone.config(function($routeProvider){
		$routeProvider

		//home page
		.when('/', {
			templateUrl: 'pages/home.html',
			controller: 'homeController'
		})

		.when('/home', {
			templateUrl: 'pages/home.html',
			controller: 'homeController'
		})

		// route for about
		.when('/about', {
			templateUrl: 'pages/about.html',
			controller: 'aboutController'
		})

		//route for the login page
		.when('/login', {
			templateUrl: 'pages/login.html',
			controller: 'loginController'
		});
	});



	//controllers
	//home
	pixelTone.controller('homeController', ['$scope', function($scope) {
		$scope.recentSongs = [ 
	  { 
	    icon: 'img/test1.jpg', 
	    author: 'John' 
	  }, 
	  { 
	    icon: 'img/test2.jpg', 
	    author: 'Mike'
	  },
	  {
	    icon: 'img/test3.jpg',
	    author: 'Sarah'
	  },
	  {
	    icon: 'img/test4.jpg',
	    author: 'Jessica'
	  }
		];

		this.newPic = 1;

	}]);

	//about
	pixelTone.controller('aboutController', function($scope){
		$scope.message = "this is about!";
	});

	//login
	pixelTone.controller('loginController', ['$scope', function($scope){
		

		$scope.loginFunction = function(){
		  var password = $("#loginPassword").val(); 
		  var usernameInput = $("#loginUsername").val();  
		  alert("Send "+usernameInput+" and "+password+" to server");
		}

		$scope.signUpFunction = function(){

		  var emailInput = $("#signUpEmail").val(); 
		  var usernameInput = $("#signUpUsername").val(); 
		  var password = $("#signUpPassword").val(); 
		  alert("Send "+usernameInput+" and "+password+" and "+emailInput+" to server");
		}


	}]);





})();