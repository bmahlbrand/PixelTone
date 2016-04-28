function getStudpidDurationForNotes(duration) {
  if(duration == "Whole") return "w";
  if(duration == "Half") return "h";
  if(duration == "DottedHalf") return "hd";
  if(duration == "Quarter") return "q";
  if(duration == "DottedQuarter") return "qd";
  if(duration == "Eighth") return "8";
  if(duration == "DottedEighth") return "8d";
  if(duration == "Sixteenth") return "16";
  if(duration == "DottedSixteenth") return "16d";
  console.log(duration);
  return "You Fucked Up";
}

function getStaveNotesFromMeasures(measureGroup) {
  var staves = [];
  var measures = this.measures;
  for(var i=0; i<measureGroup.length; i++) {
    var temp = measureGroup[i].getStaveNotes();
    for(var j=0; j<temp.length; j++) {
      staves.push(temp[j]);
    }
    if(i != measureGroup.length-1)
      staves.push( new Vex.Flow.BarNote());
  }
  return staves;
}

var tone = function(other) {
  if(other != null){
    this.accidental = other.accidental;
    this.tone = other.tone;
  }else{
    this.accidental = "";
    this.tone = other.tone;
  }
  return this;
}

var note = function(other) {
  if(other != null){
    this.tone = new tone(other.tone);
    this.octave = other.octave;
    this.dynamic = other.dynamic;
    this.accent = other.accent;
    this.duration = other.duration;
  }else{
    this.tone = new tone();
    this.octave = 0;
    this.dynamic = "";
    this.accent = ""
  }
  return this;
}
note.prototype.getKey = function() {
  var key = this.tone.tone + "/" + this.octave.octave;
  return key;
}
note.prototype.getStaveNote = function() {
  var keys = [];
  keys.push(this.getKey());
  var duration = this.getDuration();
  var sn = {keys: keys, duration:duration};
  return new Vex.Flow.StaveNote(sn);
}
note.prototype.getDuration = function() {
  return getStudpidDurationForNotes(this.duration);
}

var rest = function(other) {
  if(other != null){
    this.duration = other.duration;
  }else{
    this.duration = "";
  }
  return this;
}
rest.prototype.getDuration = function() {
  var ret = getStudpidDurationForNotes(this.duration);
  return ret + "r";
}
rest.prototype.getStaveNote = function() {
  var duration = this.getDuration();
  var keys = ["b/4"];
  var sn = {keys: keys, duration:duration};
  return new Vex.Flow.StaveNote(sn);
}

var triad = function(other) {
  var trio = other.triad;
  if(other != null){
    this.duration = other.duration;
    this.degrees = trio.degrees;
    this.mode = trio.mode;
    this.notes = [];
    for(var i=0; i<trio.notes.length; i++) {
      var otherNote = trio.notes[i];
      this.notes.push(new note(otherNote));
    }
  }else{
    this.duration = "";
    this.degrees = [];
    this.notes = [];
    this.degrees.push("");
    this.degrees.push("");
    this.degrees.push("");
    this.notes.push(new note());
    this.notes.push(new note());
    this.notes.push(new note());
  }
  return this;
}
triad.prototype.getKeysArray = function() {
  var keys = [];
  for(var i=0; i<this.notes.length; i++) {
    var key = this.notes[i].getKey();
    keys.push(key);
  }
  return keys;
}
triad.prototype.getDuration = function() {
  return getStudpidDurationForNotes(this.duration);
}
triad.prototype.getStaveNote = function() {
  var keys = this.getKeysArray();
  var duration = this.getDuration();
  var sn = {keys: keys, duration:duration};
  return new Vex.Flow.StaveNote(sn);
}

function noteMaker(other) {
  if(other != null) {
    if(other.triad != null) {
      return new triad(other);
    }else if(other.tone != null){
      return new note(other);
    }else {
      return new rest(other);
    }
  }else{
    return new note();
  }
}

var beat = function(other) {
  if(other!= null) {
    this.notes = [];
    for(var i=0; i<other.notes.length; i++) {
      var otherNote = noteMaker(other.notes[i]);
      this.notes.push(otherNote);
    }
  }else{
    this.notes = [];
  }
  return this;
}
beat.prototype.getStaveNotes = function(){
  var staves = [];
  var notes = this.notes;
  for(var i =0; i<notes.length; i++) {
    var temp = notes[i].getStaveNote();
    staves.push(temp);
  }
  return staves;
}

var measure = function(other) {
  if(other != null) {
    this.beatCapacity = other.beatCapacity;
    this.beats = [];
    for(var i=0; i<other.beats.length; i++) {
      var otherBeat = new beat(other.beats[i]);
      this.beats.push(otherBeat);
    }
  }else{
    this.beatCapacity = "";
    this.beats = [];
  }
  return this;
}
measure.prototype.getStaveNotes = function(){
  var staves = [];
  var beats = this.beats;
  for(var i =0; i<beats.length; i++) {
    var temp = beats[i].getStaveNotes();
    for(var j=0; j<temp.length; j++) {
      staves.push(temp[j]);
    }
  }
  return staves;
}

