angular.module("pixelTone")
    .controller('uploadController', ['$scope', 'Upload', '$http','facebookFactory', function ($scope, Upload, $http, facebookFactory) {
      $scope.selectedPhoto =null;
      $scope.facebookPhotos = [];
      $scope.getUsersPhotos = function(){
        facebookFactory.login().then(function(response){
          facebookFactory.getUsersPhotos().then(function(response){
            photos = response.data;
            $scope.facebookPhotos = photos;
          })
        })
      };

      $scope.selectFBPhoto = function(photo){
        console.log(photo);
        $scope.selectedPhoto = photo;
      }

      $scope.toggleFacebookModal = function(){
        $("#facebookModal").modal();
        $scope.getUsersPhotos();
      }
        $scope.submit = function() {
            if ($scope.form.file.$valid && $scope.file) {
                var param1 = $("#parameter1").val();
                var param2 = $("#parameter2").val();
                var param3 = $("#parameter3").val();
                var nameOfSong  = $("#nameOfSong").val();
                $scope.upload($scope.file, param1, param2, param3, nameOfSong, null);
            }else if($scope.selectedPhoto != null) {
                var param1 = $("#parameter1").val();
                var param2 = $("#parameter2").val();
                var param3 = $("#parameter3").val();
                var nameOfSong  = $("#nameOfSong").val();
                var fblink = $scope.facebookPhotos[$scope.selectedPhoto];
                $scope.upload(null, param1, param2, param3, nameOfSong, fblink);
            }
        };

        // upload on file select
        $scope.upload = function (file, param1, param2, param3, nameOfSong, fblink) {

            Upload.upload({
                url: '/images/process',
                data: { image: file, chaos: param2, voices: param3, pref: param1, name: nameOfSong, fblink: fblink}
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
