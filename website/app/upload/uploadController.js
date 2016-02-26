angular.module("pixelTone")
    .controller('uploadController', ['$scope', 'Upload', '$http', function ($scope, Upload, $http) {

        $scope.submit = function () {
            if ($scope.form.file.$valid && $scope.file) {
                $scope.upload($scope.file);
            }
        };

        // upload on file select
        $scope.upload = function (file) {

            Upload.upload({
                url: '/images/process',
                data: { image: file }
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