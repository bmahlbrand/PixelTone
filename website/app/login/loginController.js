angular.module("pixelTone")
    .controller('loginController', ['$scope', '$http', '$httpParamSerializerJQLike', function ($scope, $http, $httpParamSerializerJQLike) {

        $scope.user = {};
        $scope.cuser = {};

        $scope.loginFunction = function () {

            $http({
                method: 'POST',
                url: '/login',
                data: $httpParamSerializerJQLike({ 'email': $scope.user.email, 'password': $scope.user.password }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).success(function () {
                alert("Login Success");
            }).error(function () {
                alert("Login Failure");
            });
        }

        $scope.signUpFunction = function () {

            $http({
                method: 'POST',
                url: '/signup',
                data: $httpParamSerializerJQLike({ 'email': $scope.cuser.email, 'password': $scope.cuser.password }),
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).success(function () {
                alert("Create User Success");
            }).error(function () {
                alert("Create User Failure");
            });
        }
    }]);