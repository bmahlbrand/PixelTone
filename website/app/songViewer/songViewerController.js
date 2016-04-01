angular.module("pixelTone")
    .controller('songViewerController', ['$scope', '$http', function ($scope, $http) {
        $scope.song = new composition();
        // get specific song
        $scope.displaySongOnCanvas = function() {
          //get canvas
          var vfCancas = $("#vexflow-canvas");
          var canvas = vfCancas[0];

          //get voices
          var voices = $scope.song.getVoices();

          //find the width necessary for the voices
          var formatter = new Vex.Flow.Formatter();
          var width = formatter.preCalculateMinTotalWidth(voices);

          //get renderer and context
          var renderer = new Vex.Flow.Renderer(canvas,
            Vex.Flow.Renderer.Backends.CANVAS);

          //make treble cleff and draw
          var ctx = renderer.getContext();
          var stave = new Vex.Flow.Stave(10, 0, width);
          stave.addClef("treble").setContext(ctx).draw();

          //draw notes
          formatter.joinVoices(voices).format(voices, 500);
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
