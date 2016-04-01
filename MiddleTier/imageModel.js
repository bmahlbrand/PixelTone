var mongoose = require('mongoose');

// define the schema for our image model
var imageSchema = mongoose.Schema({
    local: {
<<<<<<< HEAD
        key: String,
        user: String,
        uploadDate: Date,
        songKey: String
=======
        key: 		String,
        user: 		String,
        uploadDate: Date,
        songKey: 	String,
        songPath:   String,
        url: 		String
>>>>>>> MusicAPI
    }
});

// create the model for images and expose it to our app
module.exports = mongoose.model('Image', imageSchema);