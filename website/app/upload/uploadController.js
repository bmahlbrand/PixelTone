angular.module("pixelTone")
    .controller('uploadController', ['$scope', 'Upload', '$http', function ($scope, Upload, $http) {

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
                $scope.name = "Sucessfully uploaded:" + file.name;
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