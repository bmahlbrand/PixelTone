angular.module("pixelTone")
  .controller('homeController', ['$scope', function($scope) {
    
    $scope.upLoadNewSong = function(){
      var fileUploadControl = $("#profilePhotoFileUpload")[0];
      var file = fileUploadControl.files[0];
      var name = file.name; //This does *NOT* need to be a unique name

      var output = URL.createObjectURL(file);


      var param1 = $("#parameter1").val();
      var param2 = $("#parameter2").val();
      var param3 = $("#parameter3").val();
      var auth = $("#author").val();
      var songName = $("#nameOfSong").val();
      alert("file is = "+output+" Song Name "+songName+" Author"+auth+" parameter1="+param1+" parameter2="+param2+" parameter3="+param3+"to server");

      $('#myModal').modal('hide');
      $scope.addSong(auth,output);

      

    }


    $scope.recentSongs = [
      {
        icon: 'assets/img/test1.jpg',
        author: 'John'
      },
      {
        icon: 'assets/img/test2.jpg',
        author: 'Mike'
      },
      {
        icon: 'assets/img/test3.jpg',
        author: 'Sarah'
      },
      {
        icon: 'assets/img/test4.jpg',
        author: 'Jessica'
      }
    ];
    

    $scope.addSong = function(author, img){
      $scope.songsCreated.push({'icon' :img, 'author' :author});
    }

    $scope.songsCreated = [];
    


}]);
