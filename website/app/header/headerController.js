angular.module('pixelTone').controller('headerController',
  ['$scope', '$location', 'AuthService',
  function ($scope, $location, AuthService) {

    $scope.showLogout = !AuthService.isLoggedIn();
    $scope.showLogin = AuthService.isLoggedIn();
    // call login from service
  
    
     


  //console.log(AuthService.isLoggedIn());

}]);