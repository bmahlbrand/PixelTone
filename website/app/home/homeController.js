angular.module("pixelTone")
    .controller('homeController', ['$scope', '$location', 'AuthService',
        function ($scope, $location, AuthService) {
            
            $scope.goToLogin = function(){
                $location.path('/login');
            }
            $scope.userName = null;
            $scope.userName = AuthService.getUserName();
            console.log($scope.userName);


            if(AuthService.isLoggedIn()){
                $('#logedInJumbo').show();
                $('#logoutJumbo').hide();
            }
            else{
                $('#logoutJumbo').show();
                $('#logedInJumbo').hide();
            }


            $scope.isDataBack = false;

            AuthService.getData()
                // handle success
                .then(function (data) {
                    $scope.recentSongs = AuthService.getUserData().imageData;
                    $scope.isDataBack = true;
                })
                // handle error
                .catch(function (data) {
                    console.log("ERROR");
                    console.log(data);
                    $scope.error = true;
                    $scope.isDataBack = false;
                });

            $scope.songsCreated = [];

            $scope.playSong = function (songKey) {
                AuthService.setSong(songKey);
                $location.path('/songViewer');

            }

            $scope.submit = function() {
                if ($scope.form.file.$valid && $scope.file) {
                    var param1 = $("#parameter1").val();
                    var param2 = $("#parameter2").val();
                    var param3 = $("#parameter3").val();
                    $scope.upload($scope.file, param1, param2, param3);
                }
            };

            // upload on file select
            $scope.upload = function (file, param1, param2, param3) {

                Upload.upload({
                    url: '/images/process',
                    data: { image: file, chaos: param2, voices: param3, pref: param1}
                }).then(function (resp) {
                    console.log('Success ');
                    $scope.name = "Sucessfully uploaded! Ready for Generation, Press Process to Continue";
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                }, function (evt) {
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            };
            
            // upload on file select
            $scope.process = function () {
                $http({
                    method: 'GET',
                    url: '/images/analyze'
                }).success(function () {
                    $scope.name = "Generation Request Success";
                }).error(function () {
                    $scope.name = "Generation Request Failure";
                });
            };
        }]);