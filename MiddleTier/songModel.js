var mongoose = require('mongoose');

// define the schema for our image model
var songSchema = mongoose.Schema({
    local: {
        key: String,
        url: String,
		ntsurl: String
    }
});

// create the model for images and expose it to our app
module.exports = mongoose.model('Song', songSchema);
