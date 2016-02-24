angular.module("pixelTone")
  .controller('loginController', ['$scope', function($scope){
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
