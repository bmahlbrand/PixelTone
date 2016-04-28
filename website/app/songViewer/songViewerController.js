angular.module("pixelTone")
.controller('songViewerController', ['$scope', '$http', 'AuthService', function ($scope, $http, AuthService) {
  $scope.song = new composition();
  // get specific song

  $scope.displaySongOnCanvas = function () {
    //delete the stupid canvas
    var canvasDiv = document.getElementById("vexflow-canvas-div");
    canvasDiv.innerHTML = "";

    //get voices
    var voices = $scope.song.getVoices();
    var measures = $scope.song.voices[0].getMeasures();
    var measureGroups = $scope.song.voices[0].getMeasuresInGroupsOf(4);
    var voices = $scope.song.voices[0].getVoiceInGroupsOf(4);
    console.log(measures);
    console.log(measureGroups);
    console.log(voices);

    //find the width necessary for the voices

    //create new canvas to actually fit this stuff
    var height = 250 * measureGroups.length;
    var width = 600;
    var newCanvasHTML = "<canvas height='"+ height + "' width='" + width + "'id='vexflow-canvas'></canvas>";
    canvasDiv.innerHTML = newCanvasHTML;
    var canvas = $("#vexflow-canvas")[0];

    //get renderer and context
    var renderer = new Vex.Flow.Renderer(canvas,
    Vex.Flow.Renderer.Backends.CANVAS);

    // for every voice group
    for(var i=0; i<voices.length; i++) {
      //make treble cleff and draw
      var ctx = renderer.getContext();
      var stave = new Vex.Flow.Stave(10, i*250, width);
      stave.addClef("treble").setContext(ctx).draw();

      var stave = new Vex.Flow.Stave(10, i*250 + 100, width);
      stave.addClef("bass").setContext(ctx).draw();

      var voice = voices[i]
      var formatter = new Vex.Flow.Formatter().
      joinVoices([voice]).format([voice], width-50);

      // Render voice
      voice.draw(ctx, stave);
    }



    //draw notes
    //formatter.joinVoices(voices).format(voices);
    //for (var i = 1; i < voices.length && i < 2; i++) {
    //  voices[i].draw(ctx, stave);
    //  console.log(voices[i]);
    //}
    };

    $scope.loadSong = function(songurl) {
      MIDI.loadPlugin({
        soundfontUrl: "./assets/soundfont/",
        instrument: "acoustic_grand_piano",
        onprogress: function(state, progress) {
        },
        onsuccess: function() {
          player = MIDI.Player;
          player.loadFile(songurl, function(){
            player.start();
            player.stop();
          });
		      var musicbarinner = document.getElementById("music-bar-inner");
		      var musicbarouter = document.getElementById("music-bar-outer");
          var timeleft = document.getElementById("time-left");
          var timeplayed = document.getElementById("time-played");
          player.setAnimation(function(data, element) {

                  var timeFormatting = function(n) {
              			var minutes = n / 60 >> 0;
              			var seconds = String(n - (minutes * 60) >> 0);
              			if (seconds.length == 1) seconds = "0" + seconds;
              			return minutes + ":" + seconds;
              		}

          			var percent = data.now / data.end;
          			var now = data.now >> 0; // where we are now
          			var end = data.end >> 0; // end of song
          			// display the information to the user
          			musicbarinner.style.width = (percent * 100) + "%";
                timeplayed.innerHTML = timeFormatting(now);
                timeleft.innerHTML = "-" + timeFormatting(end - now);
          		});
        }
      });
    };

    $scope.pausePlayButton = function() {
      if(MIDI.Player.playing) {
        MIDI.Player.pause(true);
        $("#pausePlayIcon").addClass('glyphicon-play').removeClass('glyphicon-pause');
      }else{
        player.resume();
        $("#pausePlayIcon").addClass('glyphicon-pause').removeClass('glyphicon-play');
      }
    }

    $scope.stopButton = function() {
      MIDI.Player.stop();
      $("#pausePlayIcon").addClass('glyphicon-play').removeClass('glyphicon-pause');
    }



    $scope.fetchSong = function (song) {
      $http.get("/songs/notes/"+song+".mid.NTS")
      .then(function(res) {
        console.log(res.data);
        comp = new composition(res.data);
        $scope.song = comp;
        $scope.displaySongOnCanvas();
        $scope.loadSong("/songs/song/"+song+".mid");
      });
    };

    var songId = AuthService.getSong();
    $scope.fetchSong("testmidi");
  }]);
