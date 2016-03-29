app.directive('recentSongs', function() { 
  return { 
    restrict: 'E', 
    scope: { 
      info: '=' 
    }, 
    templateUrl: 'js/directives/recentSongs.html' 
  }; 
});