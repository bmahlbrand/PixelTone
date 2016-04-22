angular.module("pixelTone")
    .controller('homeController', ['$scope', '$location', 'AuthService',
        function ($scope, $location, AuthService) {
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

            /*$scope.addSong = function (author, img) {
                $scope.songsCreated.push({ 'icon': img, 'author': author });
            }*/
                 $scope.songsCreated = [];
        }]);