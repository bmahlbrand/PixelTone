angular.module("pixelTone")
  .controller('homeController', ['$scope', function($scope) {
    $scope.recentSongs = [
      {
        icon: 'img/test1.jpg',
        author: 'John'
      },
      {
        icon: 'img/test2.jpg',
        author: 'Mike'
      },
      {
        icon: 'img/test3.jpg',
        author: 'Sarah'
      },
      {
        icon: 'img/test4.jpg',
        author: 'Jessica'
      }
    ];
    this.newPic = 1;
}]);
