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
          var vexNotes = [];
          var measures = $scope.song.measures;
          for(var i=0; i<measures.length; i++) {
            var measureNotes = measures[i].notes;
            for(var j=0; j<measureNotes.length; j++){
              var note = measureNotes[j];
              vexNotes.push( new Vex.Flow.StaveNote({ keys: note.keys, duration: note.duration }));
            }
            vexNotes.push( new Vex.Flow.BarNote());
          }
          Vex.Flow.Formatter.FormatAndDraw(ctx, stave, vexNotes);
        }

        $scope.fetchSong = function (song) {
          $http.get("/songs/song")
            .then(function(res) {
              $scope.song = new composition(res.data);
              $scope.displaySongOnCanvas();
            });
        };

        $scope.fetchSong();
    }]);
