angular.module("pixelTone")
  .controller('profileController',
  ['$scope', '$location', 'AuthService',
  function ($scope, $location, AuthService) {
    


    console.log(AuthService.getUserName());

    $scope.name = AuthService.getUserName();



    $scope.profileUpLoadNewSong = function(){
      var fileUploadControl = $("#profilePhotoFileUpload")[0];
      var file = fileUploadControl.files[0];
      var name = file.name; //This does *NOT* need to be a unique name

      var output = URL.createObjectURL(file);


      var param1 = $("#profileParameter1").val();
      var param2 = $("#profileParameter2").val();
      var param3 = $("#profileParameter3").val();
      var songName = $("#profileNameOfSong").val();
      //alert("file is = "+output+" Song Name "+songName+" parameter1="+param1+" parameter2="+param2+" parameter3="+param3+"to server");

      $('#profileModalUpload').modal('hide');
      $scope.addSong(songName,output);

      

    }


    $scope.pastSongsMade = [];
    

    $scope.addSong = function(songName, img){
      $scope.profileSongsCreated.push({'icon' :img, 'nameOfSong' :songName});
    }

    $scope.profileSongsCreated = [];
    


}]);