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

songRoutes.get('/song/:songfile', function(req, res) {
  var songfile = req.params.songfile;
  console.log(songfile);
  var route = {
      host: 'localhost',
      path: '/songs/'+songfile,
      port: 3001,
      method: 'GET'
  };

  var request = http.request(route, function(response) {
    response.pause();
    res.writeHeader(response.statusCode, response.headers);
    response.pipe(res);
    response.resume();
  });
  req.pipe(request);
  req.resume();
});

songRoutes.get('/notes/:notesfile', function(req, res) {
  var notesfile = req.params.notesfile;
  console.log(notesfile);
  var route = {
      host: 'localhost',
      path: '/notes/'+notesfile,
      port: 3001,
      method: 'GET'
  };

  var request = http.request(route, function(response) {
    response.pause();
    res.writeHeader(response.statusCode, response.headers);
    response.pipe(res);
    response.resume();
  });
  req.pipe(request);
  req.resume();
});
