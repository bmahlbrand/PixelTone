angular.module("pixelTone")
  .controller('homeController', ['$scope', function($scope) {
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
    this.newPic = 1;
}]);