var section = function(other) {
  if(other != null) {
    this.sectionType = other.sectionType;
    this.key = other.key;
    this.timeSig = other.timeSig;
    this.measures = [];
    for(var i=0; i<other.measures.length; i++) {
      var otherMeasure = other.measures[i];
      this.measures.push(new measure(otherMeasure));
    }
  }else{
    this.sectionType = "";
    this.key = "";
    this.timeSig = "";
    this.measures = [];
  }
  return this;
}
section.prototype.getStaveNotes = function(){
  var staves = [];
  var measures = this.measures;
  for(var i =0; i<measures.length; i++) {
    var temp = measures[i].getStaveNotes();
    for(var j=0; j<temp.length; j++) {
      staves.push(temp[j]);
    }
    staves.push( new Vex.Flow.BarNote());
  }
  return staves;
}

section.prototype.getMeasures = function(){
  return this.measures;
}

var voice = function(other) {
  if(other != null) {
    this.clef = other.clef;
    this.sections = [];
    for(var i=0; i<other.sections.length; i++) {
      var otherSection = other.sections[i];
      this.sections.push(new section(otherSection));
    }
  }else{
    this.clef = "";
    this.sections = [];
  }
  return this;
}
voice.prototype.getNumBeats = function() {
  return 4;
}
voice.prototype.getBeatValue = function() {
  return 4;
}
voice.prototype.getStaveNotes = function(){
  var staves = [];
  var sections = this.sections;
  for(var i =0; i<sections.length; i++) {
    var temp = sections[i].getStaveNotes();
    for(var j=0; j<temp.length; j++) {
      staves.push(temp[j]);
    }
  }
  return staves;
}
voice.prototype.getMeasures = function(){
  var measures = [];
  var sections = this.sections;
  for(var i =0; i<sections.length; i++) {
    var temp = sections[i].getMeasures();
    for(var j=0; j<temp.length; j++) {
      measures.push(temp[j]);
    }
  }
  return measures;
}
voice.prototype.getMeasuresInGroupsOf = function(n){
  var measures = this.getMeasures();
  var numGroups = measures.length/n;
  var measureGroups = [];
  for(var i=0; i<numGroups; i++) {
    var start = i*n;
    var end = (i+1)*n;
    measureGroups[i] = measures.slice(start, end);
  }
  return measureGroups;
}

voice.prototype.getVoiceInGroupsOf = function(n){
  var voices = [];
  var measureGroups = this.getMeasuresInGroupsOf(n);
  for(var i=0; i<measureGroups.length; i++) {
    var staveNotes = getStaveNotesFromMeasures(measureGroups[i]);
    var num_beats = this.getNumBeats();
    var beat_value = this.getBeatValue();
    var resolution = Vex.Flow.RESOLUTION;
    var voiceParams = {
      num_beats : num_beats,
      beat_value : beat_value,
      resolution : resolution
    };
    var ret = new Vex.Flow.Voice(voiceParams);
    ret.setMode(Vex.Flow.Voice.Mode.SOFT);
    ret.addTickables(staveNotes);
    voices[i] = ret;
  }
  return voices;
}

voice.prototype.getVoice = function() {
  var staveNotes = this.getStaveNotes();
  var num_beats = this.getNumBeats();
  var beat_value = this.getBeatValue();
  var resolution = Vex.Flow.RESOLUTION;
  var voiceParams = {
    num_beats : num_beats,
    beat_value : beat_value,
    resolution : resolution
  };
  var ret = new Vex.Flow.Voice(voiceParams);
  ret.setMode(Vex.Flow.Voice.Mode.SOFT);
  ret.addTickables(staveNotes);
  return ret;
}

var composition = function(other) {
  if(other != null) {
    this.metadata = other.metadata;
    this.tempo = other.tempo;
    this.voices = [];
    for(var i=0; i<other.voices.length; i++) {
      var otherVoice = other.voices[i];
      this.voices.push(new voice(otherVoice));
    }
  }else{
    this.metadata = "";
    this.tempo = "";
    this.voices = [];
  }
  return this;
}
composition.prototype.getVoices = function() {
  var voices = this.voices;
  var ret = [];
  for(var i=0; i<voices.length; i++) {
    ret.push(voices[i].getVoice());
  }
  return ret;
}
composition.prototype.getVoicesInGroupsOf = function(n) {
  var voices = this.voices;
  var allVoices = [];
  for(var i=0; i<voices.length; i++) {
    allVoices.push(voices[i].getVoiceInGroupsOf(n));
  }
  var ret = [];
  for(var i=0; i<allVoices.length; i++) {
    var voice = allVoices[i];
    for(var j=0; j<voice.length; j++) {
      if(ret[j] == null) ret[j] = [];
      ret[j].push(voice[j]);
    }
  }
  console.log(ret);
  return ret;
}
