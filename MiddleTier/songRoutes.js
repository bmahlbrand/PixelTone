var express = require('express');
var https = require('https');
var http = require('http');

var songRoutes = module.exports = express();

var song = function(name, author, image, path) {
  this.name = name;
  this.author = author;
  this.image = image;
  this.path = path;
  return this;
}

var songList = function(){
  this.songs = [];
  return this;
}
songList.prototype.addSong = function(song) {
  this.songs.push(song);
}
var note = function(keys, duration) {
  this.keys = keys;
  this.duration = duration;
  return this;
}
var measure = function() {
  this.notes = [];
  return this;
}
measure.prototype.addNote = function(note) {
  this.notes.push(note);
}

var composition = function(staff, timeSignature) {
  this.staff = staff;
  this.timeSignature = timeSignature;
  this.measures = [];
  return this;
}
composition.prototype.addMeasure = function(measure) {
  this.measures.push(measure);
}

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

songRoutes.get('/song', function(req, res) {
  var comp = new composition("1", "2");
  var meas = new measure();
  meas.addNote(new note(["a/4"], "q"));
  meas.addNote(new note(["b/4"], "q"));
  comp.addMeasure(meas);
  res.send(JSON.stringify(comp));
});
