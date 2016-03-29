var Note = function() {
  this.keys = [];
  this.duration = "";
  return this;
}

var Measure = function() {
  this.notes = [];
  return this;
}

var Composition = function() {
  this.staff = "";
  this.timeSignature = "";
  this.measures = [];
  return this;
}
