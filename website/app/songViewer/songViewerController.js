angular.module("pixelTone")
.controller('songViewerController', ['$scope', '$http', 'AuthService', function ($scope, $http, AuthService) {
  $scope.song = new composition();
  // get specific song

  $scope.refreshCheckboxes = function(){
    $scope.checks = [];
    var numVoices = $scope.song.voices.length;
    for(var i=0; i<numVoices; i++) {
      $scope.checks[i] = {
        voice: i,
        enabled: true
      };
    }
  }

  $scope.displaySongOnCanvas = function () {
    //console.log($scope.song);
    //delete the stupid canvas
    var canvasDiv = document.getElementById("vexflow-canvas-div");
    canvasDiv.innerHTML = "";

    //get voices
    var voices = $scope.song.getVoicesInGroupsOf(4);

    //create new canvas to actually fit this stuff
    var height = 250 * voices.length;
    var width = 600;
    var newCanvasHTML = "<canvas height='"+ height + "' width='" + width + "'id='vexflow-canvas'></canvas>";
    canvasDiv.innerHTML = newCanvasHTML;
    var canvas = $("#vexflow-canvas")[0];

    //get renderer and context
    var renderer = new Vex.Flow.Renderer(canvas,
    Vex.Flow.Renderer.Backends.CANVAS);

    //find the width necessary for the voices
    for(var i=0; i<voices.length; i++) {
      //make treble cleff and draw
      var ctx = renderer.getContext();
      var stave = new Vex.Flow.Stave(10, i*250, width);
      stave.addClef("treble").setContext(ctx).draw();
      stave.setTempo($scope.song.tempo);

      //var stave = new Vex.Flow.Stave(10, i*250 + 100, width);
      //stave.addClef("bass").setContext(ctx).draw();


      var formatter = new Vex.Flow.Formatter();
      formatter.joinVoices(voices[i]).format(voices[i], width-50);
      for(var j=0; j<voices[i].length; j++) {
        if($scope.checks[j].enabled){
          voices[i][j].draw(ctx, stave);
        }
      }
    }


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
      //$http.get("/songs/notes/"+song+".mid.NTS")
      $http.get(song + ".mid.NTS")
      .then(function(res) {
        comp = new composition(res.data);
        $scope.song = comp;
        $scope.refreshCheckboxes();
        $scope.displaySongOnCanvas();
        //$scope.loadSong("/songs/song/"+song+".mid");
        $scope.loadSong(song + ".mid");
      });
    };

    var songId = AuthService.getSong();
    //var songFname = songId.substr(songId.lastIndexOf("/")+1);
    //console.log(songFname);
    //$scope.fetchSong("testmidi");
    $scope.fetchSong(songId);
  }]);
