angular.module("pixelTone")
.factory('facebookFactory', function($q) {
    return {
      login : function() {
        var deferred = $q.defer();
        FB.login(function(response) {
          if (!response || response.error) {
              deferred.reject('Error occured');
          } else {
              deferred.resolve(response);
          }
        }, {scope:'user_photos'});
        return deferred.promise;
      },
      getPhoto: function(photo_id) {
          var deferred = $q.defer();
          FB.api('/'+photo_id, {
            fields : ['link', 'id', 'picture']
          },
            function(response) {
              if (!response || response.error) {
                  deferred.reject('Error occured');
              } else {
                  deferred.resolve(response);
              }
          });
          return deferred.promise;
      },
      getUsersPhotos: function() {
          var deferred = $q.defer();
          FB.api('/me/photos', {
            fields : ['link', 'id', 'picture']
          },
            function(response) {
              if (!response || response.error) {
                  deferred.reject('Error occured');
              } else {
                  deferred.resolve(response);
              }
          });
          return deferred.promise;
      },
      getUserId: function() {
          var deferred = $q.defer();
          FB.api('/me',
            function(response) {
              if (!response || response.error) {
                  deferred.reject('Error occured');
              } else {
                  deferred.resolve(response);
              }
          });
          return deferred.promise;
      }
    }
});
