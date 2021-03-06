var mongoose = require('mongoose');

// define the schema for our image model
var imageSchema = mongoose.Schema({
    local: {
        key:            String,
        user:           String,
        uploadDate:     Date,
        songKey:        String,
        songPath:       String,
        numberOfFaces:  Number,
        topEmotion:     String,
        topColor:       String,
        chosenTempo:    String,
        chosenKey:      String,
        relativeMinor:  Boolean,
        url:            String,
        notePath:       String,
        CHname:           String
    }
});

// create the model for images and expose it to our app
module.exports = mongoose.model('Image', imageSchema);
