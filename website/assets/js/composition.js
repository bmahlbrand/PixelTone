var note = function(other) {
  if(other != null){
    this.keys = other.keys;
    this.duration = other.duration;
  }else{
    this.keys = [];
    this.duration = "";
  }
  return this;
}

note.prototype.addKey = function(key) {
  this.keys.push(key);
}

note.prototype.getKeys = function() {
  return this.keys;
}

note.prototype.setDuration = function(dur) {
  this.duration = dur;
}

note.prototype.getDuration = function() {
  return this.duration;
}

var measure = function(other) {
  this.notes = [];
  if(other != null) {
    for(var i=0; i<other.notes.length; i++) {
      otherMeasure = other.notes[i];
      this.notes.push(new note(otherMeasure));
    }
  }
  return this;
}

var composition = function(other) {
  if(other != null) {
    this.staff = other.staff;
    this.timeSignature = other.timeSignature;
    this.measures = [];
    for(var i=0; i<other.measures.length; i++) {
      otherMeasure = other.measures[i];
      this.measures.push(new measure(otherMeasure));
    }
  }else{
    this.staff = "";
    this.timeSignature = "";
    this.measures = [];
  }
  return this;
}
