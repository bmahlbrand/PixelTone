angular.module("pixelTone")
    .controller('songViewerController', ['$scope', '$http', function ($scope, $http) {
        $scope.song = new composition();
        $scope.isPlaying = false;
        // get specific song
        $scope.displaySongOnCanvas = function() {
          //delete the stupid canvas
          var canvasDiv = document.getElementById("vexflow-canvas-div");
          canvasDiv.innerHTML = "";

      //get voices
      var voices = $scope.song.getVoices();

      //find the width necessary for the voices
      var formatter = new Vex.Flow.Formatter();
      var width = formatter.preCalculateMinTotalWidth(voices) * 1.15;

      //create new canvas to actually fit this stuff
      var newCanvasHTML = "<canvas height='200' width='" + width + "'id='vexflow-canvas'></canvas>";
      canvasDiv.innerHTML = newCanvasHTML;
      var canvas = $("#vexflow-canvas")[0];

      //get renderer and context
      var renderer = new Vex.Flow.Renderer(canvas,
        Vex.Flow.Renderer.Backends.CANVAS);

      //make treble cleff and draw
      var ctx = renderer.getContext();
      var stave = new Vex.Flow.Stave(10, 0, width);
      stave.addClef("treble").setContext(ctx).draw();

      //draw notes
      formatter.joinVoices(voices).format(voices);
      for (var i = 1; i < voices.length && i < 2; i++) {
        voices[i].draw(ctx, stave);
        console.log(voices[i]);
      }
    };

    $scope.playSong = function (songurl) {
      MIDI.loadPlugin({
        soundfontUrl: "./assets/soundfont/",
        instrument: "acoustic_grand_piano",
        onprogress: function (state, progress) {
        },
        onsuccess: function () {
          player = MIDI.Player;
				      player.loadFile(songurl, function () {
            player.start();
            player.stop();
            player.start();
            player.resume();
          });
        }
      });
    };

    $scope.fetchSong = function (song) {

      $http.get(song + ".mid.NTS")
        .then(function (res) {
          comp = new composition(res.data);
          $scope.song = comp;
          $scope.displaySongOnCanvas();
          $scope.playSong(song + ".mid");
        });
    };
    var songId = AuthService.getSong();
    $scope.fetchSong(songId);
  }]);
