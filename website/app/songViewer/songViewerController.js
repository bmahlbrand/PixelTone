angular.module("pixelTone")
    .controller('songViewerController', ['$scope', '$http', function ($scope, $http) {
        $scope.song = new composition();
        // get specific song
        $scope.displaySongOnCanvas = function() {
          var canvas = $("#vexflow-canvas")[0];
          var renderer = new Vex.Flow.Renderer(canvas,
          Vex.Flow.Renderer.Backends.CANVAS);
          var ctx = renderer.getContext();
          var stave = new Vex.Flow.Stave(10, 0, 500);
          stave.addClef("treble").setContext(ctx).draw();
          var voices = $scope.song.getVoices();
          console.log(voices);
          var formatter = new Vex.Flow.Formatter().
            joinVoices(voices).format(voices, 500);
          for(var i=0; i<voices.length; i++) {
            voices[i].draw(ctx, stave);
          }
        }

        $scope.fetchSong = function (song) {
          $http.get("/solo")
            .then(function(res) {
              comp = new composition(res.data);
              $scope.song = comp;
              $scope.displaySongOnCanvas();
            });
        };

        $scope.fetchSong();
    }]);
