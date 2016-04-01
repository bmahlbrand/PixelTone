var express = require('express');
var https = require('https');
var http = require('http');
var gs = require('./getSongs');

var songRoutes = module.exports = express();



songRoutes.get('/recent', function (req, res) {
  var sl = new songList();
  sl.addSong(new song("a", "a", "a", "a"));
  sl.addSong(new song("b", "b", "b", "b"));
  res.setHeader('Content-Type', 'application/json');
  res.send(JSON.stringify(sl));
});

songRoutes.get('/user', function (req, res) {
  var sl = new songList();
  sl.addSong(new song("c", "c", "c", "c"));
  sl.addSong(new song("d", "d", "d", "d"));
  res.setHeader('Content-Type', 'application/json');
  res.send(JSON.stringify(sl));
});

songRoutes.get('/:songfile', function(req, res) {
  var songfile = req.params.songfile;
  console.log(songfile);
  var route = {
      host: 'localhost',
      path: '/songs/'+songfile,
      port: 3001,
      method: 'GET'
  };

  var req = http.request(route, function(response) {
      var str = ''
      response.on('data', function(chunk) {
          str += chunk;
      });

      response.on('end', function() {
          console.log("Response from BackEnd:");
          //INSERT CODE TO HANDLE RESPONSE (Will be a song??)
          console.log(str);
          res.send(str);
      });

      response.on('error', function(err) {
          console.log(err);
      });

  });
  req.write(songfile);
});
